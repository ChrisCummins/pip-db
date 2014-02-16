(ns pip-db.test.views.navbar
  (:use clojure.test)
  (:require [pip-db.views.navbar :as dut]))

(deftest navbar-search
  (testing "Search text"
    (is (not (= (dut/navbar-search {:params {}})
                (dut/navbar-search {:params {"q" "foo"}}))))))

(deftest html
  (testing "Content types"
    (is (not (= (dut/html {:params {} :navbar {}})
                (dut/html {:params {} :navbar {:login-only true}})
                (dut/html {:params {} :navbar {:search true}})
                (dut/html {:params {} :navbar {:session {:user "foo"}}}))))))
