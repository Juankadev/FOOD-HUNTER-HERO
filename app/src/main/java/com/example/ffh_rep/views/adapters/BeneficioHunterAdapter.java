package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;

import java.util.List;

public class BeneficioHunterAdapter extends BaseAdapter {

    private List<Beneficio> lista;
    private LayoutInflater layoutInflater;

    public BeneficioHunterAdapter(Context ctx, List<Beneficio> lista){
        this.lista = lista;
        this.layoutInflater = LayoutInflater.from(ctx);
    }

    public void setLista(List<Beneficio> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.lista.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lista.get(position).getId_beneficio();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BeneficioHunterAdapter.ViewHolder vh;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_hunter_beneficios_list, null);

            vh = new BeneficioHunterAdapter.ViewHolder();
            vh.nombreBeneficio = convertView.findViewById(R.id.txtNombreBeneficio);
            vh.nombrecomercio = convertView.findViewById(R.id.txtNombreComercioBeneficio);

            convertView.setTag(vh);
        }
        else{
            vh = (BeneficioHunterAdapter.ViewHolder) convertView.getTag();
        }

        Beneficio a = this.lista.get(position);
        vh.nombreBeneficio.setText(a.getDescripcion());
        vh.nombrecomercio.setText(a.getId_comercio().getRazonSocial());

        return convertView;
    }


    static class ViewHolder{
        TextView nombreBeneficio, nombrecomercio;
    }
}
