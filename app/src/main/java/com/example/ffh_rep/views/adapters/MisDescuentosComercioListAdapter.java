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

    /**
     * Se recibe una lista de beneficios (List<Beneficio>), un contexto (Context), y un ViewMode (MisDescuentosComercioViewModel) como parámetros en el constructor.
     * Estos parámetros se utilizan para inicializar las variables de la clase.
     */
    public MisDescuentosComercioListAdapter(List<Beneficio> lista, Context ctx, MisDescuentosComercioViewModel viewModel){
        this.lBeneficios = lista;
        this.inflater = LayoutInflater.from(ctx);
        this.mViewModel = viewModel;
    }

    /// Devuelve el número de elementos en la lista de beneficios.
    @Override
    public int getCount() {
        return this.lBeneficios.size();
    }

    /**
     * La función setData es un método que se utiliza para actualizar la lista de beneficios con una nueva lista de datos.
     * Después de actualizar la lista de datos, se llama a notifyDataSetChanged() para notificar al adaptador que los datos han cambiado
     * y que la vista debe actualizarse.
     */
    public void setData(List<Beneficio> ldata){
        this.lBeneficios = ldata;
        notifyDataSetChanged();
    }

    // Devuelve el beneficio en la posición especificada.
    @Override
    public Object getItem(int position) {
        return this.lBeneficios.get(position);
    }

    // Devuelve el ID del elemento en la posición especificada.
    @Override
    public long getItemId(int position) {
        return this.lBeneficios.get(position).getId_beneficio();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MisDescuentosComercioListAdapter.ViewHolder vh;

        // Verifica si la vista actual es nula, si es así, infla una nueva vista y guarda referencias a los elementos de
        // la vista en un ViewHolder.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_comercio_beneficio, null);

            vh = new MisDescuentosComercioListAdapter.ViewHolder();
            vh.id = convertView.findViewById(R.id.tv_item_descuento_id);
            vh.descripcion = convertView.findViewById(R.id.tv_item_descuento_descripcion);
            vh.puntos = convertView.findViewById(R.id.tv_item_descuento_puntos);
            vh.btnEliminarDescuento = convertView.findViewById(R.id.btnEliminarDescuento);
            vh.btnModificarDescuento = convertView.findViewById((R.id.btnModificarDescuento));

            convertView.setTag(vh);
        } else {
            // Si la vista no es nula, obtén el ViewHolder desde la etiqueta de la vista.
            vh = (MisDescuentosComercioListAdapter.ViewHolder) convertView.getTag();
        }

        // Obtiene el beneficio en la posición actual.
        Beneficio a = this.lBeneficios.get(position);

        // Configura los datos en los elementos de la vista.
        vh.id.setText(String.valueOf(a.getId_beneficio()));
        vh.descripcion.setText(a.getDescripcion());
        vh.puntos.setText(String.valueOf(a.getPuntos_requeridos()));

        // Configura los listeners de los botones en la vista.
        vh.btnEliminarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Muestra un diálogo de confirmación para eliminar el beneficio.
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Eliminar beneficio");
                builder.setMessage("¿Estás seguro de que deseas eliminar este beneficio? Esta acción no se puede deshacer.");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Llama al método deleteBeneficio para eliminar el beneficio en la posición actual.
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
                // Navega a la pantalla de modificación de beneficios y pasa el beneficio seleccionado como argumento.
                Bundle args = new Bundle();
                args.putSerializable("idBeneficioSelect", a);
                Navigation.findNavController(v).navigate(R.id.action_fragmentAgregarDescuentoComercio_to_modificarDescuento, args);
            }
        });

        return convertView;
    }

    public void deleteBeneficio(int position) {
        // Verifica que la posición esté dentro de los límites de la lista de beneficios.
        if (position >= 0 && position < lBeneficios.size()) {
            // Llama al método eliminarBeneficio del ViewModel para eliminar el beneficio en la posición dada.
            mViewModel.eliminarBeneficio(lBeneficios.get(position));

            // Remueve el beneficio de la lista de beneficios.
            lBeneficios.remove(position);

            // Notifica al adaptador que los datos han cambiado, y la vista asociada debe actualizarse.
            notifyDataSetChanged();
        }
    }

    static class ViewHolder{
        TextView id, descripcion, puntos;
        Button btnEliminarDescuento, btnModificarDescuento;
    }
}