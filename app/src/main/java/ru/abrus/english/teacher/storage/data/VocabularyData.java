package ru.abrus.english.teacher.storage.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vocabularies")
public class VocabularyData {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "en")
    public String enText;

    @ColumnInfo(name = "uz")
    public String uzText;

    public String type;

    public String sentence;

    @ColumnInfo(name = "category_id")
    public int categoryId;


    public VocabularyData() {

    }

    public VocabularyData(String enText, String uzText, String type, String sentence, int categoryId) {
        this.enText = enText;
        this.uzText = uzText;
        this.type = type;
        this.sentence = sentence;
        this.categoryId = categoryId;
    }
}
