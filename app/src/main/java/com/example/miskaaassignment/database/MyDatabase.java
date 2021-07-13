package com.example.miskaaassignment.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.example.miskaaassignment.Dao.DaoAccess;
import com.example.miskaaassignment.model.Model;

@Database(entities = {Model.class}, version = 1, exportSchema = false)
public abstract class MyDatabase  extends RoomDatabase {

    public static final String DB_NAME = "app_db";
    public static final String IMAGE_DB_NAME = "Images";
    public static final String IMAGE_TABLE = "image_table";
    public static final String TABLE_NAME= "countries_list_table";

    public abstract DaoAccess daoAccess();



}
