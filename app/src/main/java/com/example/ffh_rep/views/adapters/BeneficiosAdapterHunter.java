package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.interfaces.CanjearBeneficiosCallback;

import java.util.List;

public class BeneficiosAdapterHunter extends BaseAdapter {

    private LayoutInflater inflater;
    private boolean isLoading;
    private int loadingPosition = -1;

    private List<Beneficio> lBeneficios;
    private CanjearBeneficiosCallback cbCallback;

    public BeneficiosAdapterHunter(Context ctx, List<Beneficio> lista, CanjearBeneficiosCallback callback){
        this.lBeneficios = lista;
        this.inflater = LayoutInflater.from(ctx);
        this.cbCallback = callback;
        this.isLoading = false;
    }


    public void setlBeneficios(List<Beneficio> lBeneficios) {
        this.lBeneficios = lBeneficios;
        notifyDataSetChanged();
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_hunter_lista_beneficio, null);
            vh = new BeneficiosAdapterHunter.ViewHolder();
            vh.nombre = convertView.findViewById(R.id.tv_nombre_beneficio);
            vh.puntos = convertView.findViewById(R.id.tv_puntos_beneficio);
            vh.btn = convertView.findViewById(R.id.btn_canjear_bene);
            vh.pbCanjear = convertView.findViewById(R.id.pbCanjeandoBeneficio);
            convertView.setTag(vh);
        } else {
            vh = (BeneficiosAdapterHunter.ViewHolder) convertView.getTag();
        }

        Beneficio bene = this.lBeneficios.get(position);

        vh.btn.setEnabled(true);
        vh.nombre.setVisibility(View.VISIBLE);
        vh.puntos.setVisibility(View.VISIBLE);

        vh.nombre.setText(bene.getDescripcion());
        vh.puntos.setText(bene.getPuntos_requeridos().toString() + " Puntos");
        vh.pbCanjear.setVisibility(View.GONE);

        if (position == loadingPosition && isLoading) {
            vh.btn.setEnabled(false);
            vh.nombre.setVisibility(View.GONE);
            vh.puntos.setVisibility(View.GONE);
            vh.pbCanjear.setVisibility(View.VISIBLE);
        }

        vh.btn.setOnClickListener(v -> {
            loadingPosition = position;
            notifyDataSetChanged();
            cbCallback.onCanjearBeneficio(bene);
        });

        return convertView;
    }

    static class ViewHolder{
        TextView nombre;
        TextView puntos;
        Button btn;
        ProgressBar pbCanjear;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
