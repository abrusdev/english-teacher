package ru.abrus.english.teacher.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.abrus.english.teacher.Constants;
import ru.abrus.english.teacher.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    ActivityStartBinding binding;

    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryId = getIntent().getIntExtra(Constants.KEY_CATEGORY_ID, 0);

        binding.enToTrans.setOnClickListener(v -> startActivity(Constants.VALUE_TEST_EN_TRANS));
        binding.transToEn.setOnClickListener(v -> startActivity(Constants.VALUE_TEST_TRANS_EN));
        binding.mix.setOnClickListener(v -> startActivity(Constants.VALUE_TEST_MIX));
    }

    private void startActivity(String type) {
        Intent intent = new Intent(StartActivity.this, CheckerActivity.class);
        intent.putExtra(Constants.KEY_CATEGORY_ID, categoryId);
        intent.putExtra(Constants.KEY_TEST_TYPE, type);
        startActivity(intent);
    }
}