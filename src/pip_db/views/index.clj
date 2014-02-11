(ns pip-db.views.index
  (:use [pip-db.views.layout :only (page)]
        [pip-db.views.components :only (inline-js search-bar)]))

(defn index [data]
  (page {:navbar {:login-only true}
         :body (list [:div.row
                      [:div.search.search-block.text-center
                       [:img {:src "/img/logo-640x226.png"
                              :alt-text "pip-db"
                              :title "pip-db"}]

                       [:div#s (search-bar data)]]]
                     [:div.row
                      [:div.col-md-12
                       [:p.lead (str "Lorem ipsum dolor sit amet, consectetur "
                                     "adipiscing elit. Etiam ut aliquet nisi."
                                     "Phasellus sed felis eu ipsum ultricies "
                                     "vehicula. Ut sed urna vitae risus dictum "
                                     "condimentum vel at lorem.")]
                       [:p.lead (str "Vivamus iaculis congue est, quis rutrum "
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
                       [:p.lead (str "Feugiat vivamus leo et gravida "
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
                                     "condimentum in.")]]])}))
