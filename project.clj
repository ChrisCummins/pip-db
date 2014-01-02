(defproject pip-db "0.1.11"
  :description "Shouter app"
  :url "https://github.com/ChrisCummins/pip-db"
  :license {:name "GNU General Public License v3"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :dependencies [[compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [postgresql "9.1-901.jdbc4"]
                 [ring/ring-jetty-adapter "1.1.6"]]
  :main pip-db.core
  :aot [pip-db.core])
