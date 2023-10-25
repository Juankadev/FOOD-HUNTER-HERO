package com.example.ffh_rep.adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;

import java.util.ArrayList;
import java.util.List;

public class ArticuloComercioListAdapter extends BaseAdapter {

    private List<Articulo> lArticulos;
    private LayoutInflater  inflater;

    public ArticuloComercioListAdapter(List<Articulo> lista, Context ctx){
        this.lArticulos = lista;
        this.inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return this.lArticulos.size();
    }


    public void setData(List<Articulo> ldata){
        this.lArticulos = ldata;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return this.lArticulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lArticulos.get(position).getIdArticulo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_hunter__comercios_articulo_item, null);
            vh = new ViewHolder();
            vh.nombre = convertView.findViewById(R.id.tv_item_articulo_nombre);
            vh.fecha = convertView.findViewById(R.id.tv_item_articulo_fecha_vencimiento);
            vh.marca = convertView.findViewById(R.id.tv_item_articulo_marca);
            vh.categoria = convertView.findViewById(R.id.tv_item_articulo_categoria);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }
        Articulo a = this.lArticulos.get(position);
        vh.nombre.setText(a.getDescripcion());
        vh.marca.setText(a.getMarca().getDescripcion());
        vh.categoria.setText(a.getCategoria().getDescripcion());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("articuloSelected", a);
                Navigation.findNavController(v).navigate(R.id.action_hunter_VerComercio_to_hunter_VerArticulo, args);
            }
        });
        return convertView;
    }


    static class ViewHolder{
        TextView nombre, fecha, marca, categoria;
    }
}
