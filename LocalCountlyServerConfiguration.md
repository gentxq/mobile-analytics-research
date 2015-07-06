# Introduction #

Count.ly provides both the client and the server libraries. The server is easy to install. With some minor changes we can log the incoming HTTP requests which helps to diagnose the behaviour, connectivity problems, etc.

Our initial work uses the count.ly Android client library running in an Android Virtual Device (AVD) so all connectivity is within a single machine. This is sufficient to perform initial analysis.


# Details #

## Android Setup ##
For the Android app we want to connect to the `localhost` on the desktop machine. The magic IP address `10.0.2.2` routes from the Android Virtual Device (AVD) to the desktop's `localhost` IP address (`127.0.0.1`)
http://developer.android.com/tools/devices/emulator.html#networkaddresses

This means we can configure count.ly's web server, and in particular the nginx server it uses to use `localhost`. This is how count.ly's configuration script configures the web server so our testing can work with a vanilla installation of count.ly

  * Limitation: we cannot connect to the vanilla count.ly web server over the network e.g. so we cannot connect over wifi until we change the configuration of nginx used by count.ly

## Configuring nginx ##
The first change I ended up making was to enable access logging in the web server to diagnose whether the app in the AVD was able to connect correctly to the locally-hosted count.ly web server. Details are available in http://wiki.nginx.org/HttpLogModule

Here's my revised configuration file
```
server {
        listen   80;
        server_name  localhost 10.0.2.2;

        access_log  /var/log/nginx/access.log;

        location = /i {
                proxy_pass http://127.0.0.1:3001;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Real-IP $remote_addr;
        }

        location ^~ /i/ {
                proxy_pass http://127.0.0.1:3001;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Real-IP $remote_addr;
        }

        location = /o {
                proxy_pass http://127.0.0.1:3001;
        }

        location ^~ /o/ {
                proxy_pass http://127.0.0.1:3001;
        }

        location / {
                proxy_pass http://127.0.0.1:6001;
                proxy_set_header Host $http_host;
        }
}
```



The next step is to open up the nginx server so it listens to traffic on the local IP address as well as localhost. I need to work out how to configure the server to enable this. Here are the clues I have to work with.

http://blog.martinfjordvald.com/2010/07/nginx-primer/


http://wiki.nginx.org/HttpProxyModule

http://stackoverflow.com/questions/9801187/nginx-shorter-proxy-pass-configuration

## Sample log entries ##
Here is the first log entry captured from starting my skeletal Android app that initialises count.ly's android code.
```
127.0.0.1 - - [18/Jun/2013:21:31:13 +0100] "GET /i?app_key=91b017d2334b8268f31e8b2f9e6092aa8c910e81&device_id=4e861960d10e097b&timestamp=1371587471&sdk_version=1.0&begin_session=1&metrics=%7B%22_device%22%3A%22sdk%22%2C%22_os%22%3A%22Android%22%2C%22_os_version%22%3A%224.2.2%22%2C%22_carrier%22%3A%22Android%22%2C%22_resolution%22%3A%221184x768%22%2C%22_locale%22%3A%22en_US%22%2C%22_app_version%22%3A%221.0%22%7D HTTP/1.1" 200 31 "-" "Apache-HttpClient/UNAVAILABLE (java 1.4)"
```

The command to obtain this data is:
```
grep /i?  /var/log/nginx/access.log
```
Count.ly's API is documented online at http://count.ly/resources/reference/server-api which describes the fields in the GET request found in the nginx access log.