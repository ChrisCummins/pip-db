(ns pip-db.test.pages.login
  (:use clojure.test)
  (:require [pip-db.pages.login :as dut]))

;; View

(deftest view
  (testing "Page returns string"
    (is (= (class (dut/view))
           java.lang.String))))

;; Model

(def test-data
  {:plaintext "foobar"
   :hash "$2a$10$fsla0mUDCCtat6mIO23RTOrqnOno.efopjIipSARw0XBcK4XlXLn6"})

(deftest crypto
  (testing "hash validation"
    (is (dut/hashes-match? "foobar" (dut/get-hash "foobar")))))
