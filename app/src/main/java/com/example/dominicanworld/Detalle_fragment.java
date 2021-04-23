package com.example.dominicanworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Detalle_fragment extends Fragment {

    private static final String TEXT = "text";
    private static final String ID = "id";

    public static Detalle_fragment newInstance(String text, int id) {
        Detalle_fragment frag = new Detalle_fragment();

        Bundle args = new Bundle();
        args.putString(TEXT, text);
        args.putInt(ID, id);
        frag.setArguments(args);

        return frag;
    }
    TextView tv;
    ImageView iv;
    Button btwha;
    private GoogleMap mMap;
    String locationlugar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.detalle_fragment, container, false);

    if (getArguments() != null) {
        Playabyid("https://dbpasantia.000webhostapp.com/buscarporid.php", layout.getContext());
        tv = layout.findViewById(R.id.informacion);
        iv = layout.findViewById(R.id.imagen);
        btwha =layout. findViewById(R.id.comwha);
    }

        btwha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatsAppMessage = "http://maps.google.com/maps?saddr=" +locationlugar ;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });


        return layout;
    }


    private void Playabyid(String url, Context v){
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
                            tv.setText(row.getString("descripcion").substring(0,150));
                            locationlugar=row.getString("location");
                            Picasso.get().load(row.getString("path"))
                                    .into(iv);
                                             }
                    }catch (JSONException e){

                        Log.d("log",e.toString());
                    }
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
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}

