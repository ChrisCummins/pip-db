(ns pip-db.test.query
  (:use clojure.test)
  (:require [pip-db.query :as dut]))

(def test-eq {:query  (dut/EQ {:field "foo" :value "bar"})
              :string "(LOWER(foo) LIKE LOWER('%bar%'))"})

(def test-ne {:query  (dut/NE {:field "foo" :value "bar"})
              :string "(LOWER(foo) NOT LIKE LOWER('%bar%'))"})

(deftest match-conditions
  (testing "EQ"
    (is (= (test-eq :query) (test-eq :string))))

  (testing "NE"
    (is (= (test-ne :query) (test-ne :string)))))

(deftest compound-conditions
  (testing "list joining"
    (is (= (dut/compound-condition " + " '("a"))
           "(a)"))

    (is (= (dut/compound-condition " + " '("a" "b" "c" "d"))
           "(a + b + c + d)"))

    (is (= (dut/AND "a" "b" "c" "d")
           "(a AND b AND c AND d)"))

    (is (= (dut/AND "a")
           "(a)"))

    (is (= (dut/OR "a" "b" "c" "d")
           "(a OR b OR c OR d)")))

    (is (= (dut/OR "a")
           "(a)")))

  (testing "strip empty conditions"
    (is (= (dut/compound-condition " + " '("" "  " "()" " ()" " ( )  "))
           ""))

    (is (= (dut/compound-condition " + " '("a" "  " "b"))
           "(a + b)")))

  (testing "sequences as condition arguments"
    (is (= (dut/compound-condition " + " (for [x '("a" "b")] x))
           "(a + b)"))

    (is (= (dut/AND (for [x '("a" "b")] x))
           "(a AND b)"))

    (is (= (dut/OR (for [x '("a" "b")] x))
           "(a OR b)")))

  (testing "mixed sequences and strings"
    (is (= (dut/AND "A" (for [x '("a" "b")] x) "B")
           "(A AND a AND b AND B)"))

    (is (= (dut/OR "A" (for [x '("a" "b")] x) "B")
           "(A OR a OR b OR B)")))

  (testing "compound EQ"
    (is (= (dut/AND (test-eq :query))
           (str "(" (test-eq :string) ")")))

    (is (= (dut/AND (test-eq :query) (test-eq :query))
           (str "(" (test-eq :string) " AND " (test-eq :string) ")"))))
