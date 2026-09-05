package com.gymflow.app;
import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import org.json.*;
public class RoutinesActivity extends AppCompatActivity {
 private LinearLayout list;
 @Override protected void onCreate(Bundle b){super.onCreate(b);ScrollView s=new ScrollView(this);LinearLayout r=Ui.page(this);s.addView(r);Ui.backHeader(this,r,"Rutinas");Button add=Ui.button(this,"+ Crear rutina personalizada");add.setOnClickListener(v->startActivity(new Intent(this,CreateRoutineActivity.class)));r.addView(add);list=new LinearLayout(this);list.setOrientation(LinearLayout.VERTICAL);r.addView(list);setContentView(s);}
 @Override protected void onResume(){super.onResume();if(list!=null)load();}
 private void load(){list.removeAllViews();Cloud.get(this,"gf_routines","select=id,name,goal,level,days,equipment_only&owner_id=eq."+Session.uid(this)+"&order=created_at.desc",(c,b)->{try{JSONArray a=new JSONArray(b);if(a.length()==0)list.addView(Ui.card(this,"Todavía no hay rutinas","Creá la primera usando el equipamiento del gimnasio."));for(int i=0;i<a.length();i++){JSONObject o=a.getJSONObject(i);list.addView(Ui.card(this,o.optString("name"),o.optString("goal")+" · "+o.optString("level")+" · "+o.optInt("days")+" días"));}}catch(Exception e){list.addView(Ui.card(this,"Error",Cloud.err(b)));}});}
}
