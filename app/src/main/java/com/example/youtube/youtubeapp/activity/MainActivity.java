package com.example.youtube.youtubeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.example.youtube.youtubeapp.R;
import com.example.youtube.youtubeapp.adapter.AdapterVideo;
import com.example.youtube.youtubeapp.api.YoutubeService;
import com.example.youtube.youtubeapp.databinding.ActivityMainBinding;
import com.example.youtube.youtubeapp.helper.RecyclerItemClickListener;
import com.example.youtube.youtubeapp.helper.RetrofitConfig;
import com.example.youtube.youtubeapp.helper.YoutubeConfig;
import com.example.youtube.youtubeapp.model.Item;
import com.example.youtube.youtubeapp.model.Resultado;
import com.example.youtube.youtubeapp.model.Video;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private List<Item> listaVideo = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;
    private SearchView searchView;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Configuração inicial
        recyclerView = binding.RecyclerView;
        retrofit = RetrofitConfig.getRetrofit();

        //Configurar Toolbar
        setSupportActionBar(binding.toolbarActivityMain.toolbar);
        getSupportActionBar().setTitle("Youtube");

        //Recuperar videos
        recuperarVideos("");
    }

    private void recuperarVideos(String pesquisa){

        String q = pesquisa.replaceAll(" ","+");
        YoutubeService service = retrofit.create(YoutubeService.class);
        service.recupearVideos(
                "snippet",
                "date",
                "20",
                YoutubeConfig.GOOGLE_API_KEY,
                YoutubeConfig.CANAL_ID,
                q
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                if(response.isSuccessful()){
                     resultado = response.body();

                     listaVideo = resultado.items;
                     configurarRecyclerView();
                }
            }
            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {
            }
        });
    }

    public void configurarRecyclerView(){

        adapterVideo = new AdapterVideo(listaVideo,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterVideo);

        //Configura o evento de clique
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                Item item = listaVideo.get(position);
                String idVideo = item.id.videoId;

                Intent i = new Intent(getApplicationContext(), PlayerActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("idVideo",idVideo);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.search_menu);
        searchView = (SearchView) item.getActionView();

        //Configura métodos para SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recuperarVideos("");
                searchView.onActionViewCollapsed();
                return true;
            }
        });

        return true;
    }
}