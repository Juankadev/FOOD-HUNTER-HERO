package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.DialogInterface;
import android.app.AlertDialog;

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
            vh.razonSocial = convertView.findViewById(R.id.razonSocial);
            vh.cuit = convertView.findViewById(R.id.cuit);
            vh.direccion = convertView.findViewById(R.id.direccion);
            vh.rubro = convertView.findViewById(R.id.rubro);
            vh.btnRechazar = convertView.findViewById(R.id.btnRechazar);
            vh.btnAprobar = convertView.findViewById(R.id.btnAprobar);
            convertView.setTag(vh);
        } else {
            vh = (AprobarComerciosViewAdapter.ViewHolder) convertView.getTag();
        }

        Comercio c = this.lComercios.get(position);
        vh.razonSocial.setText(c.getRazonSocial());
        vh.cuit.setText(c.getCuit());
        vh.direccion.setText(c.getDireccion());
        vh.rubro.setText(c.getRubro());

        vh.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Rechazar Comercio");
                builder.setMessage("¿Estás seguro que deseas rechazar este comercio? Esta acción no se puede deshacer.");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rechazarComercio(position);
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

        vh.btnAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Aprobar Comercio");
                builder.setMessage("¿Estás seguro que deseas aprobar este comercio? Esta acción no se puede deshacer.");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aprobarComercio(position);
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

        return convertView;
    }

    public void rechazarComercio(int position) {
        if (position >= 0 && position < lComercios.size()) {
            viewModel.rechazarComercio(lComercios.get(position));
            lComercios.remove(position);
            notifyDataSetChanged();
        }
    }

    public void aprobarComercio(int position) {
        if (position >= 0 && position < lComercios.size()) {
            viewModel.aprobarComercio(lComercios.get(position));
            lComercios.remove(position);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder{
        TextView razonSocial, cuit, rubro, direccion;
        ImageButton btnRechazar;
        ImageButton btnAprobar;
    }
}