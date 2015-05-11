package com.uniquindio.electiva_android.curremtfilmsactivity.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Esta clase es la encargada de inicializar todas las operaciones reslizadas con la base de datos
 *
 * @author Einer
 * @version 1.0 on 22/04/2015.
 */
public class FilmSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE FilmDB ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, YEAR TEXT, URLTRAILER TEXT, DESCRIPTION TEX)";

    /**
     * Metodo constructor de la aplicacion encargado de crear el archivo de la base de datos
     * @param context contexto de la activdad que realiza el llamado a la clase
     * @param name nombre de la base de datos
     * @param factory forma definida para obtener a informacion (en la mayoria de casos es null)
     * @param version version de la base de datos a crear
     */
    public FilmSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * método encargado de ejecutar tareas en la base de datos
     * @param db instancia de la base de datos por medio de la cual se realizan las operaciones
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    /**
     * este metodo es llamado automaticamnte cuando se actualiza la base de datos
     * @param db instancia de la base de datos
     * @param oldVersion version de la base de datos actual
     * @param newVersion version nueva de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
