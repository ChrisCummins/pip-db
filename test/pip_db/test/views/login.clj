(ns pip-db.test.views.login
  (:use clojure.test)
  (:require [pip-db.views.login :as dut]))

(deftest login
  (testing "Page returns string"
    (is (= (class (dut/login))
           java.lang.String))))
