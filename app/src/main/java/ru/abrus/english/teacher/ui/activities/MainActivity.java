package ru.abrus.english.teacher.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ru.abrus.english.teacher.Constants;
import ru.abrus.english.teacher.databinding.ActivityMainBinding;
import ru.abrus.english.teacher.storage.AppDao;
import ru.abrus.english.teacher.storage.AppDatabase;
import ru.abrus.english.teacher.storage.data.CategoryData;
import ru.abrus.english.teacher.ui.activities.add.AddCategoryDialog;
import ru.abrus.english.teacher.ui.adapters.CategoryAdapter;
import ru.abrus.english.teacher.utils.MainUtils;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initClicks();

        observe();
    }

    private void initViews() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        binding.recyclerCategories.setMinimumHeight(displayMetrics.heightPixels);


        adapter = new CategoryAdapter();
        adapter.setClickListener(new CategoryAdapter.CategoryClickListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY_ID, id);
                startActivity(intent);
            }

            @Override
            public void onDelete(int id) {
                AsyncTask.execute(() -> {
                    AppDao dao = AppDatabase.getInstance(MainActivity.this).appDao();
                    dao.deleteCategory(id);
                    dao.deleteVocabularies(id);
                });
            }
        });
        binding.recyclerCategories.setAdapter(adapter);
    }

    private void initClicks() {
        binding.addCategory.setOnClickListener(v -> {
            AddCategoryDialog dialog = new AddCategoryDialog();
            dialog.show(getSupportFragmentManager(), "add_category_dialog");
        });

        binding.uploadData.setOnClickListener(v -> {
            MainUtils.uploadAllDefaultData(AppDatabase.getInstance(this).appDao());
        });
    }

    private void observe() {
        AppDatabase.getInstance(this).appDao().categories().observe(this, categoryData -> {
            adapter.setCategories((ArrayList<CategoryData>) categoryData);
            if (!categoryData.isEmpty())
                binding.uploadData.setVisibility(View.GONE);
            else
                binding.uploadData.setVisibility(View.VISIBLE);
        });
    }
}