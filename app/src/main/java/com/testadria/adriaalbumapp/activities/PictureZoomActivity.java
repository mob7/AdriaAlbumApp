package com.testadria.adriaalbumapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.testadria.adriaalbumapp.R;
import com.testadria.adriaalbumapp.presenters.PhotosPresenter;

public class PictureZoomActivity extends AppCompatActivity {

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_zoom);
        initViews();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Picasso.with(this).load(url).into(photoView);

    }

    private void initViews() {
        photoView = findViewById(R.id.photoView);
    }
}
