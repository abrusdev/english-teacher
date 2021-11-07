package ru.abrus.english.teacher.storage.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryData {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public int count = 0;

    public CategoryData() {
    }

    public CategoryData(String name) {
        this.name = name;
    }
}
