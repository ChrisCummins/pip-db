(ns pip-db.test.views.search
  (:use clojure.test)
  (:require [pip-db.views.search :as dut]
            [clojure.string :as str]))

(deftest pi-text
  (testing "pI value"
    (is (= (dut/pi-text {:pi "2"}) "2"))
    (is (not (= (dut/pi-text {:pi "1"})
                (dut/pi-text {:pi "2"})))))

  (testing "pI major"
    (is (= (dut/pi-text {:pi_major "2"}) "2m")))

  (testing "pi Ranges"
    (is (= (dut/pi-text {:pi_range_min "2"}) "2"))
    (is (= (dut/pi-text {:pi_range_max "2"}) "2"))
    (is (= (dut/pi-text {:pi_range_min "1"
                         :pi_range_max "2"})
           (str "1" dut/pi-range-separator "2"))))

  (testing "No pI"
    (is (str/blank? (dut/pi-text {})))))

(deftest tablify-results
  (testing "Table contents"
    (is (not (= (dut/tablify-results '())
                (dut/tablify-results '({:id 10
                                        :name "foo"
                                        :source "bar"
                                        :organ "alpha"
                                        :pi "5"}))
                (dut/tablify-results '({:id 10
                                        :name "foo"
                                        :source "bar"
                                        :organ "alpha"
                                        :pi "5"}
                                       {:id 11
                                        :name "bar"
                                        :source "foo"
                                        :organ "beta"
                                        :pi "7"}))))))

  (testing "Out of order elements"
    (is (= (dut/tablify-results '({:id 10
                                   :name "foo"
                                   :source "bar"
                                   :organ "alpha"
                                   :pi "5"}))
           (dut/tablify-results '({:source "bar"
                                   :id 10
                                   :name "foo"
                                   :pi "5"
                                   :organ "alpha"}))))))


(deftest page-links
  (testing "One page"
    (is (= (dut/page-links 1 '(1) 1)
           '(nil ([:a.page-ref.btn.btn-success
                   {:data-page 1, :class "disabled"} 1]) nil))))

  (testing "Two pages"
    (is (= (dut/page-links 1 '(1 2) 2)
           '(nil ([:a.page-ref.btn.btn-success
                   {:data-page 1, :class "disabled"} 1]
                    [:a.page-ref.btn.btn-success {:data-page 2} 2]) nil))))

  (testing "Pages which don't start at 1"
    (is (= (dut/page-links 3 '(2 3) 3)
           '([:a.page-ref.btn.btn-success {:data-page 1}
              "&laquo;"] ([:a.page-ref.btn.btn-success {:data-page 2} 2]
                            [:a.page-ref.btn.btn-success
                             {:data-page 3, :class "disabled"} 3]) nil))))

  (testing "Pages which don't end at the last page"
    (is (= (dut/page-links 3 '(2 3) 4)
           '([:a.page-ref.btn.btn-success {:data-page 1} "&laquo;"]
               ([:a.page-ref.btn.btn-success {:data-page 2} 2]
                  [:a.page-ref.btn.btn-success
                   {:data-page 3, :class "disabled"} 3])
               [:a.page-ref.btn.btn-success {:data-page 4} "&raquo;"])))) )

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
