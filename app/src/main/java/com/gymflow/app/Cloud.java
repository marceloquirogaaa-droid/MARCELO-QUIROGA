package com.gymflow.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cloud {
 public interface Result { void done(int code,String body); }
 private static final Object REFRESH_LOCK=new Object();

 private static class HttpResult {
  final int code; final String body;
  HttpResult(int code,String body){this.code=code;this.body=body;}
 }

 private static HttpResult perform(String method,String path,String body,String token){
  int code=-1; String out=""; HttpURLConnection h=null;
  try{
   URL u=new URL(SupabaseConfig.URL+path);
   h=(HttpURLConnection)u.openConnection();
   h.setRequestMethod(method); h.setConnectTimeout(15000); h.setReadTimeout(15000);
   h.setRequestProperty("apikey",SupabaseConfig.KEY);
   h.setRequestProperty("Content-Type","application/json");
   h.setRequestProperty("Accept","application/json");
   if(token!=null&&!token.isEmpty())h.setRequestProperty("Authorization","Bearer "+token);
   if(path.startsWith("/rest/v1/")&&("POST".equals(method)||"PATCH".equals(method)))h.setRequestProperty("Prefer","return=representation");
   if(body!=null){h.setDoOutput(true);try(OutputStream os=h.getOutputStream()){os.write(body.getBytes(StandardCharsets.UTF_8));}}
   code=h.getResponseCode();
   InputStream is=code>=200&&code<400?h.getInputStream():h.getErrorStream();
   if(is!=null)try(BufferedReader br=new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))){StringBuilder sb=new StringBuilder();String line;while((line=br.readLine())!=null)sb.append(line);out=sb.toString();}
  }catch(Exception e){out=e.toString();}
  finally{if(h!=null)h.disconnect();}
  return new HttpResult(code,out);
 }

 private static void deliver(Result cb,HttpResult r){new Handler(Looper.getMainLooper()).post(()->cb.done(r.code,r.body));}

 private static boolean saveAuthResponse(Context c,String body){
  try{
   JSONObject x=new JSONObject(body); String token=x.optString("access_token");
   if(token.isEmpty())return false;
   String refresh=x.optString("refresh_token",Session.refresh(c));
   JSONObject user=x.optJSONObject("user"); String uid=user==null?Session.uid(c):user.optString("id",Session.uid(c));
   Session.save(c,token,refresh,uid,x.optLong("expires_in",3600)); return !uid.isEmpty();
  }catch(Exception ignored){return false;}
 }

 private static boolean refreshSession(Context c,String rejectedToken){
  synchronized(REFRESH_LOCK){
   String current=Session.token(c);
   if(rejectedToken!=null&&!rejectedToken.equals(current))return true;
   if(rejectedToken==null&&!Session.shouldRefresh(c))return true;
   String refresh=Session.refresh(c); if(refresh.isEmpty())return false;
   try{
    JSONObject o=new JSONObject(); o.put("refresh_token",refresh);
    HttpResult r=perform("POST","/auth/v1/token?grant_type=refresh_token",o.toString(),null);
    return r.code>=200&&r.code<300&&saveAuthResponse(c,r.body);
   }catch(Exception ignored){return false;}
  }
 }

 private static void request(Context c,String method,String path,String body,boolean auth,Result cb){
  new Thread(()->{
   if(auth&&Session.shouldRefresh(c))refreshSession(c,null);
   String token=auth?Session.token(c):null;
   HttpResult r=perform(method,path,body,token);
   if(auth&&r.code==401&&refreshSession(c,token))r=perform(method,path,body,Session.token(c));
   deliver(cb,r);
  }).start();
 }

 public static void signIn(Context c,String email,String pass,Result cb){try{JSONObject o=new JSONObject();o.put("email",email);o.put("password",pass);request(c,"POST","/auth/v1/token?grant_type=password",o.toString(),false,(code,b)->{if(code>=200&&code<300)saveAuthResponse(c,b);cb.done(code,b);});}catch(Exception e){cb.done(-1,e.toString());}}
 public static void signUp(Context c,String email,String pass,Result cb){try{JSONObject o=new JSONObject();o.put("email",email);o.put("password",pass);request(c,"POST","/auth/v1/signup",o.toString(),false,(code,b)->{if(code>=200&&code<300)saveAuthResponse(c,b);cb.done(code,b);});}catch(Exception e){cb.done(-1,e.toString());}}
 public static void signOut(Context c,Result cb){new Thread(()->{if(Session.shouldRefresh(c))refreshSession(c,null);HttpResult r=perform("POST","/auth/v1/logout?scope=local",null,Session.token(c));Session.logout(c);deliver(cb,r);}).start();}
 public static void get(Context c,String table,String query,Result cb){request(c,"GET","/rest/v1/"+table+"?"+query,null,true,cb);}
 public static void post(Context c,String table,JSONObject o,Result cb){request(c,"POST","/rest/v1/"+table,o.toString(),true,cb);}
 public static void patch(Context c,String table,String query,JSONObject o,Result cb){request(c,"PATCH","/rest/v1/"+table+"?"+query,o.toString(),true,cb);}
 public static void delete(Context c,String table,String query,Result cb){request(c,"DELETE","/rest/v1/"+table+"?"+query,null,true,cb);}
 public static String err(String b){try{JSONObject o=new JSONObject(b);String m=o.optString("msg");if(m.isEmpty())m=o.optString("message");if(m.isEmpty())m=o.optString("error_description");return m.isEmpty()?b:m;}catch(Exception e){return b==null||b.isEmpty()?"Error de conexión":b;}}
}
