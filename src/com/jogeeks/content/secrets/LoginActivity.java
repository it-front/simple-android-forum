package com.jogeeks.content.secrets;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jogeeks.wordpress.WPSession;
import com.jogeeks.wordpress.Wordpress;

public class LoginActivity extends Activity implements OnClickListener  {

	private EditText usernameInput;
	private EditText passwordInput;
	private Button loginButton;
	private Wordpress wp;
	private WPSession wpSession;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		// References
	    usernameInput = (EditText) findViewById(R.id.username);
		passwordInput = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.login);

		// OnClickListener
		loginButton.setOnClickListener(this);
		
			wp = new Wordpress(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			wpSession = wp.Login(usernameInput.getText().toString(), passwordInput.getText().toString());
			int result = wpSession.getStatus();
			Log.d("Error", Integer.toString(result));
			break;

		default:
			break;
		}
	}

}
