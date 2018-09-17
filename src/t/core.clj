(ns t.core
  (:require [clojure.string :as string])
  (:gen-class))

(defn init-state
  "Returns an initial, empty state for the game."
  []
  {:image nil
   :width 0
   :height 0})

(def help-text
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

(defn print-help
  [state args]
  {:state state
   :out help-text})

(defn quit
  [state args]
  {:state nil
   :out ["Bye!"]})

(defn unrecognized-cmd
  [state args]
  {:state state
   :out (cons "Unrecognized command."
              help-text)})

(defn make-blank
  [w h]
  (->> (repeat w "O")
       vec
       (repeat h)
       vec))

(defn safe-parse
  [args n]
  (when (= n (count args))
    (try (map #(Long/parseLong %) args)
         (catch Exception e nil))))

(defn init-image
  [state args]
  (if-let [[w h] (safe-parse args 2)]
    {:state {:image (make-blank w h)
             :width w
             :height h}
     :out []}
    (print-help state args)))

(defn clear
  [{:keys [width height] :as state} args]
  {:state (assoc state :image (make-blank width height))
   :out []})

(def no-image
  (str "You do not have any image at the moment. Create one with the I command."
       " Type H for further help."))

(defn show-image
  [state args]
  {:state state
   :out (if-let [img (:image state)]
          (mapv string/join img)
          [no-image])})

(defn colour-pixel
  [state args]
  (let [[x y :as r] (safe-parse (take 2 args) 2)
        c (nth args 2)]
    (if (and r (= 3 (count args)) (= 1 (count c)))
      {:state (assoc-in state [:image (dec y) (dec x)] c)
       :out []}
      (print-help state args))))

(defn fill
  [state args]
  (let [[x y :as r] (safe-parse (take 2 args) 2)
        c (nth args 2)]
    (if (and r (= 3 (count args)) (= 1 (count c)))
      {:out []
       :state
       (assoc state
              :image
              (let [init-pos [(dec x) (dec y)]
                    in-image? (fn [[x y]] (and (<= 0 x)
                                               (< x (:width state))
                                               (<= 0 y)
                                               (< y (:height state))))
                    neighbours (fn [[x y]] [[x (inc y)] [(inc x) y] [x (dec y)] [(dec x) y]])
                    colour (fn [[x y]] (get-in (:image state) [y x]))
                    old-c (colour init-pos)
                    new-c c
                    to-fill (loop [to-fill #{init-pos}
                                   seen #{init-pos}
                                   to-check (->> (neighbours init-pos)
                                                 (filter in-image?)
                                                 set)]
                              (if (seq to-check)
                                (let [n (first to-check)]
                                  (if (= old-c (colour n))
                                    (recur (conj to-fill n)
                                           (conj seen n)
                                           (reduce conj
                                                   (set (rest to-check))
                                                   (->> (neighbours n)
                                                        (filter in-image?)
                                                        (remove seen))))
                                    (recur to-fill
                                           (conj seen n)
                                           (set (rest to-check)))))
                                to-fill))]
                (reduce (fn [img [x y]]
                          (assoc-in img [y x] new-c))
                          (:image state)
                          to-fill)))}
      (print-help state args))))

(defn main-iter
  "Takes the current state of the game and the next input from the user, and
  returns the next state of the game, or nil if the game is over."
  [state input]
  (let [[cmd & args] input
        f (get {\C clear
                \F fill
                \H print-help
                \I init-image
                \L colour-pixel
                \S show-image
                \X quit}
               cmd
               unrecognized-cmd)]
    (f state (as-> args $
               (apply str $)
               (string/trim $)
               (string/split $ #" +")))))

(defn -main
  [& args]
  (loop [state (init-state)]
    (when state
      (print "> ")
      (flush)
      (when-let [l (read-line)]
        (let [{:keys [state out]} (main-iter state l)]
          (run! println out)
          (recur state))))))
