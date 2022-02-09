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

"ftpserver.conf" is used to toggle PORT/PASV.

Write UP:

1) Some security considerations for PORT might be that it overall is just very insecure. It also
does not really follow a client/server model at all. This is because in PORT's implementation the
client is responsible for business logic. It opens a data port and tells the server where it should connect.
Usually this is not good practice since clients can be hacked or exploited by the end user. Port might only be
useful in situations where the client might be downloading from multiple servers.

Some security considerations for PASV might be that it is probably the most secure implementation. It properly
follows a server/client model since the server is responsible for telling the client what to do. This keeps all the
business logic on the server side and the client just acts as a simple user interface. This is useful in almost
every situation.

2) NAT divides up destination IP addresses into to parts: local and external. So in FTP an IP is required to connect
to server/client. In a local setting, computers on the same local network can use their private local IP address to
handle FTP requests on the local network. When the server/client wants to connect to external IPs this is done
with a local network router. The local server/client will use local IP addresses to send a request to the router.
Then the router will open up external ports to connect to external sources. The NAT prevents the local server/client
from being able to see any public IP traffic. It is important to allow applications to know about IP addresses. It's
also important to make sure that you don't reveal too much information about IPs to prevent anything from accessing the
local network.

3) In my server log I found every response from the server depending on what the client requested.
It also does log any errors along the way with explanations depending on what when wrong. This has been
very helpful and has also been helpful for implementing the config section for this assignment. It could not
understand why my PORT function test was hanging, and I checked the log to see if there was the proper response.
I realized what I did wrong, and it was fixed fairly easily instead of trying to step through the program with the
debugger and check every line that is related to the issue.

4) Yes this probably would be a good idea to implement. Different errors can create much larger issues
than others so reflecting a weight for each could help the end user figure out what went wrong much easier.
Like for example a socket connection error should be weighted much higher than an invalid command sent to the server.

5) With IMPLICIT mode there are some major advantages like having TLS always on at all times. This means
the connection will be secure at all times. The separate server can also speed up requests since there are
dedicated sockets running just for transfer. Although this can also be a trade-off since you have to dedicate
more resources to one implementation.

With EXPLICIT mode there is a major disadvantage because there is only one channel receiving the
requests. This is insecure because there is only one place were there is access. This can also
reduce the speed of the program since all traffic is over the same connection. Another disadvantage would be
that TLS is toggleable. This can be useful in some settings if you do not want protection, but there are almost
no modern implementations where that would be useful.

6) No I do not think I have done any checks to see if my server is being hacked. I really do not think there is
an easy way to check/log for attack on my server. Maybe just adding encryption will protect it. The only real
possible security risk I could check for might be a DDOS attack which usually is handled by the router. I could
count the threads currently running on the server grab their IP addresses. If there seems to be many FTP connections
from the same IP coming in very quickly then the server can block that IP from sending requests.
