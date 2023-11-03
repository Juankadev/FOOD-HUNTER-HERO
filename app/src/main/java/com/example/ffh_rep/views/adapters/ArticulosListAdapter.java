package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;
import com.bumptech.glide.Glide;

import java.util.List;

public class ArticulosListAdapter extends BaseAdapter {

    private List<Articulo> articulosList;
    private LayoutInflater inflater;

    public ArticulosListAdapter(Context contxt, List<Articulo> articulosList){
        this.articulosList = articulosList;
        this.inflater = LayoutInflater.from(contxt);
    }

    public void setlArticulos(List<Articulo> lArticulos) {
        this.articulosList = lArticulos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return articulosList.size();
    }

    @Override
    public Object getItem(int position) {
        return articulosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        ArticulosListAdapter.ViewHolder holder;

        if (convertView == null) {
            // TODO: Crear nueva grid_item_layout para el articulo
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);

            // Inicializar el ViewHolder
            holder = new ArticulosListAdapter.ViewHolder();
            // TODO: Asignar vistas a los elementos del ViewHolder
            holder.imageArticle = convertView.findViewById(R.id.imageView);
            holder.descripcion = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            holder = (ArticulosListAdapter.ViewHolder) convertView.getTag();
        }

        // Obtener datos del modelo
        Articulo articulo = articulosList.get(position);

        // Configurar las vistas con datos del modelo
        // TODO: Asignar datos a las vistas del ViewHolder
        if(articulo.getImagen() != null && articulo.getImagen() != ""){
            Glide.with(convertView).load(articulo.getImagen()).into(holder.imageArticle);
        }
        holder.descripcion.setText(articulo.getDescripcion());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("comercioSelect", articulo.getComercio());
                Navigation.findNavController(v).navigate(R.id.action_hunter_Home_to_hunter_VerComercio, args);
            }
        });

        return convertView;
    }


    static class ViewHolder{
        TextView descripcion, precio;
        ImageView imageArticle;
    }
}
