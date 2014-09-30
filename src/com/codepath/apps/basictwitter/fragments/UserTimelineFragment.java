package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

public class UserTimelineFragment extends TweetsListFragment {
	
	private long uid = 0;
	
	public UserTimelineFragment(long uid)
	{
		this.uid = uid;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setType(USER_TIMELINE);
		setUid(uid);
		populateTimeline();
	}

}
