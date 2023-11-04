
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
import com.example.ffh_rep.entidades.Stock;

import java.io.Serializable;
import java.util.List;

public class StocksArticuloComercioListAdapter extends BaseAdapter {

    private List<Stock> lStocks;
    private LayoutInflater inflater;

    public StocksArticuloComercioListAdapter(List<Stock> lista, Context ctx){
        this.lStocks = lista;
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
            convertView = inflater.inflate(R.layout.item_comercio__stock_articulo_item, null);
            vh = new ViewHolder();
            vh.fecha_vencimiento = convertView.findViewById(R.id.tv_item_articulo_stock_fecha_vencimiento);
            vh.cantidad = convertView.findViewById(R.id.tv_item_articulo_stock_cantidad);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }
        Stock s = this.lStocks.get(position);
        vh.fecha_vencimiento.setText((CharSequence) s.getFecha_vencimiento());
        vh.cantidad.setText(s.getCantidad());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("stockSelected", s);
                Navigation.findNavController(v).navigate(R.id.action_comercio_Ver_Articulo_to_comercio_Ver_Stock_x_Articulo, args);
            }
        });
        return convertView;
    }


    static class ViewHolder{
        TextView fecha_vencimiento, cantidad;

    }
}
