package com.example.playlistmanager.services;

import com.example.playlistmanager.models.AddSongResponse;
import com.example.playlistmanager.models.Playlist;
import com.example.playlistmanager.models.PlaylistPost;
import com.example.playlistmanager.models.Track;
import com.example.playlistmanager.models.TrackSearch;
import com.example.playlistmanager.models.User;
import com.example.playlistmanager.models.UserPlaylistResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyDataService {

    //GET PLAYLISTS
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/v1/me/playlists")
    Call<UserPlaylistResponse> getAllUserPlaylists
            (@Query("limit") int lim,
             @Query("offset") int offs,
             @Header("Authorization") String token);

    //GET USER
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/v1/me")
    Call<User> getUser
            (@Header("Authorization") String token);

    //GET SEARCH
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/v1/search")
    Call<TrackSearch> search
            (@Query("q") String q,
             @Query("type") String type,
             @Query("limit") int lim,
             @Query("offset") int offs,
             @Header("Authorization") String token);

    //POST PLAYLIST
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/v1/users/{user_id}/playlists")
    Call<Playlist> newPlaylist(
            @Path("user_id") String userId,
            @Body PlaylistPost options,
           /* @Field("name") String name,
            @Field("description") String description,
            @Field("public") Boolean isPublic,*/
            @Header("Authorization") String token);

    //POST SONG
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/v1/playlists/{playlist_id}/tracks")
    Call<AddSongResponse> addSong(
            @Path("playlist_id") String playlist_id,
            @Query("uris") String tracks,
            @Header("Authorization") String token
    );

}
