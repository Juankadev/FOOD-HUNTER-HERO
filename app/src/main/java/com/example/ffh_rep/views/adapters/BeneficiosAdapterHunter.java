package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;

import java.util.List;

public class BeneficiosAdapterHunter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Beneficio> lBeneficios;

    public BeneficiosAdapterHunter(Context ctx, List<Beneficio> lista){
        this.lBeneficios = lista;
        this.inflater = LayoutInflater.from(ctx);
    }


    public void setlBeneficios(List<Beneficio> lBeneficios) {
        this.lBeneficios = lBeneficios;
    }

    @Override
    public int getCount() {
        return this.lBeneficios.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lBeneficios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lBeneficios.get(position).getId_beneficio();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BeneficiosAdapterHunter.ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_hunter_lista_beneficio, null);
            vh = new BeneficiosAdapterHunter.ViewHolder();
            vh.nombre = convertView.findViewById(R.id.tv_nombre_beneficio);
            vh.btn = convertView.findViewById(R.id.btn_canjear_bene);
            convertView.setTag(vh);
        }
        else{
            vh = (BeneficiosAdapterHunter.ViewHolder) convertView.getTag();
        }

        Beneficio bene = this.lBeneficios.get(position);
        vh.nombre.setText(bene.getDescripcion());

        return convertView;
    }

    static class ViewHolder{
        TextView nombre;
        Button btn;
    }
}
