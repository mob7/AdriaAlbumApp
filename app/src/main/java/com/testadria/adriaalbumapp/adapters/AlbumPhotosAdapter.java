package com.testadria.adriaalbumapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.testadria.adriaalbumapp.R;
import com.testadria.adriaalbumapp.activities.PhotosActivity;
import com.testadria.adriaalbumapp.models.Album;

import java.util.List;

/**
 * Created by OUSSAMA on 23/01/2018.
 */

public class AlbumPhotosAdapter extends BaseAdapter {
    private Context context;
    private List<String> albumPhotoValues;

    public AlbumPhotosAdapter(Context context, List<String> albumPhotoValues) {
        this.context = context;
        this.albumPhotoValues = albumPhotoValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumPhotoViewHolder mViewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.grid_album_photo_item, parent, false);
            mViewHolder = new AlbumPhotoViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (AlbumPhotoViewHolder) convertView.getTag();
        }

        String mAlbumPhoto = albumPhotoValues.get(position);
        if (!TextUtils.isEmpty(mAlbumPhoto))
            Picasso.with(context).load(mAlbumPhoto).into(mViewHolder.mAlbumImage);

        return convertView;

    }

    private class AlbumPhotoViewHolder {
        ImageView mAlbumImage;

        public AlbumPhotoViewHolder(View v) {
            mAlbumImage = v.findViewById(R.id.grid_pic_item_image);
        }
    }

    @Override
    public int getCount() {
        return albumPhotoValues.size();
    }

    @Override
    public Object getItem(int position) {
        return albumPhotoValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
