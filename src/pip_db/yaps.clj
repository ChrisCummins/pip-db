(ns pip-db.yaps
  (:use [clojure.core :only (slurp)])
  (:require [clojure.string :as str]
            [clojure.data.json :as json]
            [pip-db.util :as util]))

(defn file->yaps [file]
  (json/read-str (slurp file)))

(defn yaps->records [yaps]
  (yaps "Records"))

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
        sequence_name   (r "Sequence-Name")
        sequence_data   (r "Sequence-Data")
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
     ref_taxonomy ref_sequence notes sequence_name sequence_data real_ec1
     real_ec2 real_ec3 real_ec4 real_mw_min real_mw_max real_pi_min real_pi_max
     real_temp_min real_temp_max]))

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
