package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

	private String body;
	private long uid;
	private String createdAt;
	private User user;
	
	public static Tweet fromJSON(JSONObject json)
	{
		Tweet tweet = new Tweet();
		
		//Extract values from JSON to populate the member variables
		try{
			tweet.body = json.getString("text");
			tweet.uid = json.getLong("id");
			tweet.createdAt = json.getString("created_at");
			tweet.user = User.fromJSON(json.getJSONObject("user"));
		}catch(JSONException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		
		for (int i = 0; i < json.length(); i++)
		{
			JSONObject o = null;
			try {
				o = json.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			
			Tweet t = Tweet.fromJSON(o);
			
			if (t != null)
			{
				tweets.add(t);
			}
			
		}
		
		return tweets;
	}
	
	@Override
	public String toString()
	{
		return getBody() + " - " + getUser().getScreenName();
	}

}
