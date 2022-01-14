package com.conect.conectapp.Prevalent;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberme";


    private static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    private static final String IS_REMEMBERME = "IsRememberme";
    public static final String KEY_SESSIONEMAIL = "email";
    public static final String KEY_SESSIOPASSWORD = "password";

    public SessionManager(Context _context, String sessionName) {
        context = _context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();


    }

    public void createLoginSession( String email, String password) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        return userData;
    }

    public boolean checkLogin() {
        if (usersSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
    public void createRememberMeSession( String email, String password) {
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONEMAIL, email);
        editor.putString(KEY_SESSIOPASSWORD, password);
        editor.commit();
    }
    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_SESSIONEMAIL, usersSession.getString(KEY_SESSIONEMAIL, null));
        userData.put(KEY_SESSIOPASSWORD, usersSession.getString(KEY_SESSIOPASSWORD, null));
        return userData;
    }
    public boolean checkRememberMe() {
        if (usersSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else {
            return false;
        }
    }


}
