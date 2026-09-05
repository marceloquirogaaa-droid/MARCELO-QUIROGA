package com.gymflow.app;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MemberStore {
    private static final String KEY="members_json";
    public static class Member {
        public String name, plan, status;
        public Member(String n,String p,String s){name=n;plan=p;status=s;}
    }
    public static ArrayList<Member> get(Context c){
        ArrayList<Member> out=new ArrayList<>();
        String raw=c.getSharedPreferences("gymflow",0).getString(KEY,"");
        if(raw.isEmpty()) return out;
        try{ JSONArray a=new JSONArray(raw); for(int i=0;i<a.length();i++){JSONObject o=a.getJSONObject(i);out.add(new Member(o.optString("name"),o.optString("plan"),o.optString("status","Activo")));} }catch(Exception ignored){}
        return out;
    }
    public static void save(Context c,ArrayList<Member> xs){
        JSONArray a=new JSONArray();
        try{ for(Member m:xs){JSONObject o=new JSONObject();o.put("name",m.name);o.put("plan",m.plan);o.put("status",m.status);a.put(o);} }catch(Exception ignored){}
        c.getSharedPreferences("gymflow",0).edit().putString(KEY,a.toString()).apply();
    }
}
