package com.jogeeks.content.secrets;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jogeeks.wordpress.WPLogin;

@EActivity(R.layout.activity_login)
@OptionsMenu(R.menu.login)
public class LoginActivity extends Activity {

	@RestService
	MyRestClient myRestClient;

	@ViewById(R.id.username)
	EditText usernameInput;

	@ViewById(R.id.password)
	EditText passwordInput;

	@ViewById(R.id.login)
	Button loginButton;

	@Click
	void login() {
		new WPLogin(this, "firas", "123123", SecretsActivity.class);
	}

	@AfterViews
	void afterViews() {
		getPostsAsync("", "");
	}

	@Background
	void getPostsAsync(String searchString, String userId) {
		Log.d("REST", myRestClient.getRecentPosts().toString());
	}

}
