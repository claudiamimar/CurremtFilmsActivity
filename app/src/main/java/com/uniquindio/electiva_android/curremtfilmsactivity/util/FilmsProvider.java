package com.uniquindio.electiva_android.curremtfilmsactivity.util;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by claudiamilena on 7/05/15.
 */
public class FilmsProvider extends ContentProvider {

    //Definición del CONTENT_URI
    private static final String uri ="content://com.uniquindio.electiva_android.curremtfilmsactivity/films";
    public static final Uri CONTENT_URI = Uri.parse(uri);
    //Necesario para UriMatcher
    private static final int FILMS = 1;
    private static final int FILMS_ID = 2;
    private static final UriMatcher uriMatcher;
    //Base de datos
    private FilmSQLiteHelper filmdbh;

    //Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.uniquindio.electiva_android.curremtfilmsactivity", "films", FILMS);
        uriMatcher.addURI("com.uniquindio.electiva_android.curremtfilmsactivity", "films/#", FILMS_ID);
    }

    public FilmsProvider() {
    }

    @Override
    public boolean onCreate() {
        filmdbh = new FilmSQLiteHelper(getContext(), Utilities.NAME_DB, null, Utilities.VERSION_DB);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == FILMS_ID){
            where = "ID=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = filmdbh.getWritableDatabase();
        Cursor c = db.query(Utilities.NAME_DB, projection, where,
                selectionArgs, null, null, sortOrder);
        return c;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;
        SQLiteDatabase db = filmdbh.getWritableDatabase();
        regId = db.insert(Utilities.NAME_DB, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri; }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int cont;
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == FILMS_ID){
            where = "ID=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = filmdbh.getWritableDatabase();
        cont = db.update(Utilities.NAME_DB, values, where, selectionArgs);
        return cont;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == FILMS_ID){
            where = "ID=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = filmdbh.getWritableDatabase();
        cont = db.delete(Utilities.NAME_DB, where, selectionArgs);
        return cont;
    }
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match)
        {
            case FILMS:
                return "vnd.android.cursor.dir/vnd.electiva_android.film";
            case FILMS_ID:
                return "vnd.android.cursor.item/vnd.electiva_android.film";
            default:
                return null;
        }
    }

}
