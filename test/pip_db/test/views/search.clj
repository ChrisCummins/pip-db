(ns pip-db.test.views.search
  (:use clojure.test)
  (:require [pip-db.views.search :as dut]
            [clojure.string :as str]))

(deftest pagination-links
  (testing "Single page (no links)"
    (is (= (dut/pagination-links 1 '(1) 10 1) nil)))

  (testing "Multiple pages"
    (is (not (= (dut/pagination-links 1 '(1)   10 1)
                (dut/pagination-links 1 '(1 2) 10 2))))))

(deftest beta-warning
  (testing "Beta warning"
    (is (= (dut/beta-warning)
           [:div.alert.alert-info [:strong "Limited Results "]
            (str "The number of results has been limited for the beta version "
                 "of the website.")
            [:a.close {:href "#", :data-dismiss "alert", :aria-hidden "true"}
             "&times;"]]))))

(deftest search
  (testing "No results"
    (is (= (class (dut/search {:params {"q" "foo"}
                               :results-count 0}))
           java.lang.String))))
