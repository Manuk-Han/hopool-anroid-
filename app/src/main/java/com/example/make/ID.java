package com.example.make;

import android.app.Application;

public class ID extends Application {
    private int ID;

    public void onCreate() {
        ID = 0;
        super.onCreate();
    }

    public void onTerminate() {
        super.onTerminate();
    }

    public void setID(int ID) {
        this.ID=ID;
    }

    public int getID(){
        return ID;
    }
}
