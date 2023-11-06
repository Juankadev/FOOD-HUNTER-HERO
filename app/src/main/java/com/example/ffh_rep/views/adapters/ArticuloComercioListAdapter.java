package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Stock;

import java.util.ArrayList;
import java.util.List;

public class ArticuloComercioListAdapter extends BaseAdapter {

    private List<Stock> lStocks;
    private LayoutInflater  inflater;

    public ArticuloComercioListAdapter(List<Stock> lStocks, Context ctx){
        this.lStocks = lStocks;
        this.inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return this.lStocks.size();
    }


    public void setData(List<Stock> ldata){
        this.lStocks = ldata;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return this.lStocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lStocks.get(position).getId_stock();
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
            vh.precio = convertView.findViewById(R.id.tv_item_articulo_precio);
            vh.categoria = convertView.findViewById(R.id.tv_item_articulo_categoria);
            vh.imageArticle = convertView.findViewById(R.id.image_article_contain);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }
        Stock s = this.lStocks.get(position);
        vh.nombre.setText(s.getId_articulo().getDescripcion());
        vh.marca.setText(s.getId_articulo().getMarca().getDescripcion());
        vh.precio.setText(String.valueOf(s.getId_articulo().getPrecio()));
        vh.fecha.setText(s.getFecha_vencimiento().toString());
        vh.categoria.setText(s.getId_articulo().getCategoria().getDescripcion());
        if(s.getId_articulo().getImagen() != null && s.getId_articulo().getImagen() != ""){
            Glide.with(convertView).load(s.getId_articulo().getImagen()).into(vh.imageArticle);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("stockSelected", s);
                Navigation.findNavController(v).navigate(R.id.action_hunter_VerComercio_to_hunter_VerArticulo, args);
            }
        });
        return convertView;
    }


    static class ViewHolder{
        TextView nombre, fecha, marca, categoria, precio;
        ImageView imageArticle;

    }
}
