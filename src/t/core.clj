(ns t.core
  (:gen-class))

(defn init-state
  "Returns an initial, empty state for the game."
  []
  {:image []
   :width 0
   :height 0})

(defn print-help
  [state args]
  (run! println
        ["You can use any of the following commands:"
         "I M N <ENTER> -> Create a new M x N image with all pixels coloured white (O)."
         "C <ENTER> -> Clears the table, setting all pixels to white (O)."
         "L X Y C <ENTER> -> Colours the pixel (X,Y) with colour C."
         "V X Y1 Y2 C <ENTER> -> Draw a vertical segment of colour C in column X between rows Y1 and Y2 (inclusive)."
         "H X1 X2 Y C <ENTER> -> Draw a horizontal segment of colour C in row Y between columns X1 and X2 (inclusive)."
         "F X Y C <ENTER> -> Fill the region R with the colour C. R is defined as: Pixel (X,Y) belongs to R. Any other pixel which is the same colour as (X,Y) and shares a common side with any pixel in R also belongs to this region."
         "S <ENTER> -> Show the contents of the current image."
         "H <ENTER> -> Show this help text."
         "X <ENTER> -> Terminate the session."])
  state)

(defn quit
  [state args]
  nil)

(defn unrecognized-cmd
  [state args]
  (println "Unrecognized command.")
  (print-help state args))

(defn main-iter
  "Takes the current state of the game and the next input from the user, and
  returns the next state of the game, or nil if the game is over."
  [state input]
  (let [[cmd & args] input
        f (get {\H print-help
                \X quit}
               cmd
               unrecognized-cmd)]
    (f state args)))

(defn -main
  [& args]
  (loop [state (init-state)]
    (when state
      (print "> ")
      (flush)
      (when-let [l (read-line)]
        (recur (main-iter state l))))))
