# Introduction #

There are some documented issues using the count.ly Android library:

I've made a test application and I put Countly methods exactly in the same places of the demo code. The problem is that when I rotate the screen the activity is destroyed and then recreated and this causes Countly to create new sessions

http://support.count.ly/discussions/questions/285-android-new-session-on-screen-rotation

The following may be relevant:
http://stackoverflow.com/questions/13723157/android-queue-a-post-until-there-is-a-connection