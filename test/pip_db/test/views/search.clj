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
    (is (= (dut/search "foo" {:results-count 0})
           (str "<!DOCTYPE html>\n<html class=\"no-js\" lang=\"en\"><head><meta"
                " charset=\"utf-8\"><meta content=\"IE=edge,chrome=1\" http-equ"
                "iv=\"X-UA-Compatible\"><meta content=\"width=device-width, ini"
                "tial-scale=1\" name=\"viewport\"><meta content=\"Protein Isoel"
                "ectric Point Database.\" name=\"msapplication-tooltip\"><title"
                ">pip-db foo</title><link href=\"/css/styles.css\" rel=\"styles"
                "heet\" type=\"text/css\"><script src=\"/js/modernizr-2.7.0.min"
                ".js\" type=\"text/javascript\"></script><script>(function(a,e,"
                "f,g,b,c,d){a.GoogleAnalyticsObject=b;a[b]=a[b]||function(){(a["
                "b].q=a[b].q||[]).push(arguments)};a[b].l=1*new Date;c=e.create"
                "Element(f);d=e.getElementsByTagName(f)[0];c.async=1;c.src=g;d."
                "parentNode.insertBefore(c,d)})(window,document,\"script\",\"//"
                "www.google-analytics.com/analytics.js\",\"ga\");ga(\"create\","
                "\"UA-47069255-1\",\"pip-db.org\");ga(\"send\",\"pageview\");\n"
                "</script></head><body><div class=\"navbar navbar-fixed-top nav"
                "bar-default\"><div class=\"container\"><div class=\"navbar-hea"
                "der\"><button class=\"navbar-toggle\" data-target=\"navbar-col"
                "lapse\" data-toggle=\"collapse\" type=\"button\"><span class="
                "\"icon-bar\"></span><span class=\"icon-bar\"></span><span clas"
                "s=\"icon-bar\"></span></button><a class=\"navbar-brand\" href="
                "\"/\">pip-db</a></div><div class=\"navbar-collapse collapse\">"
                "<div class=\"navbar-search col-sm-7 col-md-7\"><form action=\""
                "/s\" class=\"navbar-form\" role=\"search\"><div class=\"input-"
                "group\"><input autocomplete=\"off\" class=\"form-control\" id="
                "\"q\" name=\"q\" type=\"text\" value=\"foo\"><div class=\"inpu"
                "t-group-btn\"><button class=\"btn btn-success\" type=\"submit"
                "\">Search</button></div></div></form></div><ul class=\"nav nav"
                "bar-nav\"><li><div class=\"navbar-button-group\"><div class=\""
                "navbar-button\"><a class=\"btn btn-primary\" href=\"/advanced"
                "\">Advanced</a></div></div></li></ul><ul class=\"nav navbar-na"
                "v navbar-right\"><li><a href=\"/login\">Login</a></li></ul></d"
                "iv></div></div><div id=\"wrap\"><div class=\"container\"><div "
                "class=\"page-title\"><div class=\"page-title-inner\"><div clas"
                "s=\"download\"><a class=\"btn btn-warning\" href=\"/\">Downloa"
                "d</a></div><div class=\"info\"><ul class=\"meta-tags\"><li>Fou"
                "nd 0 results...</li></ul></div></div><hr></div><div class=\"sr"
                "esults\"><p class=\"lead\">No results found.</p></div></div></"
                "div><div class=\"navbar navbar-default navbar-bottom\"><div cl"
                "ass=\"container\"><div class=\"navbar-header\"><button class="
                "\"navbar-toggle\" data-target=\"navbar-collapse\" data-toggle="
                "\"collapse\" type=\"button\"><span class=\"icon-bar\"></span><"
                "span class=\"icon-bar\"></span><span class=\"icon-bar\"></span"
                "></button></div><div class=\"navbar-collapse collapse\"><ul cl"
                "ass=\"nav navbar-nav\"><li><a href=\"#\">About</a></li><li><a "
                "href=\"#\">Contact</a></li></ul><ul class=\"nav navbar-nav nav"
                "bar-right\"><li><a href=\"#\">Terms</a></li><li><a href=\"#\">"
                "Privacy</a></li></ul></div></div></div><script src=\"//ajax.go"
                "ogleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\" type=\"te"
                "xt/javascript\"></script><script>window.jQuery || document.wri"
                "te('<script src=\"/js/jquery-1.10.2.min.js\"><\\/script>');</s"
                "cript><script src=\"/js/bootstrap-3.0.1.min.js\" type=\"text/j"
                "avascript\"></script><script src=\"/js/main.js\" type=\"text/j"
                "avascript\"></script><script src=\"/js/moment.min.js\" type=\""
                "text/javascript\"></script><script>$(document).ready(function("
                "){$(\".sresults table tr\").click(function(){window.location="
                "\"/record/\"+$(this).attr(\"data-id\")});var c=$(\"#pagination"
                "\").attr(\"data-results-per-page\"),b=window.location.toString"
                "().replace(/&start=\\d+/,\"\"),d=function(a){return 1<a?b+\"&s"
                "tart=\"+(a-1)*c:b};$(\"#pagination a.page-ref\").each(function"
                "(a){$(this).attr(\"href\",d($(this).attr(\"data-page\")))})});"
                "\n</script></body></html>")))))
