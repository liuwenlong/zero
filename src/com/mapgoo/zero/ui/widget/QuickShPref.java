package com.mapgoo.zero.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class QuickShPref {
public static String IEMI = "imei";
	
private static SharedPreferences sSharedPreferences;
private static Editor sEditor;
	
	public static void  init(Context c){
		sSharedPreferences = c.getSharedPreferences(c.getPackageName(),Context.MODE_PRIVATE);
		sEditor = sSharedPreferences.edit();
	}
	
	public static void putValueObject(String key ,Object obj){
		if(obj instanceof String){
			sEditor.putString(key, (String)obj);
		}else if(obj instanceof Integer){
			sEditor.putInt(key, (Integer)obj);
		}else if(obj instanceof Boolean){
			sEditor.putBoolean(key, (Boolean)obj);
		}else{
			return;
		}
		sEditor.commit();
	}
	
	public static String getString(String key){
		return sSharedPreferences.getString(key, null);
	}
	public static int getInt(String key){
		return sSharedPreferences.getInt(key, -1);
	}
	public static boolean getBoolean(String key){
		return sSharedPreferences.getBoolean(key, false);
	}	
}
