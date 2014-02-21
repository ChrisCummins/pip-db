(ns pip-db.test.views.record
  (:use clojure.test)
  (:require [pip-db.views.record :as dut]))

(deftest record
  (testing "Page title and heading"
    (is (= (class (dut/record {:params {} :results {}}))
           java.lang.String))))
