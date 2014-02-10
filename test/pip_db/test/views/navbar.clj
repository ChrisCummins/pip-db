(ns pip-db.test.views.navbar
  (:use clojure.test)
  (:require [pip-db.views.navbar :as dut]))

(deftest navbar-search
  (testing "Search text"
    (is (not (= (dut/navbar-search {})
                (dut/navbar-search {:search-text "foo"}))))))

(deftest html
  (testing "Content types"
    (is (not (= (dut/html {})
                (dut/html {:login-only true})
                (dut/html {:search true})
                (dut/html {:session {:user "foo"}}))))))
