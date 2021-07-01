package com.example.sanjaya.imit;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefernceManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String KEY_IS_LOGGEDIN="session";
   // int defa=0;

    public PrefernceManager(Context context){

        this.context=context;
        sharedPreferences=this.context.getSharedPreferences("MyData",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void putString(String key,String value){

        editor.putString(key,value);
        editor.commit();
    }
    public String getString(String key)
    {
        return sharedPreferences.getString(key,null);

    }
    public void putInt(String key,int value){

        editor.putInt(key,value);
        editor.commit();
        editor.apply();
    }
    public int getInt(String key)
    {
        return sharedPreferences.getInt(key,0);

    }
    public void removeKey(String key)
    {
        editor.remove(key);
        editor.commit();
    }
    public boolean isLoggedIn(){

        return sharedPreferences.getBoolean(KEY_IS_LOGGEDIN,false);
    }

    public void setLogin(boolean value)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN,value);
        editor.commit();
    }



}
