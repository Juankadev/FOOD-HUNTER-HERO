package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.admin.AdminAprobarComerciosViewModel;

import java.util.List;

public class AprobarComerciosViewAdapter extends BaseAdapter {
    private List<Comercio> lComercios;
    private AdminAprobarComerciosViewModel viewModel;
    private LayoutInflater  inflater;
    public AprobarComerciosViewAdapter(List<Comercio> items, AdminAprobarComerciosViewModel viewModel, Context ctx) {
        this.lComercios = items;
        this.inflater = LayoutInflater.from(ctx);
        this.viewModel = viewModel;
    }

    @Override
    public int getCount() {
        return this.lComercios.size();
    }

    public void setData(List<Comercio> ldata){
        this.lComercios = ldata;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return this.lComercios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lComercios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AprobarComerciosViewAdapter.ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_aprobar_comercios_item, null);
            vh = new AprobarComerciosViewAdapter.ViewHolder();
            vh.nombre = convertView.findViewById(R.id.razonSocial);
            vh.btnRechazar = convertView.findViewById(R.id.btnRechazar);
            vh.btnAprobar = convertView.findViewById(R.id.btnAprobar);
            convertView.setTag(vh);
        } else {
            vh = (AprobarComerciosViewAdapter.ViewHolder) convertView.getTag();
        }

        Comercio c = this.lComercios.get(position);
        vh.nombre.setText(c.getRazonSocial());

        vh.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechazarComercio(position);
            }
        });

        vh.btnAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update comercio en columna aprobado
            }
        });

        return convertView;
    }

    public void rechazarComercio(int position) {
        if (position >= 0 && position < lComercios.size()) {
            viewModel.rechazarComercio(lComercios.get(position));
            lComercios.remove(position);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder{
        TextView nombre;
        ImageButton btnRechazar;
        ImageButton btnAprobar;
    }
}