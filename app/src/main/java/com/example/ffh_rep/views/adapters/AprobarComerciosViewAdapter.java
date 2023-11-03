package com.example.ffh_rep.views.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.views.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

public class AprobarComerciosViewAdapter extends BaseAdapter {
    private List<Comercio> lComercios;
    private LayoutInflater  inflater;
    public AprobarComerciosViewAdapter(List<Comercio> items, Context ctx) {
        this.lComercios = items;
        this.inflater = LayoutInflater.from(ctx);
    }
    private int selectedPosition = -1; // Inicialmente no hay elementos seleccionados
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
    public int getSelectedPosition() {
        return selectedPosition;
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
                deleteComercio(position);
            }
        });

        vh.btnAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update comercio en columna aprobado
            }
        });

        if (position == selectedPosition) {
            convertView.setBackgroundColor(Color.BLUE);
        }
        return convertView;
    }

    public void deleteComercio(int position) {
        if (position >= 0 && position < lComercios.size()) {
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