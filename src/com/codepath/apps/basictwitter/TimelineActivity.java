package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {

	
	
	private TweetsListFragment fragmentTweetsList;
	private HomeTimelineFragment htf;
	private MentionsTimelineFragment mtf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
		
		htf = (HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag("HomeTimelineFragment");
		mtf = (MentionsTimelineFragment) getSupportFragmentManager().findFragmentByTag("MentionsTimelineFragment");
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }
	
	public void onProfileView(MenuItem mi)
	{
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("user_id", 0L);
		startActivity(i);
	}
	
	public void onProfileImageClick(View v)
	{
		
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
				htf.populateTimeline();
				getActionBar().setSelectedNavigationItem(0);
				//String value = data.getStringExtra("value");
				//Settings mySettings = (Settings) data.getSerializableExtra("settings");
				//this.settings = mySettings;
				//Toast.makeText(this, settings.value, Toast.LENGTH_SHORT).show();
			}
		}
			
	}
}
