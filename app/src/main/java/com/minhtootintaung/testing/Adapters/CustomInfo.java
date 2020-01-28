package com.minhtootintaung.testing.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.button.MaterialButton;
import com.minhtootintaung.testing.Model.AttractionModel;
import com.minhtootintaung.testing.R;

public class CustomInfo implements GoogleMap.InfoWindowAdapter {


    private Context context;

    public CustomInfo(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }


    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.custom_info, null);


        final AttractionModel attractionModel = (AttractionModel) marker.getTag();

        TextView titletv = view.findViewById(R.id.title);
        TextView pricetv = view.findViewById(R.id.pricetv);
        MaterialButton selectbtn = view.findViewById(R.id.selectbtn);

        titletv.setText(attractionModel.name);
        pricetv.setText(attractionModel.price + "");


        if (attractionModel.isSelected) {
            selectbtn.setText("Selected");

            selectbtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_box_black_24dp, 0);
        } else {
            selectbtn.setText("Select");

            selectbtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        }

        return view;
    }


}