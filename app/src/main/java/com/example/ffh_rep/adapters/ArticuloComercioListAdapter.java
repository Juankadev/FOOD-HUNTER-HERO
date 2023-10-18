package com.example.ffh_rep.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;

import java.util.ArrayList;
import java.util.List;

public class ArticuloComercioListAdapter extends BaseAdapter {

    private List<Articulo> lArticulos;
    private LayoutInflater  inflater;

    public ArticuloComercioListAdapter(ArrayList<Articulo> lista, Context ctx){
        this.lArticulos = lista;
        this.inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return 0;
    }


    public void setData(List<Articulo> ldata){
        this.lArticulos = ldata;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if(convertView != null){
            convertView = inflater.inflate(R.layout.item_hunter__comercios_articulo_item, null);
            vh = new ViewHolder();
            vh.nombre = convertView.findViewById(R.id.tv_item_articulo_nombre);
            vh.fecha = convertView.findViewById(R.id.tv_item_articulo_fecha_vencimiento);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }

        Articulo a = lArticulos.get(position);
        vh.nombre.setText(a.getDescripcion());
        vh.fecha.setText(a.getEstado());

        return convertView;
    }


    static class ViewHolder{
        TextView nombre, fecha;
    }
}
