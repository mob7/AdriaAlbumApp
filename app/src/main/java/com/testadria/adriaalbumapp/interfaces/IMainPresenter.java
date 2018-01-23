package com.testadria.adriaalbumapp.interfaces;

import com.testadria.adriaalbumapp.models.Album;

/**
 * Created by OUSSAMA on 23/01/2018.
 */

public interface IMainPresenter {
    void getFBAlbums();

    void getAlbumPicture(final Album album);
}
