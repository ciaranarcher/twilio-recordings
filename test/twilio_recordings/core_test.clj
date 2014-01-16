(ns twilio-recordings.core-test
  [:require [clojure.java [io :as io]]]
  [:use [twilio-recordings [core :as twl-recs]]]
  [:use clojure [test :as test])

(def temp-files '("/tmp/piece1.dat" "/tmp/piece2.dat" "/tmp/piece3.dat"))

(defn file-setup [f]
  (dorun (map #(io/writer %) temp-files))
  (f)
  (dorun (map #(io/delete-file %) temp-files)))

(use-fixtures :each file-setup)

(deftest test-urls-files
  (let [recordings-sids '("RE123" "RE456" "RE789")]
  (let [acc-sid "AC112233"]
  (let [expected '({:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE123" :file "/tmp/RE123.mp3"}
                    {:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE456" :file "/tmp/RE456.mp3"}
                    {:url "http://api.twilio.com/2010-04-01/Accounts/AC112233/Recordings/RE789" :file "/tmp/RE789.mp3"})]

  (testing "creating file paths and urls from recordings sids"
    (is (= (twl-recs/urls-files acc-sid recordings-sids) expected)))))))

(deftest test-cat-many
  (testing "concatenating many files"
    (let [target-file "/tmp/test.dat"]
    (twl-recs/cat-many target-file temp-files)
    (is (= (.exists (io/as-file target-file)) true)))))

(deftest test-fetch-url ;This will hit the internets. Alternative solutions?
  (testing "fetching urls and saving to disk"
    (let [target-file "/tmp/doge.jpg"]
    (twl-recs/fetch-url "http://i.huffpost.com/gen/1451579/thumbs/o-DOGE-570.jpg?6" target-file)
    (is (= (.exists (io/as-file target-file)) true))
    (io/delete-file target-file))))

(deftest test-fetch
  (testing "fetching works and creates a new single recording"
    (let [target-file "/tmp/all.mp3"]
    (let [recording-sids '("RE93fcf1c3912aea0db664914147789e10"
                           "REcd5c5bec7ff667d5c3f1502d04ccb79e"
                           "REf7b24375cdbbbb42f58828f2fdf7b5a9"
                           "RE23c45b6262e256a455b1ee296af53fbb")]
    (twl-recs/fetch "ACeb4e7b38952d70a91bc4a4acea8dc9e0" recording-sids target-file)
    (is (= (.exists (io/as-file target-file)) true))))))
