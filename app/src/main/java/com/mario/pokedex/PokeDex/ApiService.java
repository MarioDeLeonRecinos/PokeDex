package com.mario.pokedex.PokeDex;

import com.mario.pokedex.models.Pokemon;
import com.mario.pokedex.models.PokemonAll;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("pokemon")
    Call<PokemonAll> ObtainPokeList(@Query("limit") int limit,@Query("offset") int offset);

}
