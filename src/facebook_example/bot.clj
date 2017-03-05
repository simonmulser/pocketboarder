(ns facebook-example.bot
  (:gen-class)
  (:require [clojure.string :as s]
            [environ.core :refer [env]]
            [facebook-example.facebook :as fb]))

(defn on-message [payload]
  (println "on-message payload:")
  (println payload)
  (let [sender-id (get-in payload [:sender :id])
        recipient-id (get-in payload [:recipient :id])
        time-of-message (get-in payload [:timestamp])
        message-text (get-in payload [:message :text])]
    ;;(cond
      ;;(s/includes? (s/lower-case message-text) "hi")
      (fb/send-message sender-id (fb/text-message "Hey, welcome to Pocket Boarder! Which slope would you like to shred today? 🏂 🗻  "))
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Slope options:"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "Filzmoos"
                                                                      :payload "FILZMOOS"}

                                                                    { :type "postback"
                                                                      :title "Ramsau"
                                                                      :payload "RAMSAU"}

                                                                    { :type "postback"
                                                                      :title "Graukogel"
                                                                      :payload "GRAUKOGEL"}]}}})))

      ;;(s/includes? (s/lower-case message-text) "hello") (fb/send-message sender-id (fb/text-message "Hi there, happy to help"))
      ;;(s/includes? (s/lower-case message-text) "fuck") (fb/send-message sender-id (fb/image-message "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/M101_hires_STScI-PRC2006-10a.jpg/1280px-M101_hires_STScI-PRC2006-10a.jpg"))
      ;; If no rules apply echo the user's message-text input
      ;;:else (fb/send-message sender-id (fb/text-message message-text))))

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
        (fb/send-message sender-id (fb/image-message "http://www.stoefflerhof.com/de/images/sommer-winter/dreizinnen-winter-gr.jpg")))
      (= postback "RAMSAU")
      ( do
        (fb/send-message sender-id (fb/text-message "Sweet brah. We hope you're ready for Ramsau! Remember the goal is to go as fast as possible while watching out for the obstacles. 😎"))
        (fb/send-message sender-id (fb/image-message "https://s-media-cache-ak0.pinimg.com/564x/22/ab/cc/22abccf94cd21274c471a665fb3089cc.jpg")))
      (= postback "GRAUKOGEL")
      ( do
        (fb/send-message sender-id (fb/text-message "Sick! Enjoy Graukogel and don't forget your goals! Get the fastest time possible without wiping out. Let's go!⛄"))
        (fb/send-message sender-id (fb/image-message "http://www.skiamade.com/website/var/tmp/image-thumbnails/1750000/1759940/thumb__headerImage/alpendorf-betterpark-snowboard.jpeg")))

      (= postback "GET_STARTED") (fb/send-message sender-id (fb/text-message "Welcome =)"))

      :else (fb/send-message sender-id (fb/text-message "Sorry, it seems I've got brain freeze right now. I don't quite understand what you want from me.")))))

(defn on-attachments [payload]
  (println "on-attachment payload:")
  (println payload)
  (let [sender-id (get-in payload [:sender :id])
        recipient-id (get-in payload [:recipient :id])
        time-of-message (get-in payload [:timestamp])
        attachments (get-in payload [:message :attachments])]
    (fb/send-message sender-id (fb/text-message "Thanks for your attachments :)"))))
