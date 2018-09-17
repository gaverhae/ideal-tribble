(ns t.core
  (:gen-class))

(defn init-state
  "Returns an initial, empty state for the game."
  []
  {:image []
   :width 0
   :height 0})

(defn main-iter
  "Takes the current state of the game and the next input from the user, and
  returns the next state of the game, or nil if the game is over."
  [state input]
  nil)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
