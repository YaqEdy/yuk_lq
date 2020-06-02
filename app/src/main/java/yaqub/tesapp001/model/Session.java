package yaqub.tesapp001.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import yaqub.tesapp001.activity.MainActivity;

/**
 * Created by Yaqub on 1/24/2018.
 */

public class Session {
    public static final String SP_LOGIN = "LoginApp";
    public static final String SP_NIP = "sp_nip";
    public static final String SP_NAMA = "sp_nama";
    public static final String SP_ID_GROUP = "sp_id_group";
    public static final String SP_NAMA_GROUP = "sp_nama_group";
    public static final String SP_GROUP_DESC = "sp_group_desc";
    public static final String SP_FILE_PATH = "sp_file_path";
    public static final boolean SP_STATUS_LOGIN = false;

    Context _Context;
    public SharedPreferences sp;
    public SharedPreferences.Editor spEditor;
//    int PRIVATE_MODE=1;

    public Session(Context context) {
        this._Context=context;
        sp = _Context.getSharedPreferences(SP_LOGIN, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(boolean keySP, boolean value) {
        spEditor.putBoolean(String.valueOf(keySP), value);
        spEditor.commit();
    }

    public boolean checkLogin() {
        return !this.isUserLoggedIn();
    }

    public void logOutUser() {
        spEditor.clear();
        spEditor.commit();

        Intent intent = new Intent(_Context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _Context.startActivity(intent);
    }

    //cek login
    public boolean isUserLoggedIn() {
        return sp.getBoolean(String.valueOf(SP_STATUS_LOGIN), false);
    }

    public String getSPNip() {
        return sp.getString(SP_NIP, "");
    }

    public String getSPNama() {
        return sp.getString(SP_NAMA, "");
    }

    public String getSPidGroup() {
        return sp.getString(SP_ID_GROUP, "");
    }

    public String getSPnamaGroup() {
        return sp.getString(SP_NAMA_GROUP, "");
    }
    public String getSPGroupDesc() {
        return sp.getString(SP_GROUP_DESC, "");
    }
    public String getSPFilePath() {
        return sp.getString(SP_FILE_PATH, "");
    }


    //    public static void saveLonginSesion(String iNip, String iNama, String iIdGroup, String iNamaGroup){
//        spEditor.putString(SP_NIP,iNip);
//        spEditor.putString(SP_NAMA,iNama);
//        spEditor.putString(SP_ID_GROUP,iIdGroup);
//        spEditor.putString(SP_NAMA_GROUP,iNamaGroup);
//
//        spEditor.putBoolean(String.valueOf(SP_STATUS_LOGIN),true);
//
//        spEditor.commit();
//    }

//    public HashMap<String,String> getUserDetail(){
//        HashMap<String,String> user=new HashMap<String, String>();
//        user.put(SP_NIP,sp.getString(SP_NIP,null));
//        user.put(SP_NAMA,sp.getString(SP_NAMA,null));
//        user.put(SP_ID_GROUP,sp.getString(SP_ID_GROUP,null));
//        user.put(SP_NAMA_GROUP,sp.getString(SP_NAMA_GROUP,null));
//
//        return user;
//    }


}
