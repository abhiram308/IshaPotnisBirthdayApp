package com.example.hatolkaa.myapplication;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hatolkaa on 26/03/17.
 */

public class DetailedMessage extends AppCompatActivity{

    private static final String PAUSE = "Pause";
    private static final String PLAY = "Play";
    private static final String STOP = "Stop";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String author = getAuthor();
        getSupportActionBar().setTitle(author.toUpperCase());

        setImageOfAuthorOnDetailedMessageScreen(author);

        TextView message = (TextView) findViewById(R.id.message);
        message.setText(readFileContents(author));

        MediaPlayer mediaPlayer = setupVoiceMessageTrack(author);

        Button voiceMessagePlayPauseButton = getButton();
        setButtonOnClickListener(voiceMessagePlayPauseButton, mediaPlayer);

    }

    private void setButtonOnClickListener(final Button button, final MediaPlayer mediaPlayer) {
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    button.setText(PLAY);
                } else {
                    mediaPlayer.start();
                    button.setText(PAUSE);
                }
            }
        });
    }

    private Button getButton() {
        return (Button) findViewById(R.id.mediaButton);
    }

    private MediaPlayer setupVoiceMessageTrack(String author) {
        String authorVoiceMessage = author + "_voice_message";

        int fileId = getResources().getIdentifier(authorVoiceMessage, "raw", getPackageName());
        MediaPlayer mediaPlayer = MediaPlayer.create(this, fileId);

        return mediaPlayer;

    }

    private String readFileContents (String author) {
        InputStream inputStream = getFile(author);
        StringBuilder stringBuilder = null;
        String fileContents = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();
            if (inputStream != null) {
                while ((fileContents = reader.readLine()) != null) {
                    stringBuilder.append(fileContents + "\n" );
                }
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }

        return stringBuilder.toString();
    }

    private InputStream getFile(String author) {
        int fileId = getResources().getIdentifier(author, "raw", getPackageName());
        return getResources().openRawResource(fileId);
    }

    private String getAuthor() {
        Bundle bundle = getIntent().getExtras();
        String value = null;
        if(bundle != null)
            value = bundle.getString("author");

        return value.replace(' ', '_');
    }

    private void setImageOfAuthorOnDetailedMessageScreen(String author) {
        ImageView image = (ImageView) findViewById(R.id.imageView1);

        Resources res = getResources();
        String drawableName = author;

        int resID = res.getIdentifier(drawableName , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID);
        image.setImageDrawable(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
