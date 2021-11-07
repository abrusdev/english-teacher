package ru.abrus.english.teacher.ui.activities.add;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.abrus.english.teacher.R;
import ru.abrus.english.teacher.databinding.DialogAddCategoryBinding;
import ru.abrus.english.teacher.storage.AppDatabase;
import ru.abrus.english.teacher.storage.data.CategoryData;

public class AddCategoryDialog extends BottomSheetDialogFragment {

    DialogAddCategoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogAddCategoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClicks();
    }

    private void initClicks() {
        binding.addCategory.setOnClickListener(v -> {
            String name = binding.categoryName.getText().toString();
            if (name.isEmpty()) {
                binding.categoryEdt.setError("The name is required to fill");
                return;
            }
            CategoryData data = new CategoryData(name);

            AsyncTask.execute(() -> {
                AppDatabase.getInstance(requireContext()).appDao().insertCategory(data);
                dismiss();
            });
        });
    }
}