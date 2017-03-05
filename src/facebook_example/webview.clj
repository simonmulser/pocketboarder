(ns facebook-example.webview
  (:gen-class)
  (:require [clojure.string :as s]
            [environ.core :refer [env]]
            [facebook-example.facebook :as fb]))

(defn handle-message [payload]
  (println "JUHU handle-messge webview payload:")
  (println payload))
