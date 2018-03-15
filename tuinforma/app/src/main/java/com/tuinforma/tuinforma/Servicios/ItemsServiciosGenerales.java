package com.tuinforma.tuinforma.Servicios;

import com.tuinforma.tuinforma.Modelos.Generales.ItemsServicioRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Fabian on 22/02/2018.
 */

public interface ItemsServiciosGenerales {
    @GET("action=seccionesGenerales&document=1000200")
    Call<ItemsServicioRespuesta> obtenerServiciosGenerales();
}
