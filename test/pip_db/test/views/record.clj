(ns pip-db.test.views.record
  (:use clojure.test)
  (:require [pip-db.views.record :as dut]))

(deftest properties
  (testing "Testing tree structure"
    (is (= (dut/properties "foo")
           [:table#properties.table.table-striped.table-bordered
            [:tbody '("foo")]]))))

(deftest property
  (testing "Property with no value"
    (is (= (dut/property "foo" {} :nonexistent-value)
           nil)))

  (testing "Property with value"
    (is (= (dut/property "foo" {:bar "foo"} :bar)
           [:tr.property {:data-key "bar"}
            [:td.description "foo"] [:td.value "foo"]]))))

(deftest property-range
  (testing "Range property with no value"
    (is (= (dut/property-range "foo" {} :nonexistent-min :nonexistent-max)
           nil)))

  (testing "Property values"
    (is (not (= (dut/property-range "foo" {} :bar :baz)
                (dut/property-range "foo" {:bar "foo" :baz "bar"} :bar :baz)
                (dut/property-range "foo" {:bar "foo"} :bar :baz)
                (dut/property-range "foo" {:baz "bar"} :bar :baz))))))

(deftest extern-links
  (testing "No external links"
    (is (= (dut/extern-links) nil)))

  (testing "External links"
    (is (= (dut/extern-links "foo")
           [:div.panel.panel-primary.panel-extern
            [:div.panel-heading [:h3.panel-title "External Links"]]
            [:div.panel-body [:ul.panel-extern-list '("foo")]]]))))

(deftest extern
  (testing "No URL"
    (is (= (dut/extern "foo" "")
           nil)))

  (testing "With URL"
    (is (= (dut/extern "foo" "bar")
           [:li [:a.btn.btn-success.btn-block
                 {:href "bar", :target "_blank"} "foo"]]))))

(deftest notes-panel
  (testing "No notes"
    (is (= (dut/notes-panel nil)
           (dut/notes-panel "")
           (dut/notes-panel "  ")
           nil)))

  (testing "Notes"
    (is (not (= (dut/notes-panel "foo")
                (dut/notes-panel "bar"))))))

(deftest record
  (testing "Page title and heading"
    (is (= (class (dut/record {:name "foo" :params {}}))
           java.lang.String))))
