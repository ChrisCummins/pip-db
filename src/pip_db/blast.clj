;; # BLAST+ interface
(ns pip-db.blast
  (:use [clojure.java.shell :only (sh)])
  (:require [clojure.string :as str]
            [pip-db.db :as db]))

;; Execute a BLAST+ search on the given sequence.
(defn blastp [sequence]
  (sh "./extern/bin/blastp" "-db" "pip-db" "-evalue" "10"
      "-outfmt" "6 stitle score evalue qseq sseq"
      :in sequence :env {"BLASTDB" "db"}))

;; Returns a blastp results map for a given line of output.
(defn row->result [row]
  (let [c (str/split row #"\t")]
    {:title            (c 0)
     :Raw-Score        (c 1)
     :Expect-Value     (c 2)
     :Query-Sequence   (c 3)
     :Subject-Sequence (c 4)}))

;; Accepts a record and wraps the blastp search results data into it.
(defn wrap-blast-result [record result]
  (merge record (dissoc result :title)))

;; Accepts a blastp search result map and returns an array of records
;; from within the database which match that sequence name, in
;; addition to any further parameters defined in the request map.
(defn result->records [request result]
  (let [params  (assoc (request :params) "seq_name" (result :title))
        records (db/search (assoc request :params params))]
    (map #(wrap-blast-result % result) records)))

;; Performs a BLAST+ search and returns a vector of blastp result
;; maps.
(defn blast-results [sequence]
  (let [result (blastp sequence)
        output (result :out)
        lines  (if-not (str/blank? output) (str/split output #"\n"))]
    (apply vector (map row->result lines))))

;; Perform a BLAST+ search. Accepts a request map containing a :params
;; map.
(defn search [request]
  (let [results (blast-results ((request :params) "seq"))]
    (flatten (map #(result->records request %) results))))
