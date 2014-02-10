(ns pip-db.test.views.advanced
  (:use [clojure.test])
  (:require [pip-db.views.advanced :as dut]))

(deftest advanced
  (testing "Page returns string"
    (is (= (class (dut/advanced))
           java.lang.String))))
