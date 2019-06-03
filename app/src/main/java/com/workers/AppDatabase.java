package com.workers;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.actions.HoverAction;
import com.actions.HoverActionDao;
import com.transactions.HoverTransaction;
import com.transactions.HoverTransactionDao;

@Database(entities = {HoverAction.class, HoverTransaction.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract HoverActionDao actionDao();
    public abstract HoverTransactionDao transactionDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `transactions` (`id` INTEGER, "
                    + "`uuid` TEXT, `action_id` TEXT, `ussd_messages` TEXT, "
                    + "`request_timestamp` INTEGER, `update_timestamp` INTEGER, "
                    + "`status` TEXT, `status_meaning` TEXT, `status_description` TEXT, "
                    + "`transaction_extras` TEXT,  PRIMARY KEY(`id`))");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hover.db")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
