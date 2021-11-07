package ru.abrus.english.teacher.utils;

import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.abrus.english.teacher.storage.AppDao;
import ru.abrus.english.teacher.storage.data.CategoryData;
import ru.abrus.english.teacher.storage.data.VocabularyData;

public class MainUtils {

    public static String getToday() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(c);
    }

    public static void uploadAllDefaultData(AppDao dao) {
        AsyncTask.execute(() -> {
            int id = (int) dao.insertCategory(new CategoryData(getToday()));

            dao.insertVocabulary(new VocabularyData("Good", "Yaxshi", "Adjective", "Keep up the good work.", id));
            dao.insertVocabulary(new VocabularyData("People", "Odamlar", "Noun", "There are so many people in Times Square!", id));
            dao.insertVocabulary(new VocabularyData("Say", "Aytmoq", "Verb", "How do you say this word?, What did you say?", id));
            dao.insertVocabulary(new VocabularyData("Do", "Qilmoq", "Verb", "I have so much to do today., What did you do?", id));
            dao.insertVocabulary(new VocabularyData("First", "Birinchi", "Adjective", "He gave the students their first warning.", id));
            dao.insertVocabulary(new VocabularyData("Time", "Vaqt", "Noun", "What time should we meet?", id));
            dao.insertVocabulary(new VocabularyData("Make", "Qilmoq", "Verb", "My family taught me how to make our famous recipes.", id));
            dao.insertVocabulary(new VocabularyData("Have", "Bor", "Verb", "I have three meetings today.", id));
            dao.insertVocabulary(new VocabularyData("Know", "Bilmoq", "Verb", "I know that Saturday is the day after Friday.", id));
            dao.insertVocabulary(new VocabularyData("Great", "Buyuk", "Adjective", "There was a great famine in Rome.", id));
            dao.insertVocabulary(new VocabularyData("Day", "Kun", "Noun", "Have a great day!", id));
            dao.insertVocabulary(new VocabularyData("Look", "Qaramoq", "Verb", "Look towards the teacher.", id));

            dao.updateCategoryCount(id, 12);
        });
    }
}
