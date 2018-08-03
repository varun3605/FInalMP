package com.hvr.music.vaanimusicplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hvr.music.vaanimusicplayer.handler.BgImageHandler;

public class SettingsActivity extends AppCompatActivity {

    private TextView mSetImageTextView;
    private String mBgImagePath;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mPrefEditor;
    private static final int REQUEST_CODE_FILE_PATH_BROWSED = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSetImageTextView = findViewById(R.id.bg_img_set);
        mSetImageTextView.setText(R.string.bg_img_set);
        mSetImageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Choose Application: "),REQUEST_CODE_FILE_PATH_BROWSED);
            }
        });

    }

    public static Intent newIntent(Context context)
    {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUEST_CODE_FILE_PATH_BROWSED:
                if (resultCode == RESULT_OK)
                {
                    mBgImagePath = data.getDataString();
                    Log.i("App",mBgImagePath);
                    BgImageHandler.getInstance().setImageUri(mBgImagePath);
                }

        }
    }

}
