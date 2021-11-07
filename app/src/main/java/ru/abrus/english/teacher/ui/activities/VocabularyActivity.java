package ru.abrus.english.teacher.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import ru.abrus.english.teacher.Constants;
import ru.abrus.english.teacher.databinding.ActivityVocabularyBinding;
import ru.abrus.english.teacher.storage.AppDao;
import ru.abrus.english.teacher.storage.AppDatabase;
import ru.abrus.english.teacher.storage.data.VocabularyData;
import ru.abrus.english.teacher.ui.activities.add.AddVocabularyActivity;
import ru.abrus.english.teacher.ui.adapters.VocabularyAdapter;

public class VocabularyActivity extends AppCompatActivity {

    ActivityVocabularyBinding binding;

    VocabularyAdapter adapter;

    int categoryId;

    TextToSpeech ttsUK;
    TextToSpeech ttsUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVocabularyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryId = getIntent().getIntExtra(Constants.KEY_CATEGORY_ID, 0);

        ttsUK = new TextToSpeech(getApplication(), (status) -> {
            if (ttsUK != null)
                ttsUK.setLanguage(Locale.UK);
        });

        ttsUS = new TextToSpeech(getApplication(), (status) -> {
            if (ttsUS != null)
                ttsUS.setLanguage(Locale.US);
        });

        observe();

        initViews();
    }

    private void initViews() {
        adapter = new VocabularyAdapter(ttsUK, ttsUS);
        binding.recyclerVocabulary.setAdapter(adapter);

        binding.addVocabulary.setOnClickListener(v -> {
            addActivity(AddVocabularyActivity.class);
        });

        binding.start.setOnClickListener(v -> {
            if (adapter.vocabularies.size() > 1)
                addActivity(StartActivity.class);
            else
                Toast.makeText(this, "At least, add 2 words to your vocabulary", Toast.LENGTH_SHORT).show();
        });
    }

    private void addActivity(Class<?> cls) {
        Intent intent = new Intent(VocabularyActivity.this, cls);
        intent.putExtra(Constants.KEY_CATEGORY_ID, categoryId);
        startActivity(intent);
    }

    private void observe() {
        AppDao dao = AppDatabase.getInstance(this).appDao();
        dao.vocabularies(categoryId).observe(this, vocabularyData -> {
            adapter.setVocabularies((ArrayList<VocabularyData>) vocabularyData);

            AsyncTask.execute(() -> {
                dao.updateCategoryCount(categoryId, vocabularyData.size());
            });
        });
    }
}