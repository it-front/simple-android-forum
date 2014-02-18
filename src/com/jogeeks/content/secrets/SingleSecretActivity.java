package com.jogeeks.content.secrets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SingleSecretActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_secret);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_secret, menu);
		return true;
	}

}
