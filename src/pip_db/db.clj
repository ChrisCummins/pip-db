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

(def ac-table-keys
  [[:text              "varchar   NOT NULL"]
   [:frequency         "integer   NOT NULL"]])

;; The maximum number of rows within an auto-complete table.
(def ac-table-size 900)

;; Out database tables.
(def tables
  {:records     [[:id                 "varchar(11) NOT NULL"]
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
                 [:Sequence           "varchar"]
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
   :users       [[:id                 "serial    PRIMARY KEY"]
                 [:email              "varchar   NOT NULL"]
                 [:pass               "varchar   NOT NULL"]]
   :ac_names      ac-table-keys
   :ac_words      ac-table-keys
   :ac_sources    ac-table-keys
   :ac_locations  ac-table-keys
   :ac_methods    ac-table-keys})

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

;; Create a set of tables.
(defn create-tables [& tables]
  (with-connection (doseq [t tables] (apply sql/create-table (t 0) (t 1)))))

;; Delete a set of tables.
(defn drop-tables [& tables]
  (with-connection (doseq [t tables] (sql/drop-table t))))

;; The subset of fields within the records table that are considered
;; private, i.e. those which should be returned to users when
;; performing queries.
(def private-record-fields
  (map keyword (filter #(re-matches #"real_.*" %)
                       (map (comp name first) (tables :records)))))

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
        sequence        (r "Sequence")
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
     ref_taxonomy ref_sequence notes sequence real_ec1 real_ec2 real_ec3
     real_ec4 real_mw_min real_mw_max real_pi_min real_pi_max real_temp_min
     real_temp_max]))

;; Return a vector of non-nil values for the given property from
;; within a set of records.
(defn records->properties [property records]
  (remove nil? (flatten (map #(get % property) records))))

;; Return a vector of individual words contained within the given
;; properties of a set of records. Words list is filtered to remove
;; single or double character words, words with only numbers, words
;; with unbalanced parenthesis etc.
(defn records->property-words [property records]
  (let [properties (records->properties property records)
        words      (flatten (map #(str/split % #"\s+") properties))]
    (filter #(not (re-matches #"(\w\w?)|(\d+)|(\([^\)]*)|([^\(]*\))" %))
            (map (comp str/capitalize str/lower-case) words))))

;; Accepts a vector of property values and constructs an autocomplete
;; frequency table.
(defn ac-table [properties]
  (take ac-table-size (reverse (sort-by second (frequencies properties)))))

;; Add a set of YAPS encoded records to the database.
(defn add-records [& records]
  (let [names     (ac-table (records->properties     "Protein-Names" records))
        words     (ac-table (records->property-words "Protein-Names" records))
        sources   (ac-table (records->properties     "Source"        records))
        locations (ac-table (records->properties     "Location"      records))
        methods   (ac-table (records->properties     "Method"        records))
        ac-keys   (map first ac-table-keys)]
    (with-connection
      (if names     (apply sql/insert-values :ac_names     ac-keys names))
      (if words     (apply sql/insert-values :ac_words     ac-keys words))
      (if sources   (apply sql/insert-values :ac_sources   ac-keys sources))
      (if locations (apply sql/insert-values :ac_locations ac-keys locations))
      (if methods   (apply sql/insert-values :ac_methods   ac-keys methods))

      (apply sql/insert-values :records
             (vec created-record-fields) (map record->vector records)))))

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

;; Determine whether the required tables exist.
(defn migrated? []
  (pos? (count-rows "information_schema.tables"
                    (str "table_name='" (name ((first tables) 0)) "'"))))

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
;; map. Accepts a request map contains a :params map. Note as an
;; implementation detail, the `merge` function means that properties
;; are returned in the reverse order to as included here.
(defn search [request]
  (let [params                (request :params)
        headers               (request :headers)
        query-str             (params->str params)
        include-query-terms?  (not (= (headers "x-pip-db-query-terms") "None"))
        include-records?      (not (= (headers "x-pip-db-records")     "None"))]
    (merge
     (if include-records?
       (let [matching-rows    (search-results query-str)
             returned-rows    (take max-no-of-returned-records matching-rows)
             returned-records (map row->record returned-rows)]
         {:No-Of-Records-Returned     (count returned-records)
          :No-Of-Records-Matched      (count matching-rows)
          :Records                    returned-records})
       (let [conditions        (query/params->query params)]
         {:No-Of-Records-Matched      (count-rows :records conditions)}))
     {:Max-No-of-Returned-Records     max-no-of-returned-records}
     {:No-Of-Records-Searched         (count-rows :records)}
     (if include-query-terms?
       {:Query-Terms                  params}))))

;; Accepts a request map containing a :params map, and returns
;; autocomplete suggestions for the text paramter "t" from
;; autocomplete table "s".
(defn autocomplete [request]
  (let [params    (request :params)
        table     (str "ac_" (params "s"))
        text      (params "t")
        query-str (str "SELECT text FROM " table " WHERE LOWER(text) LIKE '%"
                       text "%' ORDER BY frequency DESC LIMIT 10")]
    (with-connection-results-query results [query-str]
      (apply vector (map #(get % :text) results)))))
