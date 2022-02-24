package com.example.playlistmanager.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmanager.R;
import com.example.playlistmanager.models.Artist;
import com.example.playlistmanager.models.Track;

import java.util.ArrayList;
import java.util.List;

public class songsRecyclerViewAdapter extends RecyclerView.Adapter{
    private List<Track> itemClassList;

    // public constructor for this class
    public songsRecyclerViewAdapter()
    {
        this.itemClassList = new ArrayList<Track>() {};
    }

    public void updateAdapter(ArrayList<Track> newPlaylist){
        this.itemClassList = newPlaylist;
        notifyDataSetChanged();
    }


    // Create classes for each layout ViewHolder.

    public class LayoutSongViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView text_one, text_two;
        private LinearLayout linearLayout;

        public LayoutSongViewHolder(@NonNull View itemView)
        {
            super(itemView);
            icon = itemView.findViewById(R.id.songimage);
            text_one = itemView.findViewById(R.id.song_text_one);
            text_two = itemView.findViewById(R.id.song_text_two);
            linearLayout = itemView.findViewById(R.id.songlinearlayout);
        }

        private void setViews(int image, String textOne, String textTwo)
        {
            icon.setImageResource(image);
            text_one.setText(textOne);
            text_two.setText(textTwo);
        }
    }

    // In the onCreateViewHolder, inflate the
    // xml layout as per the viewType.
    // This method returns either of the
    // ViewHolder classes defined above,
    // depending upon the layout passed as a parameter.

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutTwo = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_recycler_view_cards, parent, false);
        return new LayoutSongViewHolder(layoutTwo);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //int image = itemClassList.get(position).geticon();
        int image = R.drawable.ic_newplaylist;
        String text_one = itemClassList.get(position).getName();
        StringBuilder text_two = new StringBuilder("Artists:");
        for (Artist artist: itemClassList.get(position).getArtists()) {
            text_two.append(" ").append(artist.name).append(",");
        }
        text_two.deleteCharAt(text_two.length()-1);
        ((LayoutSongViewHolder)holder).setViews(image, text_one, text_two.toString());
        ((LayoutSongViewHolder)holder).linearLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(),
                                "Hello from Layout Two!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return itemClassList.size();
    }

}
