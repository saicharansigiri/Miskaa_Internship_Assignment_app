package com.example.miskaaassignment.database;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.example.miskaaassignment.Dao.DaoAccess;
import com.example.miskaaassignment.model.Model;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatabaseHandler {
    private static MyDatabase myDatabase;
    private static DaoAccess daoAccess;

    public static void init(Context context){
            myDatabase = Room.databaseBuilder(context,MyDatabase.class,MyDatabase.DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
            daoAccess =myDatabase.daoAccess();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CompletableFuture<List<Model>> loadDataFromDb() {
            return CompletableFuture.supplyAsync(() -> daoAccess.getAll());

    }
    //checks db and if db is empty returns null
    public static String checkDb() {
        if(!daoAccess.getAll().isEmpty())
            return "NotEmpty";
        else
            return null;
    }

    public static void storeData(List<Model> countries) {
        daoAccess.insertAll(countries);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void clearDatabaseAsync() {
        CompletableFuture.runAsync(() -> myDatabase.clearAllTables());
    }



}
