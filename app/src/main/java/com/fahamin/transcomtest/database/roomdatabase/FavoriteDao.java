package com.fahamin.transcomtest.database.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.fahamin.transcomtest.model.FavModel;

import java.util.List;


@Dao
public interface FavoriteDao {
    @Insert
    public void addData(FavModel favModel);

    @Query("select * from favModel")
    public List<FavModel> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favModel WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(FavModel favModel);

}
