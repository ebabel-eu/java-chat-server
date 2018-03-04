# java-chat-server
Server side Java chat server, following a [tutorial](http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html) 

There are 2 main parts: the chat server and the chat client.

## Build both Chat server and client
```
javac -d out/production/java-chat-server/ src/ChatServer.java src/ChatClient.java
```

## Run the chat server
```
java -cp /Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/ ChatServer 4444
```

To end the execution, use the keys `CTRL C` in Terminal.

## Run the chat client
```
java -cp /Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/ ChatClient localhost 4444
```
