package com.example.marcos.pokedex.models;

import java.util.ArrayList;

/**
 * Created by Karol on 21/05/2017.
 */

public class PokemonRespuesta {
    private ArrayList<Pokemon> results;
    private Pokemon result;

    public Pokemon getResult() {
        return result;
    }

    public void setResult(Pokemon result) {
        this.result = result;
    }

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
