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
import com.example.playlistmanager.models.Playlist;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter{
    private List<Playlist> itemClassList;

    // public constructor for this class
    public RecyclerViewAdapter()
    {
        this.itemClassList = new ArrayList<Playlist>() {};
    }

    public void updateAdapter(ArrayList<Playlist> newPlaylist){
        this.itemClassList = newPlaylist;
        notifyDataSetChanged();
    }


    // Create classes for each layout ViewHolder.

    public class LayoutTwoViewHolder
            extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView text_one, text_two;
        private LinearLayout linearLayout;

        public LayoutTwoViewHolder(@NonNull View itemView)
        {
            super(itemView);
            icon = itemView.findViewById(R.id.image);
            text_one = itemView.findViewById(R.id.text_one);
            text_two = itemView.findViewById(R.id.text_two);
            linearLayout = itemView.findViewById(R.id.linearlayout);
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
                        .inflate(R.layout.recycler_view_cards, parent, false);
        return new LayoutTwoViewHolder(layoutTwo);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //int image = itemClassList.get(position).geticon();
        int image = R.drawable.ic_song;
        String text_one = itemClassList.get(position).getName();
        String text_two = itemClassList.get(position).getDescription();
        ((LayoutTwoViewHolder)holder).setViews(image, text_one, text_two);
        ((LayoutTwoViewHolder)holder).linearLayout.setOnClickListener(
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
