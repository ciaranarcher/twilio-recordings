(ns twilio-recordings.core-test
  [:require [clojure.java [io :as io]]]
  (:use clojure.test
        twilio-recordings.core))

(def temp-files '("/tmp/piece1.dat" "/tmp/piece2.dat" "/tmp/piece3.dat"))

(defn file-setup [f]
  (dorun (map #(io/writer %) temp-files))
  (f)
  (dorun (map #(io/delete-file %) temp-files)))

(use-fixtures :each file-setup)

(deftest test-urls-files
  (def recordings-sids '("RE123" "RE456" "RE789"))
  (def acc-sid "AC112233")

  (testing "creating file paths and urls from recordings sids"
    (def expected '({:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE123" :file "/tmp/RE123.mp3"}
                    {:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE456" :file "/tmp/RE456.mp3"}
                    {:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE789" :file "/tmp/RE789.mp3"}))
    (is (= (twilio-recordings.core/urls-files acc-sid recordings-sids) expected))))


(deftest test-cat-many
  (testing "concatenating many files"
    (def target-file "/tmp/test.dat")
    (twilio-recordings.core/cat-many target-file temp-files)
    (is (= (.exists (io/as-file target-file)) true))))
