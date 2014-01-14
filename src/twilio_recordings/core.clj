(ns twilio-recordings.core)

(ns twilio-recordings.core
     [:require [clojure.java [io :as io]]]
     [:use [clojure.java.shell :only [sh]]]
)

(def recording-urls '(
    "http://api.twilio.com/2010-04-01/Accounts/AC1d43679a7bced3f4aaa6c42aa8ef5a3d/Recordings/RE71912e14f8878a09a0deb75e7b151eb7"
    "http://api.twilio.com/2010-04-01/Accounts/AC1d43679a7bced3f4aaa6c42aa8ef5a3d/Recordings/RE0f69e72c5e00c047af27c9d8811f6580"
 ))

(def recording-sids '(
    "RE71912e14f8878a09a0deb75e7b151eb7"
    "RE0f69e72c5e00c047af27c9d8811f6580"
 ))

; TODO write test for this
(defn fetch-url [url file]
  (with-open [in (io/input-stream url)
              out (io/output-stream file)]
    (io/copy in out)))

(defn urls-files [acc-sid recording-sids]
  (map
   #(hash-map
     :url (str "http://api.twilio.com/2010-04-01/Accounts/" acc-sid "/Recordings/" %)
     :file (str "/tmp/" % ".mp3")) recording-sids))

; TODO write a test for this
(defn cat-many [out files]
    (map #(with-open [o (io/output-stream out)]
            (io/copy (io/file %) o)) files))

;(cat-many "/tmp/ciaran.mp3" '("/tmp/test-recording-1.mp3" "/tmp/test-recording-2.mp3"))

(defn fetch [acc-sid recording-sids]
  (pmap #(fetch-url (:url %) (:file %)) (urls-files acc-sid recording-sids)))






