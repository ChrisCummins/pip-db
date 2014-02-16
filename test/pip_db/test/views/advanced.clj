(ns pip-db.test.views.advanced
  (:use [clojure.test])
  (:require [pip-db.views.advanced :as dut]))

(deftest advanced
  (testing "Page returns string"
    (is (= (class (dut/advanced {:params {}}))
           java.lang.String)))

  (testing "Preloaded search query"
    (is (not (= (dut/advanced {:params {}})
                (dut/advanced {:params {"q" "foo"}}))))))
