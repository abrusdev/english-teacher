package ru.abrus.english.teacher.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.TransitionManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import ru.abrus.english.teacher.Constants;
import ru.abrus.english.teacher.databinding.ActivityCheckerBinding;
import ru.abrus.english.teacher.storage.AppDatabase;
import ru.abrus.english.teacher.storage.data.VocabularyData;
import ru.abrus.english.teacher.ui.adapters.AnswerAdapter;
import ru.abrus.english.teacher.ui.adapters.data.AnswerData;
import ru.abrus.english.teacher.ui.adapters.data.QuestionData;

public class CheckerActivity extends AppCompatActivity {

    int currentTest = -1;

    ActivityCheckerBinding binding;

    int categoryId = 0;

    String testType = Constants.VALUE_TEST_EN_TRANS;

    ArrayList<QuestionData> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryId = getIntent().getIntExtra(Constants.KEY_CATEGORY_ID, 0);
        testType = getIntent().getStringExtra(Constants.KEY_TEST_TYPE);

        observe();
    }

    private void observe() {
        AppDatabase.getInstance(this).appDao().vocabularies(categoryId)
                .observe(this, vocabularyData -> {
                    initQuestions((ArrayList<VocabularyData>) vocabularyData);
                    startTest();
                });
    }

    AnswerAdapter adapter;

    private void startTest() {
        adapter = new AnswerAdapter();
        binding.recyclerAnswers.setAdapter(adapter);

        /**
         * START
         */
        generateNewQuestion();

        adapter.setListener(isCorrect -> {
            if (currentTest + 1 == questions.size()) {
                finish();
                return;
            }

            Handler h = new Handler(Looper.getMainLooper());

            h.removeCallbacksAndMessages(null);
            if (isCorrect) {
                binding.failure.setVisibility(View.GONE);
                binding.success.setVisibility(View.VISIBLE);
            } else {
                binding.success.setVisibility(View.GONE);
                binding.failure.setVisibility(View.VISIBLE);
                binding.failureLastAnswer.setText(String.format("Answer: %s", questions.get(currentTest).name));
            }

            h = new Handler(Looper.getMainLooper());
            h.postDelayed(() -> {
                TransitionManager.beginDelayedTransition(binding.getRoot());
                binding.success.setVisibility(View.GONE);
                binding.failure.setVisibility(View.GONE);
            }, 3000);
            generateNewQuestion();
        });
    }

    private void generateNewQuestion() {
        currentTest++;
        binding.question.setText(questions.get(currentTest).name);
        adapter.setAnswers(questions.get(currentTest).data);
    }

    private void initQuestions(ArrayList<VocabularyData> items) {
        Collections.shuffle(items);
        switch (testType) {
            case Constants.VALUE_TEST_EN_TRANS: {
                for (VocabularyData data : items) {
                    QuestionData q = new QuestionData();
                    q.name = data.enText;
                    q.data = generateAnswers(items, data, false);
                    questions.add(q);
                }
                break;
            }
            case Constants.VALUE_TEST_TRANS_EN: {
                for (VocabularyData data : items) {
                    QuestionData q = new QuestionData();
                    q.name = data.uzText;
                    q.data = generateAnswers(items, data, false);
                    questions.add(q);
                }
                break;
            }
            case Constants.VALUE_TEST_MIX: {
                for (VocabularyData data : items) {

                    boolean isEn = (int) (Math.random() * 10) % 2 == 0;

                    QuestionData q = new QuestionData();
                    q.name = isEn ? data.enText : data.uzText;
                    q.data = generateAnswers(items, data, isEn);
                    questions.add(q);
                }
                break;
            }
        }
    }

    public ArrayList<AnswerData> generateAnswers(ArrayList<VocabularyData> items, VocabularyData correctData, boolean isEn) {
        ArrayList<AnswerData> answers = new ArrayList<>();

        switch (testType) {
            case Constants.VALUE_TEST_EN_TRANS: {
                answers.add(new AnswerData(correctData.uzText, true));
                break;
            }
            case Constants.VALUE_TEST_TRANS_EN: {
                answers.add(new AnswerData(correctData.enText, true));
                break;
            }
            default: {
                answers.add(new AnswerData(isEn ? correctData.uzText : correctData.enText, true));
            }
        }

        int count = 1;
        ArrayList<VocabularyData> shuffledItems = new ArrayList<>(items);
        Collections.shuffle(shuffledItems);
        for (VocabularyData wrongData : shuffledItems) {
            if (wrongData.id == correctData.id)
                continue;

            switch (testType) {
                case Constants.VALUE_TEST_EN_TRANS: {
                    answers.add(new AnswerData(wrongData.uzText));
                    break;
                }
                case Constants.VALUE_TEST_TRANS_EN: {
                    answers.add(new AnswerData(wrongData.enText));
                    break;
                }
                default: {
                    answers.add(new AnswerData(isEn ? wrongData.uzText : wrongData.enText));
                    break;
                }
            }
            count++;

            if (count == 4)
                break;
        }

        Collections.shuffle(answers);
        return answers;
    }
}