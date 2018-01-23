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
public class AlbumsAdapter extends BaseAdapter {
    private Context context;
    private List<Album> albumValues;

    public AlbumsAdapter(Context context, List<Album> albumValues) {
        this.context = context;
        this.albumValues = albumValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumViewHolder mViewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.grid_album_item, parent, false);
            mViewHolder = new AlbumViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (AlbumViewHolder) convertView.getTag();
        }

        final Album mAlbum = albumValues.get(position);
        mViewHolder.mAlbumText.setText(mAlbum.getName());
        if (!TextUtils.isEmpty(mAlbum.getCover_photo()))
            Picasso.with(context).load(mAlbum.getCover_photo()).into(mViewHolder.mAlbumImage);

        mViewHolder.mAlbumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PhotosActivity.class);
                i.putExtra("id", mAlbum.getId());
                context.startActivity(i);
            }
        });

        return convertView;

    }

    private class AlbumViewHolder {
        ImageView mAlbumImage;
        TextView mAlbumText;

        public AlbumViewHolder(View v) {
            mAlbumImage = v.findViewById(R.id.grid_item_image);
            mAlbumText = v.findViewById(R.id.grid_item_label);
        }
    }

    @Override
    public int getCount() {
        return albumValues.size();
    }

    @Override
    public Object getItem(int position) {
        return albumValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}