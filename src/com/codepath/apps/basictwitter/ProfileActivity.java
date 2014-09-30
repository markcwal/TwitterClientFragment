package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Long uid = getIntent().getLongExtra("user_id", 0);
		loadProfileInfo(uid);
		
	}
	
	public void loadFragment(User u)
	{
		// Begin the transaction
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		ft.replace(R.id.flTweets, new UserTimelineFragment(u.getUid()));
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
	}
	
	public void populateProfileHeader(User u)
	{
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfImage = (ImageView) findViewById(R.id.ivProfImage);
		
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagLine());
		tvFollowers.setText("Followers: " + u.getFollowersCount().toString());
		tvFollowing.setText("Following: " + u.getFollowingCount().toString());
		
		//Clear out image from last time if it's a recycled image view
		ivProfImage.setImageResource(android.R.color.transparent);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		//populate views with tweet data
		imageLoader.displayImage(u.getProfileImageUrl(), ivProfImage);
		
	}
	
	public void loadProfileInfo(Long uid)
	{
		TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				//Tweet.fromJSONArray(json);
				//addAll(Tweet.fromJSONArray(json));
				User u = User.fromJSON(json);
				getActionBar().setTitle("@" + u.getScreenName());
				loadFragment(u);
				populateProfileHeader(u);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, uid);
	}
}
