package ru.abrus.english.teacher.ui.activities.add;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import ru.abrus.english.teacher.Constants;
import ru.abrus.english.teacher.databinding.ActivityAddVocabularyBinding;
import ru.abrus.english.teacher.storage.AppDao;
import ru.abrus.english.teacher.storage.AppDatabase;
import ru.abrus.english.teacher.storage.data.VocabularyData;

public class AddVocabularyActivity extends AppCompatActivity {

    ActivityAddVocabularyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVocabularyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initClicks();
    }

    private void initClicks() {
        binding.addVocabulary.setOnClickListener(v -> {
            int id = getIntent().getIntExtra(Constants.KEY_CATEGORY_ID, 0);
            String enVer = binding.enText.getText().toString();
            String uzVer = binding.uzText.getText().toString();
            String type = ((Chip) findViewById(binding.typesGroup.getCheckedChipIds().get(0))).getText().toString();
            String sentence = binding.sentence.getText().toString();
            VocabularyData data = new VocabularyData(enVer, uzVer, type, sentence, id);

            if (enVer.isEmpty()){
                binding.enLayout.setError("This field cannot be empty");
                return;
            }

            if (uzVer.isEmpty()){
                binding.uzLayout.setError("This field cannot be empty");
                return;
            }

            AsyncTask.execute(() -> {
                AppDao dao = AppDatabase.getInstance(this).appDao();
                dao.insertVocabulary(data);
                finish();
            });
        });
    }


}