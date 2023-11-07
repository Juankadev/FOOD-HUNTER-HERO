package com.example.ffh_rep.views.adapters;

import android.annotation.SuppressLint;
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
import com.example.ffh_rep.interfaces.CarritoActionsCallback;

import java.util.List;

public class ArticulosCarritoListAdapter extends BaseAdapter {

    private List<ItemCarrito> lArticulos;
    private LayoutInflater inflater;
    private CarritoActionsCallback cuCallback;


    public ArticulosCarritoListAdapter(Context contxt){
        this.inflater = LayoutInflater.from(contxt);
    }
    public ArticulosCarritoListAdapter(List<ItemCarrito> lista, Context contxt){
        this.inflater = LayoutInflater.from(contxt);
        this.lArticulos = lista;
    }
    public ArticulosCarritoListAdapter(List<ItemCarrito> lista, Context contxt, CarritoActionsCallback callback){
        this.inflater = LayoutInflater.from(contxt);
        this.lArticulos = lista;
        this.cuCallback = callback;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_articulos_carrito, null);
            vh = new ViewHolder();
            vh.nombre = convertView.findViewById(R.id.tv_nombre_articulo_carrito);
            vh.cantidad = convertView.findViewById(R.id.tv_cantidad_articulo_carrito);
            vh.trashBtn = convertView.findViewById(R.id.imageview_trash);
            vh.btnMax = convertView.findViewById(R.id.txtSum);
            vh.btnMinus = convertView.findViewById(R.id.txtMinus);
            vh.ivItem = convertView.findViewById(R.id.iv_item_list);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        ItemCarrito a = lArticulos.get(position);
        vh.nombre.setText(a.getArtc().getId_articulo().getDescripcion());
        vh.cantidad.setText("x"+ a.getCantidad());
        if(cuCallback != null){
            vh.btnMinus.setOnClickListener(v -> { this.cuCallback.onAbsArticuloCallback(a);});
            vh.btnMax.setOnClickListener(v -> {this.cuCallback.onSumArticuloCallback(a);});
            vh.trashBtn.setOnClickListener(v -> {this.cuCallback.onDeleteArticuloCallback(a);});
        }
        else{
            vh.trashBtn.setVisibility(View.GONE);
            vh.btnMax.setVisibility(View.GONE);
            vh.btnMinus.setVisibility(View.GONE);
        }

        Glide.with(convertView).load(a.getArtc().getId_articulo().getImagen()).into(vh.ivItem);
        return convertView;
    }

    public void listenerActions(){

    }



    static class ViewHolder{
        TextView nombre, cantidad, btnMinus, btnMax;
        ImageView trashBtn, ivItem;
    }
}
