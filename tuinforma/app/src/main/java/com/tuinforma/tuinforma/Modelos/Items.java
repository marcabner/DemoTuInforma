package com.tuinforma.tuinforma.Modelos;

/**
 * Created by Fabian on 13/02/2018.
 */

public class Items {
    private int id_sintesis;
    private String titulo;
    private String slug;
    private String sintesis;
    private String balazo;
    private String imagen;
    private int total;
    private String fuente;

    public int getId_sintesis() {
        return id_sintesis;
    }

    public void setId_sintesis(int id_sintesis) {
        this.id_sintesis = id_sintesis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSintesis() {
        return sintesis;
    }

    public void setSintesis(String sintesis) {
        this.sintesis = sintesis;
    }

    public String getBalazo() {
        return balazo;
    }

    public void setBalazo(String balazo) {
        this.balazo = balazo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
}
