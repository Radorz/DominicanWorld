package com.example.dominicanworld;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class HomeContentFragment extends Fragment {

  private static final String TEXT = "text";

  public static HomeContentFragment newInstance(String text) {
    HomeContentFragment frag = new HomeContentFragment();

    Bundle args = new Bundle();
    args.putString(TEXT, text);
    frag.setArguments(args);

    return frag;
  }
  CarouselView carouselView;
  int []sampleImages = {
          R.drawable.alcazarcolon,
          R.drawable.bahiaaguilas,
          R.drawable.cascadalimon};
  GridView simpleList;
  ArrayList birdList=new ArrayList<>();
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
          Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.inicio_fragment, container, false);

   /* if (getArguments() != null) {
      ((TextView) layout.findViewById(R.id.text)).setText(getArguments().getString(TEXT));
    }*/
    carouselView = layout.findViewById(R.id.carouselView);
    carouselView.setPageCount(sampleImages.length);
    carouselView.setImageListener(imageListener);
    simpleList = (GridView) layout.findViewById(R.id.Grid);
    birdList.add(new item("Playas",R.drawable.playas));
    birdList.add(new item("Hoteles",R.drawable.hoteles));
    birdList.add(new item("Rios",R.drawable.rios));
    birdList.add(new item("Otros",R.drawable.villas));


    Adapter myAdapter=new Adapter(layout.getContext(),R.layout.rowprovincia,birdList);
    simpleList.setAdapter(myAdapter);

    simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // set an Intent to Another Activity
        String seccion = "Seccion";
        if(position==0){

          seccion="Playas";
        }
        if(position==1){

          seccion="Hoteles";
        }
        if(position==2){

          seccion="Rios";
        }
        if(position==3){

          seccion="Otros";
        }

        Fragment fragment =  Listplaces_fragment.newInstance(seccion,0,position+1);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                .replace(((ViewGroup)getView().getParent()).getId(), fragment)
                .commit();


      }
    });
    return layout;
  }
  ImageListener imageListener = new ImageListener() {
    @Override
    public void setImageForPosition(int position, ImageView imageView) {
      imageView.setImageResource(sampleImages[position]);
    }
  };
}

