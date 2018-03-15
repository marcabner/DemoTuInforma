package com.tuinforma.tuinforma.Servicios;

import com.tuinforma.tuinforma.Modelos.ItemsRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Fabian on 13/02/2018.
 */

public interface ItemsServicio {
    @GET("action=pays&document=1000200")
    Call<ItemsRespuesta> obtenerItems();
}
