;; # Database Interface
;;
;; This namespace defines the interface and API for the database
;; back-end of pip-db.
(ns pip-db.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.util :as util]))

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
        ec_arr          (if (r "ec") (r "ec") {})
        ec0             (ec_arr 0)
        ec1             (ec_arr 1)
        ec2             (ec_arr 2)
        ec3             (ec_arr 3)
        ec              (if ec0 (str ec0 "." ec1 "." ec2 "." ec3))
        source          (r "source")
        location        (r "location")
        mw              (if (r "mw") (r "mw") {})
        mw_min          (mw "min")
        mw_max          (mw "max")
        sub             (if (r "subunit") (r "subunit") {})
        sub_no          (sub "no")
        sub_mw          (sub "mw")
        iso_enzymes     (r "iso_enzymes")
        pi              (if (r "pi") (r "pi") {})
        pi_min          (pi "min")
        pi_max          (pi "max")
        pi_major        (pi "major")
        temp            (if (r "temp") (r "temp") {})
        temp_min        (temp "min")
        temp_max        (temp "max")
        method          (r "method")
        ref             (if (r "references") (r "references") {})
        ref_orig        (if (ref "original_text") (ref "original_text") {})
        ref_full        (ref_orig "full")
        ref_abstract    (ref_orig "abstract")
        ref_pubmed      (ref "pubmed")
        ref_taxonomy    (ref "taxonomy")
        ref_sequence    (ref "sequence")
        notes           (r "notes")
        real_ec1        (util/str->int ec0)
        real_ec2        (util/str->int ec1)
        real_ec3        (util/str->int ec2)
        real_ec4        (util/str->int ec3)
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

(defn migrate []
  (when-not (migrated?)
    (print "Creating database structure...") (flush)
    (create-tables)
    (println " done")))
