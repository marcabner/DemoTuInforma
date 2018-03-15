package com.tuinforma.tuinforma.Secciones.SeccionPortada;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tuinforma.tuinforma.Modelos.Items;
import com.tuinforma.tuinforma.Modelos.ItemsRespuesta;
import com.tuinforma.tuinforma.R;
import com.tuinforma.tuinforma.Servicios.ItemsServicio;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotaPrincipal extends AppCompatActivity {
    private Retrofit retrofit;

    private Bundle bundle;
    private String img;
    private int por;

    private Toolbar toolbarNota;

    private ImageView imgNota;
    private TextView titulo;
    private TextView sintesis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_principal);

        toolbarNota = (Toolbar) findViewById(R.id.toolbarNotaPrincipal);
        setSupportActionBar(toolbarNota);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Nota Principal");

        imgNota = (ImageView)findViewById(R.id.imgNota);
        titulo = (TextView)findViewById(R.id.tituloNota);
        sintesis = (TextView)findViewById(R.id.sintesis);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://52.8.205.47/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bundle = this.getIntent().getExtras();
        img = bundle.getString("id");
        por = bundle.getInt("por");

        //Toast.makeText(this,"Valor: "+por, Toast.LENGTH_SHORT).show();

        obtenerNota();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void obtenerNota() {
        ItemsServicio servicio =  retrofit.create(ItemsServicio.class);
        Call<ItemsRespuesta> opcionesRespuestaCall = servicio.obtenerItems();

        opcionesRespuestaCall.enqueue(new Callback<ItemsRespuesta>() {
            @Override
            public void onResponse(Call<ItemsRespuesta> call, Response<ItemsRespuesta> response) {
                if(response.isSuccessful()){
                    ItemsRespuesta opcionesRespuesta = response.body();
                    ArrayList<Items> listaItems = opcionesRespuesta.getItems();

                    for(int i=0; i<listaItems.size(); i++){
                        Items it = listaItems.get(i);

                        if(it.getImagen().equals(img)){

                            Glide.with(getApplicationContext())
                                    .load(it.getImagen())
                                    .centerCrop()
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imgNota);

                            titulo.setText(it.getTitulo());
                            sintesis.setText(Html.fromHtml(it.getSintesis()));
                        }
                        else if(it.getTotal() == por){

                            Glide.with(getApplicationContext())
                                    .load(it.getImagen())
                                    .centerCrop()
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imgNota);

                            titulo.setText(it.getTitulo());
                            sintesis.setText(Html.fromHtml(it.getSintesis()));
                        }

                    }

                    //listaOpcionesAdapter.agregarInformacion(listaOpciones);
                }else{
                    //Log.e(TAG," onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ItemsRespuesta> call, Throwable t) {

            }
        });
    }
}