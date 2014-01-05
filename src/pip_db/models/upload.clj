(ns pip-db.models.upload
  (:use [clojure.core :only (slurp)]
        [clojure.java.io :only (copy)]
        [clojure-csv.core :only (parse-csv)])
  (:require [clojure.java.jdbc :as sql])
  (:import [java.io File]))

(defn upload-file [tempfile]
  (let [file (format "/tmp/%s" (tempfile :filename))]
    (copy (tempfile :tempfile) (File. file))
    file))

(defn store-line-record [line]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-values :records
                       [:dataset :ec :name :alt_name :source :organ :mw
                        :sub_no :sub_mw :no_iso :pi_max :pi_range_min
                        :pi_range_max :pi_major :pi :temp :method :valid
                        :sequence :species :citations :abstract :pubmed
                        :notes]
                       [(line 0) (line 1) (line 2) (line 3) (line 4) (line 5)
                        (line 6) (line 7) (line 8) (line 9) (line 10) (line 11)
                        (line 12) (line 13) (line 14) (line 15) (line 16)
                        (line 17) (line 18) (line 19) (line 20) (line 21)
                        (line 22) (line 23)])))

(defn parse-csv-file [file]
  (doseq [line (parse-csv (slurp file) :delimiter \tab)]
    (if (= (count line) 24)
      (store-line-record line)))
  "WOO")
