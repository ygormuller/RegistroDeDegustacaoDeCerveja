package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaCervejasActivity extends AppCompatActivity {

    private ListView listViewCervejas;
    private List<Cerveja> listaCervejas;
    private CervejaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cervejas);

        listViewCervejas = findViewById(R.id.listViewCervejas);
        listaCervejas = new ArrayList<>();
        adapter = new CervejaAdapter(this, listaCervejas);
        listViewCervejas.setAdapter(adapter);

        carregarDados();

        CervejaAdapter adapter = new CervejaAdapter(this, listaCervejas);
        listViewCervejas.setAdapter(adapter);

//        listViewCervejas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cerveja cerveja = listaCervejas.get(position);
//                Toast.makeText(ListaCervejasActivity.this,
//                        "Cerveja: " + cerveja.getNome() + " - Estilo: " + cerveja.getEstilo(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        findViewById(R.id.buttonAdicionar).setOnClickListener(v -> {
            Intent intent = new Intent(ListaCervejasActivity.this, MainActivity.class);
            startActivityForResult(intent, 1); // Código de requisição = 1
        });

        findViewById(R.id.buttonSobre).setOnClickListener(v -> {
            Intent intent = new Intent(ListaCervejasActivity.this, SobreActivity.class);
            startActivity(intent);
        });
    }

    private void carregarDados() {
        String[] nomes = getResources().getStringArray(R.array.cervejas_nome);
        String[] estilos = getResources().getStringArray(R.array.cervejas_estilo);
        int[] ibus = getResources().getIntArray(R.array.cervejas_ibu);
        int[] abvs = getResources().getIntArray(R.array.cervejas_abv);
        int[] recomendacoes = getResources().getIntArray(R.array.cervejas_recomendacao);
        String[] nacionalidades = getResources().getStringArray(R.array.cervejas_nacionalidade);
        String[] consideracoes = getResources().getStringArray(R.array.cervejas_consideracoes);
        String[] classificacoes = getResources().getStringArray(R.array.classificacao_array);

        for (int i = 0; i < nomes.length; i++) {
            boolean recomendacao = (recomendacoes[i] == 1);

            if (nomes[i] != null && estilos[i] != null && nacionalidades[i] != null && consideracoes[i] != null) {

                listaCervejas.add(new Cerveja(
                        nomes[i],
                        estilos[i],
                        ibus[i],
                        abvs[i],
                        recomendacao,
                        nacionalidades[i],
                        consideracoes[i],
                        classificacoes[i]
                ));
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Cerveja cerveja = (Cerveja) data.getParcelableExtra("CERVEJA");
            if (cerveja != null) {
                listaCervejas.add(cerveja);
                adapter.notifyDataSetChanged();
            }
        }
    }
}