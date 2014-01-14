(ns pip-db.test.resources
  (:use [clojure.test])
  (:require [pip-db.resources :as dut]))

(deftest resource-path
  (testing "Path to resources is correct"
    (is (= (dut/resource-path "foo")
           "resources/public/foo"))))
