package com.example.ffh_rep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;

import java.util.List;

public class MisDescuentosComercioListAdapter extends BaseAdapter {
    private List<Beneficio> lBeneficios;
    private LayoutInflater  inflater;

    public MisDescuentosComercioListAdapter(List<Beneficio> lista, Context ctx){
        this.lBeneficios = lista;
        this.inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return this.lBeneficios.size();
    }


    public void setData(List<Beneficio> ldata){
        this.lBeneficios = ldata;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return this.lBeneficios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lBeneficios.get(position).getId_beneficio();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MisDescuentosComercioListAdapter.ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_comercio_beneficio, null);
            vh = new MisDescuentosComercioListAdapter.ViewHolder();
            vh.id = convertView.findViewById(R.id.tv_item_descuento_id);
            vh.descripcion = convertView.findViewById(R.id.tv_item_descuento_descripcion);
            vh.puntos = convertView.findViewById(R.id.tv_item_descuento_puntos);
            convertView.setTag(vh);
        }
        else{
            vh = (MisDescuentosComercioListAdapter.ViewHolder) convertView.getTag();
        }

        Beneficio a = this.lBeneficios.get(position);
        vh.id.setText(a.getId_beneficio());
        vh.descripcion.setText(a.getDescripcion());
        vh.puntos.setText(a.getPuntos_requeridos());

        return convertView;
    }

    static class ViewHolder{ TextView id, descripcion, puntos;}
}