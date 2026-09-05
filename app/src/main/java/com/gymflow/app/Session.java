package com.gymflow.app;
import android.content.Context;
public class Session {
    private static final String P="gymflow_online";
    public static void save(Context c,String token,String refresh,String uid,long expiresInSeconds){
        long safeSeconds=expiresInSeconds>0?expiresInSeconds:3600;
        long expiresAt=System.currentTimeMillis()+(safeSeconds*1000L);
        c.getSharedPreferences(P,0).edit()
                .putString("token",token)
                .putString("refresh",refresh)
                .putString("uid",uid)
                .putLong("expires_at",expiresAt)
                .apply();
    }
    public static void save(Context c,String token,String refresh,String uid){save(c,token,refresh,uid,3600);}
    public static String token(Context c){return c.getSharedPreferences(P,0).getString("token","");}
    public static String refresh(Context c){return c.getSharedPreferences(P,0).getString("refresh","");}
    public static String uid(Context c){return c.getSharedPreferences(P,0).getString("uid","");}
    public static boolean shouldRefresh(Context c){
        long expiresAt=c.getSharedPreferences(P,0).getLong("expires_at",0L);
        return expiresAt>0L&&System.currentTimeMillis()>=expiresAt-60000L;
    }
    public static boolean logged(Context c){return !token(c).isEmpty()&&!uid(c).isEmpty();}
    public static void logout(Context c){c.getSharedPreferences(P,0).edit().clear().apply();}
}
