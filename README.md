# ferro-rnd-150415

This is a spring boot application where we wanted to test embedding an mqtt-client, camel & websocket. Its GUI is angular-based and simply shows data that arrives to the topics is presentented in the flowchart below. The GUI is also able to send data to the chat-topic via a websocket -> mqtt route. The whole server has been tested to run smoothly on a raspberry pi.

To run: 

1. Clone the repo `git clone https://github.com/jakobthun/ferro-rnd-150415.git`
2. Run on windows `.\gradlew.bat bootRun` or on mac/linux `./gradlew.sh bootRun`
3. Open browser on `http://localhost:3333` or the port you set in `application.properties`

It requires a running mqtt-broker, currently there is a mosquitto broker running at backend.ipaas.io:10383. If it is down you may have to run your own broker. In that case use `application.properties` to configure your own broker URL.

```
                       +-------------------------+                               
                       |           GUI           +---------------------+         
                       +----^----------------^---+                     |         
                            |                |                         |         
                            |                |                         |         
 +--------------------------+----+     +-----+----------------------+  |         
 |websocket:topic/chat.sensordata|     |websocket:topic/chat.message|  |         
 +--------------^----------------+     +----------------^-----------+  |         
                |                                       |              |         
                |                                       |              |         
     +----------+----------+                   +--------+------+       |         
     |mqtt:topic:sensordata|      +------------>mqtt:topic:chat|       |         
     +----------^----------+      |            +--------^------+       |         
                |                 |                     |              |         
                |                 |                     |              |         
                |                 |            +--------+--------------v--------+
+---------------+-----------------v-+          |websocket:topic/chat.message.out|
|          Other system             |          +--------------------------------+
|         (or IoT device)           |                                            
|                                   |                                            
|Can optionally publish or subscribe|                                            
|                                   |                                            
|       to chat or sensordata       |                                            
|                                   |                                            
|                                   |                                            
+-----------------------------------+                                            
```
