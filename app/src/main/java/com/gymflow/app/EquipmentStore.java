package com.gymflow.app;
import android.content.*; import java.util.*;
public class EquipmentStore {
 private static final String K="equipment";
 public static ArrayList<String> get(Context c){ String raw=c.getSharedPreferences("gymflow",0).getString(K,""); ArrayList<String> out=new ArrayList<>(); if(raw.isEmpty()){Collections.addAll(out,"Prensa","Hack Squat","Smith","Polea Alta","Polea Baja","Doble Polea","Banco Plano","Banco Inclinado","Banco Scott","Mancuernas","Barra W","Sillón Cuádriceps"); save(c,out); return out;} Collections.addAll(out,raw.split("\\|",-1)); return out; }
 public static void save(Context c,List<String> xs){ c.getSharedPreferences("gymflow",0).edit().putString(K,android.text.TextUtils.join("|",xs)).apply(); }
}
