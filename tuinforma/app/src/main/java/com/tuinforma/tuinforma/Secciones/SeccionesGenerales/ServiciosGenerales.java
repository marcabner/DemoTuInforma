package com.tuinforma.tuinforma.Secciones.SeccionesGenerales;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tuinforma.tuinforma.Modelos.Generales.ItemsGenerales;
import com.tuinforma.tuinforma.Modelos.Generales.ItemsServicioRespuesta;
import com.tuinforma.tuinforma.R;
import com.tuinforma.tuinforma.Servicios.ItemsServiciosGenerales;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fabian on 22/02/2018.
 */

public class ServiciosGenerales extends AppCompatActivity {
    private Retrofit retrofit;

    private Bundle bundle;
    private int id;

    private Toolbar toolbarGenerales;

    private ImageView imgGenerales;
    private TextView tituloGenerales;
    private TextView sintesisGenerales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generales);

        toolbarGenerales = (Toolbar) findViewById(R.id.toolbarGeneral);
        setSupportActionBar(toolbarGenerales);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        imgGenerales = (ImageView) findViewById(R.id.imgGenerales);
        tituloGenerales = (TextView) findViewById(R.id.tituloGenerales);
        sintesisGenerales = (TextView) findViewById(R.id.sintesisGenerales);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://52.8.205.47/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bundle = this.getIntent().getExtras();
        id = bundle.getInt("tituloGral");

        obtenerGenerales();
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

    private void obtenerGenerales() {
        ItemsServiciosGenerales servicio =  retrofit.create(ItemsServiciosGenerales.class);
        Call<ItemsServicioRespuesta> opcionesRespuestaCall = servicio.obtenerServiciosGenerales();

        opcionesRespuestaCall.enqueue(new Callback<ItemsServicioRespuesta>() {
            @Override
            public void onResponse(Call<ItemsServicioRespuesta> call, Response<ItemsServicioRespuesta> response) {
                if(response.isSuccessful()){
                    ItemsServicioRespuesta opcionesRespuesta = response.body();
                    ArrayList<ItemsGenerales> listaItems = opcionesRespuesta.getItems();

                    for(int i=0; i<listaItems.size(); i++){
                        ItemsGenerales it = listaItems.get(i);

                        if(it.getId() == id){
                            getSupportActionBar().setTitle(Html.fromHtml("<br />")+it.getTitulo_seccion());

                            Glide.with(getApplicationContext())
                                    .load(it.getImagen())
                                    .centerCrop()
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imgGenerales);

                            tituloGenerales.setText(it.getTitulo());
                            sintesisGenerales.setText(Html.fromHtml(it.getSintesis()));
                        }
                    }
                }else{
                    //Log.e(TAG," onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ItemsServicioRespuesta> call, Throwable t) {

            }
        });
    }
}
