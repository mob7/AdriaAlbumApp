package com.testadria.adriaalbumapp;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ImageView ivTest;
    private ArrayList<String> lstFBImages;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Log.e(TAG, "loggedIn : " + (AccessToken.getCurrentAccessToken() != null));

//        if (loggedIn)
//            getFBAlbums();
//        else
//            ivTest.setVisibility(View.INVISIBLE);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "user_photos");

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Log.e(TAG, "currentAccessToken null");
                    //write your code here what to do when user logout
                    ivTest.setVisibility(View.INVISIBLE);
//                    loggedIn = false;
                } else {
                    Log.e(TAG, "currentAccessToken not null");
                    getFBAlbums();
                }

            }
        };

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e(TAG, "onSuccess");
                        Log.e(TAG, "Access Token: " + loginResult.getAccessToken().getToken());

                        accessTokenTracker.startTracking();

                        //make the API call
                        getFBAlbums();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e(TAG, "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e(TAG, "onError");
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccessToken.getCurrentAccessToken() != null)
            getFBAlbums();
    }

    private void initViews() {
        loginButton = findViewById(R.id.login_button);
        ivTest = findViewById(R.id.ivTest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getFBAlbums() {

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + AccessToken.getCurrentAccessToken().getUserId() + "/albums",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG, "onCompleted: " + response.toString());
                        Log.e(TAG, "onCompleted: " + response.getJSONObject());

                        Log.d("TAG", "Facebook Albums: " + response.toString());
                        try {
                            if (response.getError() == null) {
                                JSONObject joMain = response.getJSONObject();
                                if (joMain.has("data")) {
                                    JSONArray jaData = joMain.optJSONArray("data");
//                                    alFBAlbum = new ArrayList<>();
                                    for (int i = 0; i < jaData.length(); i++) {//find no. of album using jaData.length()
                                        JSONObject joAlbum = jaData.getJSONObject(i); //convert perticular album into JSONObject
                                        GetFacebookImages(joAlbum.optString("id")); //find Album ID and get All Images from album
                                    }
                                }
                            } else {
                                Log.d("Test", response.getError().toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    public void GetFacebookImages(final String albumId) {
//        String url = "https://graph.facebook.com/" + "me" + "/"+albumId+"/photos?access_token=" + AccessToken.getCurrentAccessToken() + "&fields=images";
        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");

        //make the API call
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + albumId + "/photos",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        Log.v("TAG", "Facebook Photos response: " + response);
//                        tvTitle.setText("Facebook Images");
                        try {
                            if (response.getError() == null) {

                                JSONObject joMain = response.getJSONObject();
                                if (joMain.has("data")) {
                                    JSONArray jaData = joMain.optJSONArray("data");
                                    lstFBImages = new ArrayList<>();
                                    for (int i = 0; i < jaData.length(); i++) {         //Get no. of images
                                        JSONObject joAlbum = jaData.getJSONObject(i);
                                        JSONArray jaImages = joAlbum.getJSONArray("images");    //get images Array in JSONArray format
                                        if (jaImages.length() > 0) {
//                                            Images objImages = new Images();    //Images is custom class with string url field
//                                            objImages.setImage_url(jaImages.getJSONObject(0).getString("source"));
                                            String objImages = jaImages.getJSONObject(0).getString("source");
                                            lstFBImages.add(objImages);     //lstFBImages is Images object array
                                        }
                                    }
                                }

                                //set your adapter here
                                Log.e(TAG, "onCompleted: " + lstFBImages.get(0));
                                Log.e(TAG, "onCompleted: " + lstFBImages.get(1));
                                ivTest.setVisibility(View.VISIBLE);
                                Picasso.with(MainActivity.this).load(lstFBImages.get(0)).into(ivTest);


                            } else {
                                Log.v("TAG", response.getError().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }

}
