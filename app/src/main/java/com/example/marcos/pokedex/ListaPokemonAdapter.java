package com.example.marcos.pokedex;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marcos.pokedex.models.Pokemon;
import com.example.marcos.pokedex.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Karol on 21/05/2017.
 */

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>{
    private ArrayList<Pokemon> dataset;
    private Context context;
    private static final String TAG="POKEDEX";
    private Retrofit retrofit;
    public String habilidades="";

    ProgressBar progressBar;



    String id = "";

    public ListaPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Pokemon p = dataset.get(position);
        String var = p.getName();
        holder.nombreTextView.setText(p.getName());
        String heightq = p.getUrl();


        //Toast.makeText(context,"gdh"+heightq, Toast.LENGTH_SHORT).show();
        //holder.anchoTextView.setText(p.getWeight());
        //holder.experienciaTextView.setText(p.getBase_experience());


        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);



    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
        //progressBar.setVisibility(View.GONE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;
        private TextView pruebaTextView;
        private TextView anchoTextView;
        private TextView experienciaTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);


           // progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            //anchoTextView = (TextView) itemView.findViewById(R.id.anchoTextView);
            //experienciaTextView = (TextView) itemView.findViewById(R.id.experienciaTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    //id=Integer.toString(position);
                    //int idP=position+1;

                    //onAlertDialog(position);

                    //retrofit = new Retrofit.Builder()
                      //      .baseUrl("http://pokeapi.co/api/v2/")
                        //    .addConverterFactory(GsonConverterFactory.create())
                          //  .build();
                    //obtenerDatosPokemon(idP);
                    //progressBar.setVisibility(View.GONE);
                    final Pokemon p = dataset.get(position);

                    Intent intent = new Intent( context, Main2Activity.class );
                    intent.putExtra( "idP",p.getNumber());
                    context.startActivity(intent);
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
            });

        }
    }

    private void obtenerDatosPokemon(int id) {


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
                            habilidades += "Habilidad " + j + ":" + pokemon.getAbilities().get(i).getAbility().getName() + "\n";
                        }
                    }
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Información");
                    alertDialog.setMessage("Nombre: "+pokemon.getName()+
                            "\n"+"Height: "+pokemon.getHeight()+
                            "\n"+"Altura: "+pokemon.getTypes().get(0).getType().getName()+
                            "\n"+habilidades);


                    alertDialog.show();
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

    public void onAlertDialog(int position)
    {

        id= Integer.toString(position);
        Pokemon p = dataset.get(position);
        //Ability a = daAbilities.get(position1);
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Información");
        alertDialog.setMessage("Nombre: "+p.getName()+
                "\n"+"Código: "+p.getNumber()+
                "\n"+"Altura: "+p.getUrl());
        alertDialog.show();
    }


}
