package com.hvr.music.vaanimusicplayer.handler;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.hvr.music.vaanimusicplayer.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumListHandler
{
    private static AlbumListHandler sAlbumListHandler;
    private final List<Album> mAlbums;

    public static AlbumListHandler get(Context context, ContentResolver contentResolver)
    {
        if(sAlbumListHandler==null)
        {
            sAlbumListHandler = new AlbumListHandler(contentResolver);
        }
        return sAlbumListHandler;
    }

    private AlbumListHandler(ContentResolver contentResolver)
    {
        String sortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER + " ASC";

        mAlbums = new ArrayList<>();

        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,null,null,sortOrder);
        for(int i=0;i<cursor.getCount();i++)
        {
            mAlbums.clear();
            if (cursor.moveToFirst()) {
                do {
                    Album album = new Album();
                    album.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
                    album.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
                    album.setNumberofSongs(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));

                    mAlbums.add(album);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public List<Album> getAlbums()
    {
        return mAlbums;
    }
}
