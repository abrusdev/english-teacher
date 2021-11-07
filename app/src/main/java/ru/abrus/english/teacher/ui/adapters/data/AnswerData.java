package ru.abrus.english.teacher.ui.adapters.data;

public class AnswerData {

    public String answer;

    public boolean isCorrect = false;

    public AnswerData(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public AnswerData(String answer) {
        this.answer = answer;
        this.isCorrect = false;
    }
}
