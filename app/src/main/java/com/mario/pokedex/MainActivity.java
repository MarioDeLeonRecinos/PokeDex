package com.mario.pokedex;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.mario.pokedex.PokeDex.ApiService;
import com.mario.pokedex.models.Pokemon;
import com.mario.pokedex.models.PokemonAll;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    private int offset;

    private boolean abletoCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_Pokemons);
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if (abletoCharge){
                        if ((visibleItemCount+pastVisibleItem) >= totalItemCount){
                            Log.i(TAG,"se acumularon 20");
                            abletoCharge=false;
                            offset += 20;
                            ObtainData(offset);
                        }
                    }
                }
            }
        });

        retrofit = new  Retrofit.Builder()
        .baseUrl("http://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        abletoCharge=true;
        offset=0;

        ObtainData(offset);
    }

    private void ObtainData(int offset) {
        ApiService service = retrofit.create(ApiService.class);
        Call<PokemonAll> pokemonAllCall = service.ObtainPokeList(20,offset);

        pokemonAllCall.enqueue(new Callback<PokemonAll>() {
            @Override
            public void onResponse(Call<PokemonAll> call, Response<PokemonAll> response) {
                abletoCharge=true;
                if (response.isSuccessful()){

                    PokemonAll resultados = response.body();
                    ArrayList<Pokemon> lista= resultados.getResults();

                    pokemonAdapter.addToList(lista);

                }else {

                    Log.e(TAG,"on response"+response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<PokemonAll> call, Throwable t) {
                abletoCharge=true;
                Log.e(TAG,"on failure"+t.getMessage());

            }
        });
    }
}
