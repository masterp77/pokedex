package com.example.marcos.pokedex.pokeapi;

import com.example.marcos.pokedex.models.HabilidadRespuesta;
import com.example.marcos.pokedex.models.Pokemon;
import com.example.marcos.pokedex.models.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Karol on 21/05/2017.
 */

public interface PokeapiService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<Pokemon> obtenerPokemon(@Path("id") Integer id);

    @GET("ability")
    Call<HabilidadRespuesta> obtenerListaHabilidad(@Query("limit") int limit, @Query("offset") int offset);
}
