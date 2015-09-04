package com.boombile.boomdb.example;

import android.app.Application;

import com.boombile.boomdb.DatabaseManager;
import com.boombile.boomdb.example.database.MySqliteOpenHelper;

/**
 * Created by maohieng on 9/4/15.
 */
public class BoomDBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager.initialize(new MySqliteOpenHelper(this));
    }
}
