package com.mario.pokedex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mario.pokedex.models.Pokemon;
import com.mario.pokedex.models.PokemonAll;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> data;
    private Context context;

    public PokemonAdapter(Context context){
        this.context = context;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pokemon,viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Pokemon p = data.get(i);
        viewHolder.nombrePokemon.setText(p.getName());

        Glide.with(context)
        .load("http://pokeapi.co/media/sprites/pokemon/"+p.getNumber()+".png")
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addToList(ArrayList<Pokemon> pokemonArrayList) {
        data.addAll(pokemonArrayList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView nombrePokemon;

        public ViewHolder(View itemView){
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.tv_pokemon);
            nombrePokemon = (TextView) itemView.findViewById(R.id.name_pokemon);
        }
    }
}
