package com.jogeeks.paginglistview;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jogeeks.content.secrets.R;
import com.jogeeks.imageloader.ImageLoader;

public class MyPagingAdaper extends PagingBaseAdapter<String> {

	private static LayoutInflater inflater = null;
	private Activity activity;
	private ImageLoader imageLoader;
	private JSONObject posts;
	private ArrayList<Integer> ids;
	String title = null;
	String imageURL = null;
	String content = null;
	String time = null;
	String comments = null;
	String tags = null;

	public MyPagingAdaper(Activity a, ArrayList<Integer> i, JSONObject p) {
		activity = a;
		posts = p;
		ids = i;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return ids.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tvTitle;
		TextView tvContent;
		TextView tvTime;
		TextView tvComments;
		TextView tvTags;

		View vi = convertView;

		try {
			title = posts.getJSONArray("posts").getJSONObject(position)
					.getString("title_plain");

			content = stripHtml(posts.getJSONArray("posts")
					.getJSONObject(position).getString("excerpt"));

			time = posts.getJSONArray("posts").getJSONObject(position)
					.getString("date");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = posts.getJSONArray("posts").getJSONObject(position)
					.getJSONArray("attachments").getJSONObject(0)
					.getString("url");
		} catch (JSONException s) {
			imageURL = "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-prn2/t1/1795688_10152227451270941_453036500_n.jpg";
		}

		// tags
		try {
			tags = posts
					.getJSONArray("posts")
					.getJSONObject(position)
					.getJSONArray("tags")
					.getJSONObject(0)
					.getString("title")
					.concat(", "
							+ posts.getJSONArray("posts")
									.getJSONObject(position)
									.getJSONArray("tags")
									.getJSONObject(1)
									.getString("title")
									.concat(", "
											+ posts.getJSONArray("posts")
													.getJSONObject(position)
													.getJSONArray("tags")
													.getJSONObject(2)
													.getString("title")));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// comments
		try {
			comments = Integer.toString(
					posts.getJSONArray("posts").getJSONObject(position)
							.getJSONArray("comments").length()).concat(
					" Comments");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (convertView == null)
			vi = inflater.inflate(R.layout.post_item, null);

		tvTitle = (TextView) vi.findViewById(R.id.title);
		tvContent = (TextView) vi.findViewById(R.id.content);
		tvTime = (TextView) vi.findViewById(R.id.time);
		tvComments = (TextView) vi.findViewById(R.id.comments);
		tvTags = (TextView) vi.findViewById(R.id.tags);

		tvTitle.setText(title);
		tvContent.setText(content);
		tvTime.setText(time);
		tvComments.setText(comments);
		tvTags.setText(tags);

		ImageView image = (ImageView) vi.findViewById(R.id.image);
		imageLoader.DisplayImage(imageURL, image);

		return vi;
	}

	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}
}