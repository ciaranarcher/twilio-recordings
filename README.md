# twilio-recordings

A Clojure implementation of Paul's https://github.com/baxter/twilio_recordings.

Ported from Ruby and exposing my many sins as a novice Clojure programmer. Also
it actually doesn't work correctly (the only recording played is the last one
appended even though the file length is correct) but it served me well to learn
more about Clojure.

## Usage

```
(twilio-recordings.core/fetch "AC1234" ("RC123" "RC456") "/tmp/all-recordings.mp3")
```

It should fetch all recordings, and concatenate them locally to the given file.

## Things I Learned

### The good
* How to use `clojure.java.io`.
* How to use `pmap` to go nuts in parallel.
* How to convert between sequences and hash-maps.
* How to setup before and after fixtures for tests.

### Must improve...
* I don't know why I need `dorun` in places.
* I don't know how to `use` and `require` things and when to use each.
