package ru.abrus.english.teacher.storage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.abrus.english.teacher.Constants;
import ru.abrus.english.teacher.storage.data.CategoryData;
import ru.abrus.english.teacher.storage.data.VocabularyData;

@Database(entities = {CategoryData.class, VocabularyData.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

    public static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, Constants.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        return INSTANCE;
    }

}
