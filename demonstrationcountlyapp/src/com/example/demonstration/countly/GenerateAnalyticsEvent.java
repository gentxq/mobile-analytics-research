package com.example.demonstration.countly;

/**
 * Generates Analytics Events to send to count.ly for later analysis.
 * 
 * @author julianharty
 */
public class GenerateAnalyticsEvent {
	/**
	 * Sends an event for 'Download Completed OK' to the analytics server
	 * @param URL the URL that was downloaded
	 * @param fileSize the size of the file downloaded in bytes, else -1
	 * @param durationInMs duration of the download in milli-seconds.
	 * @return true if the event message was passed to count.ly without error, else false.
	 */
	boolean downloadCompletedOk(String URL, long fileSize, long durationInMs) {
		return true;
	}
	
	/**
	 * Sends an event for 'Download Failed To Complete' to the analytics server
	 * @param URL the URL we tried and failed to download
	 * @param reason the reason code e.g. from the exception
	 * @return true of the message was passed to count.ly without error, else false.
	 */
	boolean downloadFailedToComplete(String URL, String reason) {
		return true;
	}

}
