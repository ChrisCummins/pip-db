(ns pip-db.test.util
  (:use clojure.test)
  (:require [pip-db.util :as dut]))

(deftest dates
  (testing "Retrieving the current year"
    (is (number? (dut/current-year)))
    (is (> (dut/current-year) 1900))))
