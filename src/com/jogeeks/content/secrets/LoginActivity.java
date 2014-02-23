package com.jogeeks.content.secrets;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.UserDictionary.Words;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.jogeeks.wordpress.OnLoginListener;
import com.jogeeks.wordpress.WPPost;
import com.jogeeks.wordpress.WPSession;
import com.jogeeks.wordpress.Wordpress;
import com.jogeeks.wordpress.WordpressResponseHandler;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText usernameInput;
	private EditText passwordInput;
	private Button loginButton;
	private Button signupButton;
	private ProgressDialog loginProgress;
	
	private Wordpress wp;
	private WPSession wpSession;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//inits
		loginProgress = new ProgressDialog(this);
		
		// References
		usernameInput = (EditText) findViewById(R.id.username);
		passwordInput = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.login);
		signupButton = (Button) findViewById(R.id.signup);

		// OnClickListener
		loginButton.setOnClickListener(this);
		signupButton.setOnClickListener(this);

		wp = new Wordpress(this);
		/*************************tests*******************/
		/*******************get posts*******************/
		wp.getPosts(new WordpressResponseHandler<WPPost>(){
			@Override
			public void onPostsRecieved(ArrayList<WPPost> posts) {
				// you can print the posts here
				Log.d("balh",posts.get(0).getTitle());
				super.onPostsRecieved(posts);
			}
		});
		wp.getRecentPosts(new WordpressResponseHandler<WPPost>(){
			@Override
			public void onPostsRecieved(ArrayList<WPPost> posts) {
				// you can print the posts here
				Log.d("balh",posts.get(0).getTitle());
				super.onPostsRecieved(posts);
			}
		});
		
		wp.getPostsByAuthor(1,new WordpressResponseHandler<WPPost>(){
			@Override
			public void onPostsRecieved(ArrayList<WPPost> posts) {
				// you can print the posts here
				Log.d("balh",posts.get(0).getTitle());
				super.onPostsRecieved(posts);
			}
		});
		
		wp.getPostsByCategory(1 , new WordpressResponseHandler<WPPost>(){
			@Override
			public void onPostsRecieved(ArrayList<WPPost> posts) {
				// you can print the posts here
				Log.d("balh",posts.get(0).getTitle());
				super.onPostsRecieved(posts);
			}
		});
		wp.getPostsBySearch("hello",new WordpressResponseHandler<WPPost>(){
			@Override
			public void onPostsRecieved(ArrayList<WPPost> posts) {
				// you can print the posts here
				Log.d("balh",posts.get(0).getTitle());
				super.onPostsRecieved(posts);
			}
		});
		
		WPPost post = new WPPost();
		post.setTitle("hello this is a test of create post");
		post.setContent("this is the content for the test");
		
		
		/*wp.createPost(post, 1, "publish", new WordpressResponseHandler<WPPost>(){
			@Override
			public void onPostCreated(WPPost post) {
				Log.d("post", post.getTitle());
				super.onPostCreated(post);
			}
		});
		*/

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
				public void OnLoginStart() {
					loginProgress.setTitle("Please wait");
					loginProgress.setMessage("Logging you in");
					loginProgress.show();
				}
				
				@Override
				public void OnLoginSuccess(WPSession session) {
					loginProgress.dismiss();
					Toast.makeText(getApplicationContext(), "Welcome back " +
					session.getDisplayname() +
					", you've been succesfully logged in", Toast.LENGTH_LONG).show();
				}

				@Override
				public void OnLoginFailure(WPSession session) {
					loginProgress.dismiss();

					String errorMsg;

					switch (session.getStatus()) {
					case Wordpress.LOGIN_FAILED:
						errorMsg = "check internet connection or server availability.";
						break;

					case Wordpress.LOGIN_USER_NAME_ERROR:
						errorMsg = "invalid user name,";
						break;
						
					case Wordpress.LOGIN_PASSWORD_ERROR:
						errorMsg = "Password cannot be empty";
						break;
						
					case Wordpress.LOGIN_CHECK_PASSWORD_AND_OR_USERNAME:
						errorMsg = "please check your username and/or password.";
						break;
					
					default:
						errorMsg = "check internet connection or server availability.";
						break;
					}
					
					Toast.makeText(getApplicationContext(), "Login failed with error code :\"" +
							Integer.toString(session.getStatus())+"\" " +
							errorMsg, Toast.LENGTH_LONG).show();
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
