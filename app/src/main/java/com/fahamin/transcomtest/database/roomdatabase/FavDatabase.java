package com.fahamin.transcomtest.database.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fahamin.transcomtest.model.FavModel;


@Database(entities={FavModel.class},version = 1)
public abstract class FavDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

}
