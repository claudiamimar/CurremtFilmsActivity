package com.uniquindio.electiva_android.curremtfilmsactivity.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.uniquindio.electiva_android.curremtfilmsactivity.R;
import com.uniquindio.electiva_android.curremtfilmsactivity.fragment.AddFilmFragment;

import java.util.Locale;

/**
 * Esta clase provee de utilidades a la aplicación
 *
 * @author Einer
 * @version 1.0 28/04/2015
 */
public class Utilities {

    public static int REQUEST_CODE_CURRENT_FILM = 1;
    public static int CALLED_INTERNAL_CURRENT_FILM = 2;
    public final static String CALLED_INTERNAL = "calledInternal";
    public final static String NAME_FILM = "nameFilm";
    public final static String NAME_DB = "FilmDB";
    public final static String MY_PREFERENCES = "MisPreferencias";
    public final static String LANGUAGE_PREFERENCES = "languaje_preferences";
    public final static String FILM = "film";
    public final static String LANGUAGE_ES = "es";
    public final static String LANGUAGE_EN = "en";
    public final static int VERSION_DB = 1;



    /**
     * este método es encargado de mostrar un mensaje en pantalla
     *
     * @param context contexto de la actividad que invoca el método
     * @param message mensaje a mostrar al usuario
     */
    public static void showAlert(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * este método es el encargado de mostrar el dialogo por medio del cual se van a agregar peliculas
     *
     * @param fragmentManager permite realizar la trasaccion del dialogo
     * @param nameClass       nombre de la actividad que lo invoco
     */
    public static void showDialogAddFilm(FragmentManager fragmentManager, String nameClass) {
        AddFilmFragment dialogaAdFilm = new AddFilmFragment();
        dialogaAdFilm.setStyle(dialogaAdFilm.STYLE_NORMAL, R.style.MyAddDialog);
        dialogaAdFilm.show(fragmentManager, nameClass);
    }

    /**
     * metodo encargado de cambiar el lenguaje de la aplicaicion
     * @param context contexto de la actividad que invoca el método
     */
    public static void changeLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFERENCES, context.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE_PREFERENCES, LANGUAGE_EN);
        if (language.equals(LANGUAGE_ES)) {
            language = LANGUAGE_EN;
        } else if (language.equals(LANGUAGE_EN)) {
            language = LANGUAGE_ES;
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE_PREFERENCES, language);
        editor.commit();
        //getLanguage(context);
    }

    /**
     * método encargado de obtener y cambiar el lengueje con el que se quiere mostrar la aplicacion
     * @param context contexto de la aplicacion
     */
    public static void getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFERENCES, context.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE_PREFERENCES, LANGUAGE_EN);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

}
