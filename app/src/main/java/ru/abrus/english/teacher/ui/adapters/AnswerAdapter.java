package ru.abrus.english.teacher.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.abrus.english.teacher.databinding.ItemAnswerBinding;
import ru.abrus.english.teacher.ui.adapters.data.AnswerData;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private ArrayList<AnswerData> answers = new ArrayList<>();
    private AnswerClickListener listener;

    public void setAnswers(ArrayList<AnswerData> data) {
        this.answers = data;
        notifyDataSetChanged();
    }

    public void setListener(AnswerClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerViewHolder(
                ItemAnswerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.AnswerViewHolder holder, int position) {
        AnswerData data = answers.get(position);
        holder.binding.answerText.setText(data.answer);
        holder.binding.answer.setOnClickListener(v -> {
            listener.onClick(data.isCorrect);
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        ItemAnswerBinding binding;

        public AnswerViewHolder(ItemAnswerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static interface AnswerClickListener {
        void onClick(Boolean isCorrect);
    }

}
