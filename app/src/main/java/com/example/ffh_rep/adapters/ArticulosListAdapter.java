package com.example.ffh_rep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.repositories.ArticuloRepository;

import org.w3c.dom.Text;

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
            // holder.imageView = convertView.findViewById(R.id.imageView);
            holder.descripcion = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            holder = (ArticulosListAdapter.ViewHolder) convertView.getTag();
        }

        // Obtener datos del modelo
        Articulo articulo = articulosList.get(position);

        // Configurar las vistas con datos del modelo
        // TODO: Asignar datos a las vistas del ViewHolder
        // holder.imageView.setImageResource(comercio.getImageResource());
        holder.descripcion.setText(articulo.getDescripcion());

        return convertView;
    }


    static class ViewHolder{
        TextView descripcion, precio;
    }
}
