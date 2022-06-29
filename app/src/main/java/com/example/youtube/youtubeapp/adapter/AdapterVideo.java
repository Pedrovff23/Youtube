package com.example.youtube.youtubeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtube.youtubeapp.R;
import com.example.youtube.youtubeapp.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {

    private List<Item> listaVideo;
    private Context context;

    public AdapterVideo(List<Item> listaVideo, Context context) {
        this.listaVideo = listaVideo;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_video,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item video = listaVideo.get(position);
        holder.titulo.setText(video.snippet.title);
        String url = video.snippet.thumbnails.high.url;

        if(url!=null && !url.isEmpty()){
            Picasso.get().load(url).into(holder.capa);
        }
    }

    @Override
    public int getItemCount() {
        return listaVideo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView capa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTitulo);
            capa = itemView.findViewById(R.id.ImageViewCapa);
        }
    }
}
