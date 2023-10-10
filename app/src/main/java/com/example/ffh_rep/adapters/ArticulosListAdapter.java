package com.example.ffh_rep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.repositories.ArticuloRepository;

import org.w3c.dom.Text;

import java.util.List;

public class ArticulosListAdapter extends BaseAdapter {

    private List<Articulo> lArticulos;
    private LayoutInflater inflater;

    public ArticulosListAdapter(Context contxt){
        this.inflater = LayoutInflater.from(contxt);
    }

    public ArticulosListAdapter(List<Articulo> lista, Context contxt){
        this.inflater = LayoutInflater.from(contxt);
        this.lArticulos = lista;
    }

    public List<Articulo> getlArticulos() {
        return lArticulos;
    }

    public void setlArticulos(List<Articulo> lArticulos) {
        this.lArticulos = lArticulos;

    }

    @Override
    public int getCount() {
        return lArticulos.size();
    }

    @Override
    public Object getItem(int position) {
        return lArticulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_producto_home, null);
            vh = new ViewHolder();
            vh.descripcion = convertView.findViewById(R.id.txt_descripcion_producto);
            vh.precio = convertView.findViewById(R.id.txt_precio_producto);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        Articulo a = lArticulos.get(position);
        vh.precio.setText(String.valueOf(a.getPrecio()));
        vh.descripcion.setText(a.getDescripcion());

        return convertView;
    }


    static class ViewHolder{
        TextView descripcion, precio;
    }
}
