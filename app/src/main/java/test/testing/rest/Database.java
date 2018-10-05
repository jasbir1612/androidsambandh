package test.testing.rest;

import android.content.Context;
import android.content.SharedPreferences;

public class Database {

    private Context mContext;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    public static final String KEY_APP_UID = "app_uid";
    public static final String KEY_SMSID = "sms_id";
    public static final String KEY_MOBILE = "moibile";

    public Database(Context context) {
        mContext = context;
        sharedpreferences = context.getSharedPreferences("samandh", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    private boolean putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    private boolean putLong(String key, Long value) {
        editor.putLong(key, value);
        return editor.commit();
    }

    private boolean putString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    private boolean putInt(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }

    public void setAppUid(String appUid) {
        putString(KEY_APP_UID, appUid);
    }

    public String getAppUid() {
        return sharedpreferences.getString(KEY_APP_UID, "");
    }

    public void setSmsID(String smsId) {
        putString(KEY_SMSID, smsId);
    }

    public String getSmsId() {
        return sharedpreferences.getString(KEY_SMSID, "");
    }

    public void setMobile(String mobile) {
        putString(KEY_MOBILE, mobile);
    }

    public String getMobile() {
        return sharedpreferences.getString(KEY_MOBILE, "");
    }
}
