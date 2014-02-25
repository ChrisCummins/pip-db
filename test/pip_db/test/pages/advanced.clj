(ns pip-db.test.pages.advanced
  (:use [clojure.test])
  (:require [pip-db.pages.advanced :as dut]))

(deftest search-form-heading-row
  (testing "Heading text"
    (is (not (= (dut/search-form-heading-row "foo")
                (dut/search-form-heading-row "bar")))))

  (testing "Heading ID"
    (is (not (= (dut/search-form-heading-row "foo")
                (dut/search-form-heading-row "bar" "foo")
                (dut/search-form-heading-row "baz" "foo"))))))

;; View

(deftest view
  (testing "Page returns string"
    (is (= (class (dut/view {:params {}}))
           java.lang.String)))

  (testing "Preloaded search query"
    (is (not (= (dut/view {:params {}})
                (dut/view {:params {"q" "foo"}}))))))
