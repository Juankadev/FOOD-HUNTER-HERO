package com.example.ffh_rep.views.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
import java.text.SimpleDateFormat;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Caza;
import com.example.ffh_rep.viewmodels.commerce.MisComprasViewModel;
import com.example.ffh_rep.views.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MisComprasViewAdapter extends BaseAdapter {

    private List<Caza> lCazas;
    private MisComprasViewModel viewModel;
    private LayoutInflater  inflater;
    /*
    public MisComprasViewAdapter(List<Caza> items) {
        lCazas = items;
    }
    public MisComprasViewAdapter(Context contxt){
        this.inflater = LayoutInflater.from(contxt);
    }
    public MisComprasViewAdapter(List<Caza> lista, Context contxt){
        this.inflater = LayoutInflater.from(contxt);
        this.lCazas = lista;
    }
     */
    public MisComprasViewAdapter(List<Caza> items, MisComprasViewModel viewModel, Context ctx) {
        this.lCazas = items;
        this.inflater = LayoutInflater.from(ctx);
        this.viewModel = viewModel;
    }

    @Override
    public int getCount() {
        return this.lCazas.size();
    }

    public void setData(List<Caza> ldata){
        this.lCazas = ldata;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return this.lCazas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lCazas.get(position).getIdCaza();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MisComprasViewAdapter.ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_mis_compras_item, null);
            vh = new MisComprasViewAdapter.ViewHolder();
            vh.RazonSocial = convertView.findViewById(R.id.razonSocial);
            vh.Fecha = convertView.findViewById(R.id.tvFecha);
            vh.Puntos = convertView.findViewById(R.id.tvPuntos);
            vh.Cantidad = convertView.findViewById(R.id.tvCantidad);
            convertView.setTag(vh);
        } else {
            vh = (MisComprasViewAdapter.ViewHolder) convertView.getTag();
        }

        Caza c = this.lCazas.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c.getFecha());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        vh.Fecha.setText(day + "-" + (month + 1) + "-" + year);
        vh.RazonSocial.setText(c.getComercio().getRazonSocial());
        vh.Puntos.setText("Sumaste " + String.valueOf(c.getPuntos()) +" Puntos");

        if (c.getCantidad() > 1) {
            vh.Cantidad.setText(String.valueOf(c.getCantidad()) + " Productos");
        } else {
            vh.Cantidad.setText(String.valueOf(c.getCantidad()) + " Producto");
        }


        return convertView;
    }

    static class ViewHolder{
        TextView RazonSocial, Puntos, Fecha, Cantidad;
    }

}