package com.onlinelabreport.onlinelabreport;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private List<VideoItem_class> videoItems;

    public VideoAdapter(Context context, List<VideoItem_class> videoItems) {
        this.context = context;
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem_class videoItem = videoItems.get(position);
        holder.bind(videoItem);
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private final PlayerView playerView;
        private final TextView descriptionTextView;
        private SimpleExoPlayer player;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bind(VideoItem_class videoItem) {
            descriptionTextView.setText(videoItem.getDescription());

            if (player == null) {
                player = new SimpleExoPlayer.Builder(context).build();
                playerView.setPlayer(player);
            }

            Uri videoUri = Uri.parse(videoItem.getVideoUrl());
            player.setMediaItem(MediaItem.fromUri(videoUri));
            player.prepare();
            player.setPlayWhenReady(true);
        }
    }
}
