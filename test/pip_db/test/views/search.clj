(ns pip-db.test.views.search
  (:use clojure.test)
  (:require [pip-db.views.search :as dut]))

(deftest tablify-results
  (testing "empty table"
    (is (= (dut/tablify-results '())
           [:table.table.table-striped.table-hover.table-bordered
            [:thead [:tr
                     [:td [:strong "Protein"]]
                     [:td [:strong "Source"]]
                     [:td [:strong "Location"]]
                     [:td [:strong "pI"]]]]
            [:tbody '()]])))

  (testing "single record"
    (is (= (dut/tablify-results '({:id 10
                                   :name "foo"
                                   :source "bar"
                                   :organ "alpha"
                                   :pi "5"}))
           [:table.table.table-striped.table-hover.table-bordered
            [:thead [:tr
                     [:td [:strong "Protein"]]
                     [:td [:strong "Source"]]
                     [:td [:strong "Location"]]
                     [:td [:strong "pI"]]]]
            [:tbody '([:tr {:data-id 10}
                       [:td "foo"]
                       [:td "bar"]
                       [:td "alpha"]
                       [:td "5"]])]])))

  (testing "out of order elements"
    (is (= (dut/tablify-results '({:source "bar"
                                   :id 10
                                   :name "foo"
                                   :pi "5"
                                   :organ "alpha"}))
           [:table.table.table-striped.table-hover.table-bordered
            [:thead [:tr
                     [:td [:strong "Protein"]]
                     [:td [:strong "Source"]]
                     [:td [:strong "Location"]]
                     [:td [:strong "pI"]]]]
            [:tbody '([:tr {:data-id 10}
                       [:td "foo"]
                       [:td "bar"]
                       [:td "alpha"]
                       [:td "5"]])]])))

  (testing "multiple records"
    (is (= (dut/tablify-results '({:id 10
                                   :name "foo"
                                   :source "bar"
                                   :organ "alpha"
                                   :pi "5"}
                                  {:id 11
                                   :name "bar"
                                   :source "foo"
                                   :organ "beta"
                                   :pi "7"}))
           [:table.table.table-striped.table-hover.table-bordered
            [:thead [:tr
                     [:td [:strong "Protein"]]
                     [:td [:strong "Source"]]
                     [:td [:strong "Location"]]
                     [:td [:strong "pI"]]]]
            [:tbody '([:tr {:data-id 10}
                       [:td "foo"]
                       [:td "bar"]
                       [:td "alpha"]
                       [:td "5"]]
                        [:tr {:data-id 11}
                         [:td "bar"]
                         [:td "foo"]
                         [:td "beta"]
                         [:td "7"]])]]))))

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
  (testing "One page"
    (is (= (dut/pagination-links 1 '(1) 10 1)
           [:div.row {:style "margin-bottom: 20px;"}
            [:div.col-lg-12 [:div {:style "text-align: center;"}
                             [:div#pagination.btn-group
                              {:data-pages-count 1,
                               :data-results-per-page 10,
                               :style "margin: 0 auto;"}
                              '(nil ([:a.page-ref.btn.btn-success
                                      {:class "disabled", :data-page 1} 1])
                                    nil)]]]]))))

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
    (is (= (class (dut/search "foo" {:results-count 0}))
           java.lang.String))))
