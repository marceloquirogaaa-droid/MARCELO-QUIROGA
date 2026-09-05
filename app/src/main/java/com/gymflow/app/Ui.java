package com.gymflow.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.core.content.ContextCompat;

public class Ui {
    public static int dp(Context c, int v){ return (int)(v*c.getResources().getDisplayMetrics().density); }

    public static LinearLayout page(Context c){
        LinearLayout l=new LinearLayout(c); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(dp(c,18),dp(c,18),dp(c,18),dp(c,24)); l.setBackgroundColor(ContextCompat.getColor(c,R.color.gf_bg));
        return l;
    }
    public static TextView title(Context c,String s){
        TextView t=new TextView(c); t.setText(s); t.setTextColor(ContextCompat.getColor(c,R.color.gf_text)); t.setTextSize(28); t.setTypeface(Typeface.DEFAULT_BOLD); t.setPadding(0,0,0,dp(c,8)); return t;
    }
    public static TextView subtitle(Context c,String s){
        TextView t=new TextView(c); t.setText(s); t.setTextColor(ContextCompat.getColor(c,R.color.gf_muted)); t.setTextSize(14); t.setPadding(0,0,0,dp(c,16)); return t;
    }
    public static TextView card(Context c,String head,String body){
        TextView t=new TextView(c); t.setText(head+"\n"+body); t.setTextColor(ContextCompat.getColor(c,R.color.gf_text)); t.setTextSize(16); t.setTypeface(Typeface.DEFAULT_BOLD); t.setBackgroundResource(R.drawable.card_bg); t.setPadding(dp(c,16),dp(c,16),dp(c,16),dp(c,16));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT); p.setMargins(0,0,0,dp(c,12)); t.setLayoutParams(p); return t;
    }
    public static Button button(Context c,String s){
        Button b=new Button(c); b.setText(s); b.setAllCaps(false); b.setTextColor(Color.rgb(5,25,12)); b.setTextSize(16); b.setTypeface(Typeface.DEFAULT_BOLD); b.setBackgroundResource(R.drawable.button_green);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp(c,56)); p.setMargins(0,0,0,dp(c,12)); b.setLayoutParams(p); return b;
    }
    public static EditText input(Context c,String hint){
        EditText e=new EditText(c); e.setHint(hint); e.setHintTextColor(ContextCompat.getColor(c,R.color.gf_muted)); e.setTextColor(ContextCompat.getColor(c,R.color.gf_text)); e.setTextSize(16); e.setSingleLine(true); e.setBackgroundResource(R.drawable.input_bg); e.setPadding(dp(c,14),dp(c,12),dp(c,14),dp(c,12));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp(c,56)); p.setMargins(0,0,0,dp(c,12)); e.setLayoutParams(p); return e;
    }
    public static Spinner spinner(Context c,String[] items){
        Spinner s=new Spinner(c); ArrayAdapter<String> a=new ArrayAdapter<String>(c,android.R.layout.simple_spinner_dropdown_item,items){
            @Override public View getView(int pos, View v, ViewGroup p){ TextView t=(TextView)super.getView(pos,v,p); t.setTextColor(ContextCompat.getColor(c,R.color.gf_text)); t.setTextSize(16); t.setPadding(dp(c,12),0,0,0); return t; }
        }; s.setAdapter(a); s.setBackgroundResource(R.drawable.input_bg); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dp(c,56)); p.setMargins(0,0,0,dp(c,12)); s.setLayoutParams(p); return s;
    }
    public static void backHeader(android.app.Activity a, LinearLayout root, String title){
        LinearLayout row=new LinearLayout(a); row.setOrientation(LinearLayout.HORIZONTAL); row.setGravity(Gravity.CENTER_VERTICAL);
        Button b=new Button(a); b.setText("‹"); b.setTextSize(28); b.setTextColor(ContextCompat.getColor(a,R.color.gf_text)); b.setBackgroundColor(Color.TRANSPARENT); b.setOnClickListener(v->a.finish()); row.addView(b,new LinearLayout.LayoutParams(dp(a,56),dp(a,56)));
        TextView t=title(a,title); t.setTextSize(24); row.addView(t,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1)); root.addView(row);
    }
}
