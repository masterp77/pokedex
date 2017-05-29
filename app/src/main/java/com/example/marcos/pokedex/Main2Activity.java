package com.example.marcos.pokedex;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marcos.pokedex.models.Pokemon;
import com.example.marcos.pokedex.pokeapi.PokeapiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Main2Activity extends AppCompatActivity {

    private static final String TAG="POKEDEX";
    private Retrofit retrofit;
    public String habilidades;
    //Intent intent =getIntent();
    //Bundle bundle = intent.getExtras();

    private ImageView iVPoke;
    private TextView tVNombre,tVId,tVPeso,tVAltura,tVTipo,tVHabilidades;
    private Context context;
    private ArrayList<Pokemon> dataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tVNombre = (TextView) findViewById(R.id.tVNombre);
        tVId = (TextView) findViewById(R.id.tVId);
        tVPeso = (TextView) findViewById(R.id.tVPeso);
        tVAltura = (TextView) findViewById(R.id.tVAltura);
        tVTipo = (TextView) findViewById(R.id.tVTipo);
        tVHabilidades = (TextView) findViewById(R.id.tVHabilidades);
        iVPoke = (ImageView) findViewById(R.id.iVPoke);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            int idPokemon = (int) bundle.get("idP");
            String id = idPokemon+"";
            obtenerDatos(idPokemon);

        }
    }

  /*  public void Main2Activity(Context context)
    {
        this.context = context;
        dataset = new ArrayList<>();
    }*/

    private void obtenerDatos(int id) {


        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<Pokemon> pokemonRespuestaCall = service.obtenerPokemon(id);

        pokemonRespuestaCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if(response.isSuccessful()){
                    Pokemon pokemon = response.body();

                    /*ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();


                    for(int i=0;i<listaPokemon.size();i++)
                    {
                        Pokemon p = listaPokemon.get(i);
                        Log.i(TAG," Pokemon: "+p.getName()+" url: "+p.getUrl());
                    }*/
    //id= Integer.toString(pokemon.getId());
    //Pokemon p = dataset.get(position);
    //Ability a = daAbilities.get(position1);
                    habilidades = "";
                    for (int i = 0; i<pokemon.getAbilities().size(); i++){
                        int j=i+1;
                        if (pokemon.getAbilities().size()<0){
                            habilidades="Sin Habilidades";
                        }
                        else {
                            habilidades += " " + j + ":" + pokemon.getAbilities().get(i).getAbility().getName() + "\n";
                        }
                    }

                    tVNombre.setText(pokemon.getName());
                    tVId.setText(Integer.toString(pokemon.getId()));
                    tVPeso.setText(Integer.toString(pokemon.getWeight())+" Kgs.");
                    tVAltura.setText(Integer.toString(pokemon.getHeight())+" Cms.");
                    tVTipo.setText(pokemon.getTypes().get(0).getType().getName());
                    tVHabilidades.setText(habilidades);
                    //Picasso.with(getApplicationContext()).load("http://pokeapi.co/media/sprites/pokemon/1.png").into(iVPoke);
                    Glide.with(getApplicationContext())
                            .load("http://pokeapi.co/media/sprites/pokemon/"+pokemon.getId()+".png")
                            .centerCrop()
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iVPoke);
                    /*AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Información");
                    alertDialog.setMessage("Nombre: "+pokemon.getName()+
                            "\n"+"Height: "+pokemon.getHeight()+
                            "\n"+"Altura: "+pokemon.getTypes().get(0).getType().getName()+
                            "\n"+habilidades);


                    alertDialog.show();*/
                    Log.i(TAG," Pokemon: "+pokemon.getName()+" peso: "+pokemon.getWeight()+" tipo: "+pokemon.getTypes().get(0).getType().getName());

                }else
                {
                    Log.e(TAG, "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(TAG," onFailure: "+t.getMessage());
            }
        });
    }

       public void regresar(View v){
           finish();
       }
}
