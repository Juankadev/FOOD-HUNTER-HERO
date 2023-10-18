package com.example.ffh_rep.adapters;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.ffh_rep.databinding.FragmentComerciosItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ComerciosAdapter extends RecyclerView.Adapter<ComerciosAdapter.ViewHolder> {

    private List<Comercio> mValues;
    private Context ctx;
    private NavController navCon;

    public ComerciosAdapter(List<Comercio> items, Context ctx, NavController con) {
        mValues = items;
        this.ctx = ctx;
        this.navCon = con;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentComerciosItemBinding.inflate(LayoutInflater.from(this.ctx), parent, false));

    }

    public void setData(List<Comercio> newData) {
        mValues = newData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.shopName.setText(mValues.get(position).getRazonSocial());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("comercioSelect", mValues.get(position));
                navCon.navigate(R.id.action_comercios_to_hunter_VerComercio, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView shopName;
        public Comercio mItem;

        public ViewHolder(FragmentComerciosItemBinding binding) {
            super(binding.getRoot());
            shopName = binding.etShopName;
        }


        @Override
        public String toString() {
            return super.toString() + " '" + shopName.getText() + "'";
        }
    }


}