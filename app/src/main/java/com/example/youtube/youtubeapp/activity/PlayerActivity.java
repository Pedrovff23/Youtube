package com.example.youtube.youtubeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.youtube.youtubeapp.R;
import com.example.youtube.youtubeapp.databinding.ActivityPlayerBinding;
import com.example.youtube.youtubeapp.helper.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    private ActivityPlayerBinding binding;
    private YouTubePlayerView youTubePlayerView;
    private String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Configurações inicias
        youTubePlayerView = binding.playerVideo;
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            idVideo = bundle.getString("idVideo");
            youTubePlayerView.initialize(YoutubeConfig.GOOGLE_API_KEY,this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean foiRestaurado) {
        if(!foiRestaurado){
            youTubePlayer.loadVideo(idVideo);
        }
        youTubePlayer.setFullscreen(true);
        youTubePlayer.setShowFullscreenButton(false);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
    }
}