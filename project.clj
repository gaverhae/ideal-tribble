(defproject t "app"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot t.core
  :target-path "target/%s"
  :plugins [[lein-autoexpect "1.9.0"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[expectations "2.2.0-rc3"]]}})
