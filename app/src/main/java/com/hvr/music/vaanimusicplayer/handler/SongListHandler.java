package com.hvr.music.vaanimusicplayer.handler;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.hvr.music.vaanimusicplayer.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongListHandler
{
    private static SongListHandler sSongListHandler;
    private final List<Song> mSongs;

    public static SongListHandler get(Context context, ContentResolver contentResolver)
    {
        if(sSongListHandler==null)
        {
            sSongListHandler = new SongListHandler(contentResolver);
        }
        return sSongListHandler;
    }

    private SongListHandler(ContentResolver contentResolver)
    {
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        mSongs = new ArrayList<>();

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,selection,null,sortOrder);
        for(int i=0;i<cursor.getCount();i++)
        {
            mSongs.clear();
            if (cursor.moveToFirst()) {
                do {
                    Song song = new Song();
                    song.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                    song.setSongName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    song.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    song.setArtistName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    song.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                    //Log.i("App", song.getAlbumName() + " " + song.getSongName() + " " + song.getArtistName() + " " + song.getDuration());

                    mSongs.add(song);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public List<Song> getSongs()
    {
        return mSongs;
    }
}
