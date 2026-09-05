package com.gymflow.app;
import android.content.Context; import java.util.*;
public class RoutineStore {
 private static final String K="routines";
 public static ArrayList<String> get(Context c){String raw=c.getSharedPreferences("gymflow",0).getString(K,"");ArrayList<String> out=new ArrayList<>();if(!raw.isEmpty())Collections.addAll(out,raw.split("\\|",-1));return out;}
 public static void add(Context c,String s){ArrayList<String> xs=get(c);xs.add(s);c.getSharedPreferences("gymflow",0).edit().putString(K,android.text.TextUtils.join("|",xs)).apply();}
}
