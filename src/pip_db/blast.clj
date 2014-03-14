;; # BLAST+ interface
(ns pip-db.blast
  (:use [clojure.java.shell :only (sh)])
  (:require [clojure.string :as str]))

(def output (sh "blastp" "-db" "pip-db" "-evalue" "10"
                "-outfmt" "6 stitle score evalue qseq sseq"
                :in "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT"
                :env {"PATH" "blast/bin" "BLASTDB" "blast/db"}))

(defn row->result [row]
  (let [c (str/split row #"\t")]
    {:title (c 0) :score (c 1) :evalue (c 2) :qseq (c 3) :sseq (c 4)}))

(def records (map row->result (str/split (output :out) #"\n")))

(defn run-test [request]
  (str (apply vector records)))

;; (def output (sh "which" "blastp"
;;                 :env {"PATH" "blast/bin" "BLASTDB" "blast/db"}))

;; (defn run-test [request]
;;   (str (output :out)))
