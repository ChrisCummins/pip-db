(ns pip-db.test.views.upload
  (:use clojure.test)
  (:require [pip-db.views.upload :as dut]))

(deftest upload
  (testing "Page contents"
    (is (= (class (dut/upload))
           java.lang.String))))
