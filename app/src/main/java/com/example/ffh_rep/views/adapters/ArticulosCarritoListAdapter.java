package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.ItemCarrito;

import java.util.List;

public class ArticulosCarritoListAdapter extends BaseAdapter {

    private List<ItemCarrito> lArticulos;
    private LayoutInflater inflater;

    public ArticulosCarritoListAdapter(Context contxt){
        this.inflater = LayoutInflater.from(contxt);
    }

    public ArticulosCarritoListAdapter(List<ItemCarrito> lista, Context contxt){
        this.inflater = LayoutInflater.from(contxt);
        this.lArticulos = lista;
    }

    public List<ItemCarrito> getlArticulos() {
        return lArticulos;
    }

    public void setlArticulos(List<ItemCarrito> lArticulos) {
        this.lArticulos = lArticulos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lArticulos.size();
    }

    @Override
    public Object getItem(int position) {
        return lArticulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_articulos_carrito, null);
            vh = new ViewHolder();
            vh.nombre = convertView.findViewById(R.id.tv_nombre_articulo_carrito);
            vh.cantidad = convertView.findViewById(R.id.tv_cantidad_articulo_carrito);
            vh.trashBtn = convertView.findViewById(R.id.imageview_trash);
            vh.ivItem = convertView.findViewById(R.id.iv_item_list);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        ItemCarrito a = lArticulos.get(position);
        vh.nombre.setText(a.getArtc().getDescripcion());
        vh.cantidad.setText("x"+ a.getCantidad());
        Glide.with(convertView).load(a.getArtc().getImagen()).into(vh.ivItem);
        return convertView;
    }

    public void listenerActions(){

    }


    static class ViewHolder{
        TextView nombre, cantidad;
        ImageView trashBtn, ivItem;
    }
}
