# Introduction #

We need to understand the behaviour and characteristics of the underlying library in order to use it within it's performance envelope. Here are some ideas on how to test count.ly for Android. Some of the ideas may be more widely applicable e.g. to other libraries from count.ly and/or mobile analytics libraries from other providers.

Count.ly is unusual among mobile analytics libraries as both the clients and the server are freely available opensource projects. https://github.com/Countly As we have access to the source code we can use static analysis tools and inspect the code in addition to dynamic testing.

# Overview of Testing #
The majority of our testing is expected to be dynamic, where the code is executed using the Android run-time. Tests can be run in virtual and physical devices. We can also establish a local server which makes the data easier to capture and work with.

Count.ly provides two APIs,
  * input: accepts data from the mobile client and stores the data for analysis
  * output: allows a caller to receive data in almost raw, and 'ready-to-use', modes

The generic server API is described at http://count.ly/resources/reference/server-api

# Our concerns #
Here are our initial concerns in terms of the library. We may revise this section, etc. as we conduct our evaluation and experiments.

How does the library behave in terms of:
  * latency between receiving an event & forwarding it to the server?
  * bursts of events, are they all transmitted, in such a way that all the data, and their timelines can be reconstructed?
  * coping with poor network conditions?
  * "the truth, the whole truth, and nothing but the truth"? Or, in other terms, does the library report faithfully, without sending extraneous information?

# Experiments to try #
## Various N and T where (N - 1) `*` T = 100 ##
> N = number of events to send

> T = time between transmissions

The maximum rate would be 101 events, 1 second apart and the minimum number of events would be 2, 100 seconds apart.
Each event needs a unique identifier.

### Measurement ###
We should be able to find the same number of events recorded on the server, in order. The timestamp of each record should be when it was generated, not when it was sent or received.

## Intermittent network connectivity ##