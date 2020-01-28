package com.minhtootintaung.testing.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.minhtootintaung.testing.Adapters.AttractionRecyclerAdapter;
import com.minhtootintaung.testing.Adapters.CustomInfo;
import com.minhtootintaung.testing.Model.AttractionModel;
import com.minhtootintaung.testing.R;
import com.minhtootintaung.testing.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    MainViewModel mainViewModel;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;
    List<AttractionModel> attractionModelList;
    MaterialButton donebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        attractionModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        donebtn=findViewById(R.id.donebtn);
        recyclerAdapter = new AttractionRecyclerAdapter(attractionModelList, this);
        recyclerView.setAdapter(recyclerAdapter);

        Observers();
        Events();

    }

    private void Events() {
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Observers() {
        mainViewModel.GetList().observe(this, new Observer<List<AttractionModel>>() {
            @Override
            public void onChanged(List<AttractionModel> attractionModels) {
                attractionModelList.addAll(attractionModels);
                recyclerAdapter.notifyDataSetChanged();
                AddMarder();
            }
        });
        mainViewModel.GetSelect().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    recyclerAdapter.notifyDataSetChanged();
                    AddMarder();
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mainViewModel.InitList(this);


    }

    private void AddMarder() {
        mMap.clear();
        for (int i = 0; i < attractionModelList.size(); i++) {
            LatLng snowqualmie = new LatLng(attractionModelList.get(i).lat, attractionModelList.get(i).longi);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(snowqualmie)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


            CustomInfo customInfoWindow = new CustomInfo(this);
            mMap.setInfoWindowAdapter(customInfoWindow);

            Marker m = mMap.addMarker(markerOptions);
            m.setTag(attractionModelList.get(i));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(snowqualmie));
        }

        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
        AttractionModel a = (AttractionModel) marker.getTag();
        mainViewModel.InitSelect(attractionModelList.indexOf(a));
    }
}
