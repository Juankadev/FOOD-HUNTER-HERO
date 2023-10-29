package com.example.ffh_rep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;

import java.util.List;

public class MisDescuentosComercioListAdapter extends BaseAdapter {

    private List<Beneficio> beneficioList;
    private LayoutInflater inflater;

    public MisDescuentosComercioListAdapter(Context context, List<Beneficio> beneficioList) {
        this.beneficioList = beneficioList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setBeneficiosList(List<Beneficio> beneficioList) {
        this.beneficioList = beneficioList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beneficioList.size();
    }

    @Override
    public Object getItem(int position) {
        return beneficioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Devuelve la posición como ID por simplicidad
    }

    /**
     * Devuelve la vista que representa un elemento en el GridView.
     *
     * @param position    La posición del elemento en la lista de datos.
     * @param convertView La vista reciclada para reutilizar, si está disponible.
     * @param parent      El ViewGroup al que pertenece la vista.
     * @return La vista que representa un elemento en el GridView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MisDescuentosComercioListAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);

            // Inicializar el ViewHolder
            holder = new MisDescuentosComercioListAdapter.ViewHolder();
            // TODO: Asignar vistas a los elementos del ViewHolder
            // holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            holder = (MisDescuentosComercioListAdapter.ViewHolder) convertView.getTag();
        }

        // Obtener datos del modelo
        Beneficio beneficio = beneficioList.get(position);

        // Configurar las vistas con datos del modelo
        // TODO: Asignar datos a las vistas del ViewHolder
        holder.textView.setText(beneficio.getId_beneficio());
        holder.textView.setText(beneficio.getDescripcion());
        holder.textView.setText(beneficio.getPuntos_requeridos());

        return convertView;
    }

    // ViewHolder para almacenar las referencias de las vistas
    private static class ViewHolder {
        //ImageView imageView;
        TextView textView;
    }
}