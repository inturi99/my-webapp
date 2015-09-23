
(ns my-webapp.db
  (:require [clojure.java.jdbc :as sql]))
(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "db/my-webapp"})
;; (let [db-host "127.0.0.1"
;;       db-port 3306
;;       db-name "my-webap"]
;;   (def db-spec {:classname "com.mysql.jdbc.Driver"
;;            :subprotocol "mysql"
;;            :subname (str "//" db-host ":" db-port "/" db-name)
;;            :user "root"
;;            :password "Design_20"})

(defn add-location-to-db
  [x y]
  (let [results (sql/with-connection db-spec
                  (sql/insert-record :locations
                                     {:x x :y y}))]
    (assert (= (count results) 1))
    (first (vals results))))

(defn get-xy
  [loc-id]
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["select x, y from locations where id = ?" loc-id]
                    (doall res)))]
    (assert (= (count results) 1))
    (first results)))

(defn get-all-locations
  []
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["select id, x, y from locations"]
                    (doall res)))]
    results))
