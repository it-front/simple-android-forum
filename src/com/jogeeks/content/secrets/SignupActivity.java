package com.jogeeks.content.secrets;

import com.jogeeks.wordpress.OnRegisterListener;
import com.jogeeks.wordpress.Wordpress;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends Activity implements OnClickListener, OnRegisterListener {

	private EditText usernameInput;
	private EditText emailInput;
	private EditText passwordInput;
	private EditText nicknameInput;

	private Button signupBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		usernameInput = (EditText) findViewById(R.id.user_name_input);
		emailInput = (EditText) findViewById(R.id.email_input);
		passwordInput = (EditText) findViewById(R.id.password_input);
		nicknameInput = (EditText) findViewById(R.id.nickname_input);

		signupBtn = (Button) findViewById(R.id.done_sign_up);

		signupBtn.setOnClickListener(this);
	}

	@Override
	public void OnRegisterSuccess(int error) {
		Log.d("Registration Success", Integer.toString(error));
	}

	@Override
	public void OnRegisterFailure(int error) {
		Log.d("Registration Failed", Integer.toString(error));		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.done_sign_up:
			Bundle userData = new Bundle();
			userData.putString("username", usernameInput.getText().toString());
			userData.putString("password", passwordInput.getText().toString());
			userData.putString("displayname", nicknameInput.getText().toString());
			userData.putString("email", emailInput.getText().toString());
			
			Wordpress reg = new Wordpress(this);
			reg.setOnRegisterListener(this);
			reg.register(userData);
			break;

		default:
			break;
		}
	}
}
