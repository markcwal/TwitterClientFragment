package com.codepath.apps.basictwitter.fragments;


import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setType(HOME_TIMELINE);
		populateTimeline();
	}
}
