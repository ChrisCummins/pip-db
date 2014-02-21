;; # Database Interface
;;
;; This namespace defines the interface and API for the database
;; back-end of pip-db.
(ns pip-db.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.util :as util]))

(def max-no-of-returned-records 20)

;; SHA1 implementation
;;
;; See: https://gist.github.com/hozumi/1472865
(defn sha1 [s]
  (->> (-> "sha1" java.security.MessageDigest/getInstance
           (.digest (.getBytes s)))
       (map #(.substring
              (Integer/toString
               (+ (bit-and % 0xff) 0x100) 16) 1))
       (apply str)))

;; We generated truncated hashes when creating our record IDs.
(defn minihash [s]
  (subs (sha1 s) 0 11))

(defn migrated? []
  (not (zero?
        (sql/with-connection (System/getenv "DATABASE_URL")
          (sql/with-query-results results
            [(str "SELECT count(*) FROM information_schema.tables "
                  "WHERE table_name='records'")]
            (:count (first results)))))))

(defn create-tables []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :records
                      [:id            "varchar(11)" "NOT NULL"]
                      [:names         :varchar]
                      [:ec            :varchar]
                      [:source        :varchar]
                      [:location      :varchar]
                      [:mw_min        :varchar]
                      [:mw_max        :varchar]
                      [:sub_no        :varchar]
                      [:sub_mw        :varchar]
                      [:iso_enzymes   :varchar]
                      [:pi_min        :varchar]
                      [:pi_max        :varchar]
                      [:pi_major      :varchar]
                      [:temp_min      :varchar]
                      [:temp_max      :varchar]
                      [:method        :varchar]
                      [:ref_full      :varchar]
                      [:ref_abstract  :varchar]
                      [:ref_pubmed    :varchar]
                      [:ref_taxonomy  :varchar]
                      [:ref_sequence  :varchar]
                      [:notes         :varchar]
                      [:real_ec1      :integer]
                      [:real_ec2      :integer]
                      [:real_ec3      :integer]
                      [:real_ec4      :integer]
                      [:real_mw_min   :real]
                      [:real_mw_max   :real]
                      [:real_pi_min   :real]
                      [:real_pi_max   :real]
                      [:real_temp_min :real]
                      [:real_temp_max :real]
                      [:created_at    :timestamp
                       "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])

    (sql/create-table :users
                      [:id :serial "PRIMARY KEY"]
                      [:email :varchar "NOT NULL"]
                      [:pass :varchar "NOT NULL"])))

;; Generate a unique ID for a line.
(defn record-hash [record]
  (minihash (str record)))

;; YAPS map

(defn add-record [r]
  (let [id              (record-hash r)
        names           (str/join " / " (r "names"))
        ec              (r "ec")
        source          (r "source")
        location        (r "location")
        mw_min          (r "mw_min")
        mw_max          (r "mw_max")
        sub_no          (r "sub_no")
        sub_mw          (r "sub_mw")
        iso_enzymes     (r "iso_enzymes")
        pi_min          (r "pi_min")
        pi_max          (r "pi_max")
        pi_major        (r "pi_major")
        temp_min        (r "temp_min")
        temp_max        (r "temp_max")
        method          (r "method")
        ref_full        (r "ref_full")
        ref_abstract    (r "ref_abstract")
        ref_pubmed      (r "ref_pubmed")
        ref_taxonomy    (r "ref_taxonomy")
        ref_sequence    (r "ref_sequence")
        notes           (r "notes")
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

    (sql/with-connection (System/getenv "DATABASE_URL")

      ;; Insert records
      (sql/insert-values
       :records
       [:id :names :ec :source :location :mw_min :mw_max :sub_no :sub_mw :iso_enzymes
        :pi_min :pi_max :pi_major :temp_min :temp_max :method :ref_full
        :ref_abstract :ref_pubmed :ref_taxonomy :ref_sequence :notes :real_ec1
        :real_ec2 :real_ec3 :real_ec4 :real_mw_min :real_mw_max :real_pi_min
        :real_pi_max :real_temp_min :real_temp_max]
       [id names ec source location mw_min mw_max sub_no sub_mw iso_enzymes
        pi_min pi_max pi_major temp_min temp_max method ref_full
        ref_abstract ref_pubmed ref_taxonomy ref_sequence notes real_ec1
        real_ec2 real_ec3 real_ec4 real_mw_min real_mw_max real_pi_min
        real_pi_max real_temp_min real_temp_max]))))

;; This SQL query counts the number of records in the database.
(def no-of-records-query
  (str "SELECT count(*) AS exact_count FROM records"))

;; Fetch the number of records within the database.
(defn no-of-records []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results result [no-of-records-query]
      (((apply vector (doall result)) 0) :exact_count))))

;; Remove the null values from a map.
(defn filter-null [map]
  (into {} (filter second map)))

;; Construct a search results map from a set of search parameters and
;; a list of matching records.
(defn search-response [matching-records params]
  (let [returned-records (take max-no-of-returned-records matching-records)]
    {:query                      params
     :no_of_records              (no-of-records)
     :no_of_matches              (count matching-records)
     :no_of_returned_records     (count returned-records)
     :max_no_of_returned_records max-no-of-returned-records
     :records                    returned-records}))

;; Fetch a vector of records for a given query map. We wrap the entire
;; query in a try/catch block in order to catch an SQL exception when
;; the query returns no results: "org.postgresql.util.PSQLException:
;; No results were returned by the query."
(defn search [query params]
  (search-response
   (sql/with-connection (System/getenv "DATABASE_URL")
     (try (sql/with-query-results results [query]
            (apply vector (map filter-null (doall results))))
          (catch Exception e []))) params))

(defn migrate []
  (when-not (migrated?)
    (print "Creating database structure...") (flush)
    (create-tables)
    (println " done")))

(def records-table "records")

(def records-columns
  (str "id,names,ec,source,location,mw_min,mw_max,sub_no,sub_mw,"
       "iso_enzymes,pi_min,pi_max,pi_major,temp_min,temp_max,method,"
       "ref_full,ref_abstract,ref_pubmed,ref_taxonomy,ref_sequence,notes,"
       "created_at"))
