package com.testadria.adriaalbumapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.testadria.adriaalbumapp.R;
import com.testadria.adriaalbumapp.adapters.AlbumPhotosAdapter;
import com.testadria.adriaalbumapp.interfaces.IMainPresenter;
import com.testadria.adriaalbumapp.interfaces.IPhotosPresenter;
import com.testadria.adriaalbumapp.interfaces.IPhotosView;
import com.testadria.adriaalbumapp.models.Album;
import com.testadria.adriaalbumapp.presenters.MainPresenter;
import com.testadria.adriaalbumapp.presenters.PhotosPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity implements IPhotosView {

    private static final String TAG = PhotosActivity.class.getSimpleName();
    private IPhotosPresenter iPhotosPresenter;
    private ArrayList<String> listAlbumImages = new ArrayList<>();
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        initViews();

        iPhotosPresenter = new PhotosPresenter(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        iPhotosPresenter.GetAlbumImages(id);
    }

    private void initViews() {
        gridView = findViewById(R.id.gridViewPics);
    }

    public void GetAlbumImagesCompleted(GraphResponse response) {
        Log.d("TAG", "GetAlbumImagesCompleted : " + response);

        try {
            if (response.getError() == null) {

                JSONObject joMain = response.getJSONObject();
                if (joMain.has("data")) {
                    JSONArray jaData = joMain.optJSONArray("data");

                    for (int i = 0; i < jaData.length(); i++) {
                        JSONObject joAlbum = jaData.getJSONObject(i);
                        JSONArray jaImages = joAlbum.getJSONArray("images");
                        if (jaImages.length() > 0) {
                            String objImages = jaImages.getJSONObject(0).getString("source");
                            listAlbumImages.add(objImages);
                        }
                    }

                }

                Log.d(TAG, "lstFBImages size : " + listAlbumImages.size());
                gridView.setAdapter(new AlbumPhotosAdapter(PhotosActivity.this, listAlbumImages));

            } else {
                Log.v("TAG", response.getError().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
