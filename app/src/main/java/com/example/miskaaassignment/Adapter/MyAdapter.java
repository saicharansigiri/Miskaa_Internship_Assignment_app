package com.example.miskaaassignment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.miskaaassignment.R;
import com.example.miskaaassignment.model.Model;
import com.example.miskaaassignment.utils.Utils;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private Context context;
    private List<Model> modelList;

    public MyAdapter(Context context,List<Model> modelList){
        this.context = context;
        this.modelList = modelList;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
       return  new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Model model = modelList.get(position);

        holder.name.setText(model.getName());
        holder.capital.setText("Capital: "+model.getCapital());
        holder.region.setText("Region: "+model.getRegion());
        holder.subregion.setText("Subregion"+model.getSubregion());
        holder.population.setText("Population: "+String.valueOf(model.getPopulation()));
        holder.borders.setText("Border: "+ model.getBorders());
        holder.languages.setText("languages: "+model.getLanguages());

        //Glide.with(holder.flag.getContext()).asBitmap().load(model.getFlag()).placeholder(R.drawable.ic_baseline_image_not_supported_24).fitCenter().into(holder.flag);
        Utils.fetchSvg(context,model.getFlag(), holder.flag);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView flag;
        TextView name,capital,region,subregion,population,borders,languages;

        public MyHolder(View itemView) {
            super(itemView);
            flag= itemView.findViewById(R.id.flag);
            name = itemView.findViewById(R.id.name);
            capital = itemView.findViewById(R.id.captial);
            region = itemView.findViewById(R.id.region);
            subregion = itemView.findViewById(R.id.subregion);
            population = itemView.findViewById(R.id.population);
            borders = itemView.findViewById(R.id.borders);
            languages = itemView.findViewById(R.id.languages);

        }
    }

}
