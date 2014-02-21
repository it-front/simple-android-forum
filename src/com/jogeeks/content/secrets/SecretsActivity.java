package com.jogeeks.content.secrets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;

import com.jogeeks.paginglistview.MyPagingAdaper;
import com.jogeeks.paginglistview.PagingListView;
import com.jogeeks.paginglistview.SafeAsyncTask;
import com.jogeeks.wordpress.Wordpress;

@SuppressLint("NewApi")
public class SecretsActivity extends Activity {

	private PagingListView listView;
	private MyPagingAdaper adapter;

	private HashMap<Integer, ArrayList<String>> posts;

	private ArrayList<Integer> ids;
	private int pager = 0;

	private int count = 0;
	private int count_total = 0;
	private int pages = 0;

	private JSONObject postsData;

	private ProgressDialog loadingDialog;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secrets);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		postsData = new Wordpress(this).getRecentPostsJSON(getApplicationContext());

		listView = (PagingListView) findViewById(R.id.paging_list_view);

		initData();
		createProgressDialog();

		listView.setHasMoreItems(true);
		listView.setPagingableListener(new PagingListView.Pagingable() {
			@Override
			public void onLoadMoreItems() {
				if (pager < 3) {
					new CountryAsyncTask(false).execute();
				} else {
					listView.onFinishLoading(false, null);
				}
			}
		});

		clearData();
		new CountryAsyncTask(true).execute();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("SAD", Long.toString(adapter.getItemId(arg2)));
			}
		} );
	}

	public void createProgressDialog() {
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setIndeterminate(true);
		loadingDialog.setMessage(getString(R.string.loading_countries));
	}

	private void clearData() {
		if (listView.getAdapter() != null) {
			pager = 0;
			adapter = (MyPagingAdaper) ((HeaderViewListAdapter) listView
					.getAdapter()).getWrappedAdapter();
			adapter.removeAllItems();
			listView = null;
			listView = (PagingListView) findViewById(R.id.paging_list_view);
			adapter = new MyPagingAdaper(this, ids, postsData);
		}
	}

	@SuppressLint("UseSparseArrays")
	private void initData() {
		try {
			count = postsData.getInt("count");
			count_total = postsData.getInt("count_total");
			pages = postsData.getInt("pages");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		posts = new HashMap<Integer, ArrayList<String>>();

		int postCounter = 0;
		for (int i = 0; i <= pages; i++) {
			posts.put(i, new ArrayList<String>());
			for (postCounter = postCounter; postCounter <= count; postCounter++) {
				try {
					int id = Integer.parseInt(postsData.getJSONArray("posts")
							.getJSONObject(postCounter)
							.getString("id"));
					posts.get(i)
							.add(postCounter,
									Integer.toString(id));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class CountryAsyncTask extends SafeAsyncTask<List<String>> {
		private boolean showLoading;

		public CountryAsyncTask(boolean showLoading) {
			this.showLoading = showLoading;
		}

		@Override
		protected void onPreExecute() throws Exception {
			super.onPreExecute();
			if (showLoading) {
				loadingDialog.show();
			}
		}

		@Override
		public ArrayList<String> call() throws Exception {
			ArrayList<String> result = null;
			return posts.get(pager);
		}

		@Override
		protected void onSuccess(List<String> newItems) throws Exception {
			super.onSuccess(newItems);
			adapter = new MyPagingAdaper(SecretsActivity.this, ids, postsData);

			pager++;
			if (listView.getAdapter() == null) {
				listView.setAdapter(adapter);
			}
			listView.onFinishLoading(true, newItems);
		}

		@Override
		protected void onFinally() throws RuntimeException {
			super.onFinally();
			if (loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
		}
	}

}
