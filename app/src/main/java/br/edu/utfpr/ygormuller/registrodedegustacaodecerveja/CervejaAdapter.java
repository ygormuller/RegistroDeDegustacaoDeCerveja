package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CervejaAdapter extends BaseAdapter {

    private Context context;
    private List<Cerveja> cervejas;

    private static class CervejaHolder {
        public TextView textViewNome;
        public TextView textViewEstilo;
        public TextView textViewIbu;
        public TextView textViewAbv;
        public TextView textViewRecomendacao;

    }

    public CervejaAdapter(Context context, List<Cerveja> cervejas) {
        this.context = context;
        this.cervejas = cervejas;
    }

    @Override
    public int getCount() {
        return cervejas.size();
    }

    @Override
    public Object getItem(int position) {
        return cervejas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CervejaHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_lista_cervejas, parent, false);

            holder = new CervejaHolder();
            holder.textViewNome = convertView.findViewById(R.id.textViewNome);
            holder.textViewEstilo = convertView.findViewById(R.id.textViewEstilo);
            holder.textViewIbu = convertView.findViewById(R.id.textViewIBU);
            holder.textViewAbv = convertView.findViewById(R.id.textViewABV);
            holder.textViewRecomendacao = convertView.findViewById(R.id.textViewRecomendacao);

            convertView.setTag(holder);
        } else {
            holder = (CervejaHolder) convertView.getTag();
        }

        Cerveja cerveja = cervejas.get(position);

        holder.textViewNome.setText(cerveja.getNome());
        holder.textViewEstilo.setText("Estilo: " + cerveja.getEstilo());
        holder.textViewIbu.setText("IBU: " + cerveja.getIBU());
        holder.textViewAbv.setText("ABV: " + cerveja.getABV() + "%");
        holder.textViewRecomendacao.setText("Recomendação: " + (cerveja.isRecomendacao() ? "Sim" : "Não"));

        return convertView;
    }
}
