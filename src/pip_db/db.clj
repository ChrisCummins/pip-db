;; # Database Interface
;;
;; This namespace defines the interface and API for the database
;; back-end of pip-db.
(ns pip-db.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [clojure.set :as set]
            [pip-db.query :as query]
            [pip-db.util :as util]))

;; Our database spec.
(def db-spec (System/getenv "DATABASE_URL"))

;; Out database tables.
(def tables
  {:records [[:id                 "varchar(11) NOT NULL"]
             [:Protein-Names      "varchar"]
             [:EC                 "varchar"]
             [:Source             "varchar"]
             [:Location           "varchar"]
             [:MW-Min             "varchar"]
             [:MW-Max             "varchar"]
             [:Subunit-No         "varchar"]
             [:Subunit-MW         "varchar"]
             [:No-Of-Iso-Enzymes  "varchar"]
             [:pI-Min             "varchar"]
             [:pI-Max             "varchar"]
             [:pI-Major-Component "varchar"]
             [:Temperature-Min    "varchar"]
             [:Temperature-Max    "varchar"]
             [:Method             "varchar"]
             [:Full-Text          "varchar"]
             [:Abstract-Only      "varchar"]
             [:PubMed             "varchar"]
             [:Species-Taxonomy   "varchar"]
             [:Protein-Sequence   "varchar"]
             [:Notes              "varchar"]
             [:real_ec1           "integer"]
             [:real_ec2           "integer"]
             [:real_ec3           "integer"]
             [:real_ec4           "integer"]
             [:real_mw_min        "real"]
             [:real_mw_max        "real"]
             [:real_pi_min        "real"]
             [:real_pi_max        "real"]
             [:real_temp_min      "real"]
             [:real_temp_max      "real"]
             [:Created-At         "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"]]
   :users   [[:id                 "serial    PRIMARY KEY"]
             [:email              "varchar   NOT NULL"]
             [:pass               "varchar   NOT NULL"]]})

;; Evaluates body in the context of a new connection to a database
;; then closes the connection. Identifiers are quoted.
(defmacro with-connection [& body]
  `(sql/with-connection db-spec (sql/with-quoted-identifiers \" ~@body)))

;; Creates a new connection and executes a query, then evaluates body
;; with results bound to a seq of the results.
(defmacro with-connection-results-query [results sql-params & body]
  `(with-connection (sql/with-query-results ~results ~sql-params ~@body)))

(def max-no-of-returned-records 20)

;; Count the number of rows in a given table. May optionally be
;; provided with a set of conditions.
(defn count-rows
  ([table] (count-rows table ""))
  ([table condition]
     (let [condition?           (not (str/blank? condition))
           base-query           (str "SELECT count(*) FROM " (name table))
           query-with-condition (str base-query " WHERE " condition)
           query                (if condition? query-with-condition base-query)]
       (with-connection-results-query results [query]
         ((first results) :count)))))

;; Determine whether the required tables exist.
(defn migrated? []
  (pos? (count-rows "information_schema.tables"
                    (str "table_name='" (name ((first tables) 0)) "'"))))

;; Create a set of tables.
(defn create-tables [& tables]
  (with-connection (doseq [t tables] (apply sql/create-table (t 0) (t 1)))))

;; The subset of fields within the records table that are considered
;; private, i.e. those which should be returned to users when
;; performing queries.
(def private-record-fields
  (map keyword (filter #(re-matches #"real_.*" %)
                       (map #(name (first %)) (tables :records)))))

;; The subset of fields within the records table that are considered
;; public and should be returned to users when performing queries,
;; i.e. the inverse of the private-record-fields list.
(def public-record-fields
  (filter #(not (some #{%} private-record-fields)) (map first (tables :records))))

;; The subset of fields within the records table that derived at
;; insertion time.
(def derived-record-fields '(:Created-At))

;; The subset of fields within the records table that are explicitly
;; provided at insertion time.
(def created-record-fields
  (filter #(not (some #{%} derived-record-fields)) (map first (tables :records))))

;; Convert a YAPS encoded record into a vector of values, using the
;; schema defined in the records table.
(defn record->vector [r]
  (let [id              (util/minihash (str r))
        names           (str/join " / " (r "Protein-Names"))
        ec              (r "EC")
        source          (r "Source")
        location        (r "Location")
        mw_min          (r "MW-Min")
        mw_max          (r "MW-Max")
        sub_no          (r "Subunit-No")
        sub_mw          (r "Subunit-MW")
        iso_enzymes     (r "No-Of-Iso-Enzymes")
        pi_min          (r "pI-Min")
        pi_max          (r "pI-Max")
        pi_major        (r "pI-Major-Component")
        temp_min        (r "Temperature-Min")
        temp_max        (r "Temperature-Max")
        method          (r "Method")
        ref_full        (r "Full-Text")
        ref_abstract    (r "Abstract-Only")
        ref_pubmed      (r "PubMed")
        ref_taxonomy    (r "Species-Taxonomy")
        ref_sequence    (r "Protein-Sequence")
        notes           (r "Notes")
        real_ec         (if ec (str/split ec #"\.") [])
        real_ec1        (util/str->int (nth real_ec 0 nil))
        real_ec2        (util/str->int (nth real_ec 1 nil))
        real_ec3        (util/str->int (nth real_ec 2 nil))
        real_ec4        (util/str->int (nth real_ec 3 nil))
        real_mw_min     (util/str->int mw_min)
        real_mw_max     (util/str->int mw_max)
        real_pi_min     (util/str->num pi_min)
        real_pi_max     (util/str->num pi_max)
        real_temp_min   (util/str->int temp_min)
        real_temp_max   (util/str->int temp_max)]
    [id names ec source location mw_min mw_max sub_no sub_mw iso_enzymes pi_min
     pi_max pi_major temp_min temp_max method ref_full ref_abstract ref_pubmed
     ref_taxonomy ref_sequence notes real_ec1 real_ec2 real_ec3 real_ec4
     real_mw_min real_mw_max real_pi_min real_pi_max real_temp_min
     real_temp_max]))

;; Add a set of YAPS encoded records to the database.
(defn add-records [& records]
  (with-connection
    (apply sql/insert-values
           :records (vec created-record-fields) (map record->vector records))))

;; The `with-query-results` function returns a response map of field
;; names to values, with the field names all lower-cased for some
;; bizarre reason. As a result, we have to remap each key to it's
;; properly cased equivalent. To do this, we use a renaming table
;; which maps lower-case public record fields onto properly cased
;; equivalents.
(def renaming-table
  (zipmap (map #(keyword (str/lower-case (name %))) public-record-fields)
          public-record-fields))

;; Fetch a vector of records for a given query map. We wrap the entire
;; query in a try/catch block in order to catch an SQL exception when
;; the query returns no results: "org.postgresql.util.PSQLException:
;; No results were returned by the query."
(defn search-results [query]
  (try (with-connection-results-query results [query] (apply vector results))
       (catch Exception e [])))

;; Convert a record row (as returned by a query of the records table)
;; into a YAPS encoded map.
(defn row->record [row]
  (-> (into {} (filter second row)) (set/rename-keys renaming-table)))

;; Perform necessary database migration.
(defn migrate []
  (when-not (migrated?)
    (print "Creating database structure...") (flush)
    (apply create-tables tables)
    (println " done")))

;; ### Query components

;; We can now take a query map and use this to generate a SQL
;; query. If the query map is empty, then we return an empty string.
(defn params->str [params]
  (let [query  (query/params->query params)
        fields (apply util/keys->quoted-str public-record-fields)]
    (if (str/blank? query)
      ""
      (str "SELECT " fields " FROM records WHERE " query))))

;; Perform a database search and wrap the results in a search response
;; map.
(defn search
  ([params] (search (params->str params) params))
  ([query params]
     (let [matching-rows    (search-results query)
           returned-rows    (take max-no-of-returned-records matching-rows)
           returned-records (map row->record returned-rows)]
       {:Query-Terms                params
        :No-Of-Records-Searched     (count-rows :records)
        :No-Of-Records-Matched      (count matching-rows)
        :No-Of-Records-Returned     (count returned-records)
        :Max-No-of-Returned-Records max-no-of-returned-records
        :Records                    returned-records})))

;; Perform a database search and return only the number of results.
(defn search-with-no-of-records-matched [params]
  (let [query               (query/params->query params)
        no-of-matching-rows (count-rows :records query)]
    {:No-Of-Records-Searched     (count-rows :records)
     :No-Of-Records-Matched      no-of-matching-rows
     :Max-No-of-Returned-Records max-no-of-returned-records}))
