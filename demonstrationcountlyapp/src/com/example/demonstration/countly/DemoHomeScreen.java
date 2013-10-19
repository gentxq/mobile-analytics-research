package com.example.demonstration.countly;

import java.util.Random;

import ly.count.android.api.Countly;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DemoHomeScreen extends Activity
{
    private static final int TARGET_SUCCESS_RATE = 88;
	protected static final String URL = "http://www.example.com/dummy/file.mp3";
	protected static final String DUMMY_EXCEPTION = " Dummy Exception Message";
	private GenerateAnalyticsEvent eventGenerator  = new GenerateAnalyticsEvent();
    private Button generateDownloadEvent;
    private TextView textToDisplay;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        textToDisplay = (TextView) findViewById(R.id.textToDisplay);
        generateDownloadEvent = (Button) findViewById(R.id.generateDownloadEvent);
        generateDownloadEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (decideIfEventShouldBeReportedAsAFailure()) {
					eventGenerator.downloadFailedToComplete(URL, DUMMY_EXCEPTION);
					textToDisplay.setText("Fail: " + URL + DUMMY_EXCEPTION);
					
				} else {
					long durationInMs = new Random().nextInt(500);
					long calculatedDuration = durationInMs + 123L;
					eventGenerator.downloadCompletedOk(URL, 8192L, calculatedDuration);
					String text = String.format("Pass: %s %d %d", URL, 8192L, calculatedDuration);
					textToDisplay.setText(text);
				}
			}
		});

		Countly.sharedInstance().init(this, 
				"https://cloud.count.ly", 
				"eba02ff745b5438f2c8655be08ddb0327dd22585");
    }

    @Override
    public void onStart()
    {
    	super.onStart();
        Countly.sharedInstance().onStart();
    }

    @Override
    public void onStop()
    {
        Countly.sharedInstance().onStop();
    	super.onStop();
    }
    
    /**
     * Decide if the event should have failed, or not.
     * 
     * Uses a hard-coded target success rate.
     * @return true if the event should be reported as a failure, else true.
     */
    private boolean decideIfEventShouldBeReportedAsAFailure() {
    	int value = new Random().nextInt(100);
    	if (value < TARGET_SUCCESS_RATE) {
    		return false;
    	} else {
    	return true;
    	}
    }
}
