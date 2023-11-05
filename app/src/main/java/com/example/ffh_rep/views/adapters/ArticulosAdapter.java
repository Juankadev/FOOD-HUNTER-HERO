package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentArticulosItemBinding;
import com.example.ffh_rep.entidades.Articulo;

import java.util.List;

public class ArticulosAdapter extends RecyclerView.Adapter<ArticulosAdapter.ViewHolder> {

    private List<Articulo> mValues;
    private Context ctx;
    private NavController navCon;

    public ArticulosAdapter(List<Articulo> items, Context ctx, NavController con) {
        mValues = items;
        this.ctx = ctx;
        this.navCon = con;
    }

    public ArticulosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticulosAdapter.ViewHolder(FragmentArticulosItemBinding.inflate(LayoutInflater.from(this.ctx), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.itemDescription.setText(mValues.get(position).getDescripcion());
        if(mValues.get(position).getImagen() != null && mValues.get(position).getImagen() != ""){
            Glide.with(holder.imgItem).load(mValues.get(position).getImagen()).into(holder.imgItem);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("comercioSelect", mValues.get(position).getComercio());
                navCon.navigate(R.id.action_articulos_to_hunter_VerComercio, args);
            }
        });
    }

    public void setData(List<Articulo> newData) {
        mValues = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView itemDescription;
        public final ImageView imgItem;
        public Articulo mItem;

        public ViewHolder(FragmentArticulosItemBinding binding) {
            super(binding.getRoot());
            itemDescription = binding.etItemDescription;
            imgItem = binding.imgItem;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + itemDescription.getText() + "'";
        }
    }
}