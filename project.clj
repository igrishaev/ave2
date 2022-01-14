(defproject ave2 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies
  [[org.clojure/clojure "1.10.3"]
   [integrant "0.8.0"]
   [metosin/sieppari "0.0.0-alpha13"]

   [exoscale/interceptor "0.1.9"]

   [manifold "0.2.3"]
   [ch.qos.logback/logback-classic "1.2.3"]
   [cheshire "5.10.0"]
   [clj-http "3.12.0"]
   [metosin/reitit-core "0.5.15"]
   [ring/ring-jetty-adapter "1.9.4"]
   [org.clojure/tools.logging "1.1.0"]

   [ring/ring-json "0.5.1"]]

  :main ^:skip-aot ave2.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
