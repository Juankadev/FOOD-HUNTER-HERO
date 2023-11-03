package com.example.ffh_rep.views.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffh_rep.views.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.ffh_rep.databinding.FragmentArticulosItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ArticulosViewAdapter extends RecyclerView.Adapter<ArticulosViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public ArticulosViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentArticulosItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        /*holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final ImageView imgView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentArticulosItemBinding binding) {
            super(binding.getRoot());
            name = binding.etItemDescription;
            imgView = binding.imgItem;
        }
    }
}