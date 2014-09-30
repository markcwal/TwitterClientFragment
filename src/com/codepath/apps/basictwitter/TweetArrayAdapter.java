package com.codepath.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	Context context;
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		
		super(context, 0, tweets);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Tweet tweet = getItem(position);
		View v;
		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		}else
		{
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvTimeStamp = (TextView) v.findViewById(R.id.tvTimeStamp);
		
		//Clear out image from last time if it's a recycled image view
		ivProfileImage.setImageResource(android.R.color.transparent);
		
		ivProfileImage.setTag(tweet.getUser().getUid());
		
		//Set Click Handler
		ivProfileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, ProfileActivity.class);
				i.putExtra("user_id", (Long)v.getTag());
				context.startActivity(i);
				
			}
		});
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		//populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvScreenName.setText("@" + tweet.getUser().getScreenName());
		tvName.setText(tweet.getUser().getName());
		tvTimeStamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		tvBody.setText(tweet.getBody());
		
		return v;
	}
	
	public User getUserByViewId(int id)
	{
		for (int i = 0; i < this.getCount(); i++)
		{
			
		}
		return null;
	}
	
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
			String[] splits = relativeDate.split(" ");
			relativeDate = splits[0] + splits[1].charAt(0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}

}
