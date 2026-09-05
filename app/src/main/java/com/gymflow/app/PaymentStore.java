package com.gymflow.app;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class PaymentStore {
    private static final String KEY="payments_json";
    public static class Payment { public String member, method; public int amount; public Payment(String m,int a,String me){member=m;amount=a;method=me;} }
    public static ArrayList<Payment> get(Context c){
        ArrayList<Payment> out=new ArrayList<>(); String raw=c.getSharedPreferences("gymflow",0).getString(KEY,"");
        if(raw.isEmpty()) return out;
        try{JSONArray a=new JSONArray(raw);for(int i=0;i<a.length();i++){JSONObject o=a.getJSONObject(i);out.add(new Payment(o.optString("member"),o.optInt("amount"),o.optString("method")));}}catch(Exception ignored){}
        return out;
    }
    public static void save(Context c,ArrayList<Payment> xs){JSONArray a=new JSONArray();try{for(Payment p:xs){JSONObject o=new JSONObject();o.put("member",p.member);o.put("amount",p.amount);o.put("method",p.method);a.put(o);}}catch(Exception ignored){}c.getSharedPreferences("gymflow",0).edit().putString(KEY,a.toString()).apply();}
}
