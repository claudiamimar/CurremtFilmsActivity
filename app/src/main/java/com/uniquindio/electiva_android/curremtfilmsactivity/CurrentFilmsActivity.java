package com.uniquindio.electiva_android.curremtfilmsactivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.uniquindio.electiva_android.curremtfilmsactivity.fragment.AddFilmFragment;
import com.uniquindio.electiva_android.curremtfilmsactivity.fragment.DetailFimlsFragment;
import com.uniquindio.electiva_android.curremtfilmsactivity.fragment.ListFimlsFragment;
import com.uniquindio.electiva_android.curremtfilmsactivity.util.FilmSQLiteHelper;
import com.uniquindio.electiva_android.curremtfilmsactivity.util.Utilities;
import com.uniquindio.electiva_android.curremtfilmsactivity.vo.Film;

import java.util.ArrayList;

/**
 * Actividad principal de la aplicación
 * Permite la navegacion entre las peliculas
 *
 * @author Einer Zapata
 * @version 1.0 on 25/03/2015.
 */
public class CurrentFilmsActivity extends ActionBarActivity
        implements ListFimlsFragment.FilmListener, SearchView.OnQueryTextListener, AddFilmFragment.ListenerAddFilms, DetailFimlsFragment.FilmDetailListener {

    public static ArrayList<Film> films;
    private SearchView mSearchView;
    private ShareActionProvider mShareActionProvider;
    private boolean isFragment;
    private ListFimlsFragment listFimlsFragment;
    private FilmSQLiteHelper usdbh;
    private SQLiteDatabase db;

    /**
     * metodo callback donde se deben realizar las funciones pesadas
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utilities.getLanguage(this);
        setContentView(R.layout.activity_current_films);

        films = new ArrayList<Film>();
        usdbh =
                new FilmSQLiteHelper(this, Utilities.NAME_DB, null, Utilities.VERSION_DB);
        db = usdbh.getWritableDatabase();
        getInfoDB();

        init();

    }

    /**
     * inicializa los controles y framentos de la actividad
     */
    public void init() {

        listFimlsFragment = (ListFimlsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_films_list);

        isFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_films_details) != null;

        if (isFragment) {
            onFilmChoose(1);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.hide();//Ocultar ActionBar
        actionBar.setIcon(R.mipmap.ic_launcher);             //Establecer icono
        actionBar.setTitle(getString(R.string.app_name));        //Establecer titulo

        if (films.size() > 0) {
            actionBar.setSubtitle(getString(R.string.app_recommended) + ": " + films.get(0).getTitle());     //Establecer Subtitulo     }

        }

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
        getMenuInflater().inflate(R.menu.menu_current_films, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);

        MenuItem shareItem = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(getDefaultShareIntent());

        return true;
    }

    /**
     * método callback que permite identificar con que elemento del actionbar esta interactuando el usuario
     *
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
            getInfoDB();
            return true;
        }

        if (id == R.id.language) {
            Utilities.changeLanguage(this);
            Intent intent = new Intent(this, CurrentFilmsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_add) {
            Utilities.showDialogAddFilm(getSupportFragmentManager(), CurrentFilmsActivity.class.getSimpleName());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * método calback que es llamado cuando se presiona un item de la lista de peliculas
     *
     * @param position del item presionado
     */
    @Override
    public void onFilmChoose(int position) {

        Film filmChoose = films.get(position-1);

        if (isFragment) {
            if (films.size() > 0) {
                ((DetailFimlsFragment)
                        getSupportFragmentManager().findFragmentById(R.id.fragment_films_details)).showDetail(filmChoose);
            }
        } else {
            Intent intent = new Intent(CurrentFilmsActivity.this, DetailFilmActivity.class);
            intent.putExtra(Utilities.FILM, filmChoose);
            startActivityForResult(intent, Utilities.REQUEST_CODE_CURRENT_FILM);
        }
    }

    /**
     * este método recibe informacion desde la actividad llamada con anterioridad
     *
     * @param requestCode codigo enviado
     * @param resultCode numero de respuesta
     * @param data        informacion envidad desde la otra atividad
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utilities.REQUEST_CODE_CURRENT_FILM) {
            if (null != data) {
                deleteFilm(data.getStringExtra(Utilities.NAME_FILM));
            }
        }

    }

    /**
     * metodo callback que se activa cada que se da submit al control de SearchView
     *
     * @param s texto que contiene el control
     * @return falso
     */
    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    /**
     * metodo callback que se activa cada que se cambia la cadena que contiene el control de SearchView
     *
     * @param s texto que contiene el control
     * @return falso
     */
    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    /**
     * metodo para compartir informacion por medio de  cualquier
     * actividad que proveea ese servicios
     *
     * @return la intecion que contiene la informacion a compartir
     */
    private Intent getDefaultShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "Extra Text");
        return intent;
    }

    /**
     * este método permite agregar una nueva pelicula
     * @param film pelicula que se desea agregar
     */
    @Override
    public void onAddFill(Film film) {
        insertFilm(film);
        films.add(film);
        listFimlsFragment.updateAdapter();
    }

    /**
     * Este método agrega una nueva pelicula a la base de datos
     * @param film pelicula a ser agregada
     */
    private void insertFilm(Film film) {
        db.execSQL("INSERT INTO FilmDB (TITLE,YEAR,URLTRAILER,DESCRIPTION)\n" +
                "VALUES ( '" + film.getTitle() + "', '" + film.getYear() + "', '" + film.getUrlTrailer() + "', '" + film.getDescription() + "' )");
    }

    /**
     * este método permite obtener loe elementos agregados a la base de datos
     */
    public void getInfoDB() {
        String[] fields = new String[]{"TITLE", "YEAR", "URLTRAILER", "DESCRIPTION"};
        Cursor c = db.query("FilmDB", fields, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                String title = c.getString(0);
                String year = c.getString(1);
                String url = c.getString(2);
                String description = c.getString(3);
                films.add(new Film(title, year, url, description));
                Log.d("CurrentFilmsActivity", "titulo: " + title + " año: " + year + " url: " + url + " descrición: " + description);
            } while (c.moveToNext());
        }
    }

    /**
     * este método eliminar una pelicula agregada a la base de datos por le nombre
     * @param nameFilm nombre de la pelicula que se desea eliminar
     */
    public void deleteFilm(String nameFilm) {
        db.execSQL("DELETE FROM FilmDB WHERE TITLE= '" + nameFilm + "'");
        films.clear();
        getInfoDB();
        listFimlsFragment.updateAdapter();
        Log.d("CurrentFilmsActivity", "estoy eliminando");
    }

    /**
     * metodo que dise cual es la pelicula que se desea leiminar
     * @param nameFilm nombre de la pelicula a eliminar de la base de datos
     */
    @Override
    public void onDeleteFilmChoose(String nameFilm) {
        deleteFilm(nameFilm);
    }
}