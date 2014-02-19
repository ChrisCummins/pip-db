(ns pip-db.test.views.search
  (:use clojure.test)
  (:require [pip-db.views.search :as dut]
            [clojure.string :as str]))

(deftest search
  (testing "No results"
    (is (= (class (dut/search {:params {"q" "foo"}
                               :results-count 0}))
           java.lang.String))))
