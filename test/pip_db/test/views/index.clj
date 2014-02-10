(ns pip-db.test.views.index
  (:use clojure.test)
  (:require [pip-db.views.index :as dut]))

(deftest index
  (testing "Page returns string"
    (is (= (class (dut/index {}))
           java.lang.String)))

  (testing "Preloaded search query"
    (is (not (= (dut/index {})
                (dut/index {:search-text "foo"}))))))
