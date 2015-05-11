package com.uniquindio.electiva_android.curremtfilmsactivity.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.uniquindio.electiva_android.curremtfilmsactivity.CurrentFilmsActivity;
import com.uniquindio.electiva_android.curremtfilmsactivity.R;
import com.uniquindio.electiva_android.curremtfilmsactivity.util.AdapterFilms;
import com.uniquindio.electiva_android.curremtfilmsactivity.vo.Film;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * este fragmento se encarga de mostrar una lista de peliculas
 *
 * @author Einer Zapata
 * @version 1.0 on 25/03/2015.
 */
public class ListFimlsFragment extends Fragment {

    @InjectView(R.id.list_films)
    protected ListView listFilms;
    @InjectView(R.id.txt_add_film)
    protected TextView messageLoadFilms;
    private ArrayList<Film> films;
    private FilmListener listener;
    private AdapterFilms adapter;

    /**
     * metodo constructor del fragmento, se debe dejar vacio
     */
    public ListFimlsFragment() {
        // Required empty public constructor
    }

    /**
     * método callback que permite obterner la referencias de la actividad que llamo al fragmento
     * @param activity que hizo el llamado
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (FilmListener) activity;
        } catch (ClassCastException e) {
            Log.v("ListFimlsFragment", "error en el casting");
        }
    }

    /**
     * contiene el view que representa el fragment
     *
     * @param inflater inflador para adaptar el layout
     * @param container contenenor donde se agregará el layout
     * @param savedInstanceState información que con tiene extras
     * @return el view adaptado al tamaño del contenedor
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_fimls, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    /**
     * método callback que es llamado cuando se termina de ejecutar el oncreate de la actividad padre
     * se incializan las oferaciones costosas
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.films = CurrentFilmsActivity.films;

        adapter = new AdapterFilms(getActivity(), this.films);

        listFilms = (ListView) getView().findViewById(R.id.list_films);
        View header = getActivity().getLayoutInflater().inflate(R.layout.header_films, null);
        listFilms.addHeaderView(header);
        listFilms.setAdapter(adapter);

        messageLoadFilms = (TextView) getView().findViewById(R.id.txt_add_film);

        if (adapter.getCount() > 0) {
            messageLoadFilms.setVisibility(View.INVISIBLE);
        }
    }

    @OnItemClick(R.id.list_films)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position) != null) {
            listener.onFilmChoose(position);
        }
    }
    /**
     * método encargado de actualizar el adapter
     */
    public void updateAdapter(){

        if (adapter.getCount() > 0) {
            messageLoadFilms.setVisibility(View.INVISIBLE);
        }
        else{
            messageLoadFilms.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * interfaz que contiene el callback que es llamado cuando se presiona un item de la lista
     */
    public interface FilmListener {

        /**
         * método callback llamado cuando se se presiona una de las peliculas
         * @param position posicion en la lista en la que se encuetra la peliculas presionada
         */
        void onFilmChoose(int position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
