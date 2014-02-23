(ns pip-db.test.pages.index
  (:use clojure.test)
  (:require [pip-db.pages.index :as dut]))

;; View

(deftest view
  (testing "Page returns string"
    (is (= (class (dut/view {:params {}}))
           java.lang.String)))

  (testing "Preloaded search query"
    (is (not (= (dut/view {:params {}})
                (dut/view {:params {"q" "foo"}}))))))
