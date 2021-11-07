package ru.abrus.english.teacher.ui.adapters;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.abrus.english.teacher.databinding.ItemVocabularyBinding;
import ru.abrus.english.teacher.storage.data.VocabularyData;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder> {

    public ArrayList<VocabularyData> vocabularies = new ArrayList<>();

    public void setVocabularies(ArrayList<VocabularyData> data) {
        this.vocabularies = data;
        notifyDataSetChanged();
    }

    private TextToSpeech ttsUK;
    private TextToSpeech ttsUS;

    public VocabularyAdapter(TextToSpeech ttsUK, TextToSpeech ttsUS) {
        this.ttsUK = ttsUK;
        this.ttsUS = ttsUS;
    }

    @NonNull
    @Override
    public VocabularyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VocabularyViewHolder(
                ItemVocabularyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyAdapter.VocabularyViewHolder holder, int position) {
        VocabularyData data = vocabularies.get(position);
        String type = getType(data.type);

        holder.binding.enText.setText(String.format("%s (%s)", data.enText, type));
        holder.binding.uzText.setText(data.uzText);

        holder.binding.uk.setOnClickListener(v -> {
            if (ttsUK != null)
                ttsUK.speak(data.enText, TextToSpeech.QUEUE_FLUSH, null);
        });

        holder.binding.us.setOnClickListener(v -> {
            if (ttsUS != null)
                ttsUS.speak(data.enText, TextToSpeech.QUEUE_FLUSH, null);
        });

        holder.binding.sentence.setText(String.format("Sentence: %s", data.sentence));
        if (!data.sentence.isEmpty())
            holder.binding.sentence.setVisibility(View.VISIBLE);
    }

    private String getType(String origin){
        String type = "n.";
        switch (origin) {
            case "Verb": {
                type = "v.";
                break;
            }
            case "Adjective": {
                type = "adj.";
                break;
            }
            case "Adverb": {
                type = "adv.";
                break;
            }
            case "Phrase": {
                type = "phr.";
                break;
            }
            case "Neither": {
                type = "undef.";
                break;
            }
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    public static class VocabularyViewHolder extends RecyclerView.ViewHolder {
        ItemVocabularyBinding binding;

        public VocabularyViewHolder(ItemVocabularyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
