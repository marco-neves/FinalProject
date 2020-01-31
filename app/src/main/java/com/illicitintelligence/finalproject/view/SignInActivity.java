package com.illicitintelligence.finalproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;
import com.illicitintelligence.finalproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private List<AuthUI.IdpConfig> providers = Arrays.asList( new AuthUI.IdpConfig.GitHubBuilder().build() );
    List<String> scopes =
            new ArrayList<String>() {
                {
                    add( "repo" );
                }
            };

    OAuthProvider.Builder provider = OAuthProvider.newBuilder( "github.com" );


    private static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.sign_in_activity );

        login();
    }

    public void login() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        provider.setScopes( scopes );

        Intent intent = new Intent( this, MainActivity.class );
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().

                                    OAuthCredential credential = (OAuthCredential) authResult.getCredential();
                                    Log.d( TAG, "onSuccess: Pending Task: " + credential.getAccessToken() );

                                }
                            } )
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Log.d( TAG, "onFailure: Pending Failure: " );
                                }
                            } );
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.

            firebaseAuth
                    .startActivityForSignInWithProvider(this, provider.build() )
                    .addOnSuccessListener(
                    new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // authResult.getCredential().getAccessToken().

                            OAuthCredential credential = (OAuthCredential) authResult.getCredential();
                            Log.d( TAG, "onSuccess: Regular task: " + credential.getAccessToken() );
                            intent.putExtra( "AccessToken", credential.getAccessToken() );
                            startActivity( intent );

                        }
                    } )
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Log.d( TAG, "onFailure: RegularFailure: " + e.getMessage() );
                                    Boolean privateRepo = false;

                                    intent.putExtra("isPrivate", privateRepo);

                                    startActivity(intent);
                                }
                            } );
        }


    }

}
