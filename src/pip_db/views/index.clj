(ns pip-db.views.index
  (:require [pip-db.ui :as ui]))

;; The "above the fold" content, i.e. the main logo and search bar.
(defn search-block [request]
  [:div.search.search-block.text-center
   ui/big-logo (ui/search-bar request)])

;; The "below the fold" main body of text, containing a description of
;; what the site is, it's purpose, and how to use it.
(defn text-body [request]
  [:div.col-md-12
   [:p.lead
    (str "Lorem ipsum dolor sit amet, consectetur "
         "adipiscing elit. Etiam ut aliquet nisi."
         "Phasellus sed felis eu ipsum ultricies "
         "vehicula. Ut sed urna vitae risus dictum "
         "condimentum vel at lorem.")]
   [:p.lead
    (str "Vivamus iaculis congue est, quis rutrum "
         "mauris tempus quis. Praesent ligula "
         "libero, lobortis vel mauris et, ornare "
         "rutrum ligula. Morbi lectus ligula, "
         "luctus nec vestibulum ac, tincidunt a "
         "urna. Cras vel lectus at nibh consectetur"
         " fermentum quis at ligula. Quisque semper "
         "magna orci, at scelerisque metus "
         "tincidunt in. Integer orci dui, "
         "vestibulum nec posuere et, pellentesque "
         "et elit. Maecenas id ipsum a massa "
         "tincidunt euismod nec in orci. Duis sit "
         "amet purus vitae nulla tristique "
         "fermentum. Vestibulum eu urna ut massa "
         "sollicitudin aliquam. Sed quis vehicula "
         "nunc. Praesent lobortis tempus leo eu "
         "ornare.")]
   [:p.lead
    (str "Feugiat vivamus leo et gravida "
         "vestibulum. Pellentesque placerat, neque "
         "eu rutrum bibendum, felis justo venenatis "
         "erat, vel lacinia enim nisl ac metus. Nam "
         "viverra ut turpis ac euismod. Phasellus "
         "adipiscing tempus nunc. Mauris congue "
         "imperdiet sagittis. In rhoncus augue eget "
         "luctus interdum. Duis tempor nunc sit "
         "amet aliquet dignissim. Etiam rhoncus "
         "ante ut leo tristique, eu vehicula erat "
         "egestas. Nulla facilisi. Maecenas "
         "volutpat congue velit, vel feugiat quam "
         "condimentum in.")]])

(defn index [request]
  (ui/page request
           {:navbar {:login-only true}
            :body (list [:div.row (search-block request)]
                        [:div.row (text-body request)])}))
