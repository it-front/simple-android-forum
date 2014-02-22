package com.jogeeks.content.secrets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jogeeks.wordpress.OnLoginListener;
import com.jogeeks.wordpress.WPSession;
import com.jogeeks.wordpress.Wordpress;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText usernameInput;
	private EditText passwordInput;
	private Button loginButton;
	private Button signupButton;

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
		signupButton = (Button) findViewById(R.id.signup);

		// OnClickListener
		loginButton.setOnClickListener(this);
		signupButton.setOnClickListener(this);

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

			wp.setOnLoginListener(new OnLoginListener() {
				
				@Override
				public void OnLoginSuccess(WPSession session) {
					Log.d("Success", Integer.toString(session.getStatus()));
				}

				@Override
				public void OnLoginFailure(WPSession session) {
					Log.d("Failure", Integer.toString(session.getStatus()));
				}
			});

			Bundle userData = new Bundle();
			userData.putString("username", usernameInput.getText().toString());
			userData.putString("password", passwordInput.getText().toString());

			wp.login(userData);
			break;

		case R.id.signup:
			startActivity(new Intent(this, SignupActivity.class));
			finish();
			break;
		default:
			break;
		}
	}

}
