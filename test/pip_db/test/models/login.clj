(ns pip-db.test.models.login
  (:use clojure.test)
  (:require [pip-db.models.login :as dut]))

(def test-data
  {:plaintext "foobar"
   :hash "$2a$10$fsla0mUDCCtat6mIO23RTOrqnOno.efopjIipSARw0XBcK4XlXLn6"})

(deftest crypto
  (testing "hash validation"
    (is (dut/hashes-match? "foobar" (dut/get-hash "foobar")))))
