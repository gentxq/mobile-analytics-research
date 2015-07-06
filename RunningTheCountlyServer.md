# Introduction #

I discovered I need to recover the key services when my laptop died accidentally. I hope it saves you time.

## Starting the relevant services ##
The following commands will start the relevant services for count.ly to run, even if the machine didn't shutdown cleanly.

In a terminal window on Ubuntu with count.ly installed do:
```
sudo rm /var/lib/mongodb/mongod.lock 
sudo -u mongodb mongod -f /etc/mongodb.conf --repair
sudo service mongodb status
sudo service mongodb start
sudo service mongodb status
sudo /etc/init.d/nginx start
```

Thank you for the following information which made the above trivial to discover: http://stackoverflow.com/questions/8129867/failed-to-connect-to-mongodb-in-ubuntu