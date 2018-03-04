# java-chat-server
Server side Java chat server, following a [tutorial](http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html) 

There are 2 main parts: the chat server and the chat client.

## Build both Chat server and client
```
javac -d out/production/java-chat-server/ src/ChatServer.java src/ChatClient.java
```

## Chat server

### Build the chat server
```
javac -d out/production/java-chat-server/ src/ChatServer.java
```

or
```
javac -verbose -d out/production/java-chat-server/ src/ChatServer.java
```

### Run the chat server
```
java -cp [classpath] ChatServer [port-number]
```

[classpath] could be `/Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/`

[port-number] could be `4444` or `8080` for example.

```
java -cp /Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/ ChatServer 4444
```

For more details, you could optionally use the `-verbose` option.

```
java -verbose -cp /Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/ ChatServer 4444
```

To end the execution, use the keys `CTRL C` in Terminal.

## Chat client

### Build the chat client
```
javac -d out/production/java-chat-server/ src/ChatClient.java
```

### Run the chat client
```
java -cp /Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/ ChatClient 4444
```



