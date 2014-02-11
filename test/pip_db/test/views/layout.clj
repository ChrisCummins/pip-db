(ns pip-db.test.views.layout
  (:use clojure.test)
  (:require [pip-db.views.layout :as dut]))

(deftest heading
  (testing "No data"
    (is (= (dut/heading {})
           [:div.page-title [:div.page-title-inner nil nil nil] [:hr]])))

  (testing "Title text"
    (is (= (dut/heading {:title "Foo"})
           [:div.page-title [:div.page-title-inner nil [:h3 "Foo"] nil] [:hr]])))

  (testing "Download link"
    (is (= (dut/heading {:download true})
           [:div.page-title
            [:div.page-title-inner
             [:div.download [:a.btn.btn-warning {:href true} "Download"]]
             nil nil] [:hr]])))

  (testing "Zero results count"
    (is (= (dut/heading {:meta true :meta-results-count 0})
           [:div.page-title
            [:div.page-title-inner nil nil
             [:div.info [:ul.meta-tags [:li "Found 0 results..."]]]] [:hr]])))

  (testing "Single result count"
    (is (= (dut/heading {:meta true :meta-results-count 1})
           [:div.page-title
            [:div.page-title-inner nil nil
             [:div.info [:ul.meta-tags [:li "Found 1 result..."]]]] [:hr]])))

  (testing "Multiple results"
    (is (= (dut/heading {:meta true :meta-results-count 10})
           [:div.page-title
            [:div.page-title-inner nil nil
             [:div.info [:ul.meta-tags [:li "Found 10 results..."]]]] [:hr]]))))

(deftest page
  (testing "No data"
    (is (= (class (dut/page {}))
           java.lang.String))))
