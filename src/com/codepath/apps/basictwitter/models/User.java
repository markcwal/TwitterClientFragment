package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name;
	private Long uid;
	private String screenName;
	private String profileImageUrl;
	private Integer followersCount;
	private Integer followingCount;
	private String tagLine;

	public static User fromJSON(JSONObject json) {
		User user = new User();
		
		try{
			user.name = json.getString("name");
			user.uid = json.getLong("id");
			user.screenName = json.getString("screen_name");
			user.profileImageUrl = json.getString("profile_image_url");
			user.followersCount = json.getInt("followers_count");
			user.followingCount = json.getInt("friends_count");
			user.tagLine = json.getString("description");
			
		}catch(JSONException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return user;
	}
	
	public Integer getFollowingCount() {
		return followingCount;
	}
	
	public Integer getFollowersCount() {
		return followersCount;
	}
	
	public String getTagLine() {
		return tagLine;
	}

	public String getName() {
		return name;
	}

	public Long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}
