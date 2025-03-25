package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja.modelo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cerveja.class}, version = 1, exportSchema = false)
public abstract class CervejaDatabase extends RoomDatabase {

    public abstract CervejaDao cervejaDao();

    private static CervejaDatabase INSTANCE;

    public static CervejaDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CervejaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CervejaDatabase.class, "cervejas-database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
