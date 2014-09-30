package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsListFragment extends Fragment {
	
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweet;
	private ListView lvTweets;
	private TwitterClient client;
	public static final int HOME_TIMELINE = 1;
	public static final int MENTIONS_TIMELINE = 2;
	public static final int USER_TIMELINE = 3;
	private int type = HOME_TIMELINE;
	private long uid = 0;

	//fires before the onCreateView, and does all of the non-view logic
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweet = new TweetArrayAdapter(getActivity(), tweets);
		client =  TwitterApplication.getRestClient();
	}
	
	protected void setType(int type)
	{
		this.type = type;
	}
	
	protected void setUid(long uid)
	{
		this.uid = uid;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//inflate layout
			//false says don't attach to the container yet
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		//assign view references
		
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		
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
		
		//return the layout view
		return v;
	}
	
	public ListView getLvTweets()
	{
		return lvTweets;
	}
	
    public void addAll(ArrayList<Tweet> tweets)
    {
    	aTweet.addAll(tweets);
    }
    
    public void clear()
    {
    	aTweet.clear();
    }
    
    public void populateTimeline()
	{
    	JsonHttpResponseHandler rh = new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				//Tweet.fromJSONArray(json);
				clear();
				addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		};
    	
    	if (type == HOME_TIMELINE)
    		client.getHomeTimeline(rh, Long.MAX_VALUE);
    	else if (type == MENTIONS_TIMELINE)
    		client.getMentionsTimeline(rh, Long.MAX_VALUE);
    	else if (type == USER_TIMELINE)
    		client.getUserTimeline(rh, uid, Long.MAX_VALUE);
	}
    
 // Append more data into the adapter
    public void customLoadMoreDataFromApi(int totalItemsCount) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	long max_id = Long.MAX_VALUE;
    	if (totalItemsCount > 0)
    		max_id = aTweet.getItem(totalItemsCount - 1).getUid() - 1;
    	
    	JsonHttpResponseHandler rh = new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				//Tweet.fromJSONArray(json);
				addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		};
    	
    	if (type == HOME_TIMELINE)
    		client.getHomeTimeline(rh, max_id);
    	else if (type == MENTIONS_TIMELINE)
    		client.getMentionsTimeline(rh, max_id);
    	else if (type == USER_TIMELINE)
    		client.getUserTimeline(rh, uid, max_id);
    }

}
