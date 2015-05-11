package com.uniquindio.electiva_android.curremtfilmsactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.uniquindio.electiva_android.curremtfilmsactivity.fragment.DetailFimlsFragment;
import com.uniquindio.electiva_android.curremtfilmsactivity.util.Utilities;
import com.uniquindio.electiva_android.curremtfilmsactivity.vo.Film;

/**
 * Actividad encargado de mostrar la informacion detallada de cada una de las peliculas
 * Esta actividad solo se mostrará cuando la aplicacion se ejecute en smartphone
 *
 * @author Einer Zapata
 * @version 1.0 on 25/03/2015.
 */
public class DetailFilmActivity extends ActionBarActivity implements DetailFimlsFragment.FilmDetailListener {


    /**
     * metodo callback donde se deben realizar las funciones pesadas
     * @param savedInstanceState este bundle contiene el objeto parcelable enviado desde la actividad principal
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utilities.getLanguage(this);
        setContentView(R.layout.activity_detail_film);
        DetailFimlsFragment fimlsFragment = (DetailFimlsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_films_details);
        Film film = (Film) getIntent().getExtras().get(Utilities.FILM);
        fimlsFragment.showDetail(film);

    }

    /**
     * método callback donde se inicializan el menu y se obtiene instacias de los controles
     * cargados en el actionbar
     *
     * @param menu menu que se mostrará en el actionbar
     * @return verdadero
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_film, menu);
        return true;
    }

    /**
     * método callback que permite identificar con que elemento del actionbar esta interactuando el usuario
     * @param item con lo que interactua el usuario
     * @return el metodo de la super clase
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * metodo encargado de decir cual pelicula debe ser eliminada
     * @param nameFilm nombre de la pelicula a eliminar
     */
    @Override
    public void onDeleteFilmChoose(String nameFilm) {

        Intent intentMessage=new Intent();
        intentMessage.putExtra(Utilities.NAME_FILM,nameFilm);
        setResult(Utilities.REQUEST_CODE_CURRENT_FILM,intentMessage);
        finish();


    }
}
