(ns pip-db.test.pages.download
  (:use clojure.test)
  (:require [pip-db.pages.download :as dut]))

;; View

(deftest view
  (testing "Page produces String"
    (is (= (class (dut/view {:params {}}))
           java.lang.String))))
