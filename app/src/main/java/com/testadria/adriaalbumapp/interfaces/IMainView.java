package com.testadria.adriaalbumapp.interfaces;

import com.facebook.GraphResponse;
import com.testadria.adriaalbumapp.models.Album;

/**
 * Created by OUSSAMA on 23/01/2018.
 */

public interface IMainView {
    void getFBAlbumsCompleted(GraphResponse response);

    void getAlbumPictureCompleted(final Album album, GraphResponse response);
}
