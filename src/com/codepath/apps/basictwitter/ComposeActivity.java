package com.codepath.apps.basictwitter;


import org.json.JSONArray;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class ComposeActivity extends Activity {

	private TwitterClient client;
	private EditText etTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		client = TwitterApplication.getRestClient();
		etTweet = (EditText) findViewById(R.id.etTweet);
	}
	
	public void tweetClick(View v)
	{
		
		String status = etTweet.getText().toString();
		
		if (status.length() != 0)
		{
			// Create result
			Intent i = new Intent();
			//i.putExtra("settings", settings);
			
			
			
			client.postStatus(new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(JSONArray json) {
					//Tweet.fromJSONArray(json);
					//aTweet.addAll(Tweet.fromJSONArray(json));
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			}, status);
			
			// Submit my result to parent activity
			setResult(RESULT_OK, i);
			finish();
		}
	}
	
	
}
