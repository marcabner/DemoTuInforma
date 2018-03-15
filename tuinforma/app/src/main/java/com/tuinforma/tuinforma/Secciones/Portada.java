package com.tuinforma.tuinforma.Secciones;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tuinforma.tuinforma.Secciones.SeccionPortada.NotaPrincipal;
import com.tuinforma.tuinforma.Modelos.Items;
import com.tuinforma.tuinforma.Modelos.ItemsRespuesta;
import com.tuinforma.tuinforma.R;
import com.tuinforma.tuinforma.Servicios.ItemsServicio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Portada extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, IValueFormatter,
        OnChartValueSelectedListener {

    private Retrofit retrofit;

    SliderLayout sliderLayout ;
    HashMap<String, String> HashMapForURL ;

    PieChart pieChart;

    private DecimalFormat mFormat;

    public Portada() {
        mFormat = new DecimalFormat("###,###,###"); // sin decimales
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_portada, container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://52.8.205.47/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sliderLayout = (SliderLayout)view.findViewById(R.id.slider);

        obtenerDatos();

        pieChart = (PieChart)view.findViewById(R.id.pieChart);

        return view;
    }

    public void obtenerDatos() {

    HashMapForURL = new HashMap<String, String>();

    final ArrayList<PieEntry> yValues = new ArrayList<>();

    ItemsServicio servicio = retrofit.create(ItemsServicio.class);
    Call<ItemsRespuesta> itemsRespuestaCall = servicio.obtenerItems();

    itemsRespuestaCall.enqueue(new Callback<ItemsRespuesta>() {
        @Override
        public void onResponse(Call<ItemsRespuesta> call, Response<ItemsRespuesta> response) {
            if(response.isSuccessful()){
                ItemsRespuesta itemsRespuesta = response.body();
                ArrayList<Items> listaItems = itemsRespuesta.getItems();

                for(int i=0; i<listaItems.size(); i++){
                    Items img = listaItems.get(i);

                    //Slider
                    HashMapForURL.put(Html.fromHtml("<br />")+img.getTitulo()+ Html.fromHtml("<br /> <br />")+ img.getBalazo()+ Html.fromHtml("<br /><br />"),img.getImagen());
                    //Pie Chart
                    yValues.add(new PieEntry(img.getTotal(),img.getTitulo()));

                }

                //Slider
                for(String name : HashMapForURL.keySet()){
                    TextSliderView textSliderView = new TextSliderView(getContext());
                    textSliderView
                            .description(name)
                            .image(HashMapForURL.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(Portada.this);
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra",name);
                    sliderLayout.addSlider(textSliderView);
                }

                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(5000);
                sliderLayout.addOnPageChangeListener(Portada.this);

                //adaptadorPortada.agregarInformacion(listaItems);

                //PaiChart
                pieChart.setUsePercentValues(true);
                pieChart.setExtraOffsets(15, -170, 15, 10);

                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.setDrawHoleEnabled(false);

                Description description = new Description();
                description.setText("Temas del día");
                description.setTextSize(19);
                description.setXOffset(90);
                description.setYOffset(145);
                pieChart.setDescription(description);
                pieChart.animateXY(1400, 1400);

                //pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
                //Desahabilitar label de titulo en la gráfica
                pieChart.setDrawSliceText(false);
                PieDataSet dataSet = new PieDataSet(yValues,null);
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setValueTextSize(10f);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                PieData data = new PieData(dataSet);
                data.setValueTextColor(Color.WHITE);
                data.setValueTextSize(20);
                data.setValueFormatter(new Portada());

                Legend legend = pieChart.getLegend();
                legend.setTextSize(12);
                legend.setPosition(Legend.LegendPosition.PIECHART_CENTER);
                legend.setYOffset(320);
                legend.setXEntrySpace(7);
                legend.setYEntrySpace(5);

                pieChart.setData(data);
                pieChart.setOnChartValueSelectedListener(Portada.this);
                pieChart.getLegend();

            }else{
                //Log.e(TAG," onResponse: "+response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<ItemsRespuesta> call, Throwable t) {
            //Log.e(TAG," onFailure: "+t.getMessage());
        }
    });
}

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent intent = new Intent(getActivity(), NotaPrincipal.class);
        intent.putExtra("id",slider.getUrl());
        startActivity(intent);

        //Toast.makeText(getContext(),slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        //Toast.makeText(getContext(),"Seleccionado: "+position,Toast.LENGTH_SHORT);
        //Log.d("Slider", "Seleccionado: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //Porcentaje de pieChar
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        if(value < 0) return "";

        return mFormat.format(value) + " %";
    }

    //PieChart onSelected

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Intent intent2 = new Intent(getActivity(), NotaPrincipal.class);
        int param = (int) e.getY();

        intent2.putExtra("por", param);
        startActivity(intent2);
    }

    @Override
    public void onNothingSelected() {
        //Log.i("PieChart", "nothing selected");
    }
}