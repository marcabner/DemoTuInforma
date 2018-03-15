package com.tuinforma.tuinforma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.tuinforma.tuinforma.Modelos.Generales.ItemsGenerales;
import com.tuinforma.tuinforma.Modelos.Generales.ItemsServicioRespuesta;
import com.tuinforma.tuinforma.Secciones.SeccionesGenerales.AcercaDe;
import com.tuinforma.tuinforma.Secciones.SeccionesGenerales.ServiciosGenerales;
import com.tuinforma.tuinforma.Secciones.Debate;
import com.tuinforma.tuinforma.Secciones.Panorama;
import com.tuinforma.tuinforma.Secciones.Portada;
import com.tuinforma.tuinforma.Secciones.Recomendados;
import com.tuinforma.tuinforma.Secciones.Videos;
import com.tuinforma.tuinforma.Servicios.ItemsServiciosGenerales;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayout;
    ViewPager viewPager;
    Retrofit retrofit;

    Menu menuND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //NavigationDrawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menuND = navigationView.getMenu();

        menuND.add(R.id.primer_grupo,0,0,"ESTADOS").setIcon(R.mipmap.icon_estados);
        menuND.add(R.id.primer_grupo,1,0,"VISIÓN").setIcon(R.mipmap.vision_icon);
        menuND.add(R.id.primer_grupo,2,0,"NACIONAL").setIcon(R.mipmap.icon_nacional);

        menuND.setGroupCheckable(R.id.primer_grupo, true, true);
        menuND.setGroupVisible(R.id.primer_grupo, true);

        //navigationView.getMenu().addSubMenu().add(0,2,0,"NACIONAL").setIcon(R.mipmap.icon_nacional);

        //ViewPager
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //TabLayout
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://52.8.205.47/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    //NavigationDrawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);

        ItemsServiciosGenerales servicio =  retrofit.create(ItemsServiciosGenerales.class);
        Call<ItemsServicioRespuesta> opcionesRespuestaCall = servicio.obtenerServiciosGenerales();

        opcionesRespuestaCall.enqueue(new Callback<ItemsServicioRespuesta>() {
            @Override
            public void onResponse(Call<ItemsServicioRespuesta> call, Response<ItemsServicioRespuesta> response) {
                if(response.isSuccessful()){
                    ItemsServicioRespuesta opcionesRespuesta = response.body();
                    ArrayList<ItemsGenerales> listaItems = opcionesRespuesta.getItems();

                    Integer images [] ={
                            R.mipmap.icon_personalizar, //Falta icono elecciones
                            R.mipmap.icon_politica_legislativo,
                            R.mipmap.icon_justicia,
                            R.mipmap.icon_economia,
                            R.mipmap.icon_negocios,
                            R.mipmap.icon_sociedad,
                            R.mipmap.icon_estados,
                            R.mipmap.icon_ciencia,
                            R.mipmap.icon_personalizar, //Falta icono deportes
                            R.mipmap.icon_cultura};

                    for(int i=0; i<listaItems.size(); i++){
                        ItemsGenerales it = listaItems.get(i);

                        //Bitmap imagen = decodificarImagen(it.getImagen());
                        //imageView.setImageBitmap(imagen);

                        //Drawable d = new BitmapDrawable(getResources(), imagen);

                        menuND.add(R.id.segundo_grupo,it.getId(),0,it.getTitulo_seccion()).setIcon(images[i]);

                        menuND.setGroupCheckable(R.id.segundo_grupo, true, true);
                        menuND.setGroupVisible(R.id.segundo_grupo, true);

                        //navigationView.getMenu().add(0,it.getId(),0,it.getTitulo_seccion()).setIcon(d);
                    }
                    menuND.add(R.id.tercer_grupo,3,0,"ACERCA DE").setIcon(R.mipmap.icon_informacion);
                    menuND.setGroupCheckable(R.id.tercer_grupo, true, true);
                    menuND.setGroupVisible(R.id.tercer_grupo, true);
                }else{
                    //Log.e(TAG," onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ItemsServicioRespuesta> call, Throwable t) {

            }
        });
        return true;
    }

    //DecodificarIcono de NavigationDrawer
    public Bitmap decodificarImagen(String img){
        byte[] decodificarString = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodificarString, 0, decodificarString.length);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == 0){
            Toast.makeText(this,"ID: "+item,Toast.LENGTH_SHORT).show();
        }else if(id == 1){
            Toast.makeText(this,"ID: "+item,Toast.LENGTH_SHORT).show();
        }else if(id == 2){
            //Página Principal
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }else if(id == 3){
            Intent intent = new Intent(getApplicationContext(), AcercaDe.class);
            startActivity(intent);
        }else{
            //Toast.makeText(this,"Seleccionado: "+id,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ServiciosGenerales.class);
            intent.putExtra("tituloGral",id);
            startActivity(intent);
        }

        //Ocultar al seleccionar un item
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        */
        return true;
    }

    //Fragmentes - Tabs
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Portada(),"PORTADA");
        adapter.addFragment(new Panorama(),"PANORAMA");
        adapter.addFragment(new Recomendados(),"RECOMENDADOS");
        adapter.addFragment(new Debate(),"DEBATE");
        adapter.addFragment(new Videos(),"VIDEOS");
        viewPager.setAdapter(adapter);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentsList = new ArrayList<>();
        private final List<String> titulosLista = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return titulosLista.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentsList.add(fragment);
            titulosLista.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosLista.get(position);
        }
    }
}
