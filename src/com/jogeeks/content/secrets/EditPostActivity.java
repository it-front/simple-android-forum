package com.jogeeks.content.secrets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class EditPostActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_post);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_post, menu);
		return true;
	}

}
