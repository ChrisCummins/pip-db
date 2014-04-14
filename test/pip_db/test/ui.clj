(ns pip-db.test.ui
  (:use [clojure.test]
        [clojure.java.shell :only (sh)])
  (:require [pip-db.ui :as dut]))

;; Our Google Analytics tracking snippet. See:
;; resources/public/js/google-analytics.inline.js.
(def tracking-code (str "(function(a,e,f,g,b,c,d){a.GoogleAnalyticsObject=b;"
                        "a[b]=a[b]||function(){(a[b].q=a[b].q||[])"
                        ".push(arguments)};a[b].l=1*new Date;c=e"
                        ".createElement(f);d=e.getElementsByTagName(f)[0];"
                        "c.async=1;c.src=g;d.parentNode.insertBefore(c,d)})"
                        "(window,document,\"script\",\"//"
                        "www.google-analytics.com/analytics.js\",\"ga\");"
                        "ga(\"create\",\"UA-47069255-1\",\"pip-db.org\");"
                        "ga(\"send\",\"pageview\");"))

(deftest google-analytics
  (testing "Google analytics tracking code"
    (is (= (dut/google-analytics)
           [:script (str tracking-code "\n")]))))

;; Widgets

(deftest label-widget
  (testing "Widget contents"
    (is (not (= (dut/label-widget "foo")
                (dut/label-widget "bar")
                (dut/label-widget "bar" "foo")
                (dut/label-widget "foo" "bar"))))))

(deftest info-widget
  (testing "Widget contents"
    (is (not (= (dut/info-widget "foo")
                (dut/info-widget "bar"))))))

(deftest text-input-widget
  (testing "Widget contents"
    (is (not (= (dut/text-input-widget "foo" "" "bar")
                (dut/text-input-widget "bar" "" "foo")))))
  (testing "Placeholder text"
    (is (not (= (dut/text-input-widget "foo" "bar" "")
                (dut/text-input-widget "bar" "foo" ""))))))

(deftest hidden-input-widget
  (testing "Widget contents"
    (is (not (= (dut/hidden-input-widget "foo")
                (dut/hidden-input-widget "bar")
                (dut/hidden-input-widget "foo" "bar")
                (dut/hidden-input-widget "bar" "foo"))))))

(deftest button-input-widgets
  (testing "On Widget contents"
    (is (not (= (dut/on-off-button-input-widget "foo")
                (dut/on-off-button-input-widget "bar")))))

  (testing "Off Widget contents"
    (is (not (= (dut/off-on-button-input-widget "foo")
                (dut/off-on-button-input-widget "bar")))))

  (testing "Button defaults"
    (is (not (= (dut/on-off-button-input-widget "foo")
                (dut/off-on-button-input-widget "foo"))))))

(deftest range-slider-input-widget
  (testing "Widget contents"
    (is (not (= (dut/range-slider-input-widget "foo" "bar" "baz")
                (dut/range-slider-input-widget "bar" "bar" "baz")
                (dut/range-slider-input-widget "foo" "baz" "bar"))))))

;; Search form widgets

(deftest search-form-heading-row
  (testing "Heading text"
    (is (not (= (dut/search-form-heading-row "foo")
                (dut/search-form-heading-row "bar")))))

  (testing "Heading ID"
    (is (not (= (dut/search-form-heading-row "foo")
                (dut/search-form-heading-row "bar" "foo")
                (dut/search-form-heading-row "baz" "foo"))))))

(deftest search-form-text-input-widget
  (testing "Widget contents"
    (is (not (= (dut/search-form-text-input-widget "foo" "" "bar")
                (dut/search-form-text-input-widget "bar" "" "foo")))))
  (testing "Placeholder text"
    (is (not (= (dut/search-form-text-input-widget "foo" "bar" "")
                (dut/search-form-text-input-widget "bar" "foo" ""))))))

(deftest search-form-widget-row
  (testing "Row contents"
    (is (not (= (dut/search-form-widget-row "foo" "bar" "baz")
                (dut/search-form-widget-row "bar" "foo" "baz"))))))

(deftest search-form-text-row
  (testing "Row contents"
    (is (not (= (dut/search-form-text-row "foo" "bar" "baz")
                (dut/search-form-text-row "bar" "foo" "baz")
                (dut/search-form-text-row "foo" "bar" "baz" "caz")
                (dut/search-form-text-row "foo" "bar" "baz" "car"))))))

;; Main search bar

(deftest search-bar-buttons
  (testing "Buttons"
    (is (not (= dut/submit-button
                dut/advanced-button)))))

(deftest search-bar
  (testing "Search text"
    (is (not (= (dut/search-bar {:params {}})
                (dut/search-bar {:params {"q" "foo"}})
                (dut/search-bar {:params {"q" "bar"}}))))))

;; Page heading

(deftest heading
  (testing "Heading contents"
    (is (not (= (dut/heading {})
                (dut/heading {:title "Foo"})
                (dut/heading {:title "Bar"})
                (dut/heading {:download true})
                (dut/heading {:meta true :meta-results-count 0})
                (dut/heading {:meta true :meta-results-count 1})
                (dut/heading {:meta true :meta-results-count 10}))))))

;; Images

(deftest logos
  (testing "Logo paths"
    (is (not (= (dut/logo-path "10")
                (dut/logo-path "20")))))

  (testing "Logos"
    (is (not (= dut/big-logo
                dut/small-logo)))))

;; Navbar

(deftest navbar-search
  (testing "Search text"
    (is (not (= (dut/navbar-search {:params {}})
                (dut/navbar-search {:params {"q" "foo"}}))))))

(deftest navbar
  (testing "Content types"
    (is (not (= (dut/navbar {:params {} :navbar {}})
                (dut/navbar {:params {} :navbar {:login-only true}})
                (dut/navbar {:params {} :navbar {:search true}})
                (dut/navbar {:params {} :navbar {:session {:user "foo"}}}))))))

;; Page

(deftest page
  (testing "No data"
    (is (= (class (dut/page {}))
           java.lang.String))))

;; Errors

(deftest page-404
  (let [page (dut/page-404)]

    (testing "Response type"
      (is (map? page)))

    (testing "Response status"
      (is (= 404 (page :status))))

    (testing "Content Body"
      (is (contains? page :body))
      (is (= (class (page :body))
             java.lang.String)))))

(deftest page-500
  (let [page (dut/page-500 (Exception. "Test page"))]

    (testing "Response type"
      (is (= (class page)
             java.lang.String)))))
