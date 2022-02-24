package com.example.playlistmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class recyclerViewActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_cards);




    }

    public void openPlaylists(View v){
        Intent i = new Intent(recyclerViewActivity.this, AddSongActivity.class);
        startActivity(i);

    }
}
