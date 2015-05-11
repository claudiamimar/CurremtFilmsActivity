package com.uniquindio.electiva_android.curremtfilmsactivity.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Esta clase se encarga de manejar la informacion de las peliculas
 * <p/>
 * Created by Andres on 25/03/2015.
 */
public class Film implements Parcelable {

    private String title;
    private String year;
    private String urlTrailer;
    private String description;

    /**
     * metodo constructor basico
     *
     * @param title titulo de la pelicula
     * @param year  año de estreno de la pelicula
     */
    public Film(String title, String year) {
        this.title = title;
        this.year = year;
    }

    /**
     * metodo constructor con todos los parametros
     *
     * @param title       titulo de la pelicula
     * @param year        año de estreno de la pelicula
     * @param urlTrailer  trailer de la pelicula
     * @param description descricion de la pelicula
     */
    public Film(String title, String year, String urlTrailer, String description) {
        this.title = title;
        this.year = year;
        this.urlTrailer = urlTrailer;
        this.description = description;
    }

    /**
     * metodo constructor usado cuando se envia en objeto por un bundle
     * @param in Parce que contiene toda la informacion enviada desde writeToParcel
     */
    public Film(Parcel in) {
        readFromParcel(in);
    }

    /**
     * se encarga de leer toddos los datos que fueron enviador por medio del Parcel
     * @param in contiene todos los datos recibidos por el metodo constructor
     */
    private void readFromParcel(Parcel in) {
        title = in.readString();
        year = in.readString();
        urlTrailer = in.readString();
        description = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(urlTrailer);
        dest.writeString(description);
    }

    /**
     * variable necesaria para el envio de información por medio de bundle
     */
    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {

        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    /**
     * @return devuelve el Año en que fue realizada la pelucula
     */
    public String getYear() {
        return year;
    }

    /**
     * @return devuelve el titulo de la pelicula
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return devuelve la descricion de la pelicula
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return devuelve la url del trailer de la pelicula
     */
    public String getUrlTrailer() {
        return urlTrailer;
    }

}
