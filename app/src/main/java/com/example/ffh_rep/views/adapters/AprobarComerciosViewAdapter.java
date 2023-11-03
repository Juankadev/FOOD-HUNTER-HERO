package com.example.ffh_rep.views.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.views.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AprobarComerciosViewAdapter extends BaseAdapter {

    private List<Comercio> lComercios;
    private LayoutInflater  inflater;

    public AprobarComerciosViewAdapter(List<Comercio> items, Context ctx) {
        this.lComercios = items;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.lComercios.size();
    }

    public void setData(List<Comercio> ldata){
        this.lComercios = ldata;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return this.lComercios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lComercios.get(position).getId();
    }

/*
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentAprobarComerciosItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }*/
/*
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AprobarComerciosViewAdapter.ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.fragment_aprobar_comercios_item, null);
            vh = new AprobarComerciosViewAdapter.ViewHolder();
            vh.nombre = convertView.findViewById(R.id.razonSocial);
            convertView.setTag(vh);
        }
        else{
            vh = (AprobarComerciosViewAdapter.ViewHolder) convertView.getTag();
        }

        Comercio c = this.lComercios.get(position);
        vh.nombre.setText(c.getRazonSocial());

        return convertView;
    }

    static class ViewHolder{
        TextView nombre;

    }
}