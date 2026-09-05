package com.gymflow.app;
import android.content.Context; import java.util.ArrayList; import java.util.Collections;
public class TeacherStore {
 private static final String K="teachers";
 public static ArrayList<String> get(Context c){String raw=c.getSharedPreferences("gymflow",0).getString(K,"");ArrayList<String> out=new ArrayList<>();if(raw.isEmpty()){Collections.addAll(out,"Administrador");save(c,out);return out;}Collections.addAll(out,raw.split("\\|",-1));return out;}
 public static void save(Context c,ArrayList<String> xs){c.getSharedPreferences("gymflow",0).edit().putString(K,android.text.TextUtils.join("|",xs)).apply();}
}
