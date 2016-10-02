package com.apppartner.androidprogrammertest;

import android.app.Application;

/**
 * Created by zepvalue on 10/1/2016.
 */

public class MyApplication extends Application {

    private boolean logged = false;

    public void setLogged(boolean state)
    {
        logged = state;
    }

    public boolean getLogged()
    {
        return logged;
    }
}
