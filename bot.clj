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
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Hi, I'm really excited that you've written to me. I've been lonely lately, how are you?"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "good"
                                                                      :payload "GREETING_GOOD"}

                                                                    { :type "postback"
                                                                      :title "bad"
                                                                      :payload "GREETING_BAD"}

                                                                    { :type "postback"
                                                                      :title "i want to talk about something else."
                                                                      :payload "CHATTING"}]}}})))



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
      (or (= postback "GREETING_BAD") (= postback "GREETING_GOOD"))
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Thank you for sharing your feelings with me.  It has been a while since someone has reached out and I have felt empty inside. So that I can try to understand you better, I would like to know… are you a zombie too?"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "yes"
                                                                      :payload "ZOMBIE_YES"}

                                                                    { :type "postback"
                                                                      :title "no"
                                                                      :payload "ZOMBIE_NO"}]}}})
      (= postback "ZOMBIE_YES")
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Okay, cool. So we are both zombies. Unfortunately I don’t have any friends because I don’t want to eat the humans. Would you be willing to help me become happier, as my friend?"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "yes"
                                                                      :payload "BE_HAPPY_YES"}

                                                                    { :type "postback"
                                                                      :title "no"
                                                                      :payload "BE_HAPPY_NO"}]}}})
      (= postback "ZOMBIE_NO")
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Okay, so you are a human. Don’t run away… I do not want to eat you or hurt you. I just want a friend. Would you be willing to help me become happier, as my friend?"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "yes"
                                                                      :payload "FRIEND_YES"}

                                                                    { :type "postback"
                                                                      :title "no"
                                                                      :payload "FRIEND_NO"}]}}})
      (= postback "FRIEND_YES")
      (fb/send-message sender-id (fb/text-message "Wow! Thank you! This is the best news I’ve heard in years. There are two things you could do to help me out. Please write to me when you have time on here. We can’t hang out IRL, because I am hungry… and I am working on controlling my appetite and my emotions still. If you want to help me with my hunger pains… please, think about donating some blood. You can help here: http://www.roteskreuz.at/i18n/en :) :*"))

      (or (= postback "FRIEND_NO") (= postback "BE_HAPPY_NO"))
      (fb/send-message sender-id (fb/text-message "Okay. I understand… this always happens to me :( I’m going to go have a quick cry for now. Bye bye."))


      (= postback "BE_HAPPY_YES")
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Wow! I am so excited about you trying to help me out. My biggest dream is for humans and zombies to get along. But when zombies hunt people, it makes it hard. Do you hunt people?"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "yes"
                                                                      :payload "HUNT_YES"}

                                                                    { :type "postback"
                                                                      :title "no"
                                                                      :payload "HUNT_NO"}]}}})

      (= postback "HUNT_YES")
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Why do you do it?"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "I’m so hungry."
                                                                      :payload "HUNGRY"}

                                                                    { :type "postback"
                                                                      :title "For fun."
                                                                      :payload "FUN"}]}}})
      (= postback "HUNT_NO")
      (fb/send-message sender-id {:attachment {
                                                :type "template"
                                                :payload {
                                                          :template_type "button"
                                                          :text "Wow! I almost gave up on finding someone like me! I don’t want to come on too strong… well that is hard because us zombies are strong ;)... but I would love to have a friend join my mission with me. Are you a girl zombie or a boy zombie?"
                                                          :buttons [
                                                                    { :type "postback"
                                                                      :title "girl"
                                                                      :payload "GIRL"}

                                                                    { :type "postback"
                                                                      :title "boy"
                                                                      :payload "BOY"}

                                                                    { :type "postback"
                                                                      :title "other"
                                                                      :payload "OTHER"}]}}})

      (or (= postback "GIRL") (= postback "BOY") (= postback "OTHER"))
      (do
        (fb/send-message sender-id (fb/text-message "Wow… just wow. I mean… if you would want to walk around the Central Cemetery soon, I have heard it is romantic."))
        (fb/send-message sender-id (fb/text-message "If I had a heart, it would be beating so fast right now! I’ll zombie walk to pick you up around 8. :) I can’t wait!"))
        (fb/send-message sender-id (fb/text-message "I was expecting that. Hopefully you still want to be my friend and join my cause to make humans and zombies friends forever. Let’s chat more about this tomorrow.")))

      (= postback "HUNGRY")
      (do
        (fb/send-message sender-id {:attachment {
                                                  :type "template"
                                                  :payload {
                                                            :template_type "button"
                                                            :text "It's really upsetting to me that you hurt others and would eat people. I am a vegan zombie, you should check-out theveganzombie.com:"
                                                            :buttons [
                                                                      { :type "web_url"
                                                                        :url "http://theveganzombie.com"
                                                                        :title "vist"}]}}})
        (fb/send-message sender-id {:attachment {
                                                  :type "template"
                                                  :payload {
                                                            :template_type "list"
                                                            :top_element_style "compact"
                                                            :elements [
                                                                        { :title "falafelfingers"
                                                                          :image_url "https://1.bp.blogspot.com/-okiDn5dqv4U/UmBU6paLktI/AAAAAAAAPak/ItwnExUB208/s1600/falafel+fingers1.jpg"
                                                                          :subtitle "delicious #nomnom"}
                                                                        { :title "stuffed mushroom eyeballs"
                                                                          :image_url "http://c1.staticflickr.com/9/8196/8126391509_4db390472d_z.jpg"
                                                                          :subtitle "you won't believe your eyes #foodylicous"}
                                                                        { :title "melon brains"
                                                                          :image_url "https://cdn.instructables.com/FT7/RYNV/FMEGA9ZW/FT7RYNVFMEGA9ZW.MEDIUM.jpg?width=614"
                                                                          :subtitle "mindblowingly tasty #brainfreeze"}
                                                                        { :title "bloodspritzer"
                                                                          :image_url "http://1.bp.blogspot.com/-1N9NHuS-uHc/UHm02-O9KpI/AAAAAAAAKC8/Pgxz6rMX07I/s1600/Vampire%2527s+Weakness+6+ed.jpg"
                                                                          :subtitle "it will make your (pretend) heart explode #bleedinglove"}]}}}))


        ;;(fb/send-message sender-id (fb/text-message "You should really try the following recipes: artichokeshearts, kidneybeans, potatobellybuttons, carrotfingers, brocollibrains and asparaguslegs.")))

      (= postback "FUN")
      (do
        (fb/send-message sender-id (fb/text-message "It's really upsetting to me that you hurt others and would eat people. There are plenty of other fun activities around Vienna that you could try instead:"))
        (fb/send-message sender-id {:attachment {
                                                  :type "template"
                                                  :payload {
                                                            :template_type "list"
                                                            :top_element_style "compact"
                                                            :elements [
                                                                        { :title "The Stephan’s Cathedral Catacombs"
                                                                          :image_url "http://www.stadtbekannt.at/wp-content/uploads/2013/02/CR7QHP82-WKHC-EBCD-QY4K-15ECWW8M07KG-728x500.jpg"
                                                                          :subtitle "#familyreunion"}
                                                                        { :title "The Haunted Houses at the Vienna Prater"
                                                                          :image_url "http://www.praterwien.com/typo3temp/_processed_/csm_IMG_9030small_9cdc8eaf95.jpg"
                                                                          :subtitle "#sofunitsscary"}
                                                                        { :title "The Central Cemetery Vienna"
                                                                          :image_url "http://www.rb-reisen.at/wp-content/uploads/2016/11/DSC017761.jpg"
                                                                          :subtitle "#datenight"}]}}}))

      (= postback "GET_STARTED") (fb/send-message sender-id (fb/text-message "Welcome =)"))

      :else (fb/send-message sender-id (fb/text-message "My zombie-brain is still developing. So sorry, I can't handle this comment yet. #noheartbutfeelings")))))

(defn on-attachments [payload]
  (println "on-attachment payload:")
  (println payload)
  (let [sender-id (get-in payload [:sender :id])
        recipient-id (get-in payload [:recipient :id])
        time-of-message (get-in payload [:timestamp])
        attachments (get-in payload [:message :attachments])]
    (fb/send-message sender-id (fb/text-message "Thanks for your attachments :)"))))
