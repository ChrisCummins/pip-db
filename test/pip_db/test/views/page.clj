(ns pip-db.test.views.page
  (:use clojure.test)
  (:require [pip-db.views.page :as dut]))

(deftest page
  (testing "No data"
    (is (= (class (dut/page {}))
           java.lang.String))))
