package com.example.dominicanworld;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Detalle_fragment extends Fragment implements OnMapReadyCallback {

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
    LocationManager ubicacion;
    Boolean actual_location = true;
    private GoogleMap mMap;
    String latitud, longitud;
    double latitud_origen, longitud_origen;
    JSONObject jso;
    TextView tv;
    ImageView iv;
    Button btwha, btuber;
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

    @Override
    public void onResume() {
        super.onResume();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                            tv.setText(row.getString("descripcion"));
                            locationlugar=row.getString("location");
                            Picasso.get().load(row.getString("path"))
                                    .into(iv);
                            tv.setMovementMethod(new ScrollingMovementMethod());
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



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapadia));

            if (!success) {

            }
        } catch (Resources.NotFoundException e) {

        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
         ubicacion = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

       String latitud = String.valueOf(loc.getLatitude());
      String  longitud = String.valueOf(loc.getLongitude());

        Toast.makeText(getContext(), latitud + " " + longitud, Toast.LENGTH_LONG).show();

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                if (actual_location && locationlugar!=null) {
                    latitud_origen = location.getLatitude();
                    longitud_origen = location.getLongitude();
                    actual_location = false;

                    LatLng miposicion = new LatLng(latitud_origen, longitud_origen);
                    String x = locationlugar.split(",")[0];
                    String y = locationlugar.split(", ")[1];
                    Log.d("log",y);
                    Double z = Double.parseDouble(x);
                    Double w = Double.parseDouble(y);

                    LatLng rd = new LatLng(z, w);
                    mMap.addMarker(new MarkerOptions().position(rd)
                            .title("Lugar de destino"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(rd));


                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitud_origen, longitud_origen))
                            .zoom(14)
                            .bearing(25)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    String Url = "https://maps.googleapis.com/maps/api/directions/json?" +
                            "origin=" + latitud_origen + "," + longitud_origen +
                            "&destination=" + x + "," + y + "&key=AIzaSyCJZgPTOspYNDXN8IKK3m4g31PtTTnzL7Q";

                    RequestQueue rq = Volley.newRequestQueue(getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                jso = new JSONObject(response);

                                hacerruta(jso);
                                Log.i("Ruta:", "" + response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    rq.add(stringRequest);


                }

            }
        });


    }

    private void hacerruta(JSONObject jso) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {

                jLegs = ((JSONObject) (jRoutes.get(i))).getJSONArray("legs");

                for (int j = 0; j < jLegs.length(); j++) {

                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k < jSteps.length(); k++) {

                        String polyline = "" + ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end", "" + polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.RED).width(7));


                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }
}