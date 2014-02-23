;; # Database Interface
;;
;; This namespace defines the interface and API for the database
;; back-end of pip-db.
(ns pip-db.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [clojure.set :as set]
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
  `(sql/with-connection db-spec
     (sql/with-quoted-identifiers \" ~@body)))

(def max-no-of-returned-records 20)

;; Count the number of rows in a given table. May optionally be
;; provided with a set of conditions.
(defn count-rows [table & conditions]
  (let [condition?           (not (nil? conditions))
        query                (str "SELECT count(*) FROM " (name table))
        query-with-condition (apply str query " WHERE " conditions)]
    (with-connection
      (sql/with-query-results results
        [(if condition? query-with-condition query)]
        ((first results) :count)))))

;; Determine whether the required tables exist.
(defn migrated? []
  (pos? (count-rows "information_schema.tables"
                    (str "table_name='" (name ((first tables) 0)) "'"))))

;; Create a set of tables.
(defn create-tables [& tables]
  (with-connection
    (doseq [t tables] (apply sql/create-table (t 0) (t 1)))))

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
  (filter #(not (some #{%} private-record-fields))
          (map first (tables :records))))

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

    [id names ec source location mw_min mw_max sub_no sub_mw iso_enzymes
     pi_min pi_max pi_major temp_min temp_max method ref_full
     ref_abstract ref_pubmed ref_taxonomy ref_sequence notes real_ec1
     real_ec2 real_ec3 real_ec4 real_mw_min real_mw_max real_pi_min
     real_pi_max real_temp_min real_temp_max]))

;; Add a set of YAPS encoded records to the database.
(defn add-records [& records]
  (with-connection
    (apply
     sql/insert-values
     :records
     [:id :Protein-Names :EC :Source :Location :MW-Min :MW-Max :Subunit-No
      :Subunit-MW :No-Of-Iso-Enzymes :pI-Min :pI-Max :pI-Major-Component
      :Temperature-Min :Temperature-Max :Method :Full-Text :Abstract-Only
      :PubMed :Species-Taxonomy :Protein-Sequence :Notes :real_ec1
      :real_ec2 :real_ec3 :real_ec4 :real_mw_min :real_mw_max :real_pi_min
      :real_pi_max :real_temp_min :real_temp_max]
     (map record->vector records))))

;; Remove the null values from a map.
(defn filter-null [map]
  (into {} (filter second map)))

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
  (with-connection
    (try (sql/with-query-results results [query]
           (apply vector (map #(set/rename-keys (filter-null %) renaming-table)
                              results)))
         (catch Exception e []))))

;; Perform a database search and wrap the results in a search response
;; map.
(defn search [query params]
  (let [matching-records (search-results query)
        returned-records (take max-no-of-returned-records matching-records)]
    {:Query-Terms                params
     :No-Of-Records-Searched     (count-rows :records)
     :No-Of-Records-Matched      (count matching-records)
     :No-Of-Records-Returned     (count returned-records)
     :Max-No-of-Returned-Records max-no-of-returned-records
     :Records                    returned-records}))

;; Perform necessary database migration.
(defn migrate []
  (when-not (migrated?)
    (print "Creating database structure...") (flush)
    (apply create-tables tables)
    (println " done")))
