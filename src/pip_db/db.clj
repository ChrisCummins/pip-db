;; # Database Interface
;;
;; This namespace defines the interface and API for the database
;; back-end of pip-db.
(ns pip-db.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [clojure.set :as set]
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
  (pos?
   (sql/with-connection (System/getenv "DATABASE_URL")
     (sql/with-query-results results
       [(str "SELECT count(*) FROM information_schema.tables "
             "WHERE table_name='records'")]
       ((first results) :count)))))

(defn create-tables []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-quoted-identifiers \"
      (sql/create-table :records
                        [:id                 "varchar(11) NOT NULL"]
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
                        [:Created-At         "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"]))

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

    (sql/with-connection (System/getenv "DATABASE_URL")
      (sql/with-quoted-identifiers \"

        ;; Insert records
        (sql/insert-values
         :records
         [:id :Protein-Names :EC :Source :Location :MW-Min :MW-Max :Subunit-No
          :Subunit-MW :No-Of-Iso-Enzymes :pI-Min :pI-Max :pI-Major-Component
          :Temperature-Min :Temperature-Max :Method :Full-Text :Abstract-Only
          :PubMed :Species-Taxonomy :Protein-Sequence :Notes :real_ec1
          :real_ec2 :real_ec3 :real_ec4 :real_mw_min :real_mw_max :real_pi_min
          :real_pi_max :real_temp_min :real_temp_max]
         [id names ec source location mw_min mw_max sub_no sub_mw iso_enzymes
          pi_min pi_max pi_major temp_min temp_max method ref_full
          ref_abstract ref_pubmed ref_taxonomy ref_sequence notes real_ec1
          real_ec2 real_ec3 real_ec4 real_mw_min real_mw_max real_pi_min
          real_pi_max real_temp_min real_temp_max])))))

;; Fetch the number of records within the database.
(defn no-of-records []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results ["SELECT count(*) FROM records"]
      ((first results) :count))))

;; Remove the null values from a map.
(defn filter-null [map]
  (into {} (filter second map)))

;; Construct a search results map from a set of search parameters and
;; a list of matching records.
(defn search-response [matching-records params]
  (let [returned-records (take max-no-of-returned-records matching-records)]
    {:Query-Terms                params
     :No-Of-Records-Searched     (no-of-records)
     :No-Of-Records-Matched      (count matching-records)
     :No-Of-Records-Returned     (count returned-records)
     :Max-No-of-Returned-Records max-no-of-returned-records
     :Records                    returned-records}))

;; The `with-query-results` function returns a response map of field
;; names to values, with the field names all lower-cased for some
;; bizarre reason. As a result, we have to remap each key to it's
;; properly cased equivalent. This is a fucking disgrace of bad code,
;; so we *need* to come up with an actual solution, not a hack.
(defn remap-yaps-keys [map]
  (set/rename-keys map
                   {:protein-names      :Protein-Names,
                    :ec                 :EC,
                    :source             :Source,
                    :location           :Location,
                    :mw-min             :MW-Min,
                    :mw-max             :MW-Max,
                    :subunit-no         :Subunit-No,
                    :subunit-mw         :Subunit-MW,
                    :no-of-iso-enzymes  :No-Of-Iso-Enzymes,
                    :pi-min             :pI-Min,
                    :pi-max             :pI-Max,
                    :pi-major-component :pI-Major-Component,
                    :temperature-min    :Temperature-Min,
                    :temperature-max    :Temperature-Max,
                    :method             :Method,
                    :full-text          :Full-Text,
                    :abstract-only      :Abstract-Only,
                    :pubmed             :PubMed,
                    :species-taxonomy   :Species-Taxonomy,
                    :protein-sequence   :Protein-Sequence,
                    :notes              :Notes,
                    :created-at         :Created-At}))

;; Fetch a vector of records for a given query map. We wrap the entire
;; query in a try/catch block in order to catch an SQL exception when
;; the query returns no results: "org.postgresql.util.PSQLException:
;; No results were returned by the query."
(defn search [query params]
 (search-response
  (sql/with-connection (System/getenv "DATABASE_URL")
    (try (sql/with-query-results results [query]
           (apply vector (map remap-yaps-keys
                              (map filter-null (doall results)))))
         (catch Exception e []))) params))

(defn migrate []
  (when-not (migrated?)
    (print "Creating database structure...") (flush)
    (create-tables)
    (println " done")))

(def records-table "records")

(def records-columns
  (str "id,\"Protein-Names\",\"EC\",\"Source\",\"Location\",\"MW-Min\","
       "\"MW-Max\",\"Subunit-No\",\"Subunit-MW\",\"No-Of-Iso-Enzymes\","
       "\"pI-Min\",\"pI-Max\",\"pI-Major-Component\",\"Temperature-Min\","
       "\"Temperature-Max\",\"Method\",\"Full-Text\",\"Abstract-Only\","
       "\"PubMed\",\"Species-Taxonomy\",\"Protein-Sequence\",\"Notes\","
       "\"Created-At\""))
