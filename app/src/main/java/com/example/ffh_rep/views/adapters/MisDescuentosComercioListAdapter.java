package com.example.ffh_rep.views.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.viewmodels.commerce.MisDescuentosComercioViewModel;

import java.util.List;

public class MisDescuentosComercioListAdapter extends BaseAdapter {
    private List<Beneficio> lBeneficios;
    private LayoutInflater  inflater;
    private MisDescuentosComercioViewModel mViewModel;

    public MisDescuentosComercioListAdapter(List<Beneficio> lista, Context ctx, MisDescuentosComercioViewModel viewModel){
        this.lBeneficios = lista;
        this.inflater = LayoutInflater.from(ctx);
        this.mViewModel = viewModel;
    }
    @Override
    public int getCount() {
        return this.lBeneficios.size();
    }

    public void setData(List<Beneficio> ldata){
        this.lBeneficios = ldata;
        notifyDataSetChanged();
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
        MisDescuentosComercioListAdapter.ViewHolder vh;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_comercio_beneficio, null);

            vh = new MisDescuentosComercioListAdapter.ViewHolder();
            vh.id = convertView.findViewById(R.id.tv_item_descuento_id);
            vh.descripcion = convertView.findViewById(R.id.tv_item_descuento_descripcion);
            vh.puntos = convertView.findViewById(R.id.tv_item_descuento_puntos);
            vh.btnEliminarDescuento = convertView.findViewById(R.id.btnEliminarDescuento);
            vh.btnModificarDescuento = convertView.findViewById((R.id.btnModificarDescuento));

            convertView.setTag(vh);
        }
        else{
            vh = (MisDescuentosComercioListAdapter.ViewHolder) convertView.getTag();
        }

        Beneficio a = this.lBeneficios.get(position);

        vh.id.setText(String.valueOf(a.getId_beneficio()));
        vh.descripcion.setText(a.getDescripcion());
        vh.puntos.setText(String.valueOf(a.getPuntos_requeridos()));

        vh.btnEliminarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Eliminar beneficio");
                builder.setMessage("¿Estás seguro de que deseas eliminar este beneficio? Esta acción no se puede deshacer.");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBeneficio(position);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        vh.btnModificarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("idBeneficioSelect", a);
                Navigation.findNavController(v).navigate(R.id.action_fragmentAgregarDescuentoComercio_to_modificarDescuento, args);
            }
        });

        return convertView;
    }

    public void deleteBeneficio(int position) {
        if (position >= 0 && position < lBeneficios.size()) {
            mViewModel.eliminarBeneficio(lBeneficios.get(position));
            lBeneficios.remove(position);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder{
        TextView id, descripcion, puntos;
        Button btnEliminarDescuento, btnModificarDescuento;
    }
}