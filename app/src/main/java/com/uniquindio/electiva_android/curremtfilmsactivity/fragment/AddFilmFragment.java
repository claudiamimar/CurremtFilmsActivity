package com.uniquindio.electiva_android.curremtfilmsactivity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.uniquindio.electiva_android.curremtfilmsactivity.R;
import com.uniquindio.electiva_android.curremtfilmsactivity.util.Utilities;
import com.uniquindio.electiva_android.curremtfilmsactivity.vo.Film;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Este fragmento es usado como medio para agregar peliculas
 *
 * @author Einer Zapata
 * @version 1.0 22/04/2015
 */
public class AddFilmFragment extends DialogFragment {

    @InjectView(R.id.cmp_add_title)
    protected EditText fldTitle;
    @InjectView(R.id.cmp_add_year)
    protected EditText fldYear;
    @InjectView(R.id.cmp_add_url_trailer)
    protected EditText fldURLTrailer;
    @InjectView(R.id.cmp_add_description)
    protected EditText fldDescription;
    @InjectView(R.id.btn_add_fiml)
    protected Button btnAccept;
    private ListenerAddFilms listenerAddFilms;


    /**
     * Este método constructor debe permanecer vacio
     */
    public AddFilmFragment() {
        // Required empty public constructor
    }

    /**
     * este metodo es usado para inicializar el atributo de clase a listenerAddFilms
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            listenerAddFilms = (ListenerAddFilms) activity;
        }
        catch (ClassCastException e) {
            Log.v("ListenerAddFilms", "error en el casting");
        }

    }

    /**
     * callback usado para inicializar los componetes graficos
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
        getDialog().setTitle(getString(R.string.title_add_film));
        View rootView = inflater.inflate(R.layout.fragment_add_film,  container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    /**
     * Este método escucha el evento del boton accept
     * @param v vista que fue presionada
     */
    @OnClick({R.id.btn_add_fiml})
    public void onClick(View v) {

        String title = fldTitle.getText().toString();

        if(title!=null && title.trim().equals("")){
            Utilities.showAlert(getActivity(), getString(R.string.add_fill_title_empty));
            return;
        }

        String year = fldYear.getText().toString();

        if(year!=null && year.trim().equals("")){
            Utilities.showAlert(getActivity(), getString(R.string.add_fill_year_empty));
            return;
        }

        String urlTrailer = fldURLTrailer.getText().toString();

        if(urlTrailer!=null && urlTrailer.trim().equals("")){
            Utilities.showAlert(getActivity(), getString(R.string.add_fill_url_empty));
            return;
        }

        String description = fldDescription.getText().toString();

        if(description!=null && description.trim().equals("")){
            Utilities.showAlert(getActivity(), getString(R.string.add_fill_description_empty));
            return;
        }

        //CurrentFilmsActivity.addFilm(new Film(title, year, urlTrailer, description));
        listenerAddFilms.onAddFill(new Film(title, year, urlTrailer, description));
        getDialog().dismiss();
    }

    /**
     * Interfaz que contiene el callback que es llamado cuando se presiona agregar pelicula
     */
    public interface ListenerAddFilms{

        /**
         * método callback llamado cuando se se presiona agregar pelicula
         * @param film nueva pelicula a agregar
         */
        public void onAddFill(Film film);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
