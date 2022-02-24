package com.example.playlistmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmanager.UI.RecyclerViewAdapter;
import com.example.playlistmanager.UI.songsRecyclerViewAdapter;
import com.example.playlistmanager.models.AddSongResponse;
import com.example.playlistmanager.models.GetPlaylistTracks;
import com.example.playlistmanager.models.Playlist;
import com.example.playlistmanager.models.PlaylistPost;
import com.example.playlistmanager.models.PlaylistTracks;
import com.example.playlistmanager.models.TrackSearch;
import com.example.playlistmanager.models.User;
import com.example.playlistmanager.models.UserPlaylistResponse;
import com.example.playlistmanager.services.SpotifyDataService;
import com.example.playlistmanager.recyclerViewActivity;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;


//https://developer.spotify.com/documentation/android/guides/android-authentication/#adding-the-library-to-the-project

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "05684c1d384d45c2b5ef557e0dfe11b3";
    private static final String REDIRECT_URI = "http://com.yourdomain.yourapp/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final int REQUEST_CODE = 1337;
    private String TOKEN;
    private String userID = "";
    Boolean isUser = false;
    RecyclerViewAdapter adapterClass = new RecyclerViewAdapter();
    RecyclerViewAdapter AddPlaylistAdapterClass = new RecyclerViewAdapter();
    songsRecyclerViewAdapter songAdapterClass = new songsRecyclerViewAdapter();


    static SeekBar simpleProgressBar;
    static int progreso = 0;

    //Spotify client autorization
    private void authorization(){
        AuthorizationRequest.Builder authorizationBuilder =
                (new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI));
        authorizationBuilder.setScopes(new String[]{
                "streaming",
                "user-read-private",                // get current user's profile
                "user-read-email",
                "playlist-modify-private",          //Create a Playlist, Add Items to a Playlist
                "playlist-modify-public",
                "playlist-read-collaborative",       //Get a List of Current User's Playlists
                "playlist-read-private"             //Get a List of Current User's Playlists
        });

        AuthorizationRequest request = authorizationBuilder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    System.out.println("prueba TOKEN RESPONSE: Tengo un tokeeeen:   " + response.getAccessToken());
                    TOKEN = response.getAccessToken();
                    getUser();
                    spotifyConnect();


                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    System.out.println("prueba TOKEN RESPONSE: F" + response.getError());

                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authorization();
        recylerViewStart();
    }

    public void recylerViewStart(){
        // From the MainActivity, find the RecyclerView.
        RecyclerView recyclerView
                = findViewById(R.id.recyclerView);

        // Create and set the layout manager
        // For the RecyclerView.
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterClass);
    }

    public void updateRecyclerView(){
        ArrayList<Playlist> itemClasses = filterPlaylists();
        adapterClass.updateAdapter(itemClasses);
    }

    public ArrayList<Playlist> filterPlaylists(){
        ArrayList<Playlist> filteredPlaylists = new ArrayList<Playlist>();
        for (Playlist playlist: userPlaylists) {
            System.out.println("prueba playlistRecycler: " + playlist.getName());
            if (playlist.getOwner().getId() != null){
                if(playlist.getOwner().getId().equals(userID)){
                    filteredPlaylists.add(playlist);
                }
                else{
                    if (playlist.getCollaborative() != null) {
                        if (playlist.getCollaborative()) {
                            filteredPlaylists.add(playlist);
                        }
                    }
                }
            }
        }
        return filteredPlaylists;
    }


    private Boolean isPaused = false;


    @Override
    protected void onStart() {
        super.onStart();
        // We will start writing our code here.
    }

    private void spotifyConnect(){
        //Spotify conection
        ProgressDialog progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        progressDoalog.dismiss();
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("prueba MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    // On successful Spotify connection
    private void connected() {
        // Then we will write some more code here.
        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:21NfJpz1f2rFl0D1qlS5dG");
        // Subscribe to PlayerState

        simpleProgressBar = (SeekBar) findViewById(R.id.simpleProgressBar);
        setProgressValue();

        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    Log.d("prueba", "siPaused" + playerState.isPaused);
                    final Track track = playerState.track;
                    if (track != null) {

                        isPaused = playerState.isPaused;


                        simpleProgressBar.setMax((int)playerState.track.duration);
                        simpleProgressBar.setOnSeekBarChangeListener(
                                new SeekBar.OnSeekBarChangeListener() {
                                    int progressChangedValue = 0;
                                    //hace un llamado a la perilla cuando se arrastra
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar,
                                                                  int progress, boolean fromUser) {
                                        progressChangedValue = progress;

                                    }

                                    //hace un llamado  cuando se toca la perilla
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    //hace un llamado  cuando se detiene la perilla
                                    public void onStopTrackingTouch(SeekBar seekBar) {
                                        //thread.interrupt();
                                        progreso = progressChangedValue;
                                        //progressChangedValue = 0;
                                        mSpotifyAppRemote.getPlayerApi().seekTo(progreso);


                                    }

                                });


                        //Log.d("Test", track.name + " by " + track.artist.name);
                    }
                    ImageView buttonImage = (ImageView)findViewById(R.id.ImageViewPlayButton);
                    if (isPaused){
                        buttonImage.setImageResource(R.drawable.ic_play);

                    }
                    else{
                        buttonImage.setImageResource(R.drawable.ic_pause);
                    }
                });
    }


    private void setProgressValue()  {
        if(progreso>=simpleProgressBar.getMax()){
            progreso = 0;

        }
        if(!isPaused) {
        simpleProgressBar.setProgress(progreso);
        progreso += 1000;
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("MainActivity", progreso + "");
                setProgressValue();


            }

        });
        thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Aaand we will finish off here. THATS A LIE, code for after minimize
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Aaand we will finish off here.
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public void onClick_AddSongPlayList(View v){
        Intent i = new Intent(MainActivity.this, AddSongActivity.class);
        startActivity(i);

    }
    public void onClick_TestRecyclerView(View v){
        Intent i = new Intent(MainActivity.this, recyclerViewActivity.class);
        startActivity(i);

    }

    // Play-Stop Button actions
    public void onClick_playStop(View play_stop_view) {
        if (isPaused){
            mSpotifyAppRemote.getPlayerApi().resume();
            play_stop_view.setBackgroundResource(R.drawable.ic_play);
        }
        else {
            mSpotifyAppRemote.getPlayerApi().pause();
            play_stop_view.setBackgroundResource(R.drawable.ic_pause);
        }
    }

    // showPlaylist Button actions
    public void onClick_showPlaylist(View showPlaylist_view) {
        getAllUserPlaylists();
        addSong();
    }
    List<Playlist> userPlaylists;
    private void getAllUserPlaylists() {

        ProgressDialog progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        int limit = 50;
        int offset = 0;

        SpotifyDataService service = RetrofitSpotifyClient.getRetrofitInstance().create(SpotifyDataService.class);
        Log.d("prueba token:", TOKEN);
        Call<UserPlaylistResponse> call = service.getAllUserPlaylists(limit, offset, "Bearer "+ TOKEN);
        call.enqueue(new Callback<UserPlaylistResponse>() {
            @Override
            public void onResponse(Call<UserPlaylistResponse> call, Response<UserPlaylistResponse> response) {
                progressDoalog.dismiss();
                System.out.println("prueba getPlylst:" + response.body());
                if(response.body()!=null){
                    userPlaylists = response.body().getItems();
                    System.out.println(response.body().getItems().get(0).getName());
                    updateRecyclerView();
                } else {
                    if(response.errorBody()!=null){
                        try {
                            System.out.println("prueba, error getPlylst:" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserPlaylistResponse> call, Throwable t) {
                progressDoalog.dismiss();
                System.out.println("prueba, error getPlylst onfailure:" + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void newPlaylist(String name, Boolean isPublic){
        PlaylistPost nuevaPlaylist = new PlaylistPost(name, " description goes here", isPublic);
        SpotifyDataService service = RetrofitSpotifyClient.getRetrofitInstance().create(SpotifyDataService.class);
        Log.d("prueba token newPlylst:", TOKEN);
        if (userID.equals("")){
            getUser();
        }
        Call<Playlist> call = service.newPlaylist(userID, nuevaPlaylist, "Bearer "+ TOKEN);
        //Call<Playlist> call = service.newPlaylist(userID, nuevaPlaylist.getName(), nuevaPlaylist.getDescription(), nuevaPlaylist.getPublic() , "Bearer "+ TOKEN);
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                System.out.println("prueba newPlylst:" + response.body());
                if(response.body()!=null){
                    System.out.println(response.body().getName());
                } else {
                    if(response.errorBody()!=null){
                        try {
                            System.out.println("prueba, error newPlylst:" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                System.out.println("prueba, error:" + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUser() {
        ProgressDialog progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        SpotifyDataService service = RetrofitSpotifyClient.getRetrofitInstance().create(SpotifyDataService.class);
        Call<User> call = service.getUser("Bearer "+ TOKEN);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDoalog.dismiss();
                System.out.println("prueba usuario :" + response.body());
                if(response.body()!=null){
                    System.out.println(response.body().getId());
                    userID = response.body().getId();
                    isUser = true;
                    getAllUserPlaylists();
                } else {
                    if(response.errorBody()!=null){
                        try {
                            System.out.println("prueba, error de user:" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("prueba, error user onFailure:" + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // showPlaylist Button actions
    public void onClick_newPlaylist(View showPlaylist_view) {
        ConstraintLayout newPlaylisLayout = (ConstraintLayout)findViewById(R.id.onTopLayout);
        newPlaylisLayout.setVisibility(View.VISIBLE);
    }


    public void onClick_closeNewPlaylist(View view) {
        ConstraintLayout newPlaylisLayout = (ConstraintLayout)findViewById(R.id.onTopLayout);
        newPlaylisLayout.setVisibility(View.INVISIBLE);
        EditText defaultText = (EditText)findViewById(R.id.editTextPlaylistName);
        CheckBox isPublicButton = (CheckBox)findViewById(R.id.isPublicRadioButton);
        defaultText.setText(R.string.nombre_de_la_playlist);
        isPublicButton.setChecked(false);
        songListLayoutStart(view);
    }

    public void onClick_createNewPlaylist(View view) {
        EditText nameText = (EditText)findViewById(R.id.editTextPlaylistName);
        CheckBox isPublicButton = (CheckBox)findViewById(R.id.isPublicRadioButton);
        newPlaylist(nameText.getText().toString(), isPublicButton.isChecked());
        onClick_closeNewPlaylist(view);
    }

    public void onClick_showPlaylists(View view){
        if(view.getBackground().equals(R.drawable.ic_newplaylist)){
            AddPlaylistRecylerViewStart();
            AddPlaylistUpdateRecyclerView();
            recyclerViewActivity recycler = new recyclerViewActivity();
            recycler.openPlaylists(view);
        }

    }
    public void AddPlaylistRecylerViewStart(){
        // From the MainActivity, find the RecyclerView.
        RecyclerView recyclerView
                = findViewById(R.id.AddToPlaylistRecycleView);

        // Create and set the layout manager
        // For the RecyclerView.
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(AddPlaylistAdapterClass);
    }
    public void AddPlaylistUpdateRecyclerView(){
        ArrayList<Playlist> itemClasses = filterPlaylists();
        AddPlaylistAdapterClass.updateAdapter(itemClasses);
    }


    public void search(View view){
        //searching = searching.replaceAll(" ", "%20");
        EditText searching = (EditText)findViewById(R.id.editTextSearch);
        System.out.println("prueba palabra busqueda :" + searching);
        SpotifyDataService service = RetrofitSpotifyClient.getRetrofitInstance().create(SpotifyDataService.class);
        Call<TrackSearch> call = service.search(searching.getText().toString(),"track", 20,0,"Bearer "+ TOKEN);
        call.enqueue(new Callback<TrackSearch>() {
            @Override
            public void onResponse(Call<TrackSearch> call, Response<TrackSearch> response) {
                System.out.println("prueba busqueda texedit:" + searching);
                if(response.body()!=null){
                    System.out.println("prueba busqueda :" +response.body().getItems().getItems().get(0).getName());
                } else {
                    if(response.errorBody()!=null){
                        try {
                            System.out.println("prueba, error de busqueda:" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TrackSearch> call, Throwable t) {
                System.out.println("prueba, error busqueda onFailure:" + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*public void addSong(com.example.playlistmanager.models.Track track, Playlist playlist){
        System.out.println("prueba track busqueda :" + track.getName());
        String trackID = "spotify:track:" + track.getId();
        String playlistID = playlist.getId();*/

    public void addSong(){
        String trackID = "spotify:track:" + "3UjtIALeg72qmJiKPWBvM3";
        String playlistID = "1xKTKvIHe98vjQ0D8HUuuj";
        SpotifyDataService service = RetrofitSpotifyClient.getRetrofitInstance().create(SpotifyDataService.class);
        Call<AddSongResponse> call = service.addSong(playlistID,trackID, "Bearer "+ TOKEN);
        call.enqueue(new Callback<AddSongResponse>() {
            @Override
            public void onResponse(Call<AddSongResponse> call, Response<AddSongResponse> response) {
                System.out.println("prueba addSong :" + response.body());
                if(response.body()!=null){
                    System.out.println("prueba addSong :" +response.body().getSnapshot_id());
                } else {
                    if(response.errorBody()!=null){
                        try {
                            System.out.println("prueba, error de addSong:" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddSongResponse> call, Throwable t) {
                System.out.println("prueba, error addSong onFailure:" + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ArrayList<com.example.playlistmanager.models.Track> playlistsSongsRequest = new ArrayList<com.example.playlistmanager.models.Track>();
    void getPlaylistSongs(String playlist_id){
        String playlistID = "1xKTKvIHe98vjQ0D8HUuuj";
        SpotifyDataService service = RetrofitSpotifyClient.getRetrofitInstance().create(SpotifyDataService.class);
        Call<GetPlaylistTracks> call = service.getPlaylistSongs(playlistID, 100, 0, "Bearer "+ TOKEN);
        call.enqueue(new Callback<GetPlaylistTracks>() {
            @Override
            public void onResponse(Call<GetPlaylistTracks> call, Response<GetPlaylistTracks> response) {
                System.out.println("prueba getPlaylistSongs :" + response.body());
                if(response.body()!=null){
                    System.out.println("prueba getPlaylistSongs :" +response.body().getItems().get(0).getTrack().getName());
                    for (int i = 0; i < response.body().getItems().size() ; i++)  {
                        if(response.body().getItems().get(i) != null){
                            playlistsSongsRequest.add(response.body().getItems().get(i).getTrack());
                        }
                    }
                } else {
                    if(response.errorBody()!=null){
                        try {
                            System.out.println("prueba, error de getPlaylistSongs:" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPlaylistTracks> call, Throwable t) {
                System.out.println("prueba, error getPlaylistSongs onFailure:" + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void songListLayoutStart(View view){
        getPlaylistSongs("holi");
        setContentView(R.layout.song_list_layout);
        songRecylerViewStart();
    }

    public void songRecylerViewStart(){
        // From the MainActivity, find the RecyclerView.
        RecyclerView recyclerView
                = findViewById(R.id.songsRecyclerView);

        // Create and set the layout manager
        // For the RecyclerView.
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(songAdapterClass);
        updateSongRecyclerView();
    }

    public void updateSongRecyclerView(){
        ArrayList<com.example.playlistmanager.models.Track> itemClasses = playlistsSongsRequest;
        songAdapterClass.updateAdapter(itemClasses);
    }


}