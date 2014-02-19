(ns pip-db.models.upload
  (:use [clojure.core :only (slurp)]
        [clojure.java.io :only (copy)]
        [clojure-csv.core :only (parse-csv)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.db :as db])
  (:import [java.io File]))

(defn upload-file [tempfile]
  (let [file (format "/tmp/%s" (tempfile :filename))]
    (copy (tempfile :tempfile) (File. file))
    file))

;; Generate a unique ID for a line.
(defn id [line]
  (db/minihash (str/join line)))

(defn store-line-record [fields]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-values :records
                       [:id :dataset :ec :name :alt_name :source :organ :mw
                        :sub_no :sub_mw :no_iso :pi_max :pi_range_min
                        :pi_range_max :pi_major :pi :temp :method :valid
                        :sequence :species :citations :abstract :pubmed
                        :notes]
                       [(id fields) (fields 0) (fields 1) (fields 2) (fields 3)
                        (fields 4) (fields 5) (fields 6) (fields 7) (fields 8)
                        (fields 9) (fields 10) (fields 11) (fields 12)
                        (fields 13) (fields 14) (fields 15) (fields 16)
                        (fields 17) (fields 18) (fields 19) (fields 20)
                        (fields 21) (fields 22) (fields 23)])))

(defn parse-csv-file [file]
  (doseq [fields (parse-csv (slurp file) :delimiter \tab)]
    (if (= (count fields) 24)
      (store-line-record fields)))
  "WOO")
