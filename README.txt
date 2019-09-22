Notification Service v1.0 by VedFI

This is a multi-client notification service created for understanding java sockets.
Built with JAVA Sockets & GSON library.

When Server has started, it listens the port for any clients in a while loop.
If a client tries to connect, server starts a new connection thread for it.
Then Server waits for another client.

Once a Connection established, the connection information stored in a static array. 

ServerConsole is a terminal app for managing the system. It starts automatically when the ServerSocket create.
COMMANDS
 - "list" : all the connections are listed with client informations. 
 - "notify" : server sends Notification Object as a JSON String to all clients.
 - "exit" : connection threads, serverconsole and serversocket is closed.

If a client disconnects from the server. Client information is automatically deleted from array and
you will get notified by ServerConsole. If server stops, Client app will notify you and close itself.

NEXT VERSION
 - There will be a new command that terminates the selected connection(s).

Source files are open to anyone that interests.
Please don't forget to give credits if you use this project.

Feel free to report the bugs if there.
vedfi.company@gmail.com

cheers.