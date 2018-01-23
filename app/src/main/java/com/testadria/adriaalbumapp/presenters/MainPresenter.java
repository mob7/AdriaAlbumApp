package com.testadria.adriaalbumapp.presenters;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.testadria.adriaalbumapp.activities.MainActivity;
import com.testadria.adriaalbumapp.adapters.AlbumsAdapter;
import com.testadria.adriaalbumapp.interfaces.IMainPresenter;
import com.testadria.adriaalbumapp.interfaces.IMainView;
import com.testadria.adriaalbumapp.models.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by OUSSAMA on 23/01/2018.
 */

public class MainPresenter implements IMainPresenter {

    private IMainView iMainView;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public void getFBAlbums() {
        Bundle parameters = new Bundle();

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + AccessToken.getCurrentAccessToken().getUserId() + "/albums",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        iMainView.getFBAlbumsCompleted(response);
                    }
                }
        ).executeAsync();
    }

    public void getAlbumPicture(final Album album) {
        Bundle params = new Bundle();
        params.putBoolean("redirect", false);

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + album.getId() + "/picture",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        iMainView.getAlbumPictureCompleted(album, response);
                    }
                }
        ).executeAsync();
    }

}
