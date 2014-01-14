(ns pip-db.test.models.record
  (:use clojure.test)
  (:require [pip-db.models.record :as dut]))

(deftest record
  (testing "Query string"
    (is (= (dut/query-string 5)
           "SELECT * FROM records WHERE id='5'"))))
