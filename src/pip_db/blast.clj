;; # BLAST+ interface
(ns pip-db.blast
  (:use [clojure.java.shell :only (sh)])
  (:require [clojure.string :as str]))

(defn blastp [sequence]
  (sh "./extern/bin/blastp" "-db" "pip-db" "-evalue" "10"
      "-outfmt" "6 stitle score evalue qseq sseq"
      :in sequence :env {"BLASTDB" "db"}))

(defn row->result [row]
  (let [c (str/split row #"\t")]
    {:title (c 0) :score (c 1) :evalue (c 2) :qseq (c 3) :sseq (c 4)}))

(defn search [request]
  (let [sequence ((request :params) "seq")
        result   (blastp sequence)
        output   (result :out)
        lines    (if-not (str/blank? output) (str/split output #"\n"))]
    (apply vector (map row->result lines))))
