(ns twilio-recordings.core-test
  (:use clojure.test
        twilio-recordings.core))

(deftest test-urls
  (def recordings-sids '("RE123" "RE456" "RE789"))
  (def acc-sid "AC112233")

  (testing "creating file paths and urls from recordings sids"
    (def expected '({:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE123" :file "/tmp/RE123.mp3"}
                    {:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE456" :file "/tmp/RE456.mp3"}
                    {:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE789" :file "/tmp/RE789.mp3"}))
    (is (= (twilio-recordings.core/urls-files acc-sid recordings-sids) expected))))

