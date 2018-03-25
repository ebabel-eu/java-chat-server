# java-chat-server
Server side Java chat server, following a [Java socket tutorial](http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html).

[Another tutorial](https://www.callicoder.com/spring-boot-websocket-chat-example/) to consider, using Spring and HTML.

## Build both Chat server and client
```
javac -d ./out/production/java-chat-server/ src/*.java
```

## Run the chat server
```
java -cp ./out/production/java-chat-server/ ChatServer 4444
```

To end the execution, use the keys `CTRL C` in Terminal.

## Run the chat client
```
java -cp ./out/production/java-chat-server/ ChatClient "John Smith" localhost 4444
```

`"John Smith""` is the alias by which you want to be known in the chat.

You can now type a message, press `ENTER` and see that message displayed on the Chat Server.

You may kill the server from the client by sending either the message `/q` or `/quit`

The current version only supports one server and one single client.

## How to kill this process on the server
First, you need to find the pid of this process:
```
ps aux | grep java
```

You can then kill it:
```
kill -9 [the PID of your program's process]
```
