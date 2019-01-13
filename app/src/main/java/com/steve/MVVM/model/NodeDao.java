package com.steve.MVVM.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NodeDao {
    @Insert
    void insert(Node node);

    @Update
    void update(Node node);

    @Delete
    void delete(Node node);

    @Query("DELETE FROM NODE_TABLE")
    void deleteAllNodes();

    @Query("SELECT * FROM NODE_TABLE ORDER BY NAME ASC")
     LiveData<List<Node>> getAllNodes();
}
