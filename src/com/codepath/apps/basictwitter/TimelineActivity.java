package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweet;
	private ListView lvTweets;
	private long max_id = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		
		client = TwitterApplication.getRestClient();
		
		populateTimeline();
		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweet = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweet);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(totalItemsCount); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }
	
	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int totalItemsCount) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	
    	if (totalItemsCount > 0)
    		max_id = aTweet.getItem(totalItemsCount - 1).getUid() - 1;
    	
      client.getHomeTimeline(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				//Tweet.fromJSONArray(json);
				aTweet.addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, max_id);
    }
	
	public void populateTimeline()
	{
		Log.d("debug", "TESTING TESTING");
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				//Tweet.fromJSONArray(json);
				aTweet.clear();
				aTweet.addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, Long.MAX_VALUE);
	}
	
	public void onComposeClick(MenuItem mi)
	{
		//Construct Intent
		Intent i = new Intent(this, ComposeActivity.class);
		
		
		//Execute Intent startActivityForResult
		startActivityForResult(i, 5);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 5)
		{
			if (resultCode == RESULT_OK)
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Not sure we have to do anything here
				populateTimeline();
				//String value = data.getStringExtra("value");
				//Settings mySettings = (Settings) data.getSerializableExtra("settings");
				//this.settings = mySettings;
				//Toast.makeText(this, settings.value, Toast.LENGTH_SHORT).show();
			}
		}
			
	}
}
