package com.example.ffh_rep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;

import java.util.List;

public class ComercioListAdapter extends BaseAdapter {

    private List<Comercio> comercioList;
    private LayoutInflater inflater;

    public ComercioListAdapter(Context context, List<Comercio> lComercio){
        this.comercioList = lComercio;
        this.inflater = LayoutInflater.from(context);
    }

    public List<Comercio> getComercioList() {
        return comercioList;
    }

    public void setComercioList(List<Comercio> comercioList) {
        this.comercioList = comercioList;
        notifyDataSetChanged();
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_comercio_home, null);
            vh = new ViewHolder();
            vh.nombre = convertView.findViewById(R.id.txtNombre);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Comercio comercio = comercioList.get(position);
        vh.nombre.setText(comercio.getRazonSocial());

        return convertView;
    }


    static class ViewHolder{
        TextView nombre;
    }
}
