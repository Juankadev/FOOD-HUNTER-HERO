package com.example.ffh_rep.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public ComerciosAdapter(List<Comercio> items) {
        mValues = items;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentComerciosItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public void setData(List<Comercio> newData) {
        mValues = newData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.shopName.setText(mValues.get(position).getRazonSocial());
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