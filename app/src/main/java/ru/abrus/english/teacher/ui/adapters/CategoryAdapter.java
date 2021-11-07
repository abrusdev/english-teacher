package ru.abrus.english.teacher.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.abrus.english.teacher.databinding.ItemCategoryBinding;
import ru.abrus.english.teacher.storage.data.CategoryData;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryData> categories = new ArrayList<>();

    public void setCategories(ArrayList<CategoryData> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    private CategoryClickListener listener;

    public void setClickListener(CategoryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(
                ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryData data = categories.get(position);

        holder.binding.categoryName.setText(data.name);
        holder.binding.categoryCount.setText(String.valueOf(data.count));

        holder.binding.categoryLayout.setOnClickListener(v -> {
            if (listener != null)
                listener.onClick(data.id);
        });

        holder.binding.delete.setOnClickListener(v -> {
            if (listener != null)
                listener.onDelete(data.id);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static interface CategoryClickListener {
        void onClick(int id);

        void onDelete(int id);
    }
}
