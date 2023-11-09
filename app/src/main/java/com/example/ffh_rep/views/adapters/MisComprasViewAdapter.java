package com.example.ffh_rep.views.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentMisComprasItemBinding;
import com.example.ffh_rep.entidades.Caza;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.commerce.MisComprasViewModel;
import com.example.ffh_rep.views.placeholder.PlaceholderContent.PlaceholderItem;

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
            convertView.setTag(vh);
        } else {
            vh = (MisComprasViewAdapter.ViewHolder) convertView.getTag();
        }

        Caza c = this.lCazas.get(position);

        vh.RazonSocial.setText(c.getComercio().getRazonSocial());

        System.out.println(c.getFecha());
        // Crea un formato de fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Convierte la fecha a String
        String dateString = dateFormat.format(c.getFecha());
        vh.Fecha.setText(dateString);

        vh.Puntos.setText(String.valueOf( c.getPuntos() ));

        return convertView;
    }

    static class ViewHolder{
        TextView RazonSocial, Puntos, Fecha;
    }

}