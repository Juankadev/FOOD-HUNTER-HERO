package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;

import java.util.List;

public class ArticuloUsuarioComercioListAdapter extends BaseAdapter {

    private List<Articulo> lArticulos;
    private LayoutInflater  inflater;

    public ArticuloUsuarioComercioListAdapter(List<Articulo> lista, Context ctx){
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
            convertView = inflater.inflate(R.layout.item_comercio__comercios_articulo_item, null);
            vh = new ViewHolder();
            vh.nombre = convertView.findViewById(R.id.tv_item_articulo_nombre);
            vh.fecha = convertView.findViewById(R.id.tv_item_articulo_fecha_vencimiento);
            vh.marca = convertView.findViewById(R.id.tv_item_articulo_marca);
            vh.categoria = convertView.findViewById(R.id.tv_item_articulo_categoria);
            vh.imageArticle = convertView.findViewById(R.id.image_article_contain);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }
        Articulo a = this.lArticulos.get(position);
        vh.nombre.setText(a.getDescripcion());
        vh.marca.setText(a.getMarca().getDescripcion());
        vh.categoria.setText(a.getCategoria().getDescripcion());

        if(a.getImagen() != null && a.getImagen() != ""){
            Glide.with(convertView).load(a.getImagen()).into(vh.imageArticle);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("articuloSelected", a);
                //ME DIRIJO A LA VISTA
                Navigation.findNavController(v).navigate(R.id.action_commerce_MisArticulos_to_comercio_Ver_Articulo, args);
            }
        });
        return convertView;
    }


    static class ViewHolder{
        TextView nombre, fecha, marca, categoria;
        ImageView imageArticle;
    }
}
