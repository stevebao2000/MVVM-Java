package com.steve.MVVM.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Node.class}, version=1)
public abstract class NodeDatabase extends RoomDatabase {

    private static NodeDatabase instance;
    public abstract NodeDao noteDao();  // Room generates the code for us.


    public static synchronized NodeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NodeDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()  // delete old db when version changes.
                    .addCallback(roomCallback)
                    .build();
        }
         return instance;
    }

    private static RoomDatabase.Callback roomCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsyncTask(instance).execute();
                }
            };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NodeDao noteDao;

        private PopulateDbAsyncTask(NodeDatabase db) {
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Node("John", "john@google.com"));
            noteDao.insert(new Node("Mary", "mary@yahoo.com"));
            noteDao.insert(new Node("Smith", "smith@hotmail.com"));
            return null;
        }
    }
}
