package com.example.exorecycle;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.exorecycle.models.MediaObject;

import java.io.File;
import java.util.ArrayList;


public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private ArrayList<MediaObject> mediaObjects;
    private RequestManager requestManager;
    private ArrayList<MediaObject> selectList;

    Boolean isEnable = false;
    Boolean isSelectAll = false;
Context context;
    public VideoPlayerRecyclerAdapter(ArrayList<MediaObject> mediaObjects, RequestManager requestManager, Context context) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
        this.context=context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoPlayerViewHolder(

                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_video_list_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {

        ((VideoPlayerViewHolder) holder).onBind(mediaObjects.get(i), requestManager);

        ((VideoPlayerViewHolder) holder).thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isEnable) {
                    selectList = new ArrayList<>();
                    final ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            MenuInflater menuInflater = mode.getMenuInflater();
                            menuInflater.inflate(R.menu.contextual_menu, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            isEnable = true;
                            ClickItem(holder);
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            int id = item.getItemId();
                            switch (id) {
                                case R.id.action_delete:


                                    for (MediaObject s : selectList) {
                                        mediaObjects.remove(s);
                                    }
                                    notifyDataSetChanged();

                                    mode.finish();

                                    break;
                                case R.id.action_share:
                                    ArrayList<Uri> filesshare = new ArrayList<>();
                                    for (MediaObject path : selectList) {
                                        Uri uri = (Uri.fromFile(new File(path.getMedia_url())));
                                        filesshare.add(uri);
                                    }
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_STREAM, filesshare);

                                    sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    sendIntent.setType("video/*");
                                    context.startActivity(sendIntent);
                                    break;


                            }
                            return true;
                        }


                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            isEnable = false;
                            isSelectAll = false;
                            selectList.clear();
                            notifyDataSetChanged();
                        }
                    };
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
                } else {
                    ClickItem(holder);
                }
                return true;
            }
        });
        ((VideoPlayerViewHolder) holder).thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable) {
                    ClickItem(holder);
                } else {

                }
            }
        });

        if (isSelectAll) {
            ((VideoPlayerViewHolder) holder).checkMark.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            ((VideoPlayerViewHolder) holder).checkMark.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }

    public void ClickItem(RecyclerView.ViewHolder holder) {
        MediaObject s = mediaObjects.get(holder.getAdapterPosition());
        if (((VideoPlayerViewHolder) holder).checkMark.getVisibility() == View.GONE) {
            ((VideoPlayerViewHolder) holder).checkMark.setVisibility(View.VISIBLE);
            ((VideoPlayerViewHolder) holder).itemView.setBackgroundColor(Color.LTGRAY);
            selectList.add(s);
        } else {
            ((VideoPlayerViewHolder) holder).checkMark.setVisibility(View.GONE);
            ((VideoPlayerViewHolder) holder).itemView.setBackgroundColor(Color.TRANSPARENT);
            selectList.remove(s);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                ArrayList<MediaObject> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    mediaObjects = mediaObjects;
                }
                else {

                    for (MediaObject row : mediaObjects) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if( row.getTitle().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    mediaObjects = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mediaObjects;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mediaObjects = (ArrayList<MediaObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}













