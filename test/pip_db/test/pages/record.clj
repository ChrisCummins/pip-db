(ns pip-db.test.pages.record
  (:use clojure.test)
  (:require [pip-db.pages.record :as dut]))

;; View

(deftest view
  (testing "Page title and heading"
    (is (= (class (dut/view {:params {} :results {}}))
           java.lang.String))))
