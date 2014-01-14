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

(deftest extern-links
  (testing "No external links"
    (is (= (dut/extern-links)
           [:div.panel.panel-primary.panel-extern
            [:div.panel-heading [:h3.panel-title "External Links"]]
            [:div.panel-body [:ul.panel-extern-list nil]]])))

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

(deftest record
  (testing "Page title and heading"
    (is (= (dut/record {:name "foo"})
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
                "\"q\" name=\"q\" type=\"text\"><div class=\"input-group-btn\">"
                "<button class=\"btn btn-success\" type=\"submit\">Search</butt"
                "on></div></div></form></div><ul class=\"nav navbar-nav\"><li><"
                "div class=\"navbar-button-group\"><div class=\"navbar-button\""
                "><a class=\"btn btn-primary\" href=\"/advanced\">Advanced</a><"
                "/div></div></li></ul><ul class=\"nav navbar-nav navbar-right\""
                "><li><a href=\"/login\">Login</a></li></ul></div></div></div><"
                "div id=\"wrap\"><div class=\"container\"><div class=\"page-tit"
                "le\"><div class=\"page-title-inner\"><div class=\"download\"><"
                "a class=\"btn btn-warning\" href=\"/\">Download</a></div><h3>f"
                "oo</h3></div><hr></div><div class=\"record\"><div class=\"row"
                "\"><div class=\"col-md-8\"><table class=\"table table-striped "
                "table-bordered\" id=\"properties\"><tbody><tr class=\"property"
                "\" data-key=\"name\"><td class=\"description\">Name</td><td cl"
                "ass=\"value\">foo</td></tr></tbody></table><div class=\"meta-h"
                "older\"><ul class=\"meta-tags\"><li id=\"date-added\"></li></u"
                "l></div></div><div class=\"col-md-4\"><div class=\"panel panel"
                "-primary panel-extern\"><div class=\"panel-heading\"><h3 class"
                "=\"panel-title\">External Links</h3></div><div class=\"panel-b"
                "ody\"><ul class=\"panel-extern-list\"></ul></div></div><div cl"
                "ass=\"panel panel-primary panel-reference\"><div class=\"panel"
                "-heading\"><h3 class=\"panel-title\">Reference this page</h3><"
                "/div><div class=\"panel-body\"><blockquote id=\"reference\"></"
                "div></div></div></div></div></div></div><div class=\"navbar na"
                "vbar-default navbar-bottom\"><div class=\"container\"><div cla"
                "ss=\"navbar-header\"><button class=\"navbar-toggle\" data-targ"
                "et=\"navbar-collapse\" data-toggle=\"collapse\" type=\"button"
                "\"><span class=\"icon-bar\"></span><span class=\"icon-bar\"></"
                "span><span class=\"icon-bar\"></span></button></div><div class"
                "=\"navbar-collapse collapse\"><ul class=\"nav navbar-nav\"><li"
                "><a href=\"#\">About</a></li><li><a href=\"#\">Contact</a></li"
                "></ul><ul class=\"nav navbar-nav navbar-right\"><li><a href=\""
                "#\">Terms</a></li><li><a href=\"#\">Privacy</a></li></ul></div"
                "></div></div><script src=\"//ajax.googleapis.com/ajax/libs/jqu"
                "ery/1.10.2/jquery.min.js\" type=\"text/javascript\"></script><"
                "script>window.jQuery || document.write('<script src=\"/js/jque"
                "ry-1.10.2.min.js\"><\\/script>');</script><script src=\"/js/bo"
                "otstrap-3.0.1.min.js\" type=\"text/javascript\"></script><scri"
                "pt src=\"/js/main.js\" type=\"text/javascript\"></script><scri"
                "pt src=\"/js/moment.min.js\" type=\"text/javascript\"></script"
                "><script>$(document).ready(function(){$(\"#reference\").html("
                "\"pip-db (2014). <i>\"+function(c){var a;$(\"#properties .prop"
                "erty\").each(function(b){b=$(this).attr(\"data-key\");if(c===b"
                ")return a=$(this).children(\".value\").text(),!1});return a}("
                "\"name\")+'</i>, Protein Isoelectric point Database. Available"
                " from: &lt;<a href=\"'+window.location+'\"\" class=\"url\">'+w"
                "indow.location+\"</a>&gt;<br/>[Cited \"+moment().format(\"Do M"
                "MMM, YYYY\")+\"].\");$(\"#date-added\").html(\"Added \"+moment"
                "($(\"#date-added\").attr(\"data-date\")).fromNow()+\n\".\")});"
                "\n</script></body></html>")))))

(deftest no-record
  (testing "Page contents"
    (is (= (dut/no-record)
           (str "<!DOCTYPE html>\n<html class=\"no-js\" lang=\"en\"><head><meta"
                " charset=\"utf-8\"><meta content=\"IE=edge,chrome=1\" http-equ"
                "iv=\"X-UA-Compatible\"><meta content=\"width=device-width, ini"
                "tial-scale=1\" name=\"viewport\"><meta content=\"Protein Isoel"
                "ectric Point Database.\" name=\"msapplication-tooltip\"><title"
                ">pip-db Not Found</title><link href=\"/css/styles.css\" rel=\""
                "stylesheet\" type=\"text/css\"><script src=\"/js/modernizr-2.7"
                ".0.min.js\" type=\"text/javascript\"></script><script>(functio"
                "n(a,e,f,g,b,c,d){a.GoogleAnalyticsObject=b;a[b]=a[b]||function"
                "(){(a[b].q=a[b].q||[]).push(arguments)};a[b].l=1*new Date;c=e."
                "createElement(f);d=e.getElementsByTagName(f)[0];c.async=1;c.sr"
                "c=g;d.parentNode.insertBefore(c,d)})(window,document,\"script"
                "\",\"//www.google-analytics.com/analytics.js\",\"ga\");ga(\"cr"
                "eate\",\"UA-47069255-1\",\"pip-db.org\");ga(\"send\",\"pagevie"
                "w\");\n</script></head><body><div class=\"navbar navbar-fixed-"
                "top navbar-default\"><div class=\"container\"><div class=\"nav"
                "bar-header\"><button class=\"navbar-toggle\" data-target=\"nav"
                "bar-collapse\" data-toggle=\"collapse\" type=\"button\"><span "
                "class=\"icon-bar\"></span><span class=\"icon-bar\"></span><spa"
                "n class=\"icon-bar\"></span></button><a class=\"navbar-brand\""
                " href=\"/\">pip-db</a></div><div class=\"navbar-collapse colla"
                "pse\"><div class=\"navbar-search col-sm-7 col-md-7\"><form act"
                "ion=\"/s\" class=\"navbar-form\" role=\"search\"><div class=\""
                "input-group\"><input autocomplete=\"off\" class=\"form-control"
                "\" id=\"q\" name=\"q\" type=\"text\"><div class=\"input-group-"
                "btn\"><button class=\"btn btn-success\" type=\"submit\">Search"
                "</button></div></div></form></div><ul class=\"nav navbar-nav\""
                "><li><div class=\"navbar-button-group\"><div class=\"navbar-bu"
                "tton\"><a class=\"btn btn-primary\" href=\"/advanced\">Advance"
                "d</a></div></div></li></ul><ul class=\"nav navbar-nav navbar-r"
                "ight\"><li><a href=\"/login\">Login</a></li></ul></div></div><"
                "/div><div id=\"wrap\"><div class=\"container\"><div class=\"pa"
                "ge-title\"><div class=\"page-title-inner\"></div><hr></div><di"
                "v class=\"record\"><p class=\"lead\">Record not found.</p></di"
                "v></div></div><div class=\"navbar navbar-default navbar-bottom"
                "\"><div class=\"container\"><div class=\"navbar-header\"><butt"
                "on class=\"navbar-toggle\" data-target=\"navbar-collapse\" dat"
                "a-toggle=\"collapse\" type=\"button\"><span class=\"icon-bar\""
                "></span><span class=\"icon-bar\"></span><span class=\"icon-bar"
                "\"></span></button></div><div class=\"navbar-collapse collapse"
                "\"><ul class=\"nav navbar-nav\"><li><a href=\"#\">About</a></l"
                "i><li><a href=\"#\">Contact</a></li></ul><ul class=\"nav navba"
                "r-nav navbar-right\"><li><a href=\"#\">Terms</a></li><li><a hr"
                "ef=\"#\">Privacy</a></li></ul></div></div></div><script src=\""
                "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\" "
                "type=\"text/javascript\"></script><script>window.jQuery || doc"
                "ument.write('<script src=\"/js/jquery-1.10.2.min.js\"><\\/scri"
                "pt>');</script><script src=\"/js/bootstrap-3.0.1.min.js\" type"
                "=\"text/javascript\"></script><script src=\"/js/main.js\" type"
                "=\"text/javascript\"></script><script src=\"/js/moment.min.js"
                "\" type=\"text/javascript\"></script></body></html>")))))
