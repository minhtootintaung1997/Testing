package com.minhtootintaung.testing.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.minhtootintaung.testing.Activity.MapsActivity;
import com.minhtootintaung.testing.Model.AttractionModel;
import com.minhtootintaung.testing.R;
import com.minhtootintaung.testing.ViewModel.MainViewModel;

import java.util.List;

public class AttractionRecyclerAdapter extends RecyclerView.Adapter<AttractionRecyclerAdapter.ViewHolder> {
    List<AttractionModel>list;
    MapsActivity context;

    public AttractionRecyclerAdapter(List<AttractionModel> list,MapsActivity m) {
        this.list = list;
        this.context=m;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_info,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttractionModel attractionModel=list.get(position);
        holder.titletv.setText(attractionModel.name);
        holder.pricetv.setText(attractionModel.price+"");
        if(attractionModel.isSelected){
            holder.selectbtn.setText("Selected");
            holder.selectbtn.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_check_box_black_24dp, 0);
        }
        else {
            holder.selectbtn.setText("Select");
            holder.selectbtn.setCompoundDrawablesWithIntrinsicBounds( 0, 0,0, 0);

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titletv,pricetv;
        MaterialButton selectbtn;
        MainViewModel mainViewModel;
        public ViewHolder(@NonNull View view) {
            super(view);

            mainViewModel= ViewModelProviders.of(context).get(MainViewModel.class);
             titletv=view.findViewById(R.id.title);
             pricetv=view.findViewById(R.id.pricetv);
             selectbtn=view.findViewById(R.id.selectbtn);

             selectbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainViewModel.InitSelect(getAdapterPosition());
                }
            });

        }
    }
}
