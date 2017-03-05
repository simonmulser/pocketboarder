(ns facebook-example.bot
  (:gen-class)
  (:require [clojure.string :as s]
            [environ.core :refer [env]]
            [facebook-example.facebook :as fb]))

(defn send-greeting-message [sender-id]
  (fb/send-message sender-id (fb/text-message "Hey, welcome to Pocket Boarder! Which slope would you like to shred today? 🏂 🗻  "))
  (fb/send-message sender-id {:attachment {
                                            :type "template"
                                            :payload {
                                                      :template_type "button"
                                                      :text "Slope options:"
                                                      :buttons [
                                                                { :type "postback"
                                                                  :title "Filzmoos 🗻"
                                                                  :payload "FILZMOOS"}

                                                                { :type "postback"
                                                                  :title "Ramsau 🌁"
                                                                  :payload "RAMSAU"}

                                                                { :type "postback"
                                                                  :title "Graukogel 🚠"
                                                                  :payload "GRAUKOGEL"}]}}}))

(defn send-game [sender-id]
  (fb/send-message sender-id {:attachment {
                                            :type "template"
                                            :payload {
                                                      :template_type "button"
                                                      :text "Ready...set...go!"
                                                      :buttons [
                                                                { :type "web_url"
                                                                  :url "https://simonmulser.github.io/pocketboarder/"
                                                                  :title "START"
                                                                  :webview_height_ratio "full"
                                                                  :messenger_extensions true
                                                                  :webview_share_button "hide"}]}}}))

(defn on-message [payload]
  (println "on-message payload:")
  (println payload)
  (let [sender-id (get-in payload [:sender :id])
        recipient-id (get-in payload [:recipient :id])
        time-of-message (get-in payload [:timestamp])
        message-text (get-in payload [:message :text])]
      (send-greeting-message sender-id)))

(defn on-postback [payload]
  (println "on-postback payload:")
  (println payload)
  (let [sender-id (get-in payload [:sender :id])
        recipient-id (get-in payload [:recipient :id])
        time-of-message (get-in payload [:timestamp])
        postback (get-in payload [:postback :payload])
        referral (get-in payload [:postback :referral :ref])]
    (cond

      (= postback "FILZMOOS")
      ( do
        (fb/send-message sender-id (fb/text-message "Right on! Remember your goal is to get the fastest speed and to avoid obstacles. Good luck at Filzmoos! ❄️️ 💪"))
        (fb/send-message sender-id (fb/image-message "http://www.stoefflerhof.com/de/images/sommer-winter/dreizinnen-winter-gr.jpg"))
        (send-game sender-id))

      (= postback "RAMSAU")
      ( do
        (fb/send-message sender-id (fb/text-message "Sweet brah. We hope you're ready for Ramsau! Remember the goal is to go as fast as possible while watching out for the obstacles. 😎"))
        (fb/send-message sender-id (fb/image-message "https://s-media-cache-ak0.pinimg.com/564x/22/ab/cc/22abccf94cd21274c471a665fb3089cc.jpg"))
        (send-game sender-id))

      (= postback "GRAUKOGEL")
      ( do
        (fb/send-message sender-id (fb/text-message "Sick! Enjoy Graukogel and don't forget your goals! Get the fastest time possible without wiping out. Let's go!⛄"))
        (fb/send-message sender-id (fb/image-message "http://www.skiamade.com/website/var/tmp/image-thumbnails/1750000/1759940/thumb__headerImage/alpendorf-betterpark-snowboard.jpeg"))
        (send-game sender-id))

      (= postback "GET_STARTED")
      (send-greeting-message sender-id)

      :else (fb/send-message sender-id (fb/text-message "Sorry, it seems I've got brain freeze right now. I don't quite understand what you want from me.")))))

(defn on-attachments [payload]
  (println "on-attachment payload:")
  (println payload)
  (let [sender-id (get-in payload [:sender :id])
        recipient-id (get-in payload [:recipient :id])
        time-of-message (get-in payload [:timestamp])
        attachments (get-in payload [:message :attachments])]
    (fb/send-message sender-id (fb/text-message "Thanks for your attachments :)"))))
