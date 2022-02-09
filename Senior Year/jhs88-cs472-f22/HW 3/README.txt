How to run:
Open in JetBrains IDE. A config file has been generated for
this program. It should be called Server. It is in the .idea folder.
IDE should import it automatically. If you edit the config file, you change
the CLI arguments there.

Testing was done with FTPserverTest. This file can be run to test
all the different commands at one time. It will then generate a log
file called "serverlog.txt". If you want to use it just delete the existing log file.

"TESTING" folder represents the filesystem of testing suite. It almost acts as a client.
    NOTE: If you want to use FTPserverTest delete the .txt in this folder.

"Text.txt" is stored on the server and can be used by the testing suite.

"accounts" is a list of usernames and passwords that can log onto the server.

Write UP:

1) Yes it would be very easy to hack into this server. The best possible way would be to
use something like WireShark that could go and look at the FTP commands sent over TCP.
Inside these commands there should be USER & PASS. Since these are sent with plain text,
the actual credentials are exposed. Anyone could then use these to log into the server.
Someone could also look at the ports open on the server and use them to find the data ports.
They then could read the information from this port and steal people's files. All of its
problems circle around not having any encryption or security measures.

2) FTP is pros are that is very simple and easy to use. Because it is so basic there isn't
complicated to design programs for it and can be scaled very easily. At the same time this
is also its biggest con. Since it doesn't have built-in things like security or encryption.
This is a big deal because of how exposed FTP is to the entire internet.

Something that I find weird is that it also allows a client to decide
where it should send information. This doesn't make much sense and also opens up more security issues.
Server should be responsible for all management of the ports. Another strang thing is that
it uses multiple sockets to manage commands and data transfer. This is because of a physical
limitation of the time it was invented, but not it lacks much common sense.

3) With modern day computer systems this would most likely not be that hard at all.
The client/server can decipher the difference between bytes of text and of files.
You just have to let ether side know what exactly it will be sending over. So if
a client sent a request for a file then the server can send a confirmation message
and tell the client to expect a file instead of a response code.