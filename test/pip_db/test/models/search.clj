(ns pip-db.test.models.search
  (:use clojure.test)
  (:require [pip-db.models.search :as dut]))

(deftest split-args
  (testing "Null argument"
    (is (= (dut/split-args nil)
           nil)))

  (testing "Single word"
    (is (= (dut/split-args "foo")
           '("foo"))))

  (testing "Blank whitespace word"
    (is (= (dut/split-args "   ")
           nil)))

  (testing "Multiple words"
    (is (= (dut/split-args "foo bar alpha beta")
           '("foo" "bar" "alpha" "beta"))))

  (testing "Words separated with more than a single space"
    (is (= (dut/split-args "foo  bar   alpha        beta")
           '("foo" "bar" "alpha" "beta"))))

  (testing "Leading whitespace"
    (is (= (dut/split-args "  foo bar")
           '("foo" "bar"))))

  (testing "Trailing whitespace"
    (is (= (dut/split-args "foo bar   ")
           '("foo" "bar")))))

(deftest conditionals
  (testing "No parameters"
    (is (= (dut/conditionals nil)
           "")))

  (testing "Single 'q' parameter"
    (is (= (dut/conditionals {"q" "foo"})
           "((LOWER(names) LIKE LOWER('%foo%')))")))

  (testing "Multiple 'q' parameters"
    (is (= (dut/conditionals {"q" "foo bar"})
           (str "((LOWER(names) LIKE LOWER('%foo%')) AND "
                "(LOWER(names) LIKE LOWER('%bar%')))"))))

  (testing "Single 'q_any' parameter"
    (is (= (dut/conditionals {"q_any" "foo"})
           "((LOWER(names) LIKE LOWER('%foo%')))")))

  (testing "Multiple 'q_any' parameters"
    (is (= (dut/conditionals {"q_any" "foo bar"})
           (str "((LOWER(names) LIKE LOWER('%foo%')) AND "
                "(LOWER(names) LIKE LOWER('%bar%')))"))))

  (testing "Single 'q_ne' parameter"
    (is (= (dut/conditionals {"q_ne" "foo"})
           "((LOWER(names) NOT LIKE LOWER('%foo%')))")))

  (testing "Multiple 'q_ne' parameters"
    (is (= (dut/conditionals {"q_ne" "foo bar"})
           (str "((LOWER(names) NOT LIKE LOWER('%foo%')) AND "
                "(LOWER(names) NOT LIKE LOWER('%bar%')))"))))

  (testing "Location paramater"
    (is (= (dut/conditionals {"q_l" "foo bar"})
           "((LOWER(location) LIKE LOWER('%foo bar%')))")))

  (testing "Source paramater"
    (is (= (dut/conditionals {"q_s" "foo bar"})
           "((LOWER(source) LIKE LOWER('%foo bar%')))")))

  (testing "Method paramater"
    (is (= (dut/conditionals {"m" "foo bar"})
           "((LOWER(method) LIKE LOWER('%foo bar%')))"))))

(deftest query
  (testing "A query with no conditions"
    (is (= (dut/query {}) "")))

  (testing "A query with a condition"
    (is (= (dut/query {"q" "foo"})
           (str "SELECT * FROM records WHERE ((LOWER(names) "
                "LIKE LOWER('%foo%')))")))))
