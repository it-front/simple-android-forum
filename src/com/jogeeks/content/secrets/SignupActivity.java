package com.jogeeks.content.secrets;

import com.jogeeks.wordpress.OnRegisterListener;
import com.jogeeks.wordpress.Wordpress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity implements OnClickListener, OnRegisterListener {

	private EditText usernameInput;
	private EditText emailInput;
	private EditText passwordInput;
	private EditText nicknameInput;
	
	private ProgressDialog registerProgress;

	private Button signupBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		//inits
		registerProgress = new ProgressDialog(this);
		
		usernameInput = (EditText) findViewById(R.id.user_name_input);
		emailInput = (EditText) findViewById(R.id.email_input);
		passwordInput = (EditText) findViewById(R.id.password_input);
		nicknameInput = (EditText) findViewById(R.id.nickname_input);

		signupBtn = (Button) findViewById(R.id.done_sign_up);

		signupBtn.setOnClickListener(this);
	}

	@Override
	public void OnRegisterSuccess(int error) {
		registerProgress.dismiss();
		Toast.makeText(getApplicationContext(), "Thank you " +
				usernameInput.getText().toString() +
		", you've been succesfully signed up", Toast.LENGTH_LONG).show();	}

	@Override
	public void OnRegisterFailure(int error) {
		registerProgress.dismiss();

		String errorMsg;

		switch (error) {
		case Wordpress.REGISTRATION_FAILED:
			errorMsg = "check internet connection or server availability.";
			break;

		case Wordpress.REGISTRATION_INVALID_USER_NAME:
			errorMsg = "invalid user name,";
			break;
			
		case Wordpress.REGISTRATION_USER_NAME_IN_USE:
			errorMsg = "user name in use";
			break;
			
		case Wordpress.REGISTRATION_INVALID_EMAIL:
			errorMsg = "your email is invalid";
			break;
		
		case Wordpress.REGISTRATION_EMAIL_IN_USE:
			errorMsg = "the email is already in use";
			break;
		
		case Wordpress.REGISTRATION_INVALID_DISPLAY_NAME:
			errorMsg = "invalid display name";
			break;
			
		case Wordpress.REGISTRATION_INVALID_PASSWORD:
			errorMsg = "invalid password";
			break;
			
		default:
			errorMsg = "check internet connection or server availability.";
			break;
		}
		
		Toast.makeText(getApplicationContext(), "Login failed with error code :\"" +
				Integer.toString(error)+"\" " +
				errorMsg, Toast.LENGTH_LONG).show();	
		}
	
	@Override
	public void onRegisterStart() {
		registerProgress.setTitle("Please wait");
		registerProgress.setMessage("Signing you up");
		registerProgress.show();		
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
