package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.ygormuller.registrodedegustacaodecerveja.modelo.Cerveja;

public class CervejaAdapter extends BaseAdapter {

    private Context context;
    private List<Cerveja> cervejas;

    private static class CervejaHolder {
        public TextView textViewNome;
        public TextView textViewEstilo;
        public TextView textViewIbu;
        public TextView textViewAbv;
        public TextView textViewNacionalidade;
        public TextView textViewClassificacao;
        public TextView textViewRecomendacao;
        public TextView textViewConsideracoes;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_cerveja, parent, false);
            holder = new CervejaHolder();
            holder.textViewNome = convertView.findViewById(R.id.textViewNome);
            holder.textViewEstilo = convertView.findViewById(R.id.textViewEstilo);
            holder.textViewIbu = convertView.findViewById(R.id.textViewIBU);
            holder.textViewAbv = convertView.findViewById(R.id.textViewABV);
            holder.textViewNacionalidade = convertView.findViewById(R.id.textViewNacionalidade);
            holder.textViewClassificacao = convertView.findViewById(R.id.textViewClassificacao);
            holder.textViewRecomendacao = convertView.findViewById(R.id.textViewRecomendacao);
            holder.textViewConsideracoes = convertView.findViewById(R.id.textViewConsideracoes);
            convertView.setTag(holder);
        } else {
            holder = (CervejaHolder) convertView.getTag();
        }

        Cerveja cerveja = cervejas.get(position);
        holder.textViewNome.setText(cerveja.getNome());
        holder.textViewEstilo.setText(context.getString(R.string.estilo) + " " + cerveja.getEstilo());
        holder.textViewIbu.setText(context.getString(R.string.ibu) + " " + cerveja.getIbu());
        holder.textViewAbv.setText(context.getString(R.string.abv) + " " + cerveja.getAbv() + "%");
        holder.textViewNacionalidade.setText(context.getString(R.string.nacionalidade_cerveja) + "=" + context.getString(R.string.nacional));
        holder.textViewClassificacao.setText(context.getString(R.string.classificacao) + "=" + cerveja.getClassificacao());
        holder.textViewRecomendacao.setText(context.getString(R.string.recomendado) + "=" +
                (cerveja.isRecomendacao() ? context.getString(R.string.sim) : context.getString(R.string.nao)));
        holder.textViewConsideracoes.setText(context.getString(R.string.consideracoes) + "=" + cerveja.getConsideracoes());

        holder.textViewNacionalidade.setVisibility(View.VISIBLE);
        holder.textViewClassificacao.setVisibility(View.VISIBLE);
        holder.textViewRecomendacao.setVisibility(View.VISIBLE);
        holder.textViewConsideracoes.setVisibility(View.VISIBLE);

        return convertView;
    }
}
