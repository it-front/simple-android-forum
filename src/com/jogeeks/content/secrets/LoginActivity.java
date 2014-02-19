package com.jogeeks.content.secrets;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import com.jogeeks.wordpress.WPLogin;

@EActivity(R.layout.activity_login)
@OptionsMenu(R.menu.login)
public class LoginActivity extends Activity {

	@ViewById(R.id.username)
	EditText usernameInput;

	@ViewById(R.id.password)
	EditText passwordInput;
	
	@ViewById(R.id.login)
	Button loginButton;
	
	@Click
	void login(){
		new WPLogin(this, "firas", "123123", SecretsActivity.class);
	}
	
}
