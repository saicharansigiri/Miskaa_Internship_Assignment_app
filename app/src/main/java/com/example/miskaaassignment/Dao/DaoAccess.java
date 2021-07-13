package com.example.miskaaassignment.Dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.miskaaassignment.database.MyDatabase;
import com.example.miskaaassignment.model.Model;
import java.util.List;

@Dao
public interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Model> countries);

    @Query("SELECT * FROM " + MyDatabase.TABLE_NAME)
    List<Model> getAll();

}
