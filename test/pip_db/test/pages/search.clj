(ns pip-db.test.pages.search
  (:use clojure.test)
  (:require [pip-db.pages.search :as dut]
            [clojure.string :as str]))

;; View

(deftest view
  (testing "No results"
    (is (= (class (dut/view {:params {"q" "foo"}
                             :results-count 0}))
           java.lang.String))))

;; Model
