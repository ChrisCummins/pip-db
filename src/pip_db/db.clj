;; # Database Interface
;;
;; This namespace defines the interface and API for the database
;; back-end of pip-db.
(ns pip-db.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [clojure.set :as set]
            [pip-db.query :as query]
            [pip-db.yaps :as yaps]
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

;; Accepts a vector of property values and constructs an autocomplete
;; frequency table.
(defn ac-table [properties]
  (take ac-table-size (reverse (sort-by second (frequencies properties)))))

;; Add a set of YAPS encoded records to the database.
(defn add-records [& records]
  (let [names     (ac-table (yaps/records->properties     "Protein-Names" records))
        words     (ac-table (yaps/records->property-words "Protein-Names" records))
        sources   (ac-table (yaps/records->properties     "Source"        records))
        locations (ac-table (yaps/records->properties     "Location"      records))
        methods   (ac-table (yaps/records->properties     "Method"        records))
        ac-keys   (map first ac-table-keys)]
    (with-connection
      (if names     (apply sql/insert-values :ac_names     ac-keys names))
      (if words     (apply sql/insert-values :ac_words     ac-keys words))
      (if sources   (apply sql/insert-values :ac_sources   ac-keys sources))
      (if locations (apply sql/insert-values :ac_locations ac-keys locations))
      (if methods   (apply sql/insert-values :ac_methods   ac-keys methods))

      (apply sql/insert-values :records
             (vec created-record-fields) (map yaps/record->vector records)))))

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
