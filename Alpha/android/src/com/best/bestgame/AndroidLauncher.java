package com.best.bestgame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.best.bestgame.score.ScoreListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.Task;

import static android.content.ContentValues.TAG;

public class AndroidLauncher extends AndroidApplication implements ScoreListener{

	public static final int RC_SIGN_IN = 13;

	private GoogleSignInClient mGoogleSignInClient;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GoogleSignInOptions gso =
				new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
//						.requestIdToken("893611002055-uka96tblnbg6hcibkggba8r91v2eq9c1.apps.googleusercontent.com")
						.requestEmail()
						.build();
		mGoogleSignInClient = GoogleSignIn.getClient(this,
				gso);


		signIn();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		BestGame game = new BestGame();
		game.registerScoreListener(this);
		initialize(game, config);
	}

	@Override
	public void scoreChanged(int score) {
		GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
		if (account == null) {
//			Toast.makeText(this, R.string.score_submit_error, Toast.LENGTH_SHORT).show();
			return ;
		}
		Games.getLeaderboardsClient(this, account)
				.submitScore(getString(R.string.leaderboard_id), 1337);
	}

	private void signIn() {
		Intent signInIntent = mGoogleSignInClient.getSignInIntent();
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			// The Task returned from this call is always completed, no need to attach
			// a listener.
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			handleSignInResult(task);
		}

	}

	private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
		try {
			GoogleSignInAccount account = completedTask.getResult(ApiException.class);

			// Signed in successfully, show authenticated UI.
			Log.d(TAG, "Email: " + account.getEmail());
		} catch (ApiException e) {
			// The ApiException status code indicates the detailed failure reason.
			// Please refer to the GoogleSignInStatusCodes class reference for more information.

			Log.e(TAG, "signInResult:failed code=" + e.getStatusCode() + " " + e.getMessage());
		}
	}
}


