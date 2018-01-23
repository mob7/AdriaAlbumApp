package com.testadria.adriaalbumapp.presenters;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.testadria.adriaalbumapp.activities.MainActivity;
import com.testadria.adriaalbumapp.interfaces.IPhotosPresenter;
import com.testadria.adriaalbumapp.interfaces.IPhotosView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by OUSSAMA on 23/01/2018.
 */

public class PhotosPresenter implements IPhotosPresenter {
    private IPhotosView iPhotosView;

    public PhotosPresenter(IPhotosView iPhotosView) {
        this.iPhotosView = iPhotosView;
    }

    public void GetAlbumImages(String albumId) {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + albumId + "/photos",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        iPhotosView.GetAlbumImagesCompleted(response);
                    }
                }
        ).executeAsync();
    }

}
