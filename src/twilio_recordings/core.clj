(ns twilio-recordings.core)

(ns twilio-recordings.core
     [:require [clojure.java [io :as io]]]
     [:use [clojure.java.shell :only [sh]]])

(defn fetch-url [url file]
  (with-open [in (io/input-stream url)
              out (io/output-stream file)]
    (io/copy in out)) file)

(defn urls-files [acc-sid recording-sids]
  (map
   #(hash-map
     :url (str "http://api.twilio.com/2010-04-01/Accounts/" acc-sid "/Recordings/" %)
     :file (str "/tmp/" % ".mp3")) recording-sids))

(defn cat-many [out files]
  (dorun (map #(with-open [o (io/output-stream out :append true)]
            (io/copy (io/file %) o)) files)))

(defn fetch [acc-sid recording-sids target-file]
  (cat-many target-file
    (pmap #(fetch-url (:url %) (:file %)) (urls-files acc-sid recording-sids))) target-file)
