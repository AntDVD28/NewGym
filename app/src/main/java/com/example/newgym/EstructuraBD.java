package com.example.newgym;

public class EstructuraBD {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private EstructuraBD() {}


    //Definimos nombre de la tabla y columnas
    public static final String TABLE_NAME = "valoraciones";
    public static final String COLUMN_NAME_1 = "id";
    public static final String COLUMN_NAME_2 = "fecha";
    public static final String COLUMN_NAME_3 = "nombre";
    public static final String COLUMN_NAME_4 = "edad";
    public static final String COLUMN_NAME_5 = "genero";
    public static final String COLUMN_NAME_6 = "privacidad";
    public static final String COLUMN_NAME_7 = "valoracion";


    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    EstructuraBD.COLUMN_NAME_1 + " INTEGER PRIMARY KEY," +
                    EstructuraBD.COLUMN_NAME_2 + " TEXT," +
                    EstructuraBD.COLUMN_NAME_3 + " TEXT," +
                    EstructuraBD.COLUMN_NAME_4 + " TEXT," +
                    EstructuraBD.COLUMN_NAME_5 + " TEXT," +
                    EstructuraBD.COLUMN_NAME_6 + " INTEGER," +
                    EstructuraBD.COLUMN_NAME_7 + " INTEGER" +
                    ")";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EstructuraBD.TABLE_NAME;
}
