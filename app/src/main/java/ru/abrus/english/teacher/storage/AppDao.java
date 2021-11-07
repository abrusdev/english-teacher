package ru.abrus.english.teacher.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.abrus.english.teacher.storage.data.CategoryData;
import ru.abrus.english.teacher.storage.data.VocabularyData;

@Dao
public interface AppDao {

    @Query("SELECT * from categories")
    LiveData<List<CategoryData>> categories();

    @Insert
    long insertCategory(CategoryData data);

    @Query("DELETE from categories WHERE id = :id")
    void deleteCategory(int id);

    @Query("UPDATE categories SET count = :count WHERE id = :id")
    void updateCategoryCount(int id, int count);

    @Query("SELECT * from vocabularies WHERE category_id = :id")
    LiveData<List<VocabularyData>> vocabularies(int id);

    @Insert
    void insertVocabulary(VocabularyData data);

    @Query("delete from vocabularies where category_id = :categoryId")
    void deleteVocabularies(int categoryId);


}
