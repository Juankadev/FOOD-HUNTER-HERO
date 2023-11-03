package com.example.ffh_rep.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Resenia;

import java.util.List;

public class ReseniasAdapter extends BaseAdapter {

    private List<Resenia> reseniasList;
    private LayoutInflater inflater;

    public ReseniasAdapter(List<Resenia> reseniasList, Context ctx) {
        this.reseniasList = reseniasList;
        this.inflater = LayoutInflater.from(ctx);
    }

    public List<Resenia> getReseniasList() {
        return reseniasList;
    }

    public void setReseniasList(List<Resenia> reseniasList) {
        this.reseniasList = reseniasList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.reseniasList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.reseniasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.reseniasList.get(position).getId_resena();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_hunter__resenias_comercio_resenia, null);
            vh = new ReseniasAdapter.ViewHolder();
            vh.resenia = convertView.findViewById(R.id.tv_resenia);
            vh.rtBar = convertView.findViewById(R.id.ratingBar);
            convertView.setTag(vh);
        }
        else{
            vh = (ReseniasAdapter.ViewHolder) convertView.getTag();
        }

        Resenia res = this.reseniasList.get(position);

        vh.resenia.setText(res.getComentario());
        vh.rtBar.setNumStars(5);
        vh.rtBar.setRating(res.getCalificacion());

        return convertView;
    }

    static class ViewHolder{
        TextView resenia;
        RatingBar rtBar;
    }
}
