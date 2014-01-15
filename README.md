# twilio-recordings

A Clojure implementation of Paul's https://github.com/baxter/twilio_recordings. 

Ported from Ruby and exposing my many sins as a novice Clojure programmer. 

## Usage

```
(twilio-recordings.core/fetch "AC1234" ("RC123" "RC456") "/tmp/all-recordings.mp3")

```

Should fetch all recordings, and concatenate them locally to the given file.

