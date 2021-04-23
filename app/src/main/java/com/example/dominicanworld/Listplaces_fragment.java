package com.example.dominicanworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Listplaces_fragment extends Fragment {

    private static final String TEXT = "text";
    private static final String ID = "id";
    private static final String IDtipo = "idlugar";


    private  ArrayList<modelo> modelo;

    public static Listplaces_fragment newInstance(String text, int id , int tipo) {
        Listplaces_fragment frag = new Listplaces_fragment();

        Bundle args = new Bundle();
        args.putString(TEXT, text);
        args.putInt(ID, id);
        args.putInt(IDtipo, tipo);

        frag.setArguments(args);

        return frag;
    }

    RecyclerView simpleList;
    LinearLayoutManager linearLayoutManager;
    Listadapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.list_fragment, container, false);
        modelo= new ArrayList<>();
   /* if (getArguments() != null) {
      ((TextView) layout.findViewById(R.id.Titulolist)).setText(getArguments().getString(TEXT));
    }*/
        linearLayoutManager = new LinearLayoutManager(layout.getContext());
        simpleList =  layout.findViewById(R.id.recycle);
         myAdapter= new Listadapter(getContext(),modelo);

        myAdapter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(getContext(),modelo.get(simpleList.getChildAdapterPosition(v)).getId()+"",Toast.LENGTH_LONG).show();
                 if(getArguments().getString(TEXT)=="Playas" ) {
                     Fragment fragment = Listplaces_fragment.newInstance("playacambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId(),getArguments().getInt(IDtipo));
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
                 else if(getArguments().getString(TEXT)=="Hoteles" ) {
                     Fragment fragment = Listplaces_fragment.newInstance("hotelescambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId(),getArguments().getInt(IDtipo));
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
                 else if(getArguments().getString(TEXT)=="Rios" ) {
                     Fragment fragment = Listplaces_fragment.newInstance("rioscambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId(),getArguments().getInt(IDtipo));
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
                 else if(getArguments().getString(TEXT)=="Otros" ) {
                     Fragment fragment = Listplaces_fragment.newInstance("otroscambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId(),getArguments().getInt(IDtipo));
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
                 else  if(getArguments().getString(TEXT)=="playacambio" ) {
                     Fragment fragment = Detalle_fragment.newInstance("playacambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId());
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
                 else if(getArguments().getString(TEXT)=="hotelescambio" ) {
                     Fragment fragment = Detalle_fragment.newInstance("hotelescambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId());
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
                 else  if(getArguments().getString(TEXT)=="rioscambio" ) {
                     Fragment fragment = Detalle_fragment.newInstance("rioscambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId());
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
                 else if(getArguments().getString(TEXT)=="otroscambio" ) {
                     Fragment fragment = Detalle_fragment.newInstance("otroscambio",modelo.get(simpleList.getChildAdapterPosition(v)).getId());
                     getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                             .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                             .commit();
                 }
             }
         });

        simpleList.setLayoutManager(linearLayoutManager);
        if (getArguments().getString(TEXT)=="playacambio" && getArguments().getInt(ID)>0){
            Playabyciudad("https://dbpasantia.000webhostapp.com/buscarporciudad.php");
            ((TextView) layout.findViewById(R.id.Titulolist)).setText("Las hermosas playas:");

        }
        else if (getArguments().getString(TEXT)=="hotelescambio" && getArguments().getInt(ID)>0){
            Playabyciudad("https://dbpasantia.000webhostapp.com/buscarporciudad.php");
            ((TextView) layout.findViewById(R.id.Titulolist)).setText("Los hermosos Hoteles:");

        }
        else if (getArguments().getString(TEXT)=="rioscambio" && getArguments().getInt(ID)>0){
            Playabyciudad("https://dbpasantia.000webhostapp.com/buscarporciudad.php");
            ((TextView) layout.findViewById(R.id.Titulolist)).setText("Los hermosos Rios:");

        }
        else if (getArguments().getString(TEXT)=="otroscambio" && getArguments().getInt(ID)>0){
            Playabyciudad("https://dbpasantia.000webhostapp.com/buscarporciudad.php");
            ((TextView) layout.findViewById(R.id.Titulolist)).setText("Otros lugares hermosos:");

        }
        else {
            todos("https://dbpasantia.000webhostapp.com/ciudades.php");
            ((TextView) layout.findViewById(R.id.Titulolist)).setText("Seleccione su Ciudad o Provincia");

        }
        return layout;
    }
    private void todos(String url){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()){

                    Toast.makeText(getContext(), "listo", Toast.LENGTH_SHORT);
                    String name;
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject row = new JSONObject();
                        for (int i = 0 ;i < array.length(); i++) {
                            row = array.getJSONObject(i);
                            modelo.add(new modelo(row.getString("ciudad"), R.mipmap.dominican_foreground,  row.getInt("id_ciudad"),""));
                        }
                        simpleList.setAdapter(myAdapter);
                    }catch (JSONException e){

                        Log.d("log",e.toString());
                    }
                }else {
                    modelo.clear();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT);

            }
        }


        );


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    private void Playabyciudad(String url){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()){

                    Toast.makeText(getContext(), "listo", Toast.LENGTH_SHORT);
                    String name;
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject row = new JSONObject();
                        for (int i = 0 ;i < array.length(); i++) {
                            row = array.getJSONObject(i);
                            modelo.add(new modelo(row.getString("nombre"), R.drawable.cascadalimon,  row.getInt("id_lugar"),row.getString("path")));
                        }

                        simpleList.setAdapter(myAdapter);
                    }catch (JSONException e){

                        Log.d("log",e.toString());
                    }
                }else {
                    modelo.clear();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT);

            }
        }


        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id",getArguments().getInt(ID)+"");
                params.put("tipoid",getArguments().getInt(IDtipo)+"");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}


