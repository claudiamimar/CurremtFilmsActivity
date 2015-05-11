package com.uniquindio.electiva_android.curremtfilmsactivity.fragment;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uniquindio.electiva_android.curremtfilmsactivity.vo.Film;
import com.uniquindio.electiva_android.curremtfilmsactivity.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Este fragmento contiene el detalle de cada uno de las peliculas seleccionadas en la lista
 *
 * @author Einer Zapata
 * @version 1.0 on 25/03/2015.
 */
public class DetailFimlsFragment extends Fragment {

    private Film film;
    @InjectView(R.id.film_detail_title)
    protected TextView title;
    @InjectView(R.id.film_detail_description)
    protected TextView description;
    @InjectView(R.id.go_to_trailer)
    protected Button goToTrailer;
    @InjectView(R.id.delete_film)
    protected Button btnDelete;
    private FilmDetailListener filmDetailListener;

    /**
     * método inicial del ciclo de vida del fragmento
     *
     * @param activity actividad padre del fragmento
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            filmDetailListener = (FilmDetailListener) activity;
        } catch (ClassCastException e) {
            Log.v("ListFimlsFragment", "error en el casting");
        }
    }

    /**
     * contiene el view que representa el fragment
     *
     * @param inflater           inflador para adaptar el layout
     * @param container          contenenor donde se agregará el layout
     * @param savedInstanceState información que con tiene extras
     * @return el view adaptado al tamaño del contenedor
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_fimls, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * cambia la informacion contenida en los controles por la de la pelicula seleccionada
     *
     * @param film pelicula de la que se desea mostrar la informacion detallada
     */
    public void showDetail(Film film) {

        this.film = film;

        Log.d(DetailFimlsFragment.class.getSimpleName(), "este es el titulo: " + film.getTitle());

        //title = (TextView) getView().findViewById(R.id.film_detail_title);
        title.setText(film.getTitle());
        description.setText(film.getDescription());



    }

    /**
     * metodó callback escucha el evento del boton goToTrailer
     *
     * @param v view que gestiono el evento
     */
    @OnClick({R.id.delete_film, R.id.go_to_trailer})
    public void onClick(View v) {
        if (v.getId() == goToTrailer.getId()) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(film.getUrlTrailer()));
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException e) {
                Log.e("Error en la url", e.getMessage());
            }
        }
        if (v.getId() == btnDelete.getId()) {
            filmDetailListener.onDeleteFilmChoose(film.getTitle());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * metodo en el que entra cuando vuelve del trailer
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * interfaz por medio de la cual se activa el callback de la actividad padre
     * encargado de eliminar una pelicula por nombre
     */
    public interface FilmDetailListener {
        void onDeleteFilmChoose(String nameFilm);
    }
}
