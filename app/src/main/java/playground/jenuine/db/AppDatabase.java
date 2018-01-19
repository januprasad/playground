package playground.jenuine.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import timber.log.Timber;

@Database(entities = {Todo.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TodoDao todoModel();

    // TODO: PERSIST DB
    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "com.larkintuckerllc.room.db")
                            // IN THIS EXAMPLE USED FOR SIMPLICITY
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2)
                            .build();
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'Todo' ADD COLUMN 'description' TEXT");
            Timber.v("Migration");

        }
    };


    public static void destroyInstance() {
        INSTANCE = null;
    }

}
