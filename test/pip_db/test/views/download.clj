(ns pip-db.test.views.download
  (:use clojure.test)
  (:require [pip-db.views.download :as dut]))

(deftest download
  (testing "Page produces String"
    (is (= (class (dut/download {:params {}}))
           java.lang.String))))
