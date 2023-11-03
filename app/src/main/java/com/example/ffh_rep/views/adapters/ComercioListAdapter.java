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
import com.example.ffh_rep.entidades.Comercio;

import java.util.List;

public class ComercioListAdapter extends BaseAdapter {
    private List<Comercio> comercioList;
    private LayoutInflater inflater;

    public ComercioListAdapter(Context context, List<Comercio> comercioList) {
        this.comercioList = comercioList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setComercioList(List<Comercio> comercioList) {
        this.comercioList = comercioList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comercioList.size();
    }

    @Override
    public Object getItem(int position) {
        return comercioList.get(position);
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);

            // Inicializar el ViewHolder
            holder = new ViewHolder();
            // TODO: Asignar vistas a los elementos del ViewHolder
            // holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener datos del modelo
        Comercio comercio = comercioList.get(position);

        // Configurar las vistas con datos del modelo
        // TODO: Asignar datos a las vistas del ViewHolder
        // holder.imageView.setImageResource(comercio.getImageResource());
        holder.textView.setText(comercio.getRazonSocial());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("comercioSelect", comercio);
                Navigation.findNavController(v).navigate(R.id.action_hunter_Home_to_hunter_VerComercio, args);
            }
        });
        return convertView;
    }

    // ViewHolder para almacenar las referencias de las vistas
    static class ViewHolder {
        //ImageView imageView;
        TextView textView;
    }
}
