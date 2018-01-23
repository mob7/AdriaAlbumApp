package com.testadria.adriaalbumapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import com.testadria.adriaalbumapp.R;
import com.testadria.adriaalbumapp.adapters.AlbumsAdapter;
import com.testadria.adriaalbumapp.interfaces.IMainPresenter;
import com.testadria.adriaalbumapp.interfaces.IMainView;
import com.testadria.adriaalbumapp.models.Album;
import com.testadria.adriaalbumapp.presenters.MainPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ArrayList<Album> listAlbums = new ArrayList<>();
    private AccessTokenTracker accessTokenTracker;
    private GridView gridView;
    private AlbumsAdapter mAdapter;
    private IMainPresenter iMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        iMainPresenter = new MainPresenter(this);

        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null) {
            Log.d(TAG, "loggedIn : true");
            iMainPresenter.getFBAlbums();

        } else {
            Log.d(TAG, "loggedIn : false");
            gridView.setAdapter(null);
        }

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "user_photos");

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    // user logout
                    gridView.setAdapter(null);
                }
            }
        };

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, "Access Token: " + loginResult.getAccessToken().getToken());

                        accessTokenTracker.startTracking();
                        iMainPresenter.getFBAlbums();
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e(TAG, "onError");
                    }
                });

    }

    private void initViews() {
        loginButton = findViewById(R.id.login_button);
        gridView = findViewById(R.id.gridview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getFBAlbumsCompleted(GraphResponse response) {
        try {
            if (response.getError() == null) {
                JSONObject joMain = response.getJSONObject();

                if (joMain.has("data")) {
                    JSONArray jaData = joMain.optJSONArray("data");
                    for (int i = 0; i < jaData.length(); i++) {
                        JSONObject joAlbum = jaData.getJSONObject(i);

                        Album mAlbum = new Album();
                        mAlbum.setCreated_time(joAlbum.optString("created_time"));
                        mAlbum.setName(joAlbum.optString("name"));
                        mAlbum.setId(joAlbum.optString("id"));
                        iMainPresenter.getAlbumPicture(mAlbum);
                    }
                }

            } else {
                Log.d(TAG, response.getError().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAlbumPictureCompleted(final Album album, GraphResponse response) {
        try {
            if (response.getError() == null) {
                JSONObject joMain = response.getJSONObject();

                if (joMain.has("data")) {
                    JSONObject jaData = joMain.getJSONObject("data");
                    album.setCover_photo(jaData.optString("url"));
                    listAlbums.add(album);

                    Log.e(TAG, album.toString());
                    Log.e(TAG, "Albums size : " + listAlbums.size());

                    Collections.sort(listAlbums, new Comparator<Album>() {
                        @Override
                        public int compare(Album album1, Album album2) {
                            return album1.getName().compareTo(album2.getName());
                        }
                    });
                    mAdapter = new AlbumsAdapter(MainActivity.this, listAlbums);
                    gridView.setAdapter(mAdapter);
                }

            } else {
                Log.d(TAG, response.getError().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
