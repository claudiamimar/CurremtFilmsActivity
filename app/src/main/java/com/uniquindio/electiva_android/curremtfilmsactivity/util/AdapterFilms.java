package com.uniquindio.electiva_android.curremtfilmsactivity.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uniquindio.electiva_android.curremtfilmsactivity.R;
import com.uniquindio.electiva_android.curremtfilmsactivity.vo.Film;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * Esta clase alamacena la informacion de cada uno de los items de la lista de peliculas
 *
 * @author Einer Zapata
 * @version 1.0 on 25/03/2015.
 *
 */
public class AdapterFilms extends ArrayAdapter<Film> {

    private ArrayList<Film> films;

    /**
     * metodo constructor de la aplicación
     * @param context contexto de la actividad que llamo la clase
     * @param films peliculas que se quieren mostrar en la lista
     */
    public AdapterFilms(Context context, Film[] films) {
        super(context, R.layout.abstract_film, films);
//        this.films = films;
    }

/**
     * metodo constructor de la aplicación
     * @param context contexto de la actividad que llamo la clase
     * @param films peliculas que se quieren mostrar en la lista
     */
    public AdapterFilms(Context context, ArrayList<Film> films) {
        super(context, R.layout.abstract_film, films);
        this.films = films;
    }

    /**
     * se modifica el view que se encuentrar en el layout  abstract_film
     * @param position posicion del item en la listas
     * @param convertView view que alamcena la vista a mostar
     * @param parent view group padre
     * @return devuelve la vista que se desea mostrar
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.abstract_film, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

            holder.lblTitle.setText(films.get(position).getTitle());
            holder.lblYear.setText(films.get(position).getYear());

        return convertView;
    }

    /**
     * clase para almacenar los controles que contiene el layout abstract_film
     */
    static class ViewHolder {
        @InjectView(R.id.txt_title_film)
        TextView lblTitle;
        @InjectView(R.id.txt_year_film)
        TextView lblYear;
        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
