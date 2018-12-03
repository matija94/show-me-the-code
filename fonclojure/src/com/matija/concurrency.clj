(ns com.matija.concurrency)


(future (Thread/sleep 1000)
        (println "I'll print after second"))

(println "I'll print immediately")


; deref future
(let [result (future (Thread/sleep 2000)
                     (+ 1 1))]
  (println "Waiting for task to compute")
  (println "result is " @result))

; timeout a future
(let [result (future (Thread/sleep 10000) (+ 1 1))]

  (println "Waiting for task to compute")
  (let [computed_val (deref result 1000 15)]
    (println "computed_val = " computed_val)))

; check if thread is finished computing
(let [result (future (Thread/sleep 3000) (+ 1 1))]
  (println "result realized = " (realized? result)))


; delays - they are not started and evaluated upon creation but once their result is requested
(def jackson-5-delay
  (delay (let [message "Just call my name and I'll be there"]
           (println "First deref:" message)
           message)))
(force jackson-5-delay)



; promises - can be used to store value of future task. This is good practice since promises could be written to only once

(let [my-promise (promise)]
  (future (Thread/sleep 1000) (deliver my-promise (+ 1 1)))
  (println "waiting for my-promise to deliver")
  (println "my-promise = " @my-promise))


(println "Starting exercises")
; exercises

(defn web-search
  ([keyword & web-engines]
   (let [url_template "https://www.%s.com/search?q=%s"
         res (promise)]
     (doseq [web-engine web-engines]
       (future (deliver res (slurp (format url_template web-engine keyword)))))
     @res))
  ([keyword]
    (web-search keyword "bing")))

(spit "/Users/matija/Desktop/web-search.html" (web-search "hello" "google" "bing"))

(println "End of main thread")