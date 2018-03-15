package com.tuinforma.tuinforma.Secciones.SeccionPortada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tuinforma.tuinforma.Modelos.Items;
import com.tuinforma.tuinforma.R;

import java.util.ArrayList;

/**
 * Created by Fabian on 13/02/2018.
 */

public class AdaptadorPortada extends RecyclerView.Adapter<AdaptadorPortada.ViewHolder> {
    private ArrayList<Items> dataset;
    private Context context;

    public AdaptadorPortada(Context context) {
        this.context=context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portada, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Items it = dataset.get(position);

        /*
        Glide.with(context)
                .load(it.getImagen())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgPortada);
        */

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void agregarInformacion(ArrayList<Items> listaItems) {
        dataset.addAll(listaItems);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPortada;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPortada = (ImageView)itemView.findViewById(R.id.imgPortada);
        }
    }
}
