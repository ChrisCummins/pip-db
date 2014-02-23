(ns pip-db.test.pages.upload
  (:use clojure.test)
  (:require [pip-db.pages.upload :as dut]))

;; View

(deftest view
  (testing "Page contents"
    (is (= (class (dut/view {:params {}}))
           java.lang.String))))
