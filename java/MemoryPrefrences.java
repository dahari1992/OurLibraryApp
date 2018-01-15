package com.example.matan.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;


public class MemoryPrefrences {

    //vars
    private Context context;
    private SharedPreferences preferences;

    //Constructor
    public MemoryPrefrences(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    /**
     *
     * @param user
     * @param callback
     * save user in memory
     */
    public void saveUser(User user,Callback<Boolean> callback){
        try{
            preferences.edit().putString(Constans.KEY_NAME,user.getUsername()).apply();
            preferences.edit().putString(Constans.KEY_PASS,user.getPassword()).apply();
            preferences.edit().putString(Constans.KEY_MAIL,user.getEmail()).apply();
            preferences.edit().putString(Constans.KEY_ADDRESS,user.getAddress()).apply();
            preferences.edit().putString(Constans.KEY_PHONE,user.getPhone()).apply();
            callback.onSuccess(true);
        } catch (Exception e){
            callback.onError(false);
        }

    }

    /**
     * @param callback
     * load user to current context
     */
    public void loadUser(Callback<User> callback){

        String name =  preferences.getString(Constans.KEY_NAME,"");
        if(TextUtils.equals("",name)){
            callback.onError(null);
        }
        String pass =  preferences.getString(Constans.KEY_PASS,"");
        if(TextUtils.equals("",name)){
            callback.onError(null);
        }
        String email =  preferences.getString(Constans.KEY_MAIL,"");
        String address =  preferences.getString(Constans.KEY_ADDRESS,"");
        String phone =  preferences.getString(Constans.KEY_PHONE,"");
        callback.onSuccess(new User(name,pass,email,phone, address));

    }
}
