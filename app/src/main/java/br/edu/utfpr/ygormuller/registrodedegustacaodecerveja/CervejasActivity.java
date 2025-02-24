package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CervejasActivity extends AppCompatActivity {

    private ListView listViewCervejas;
    private List<Cerveja> listaCervejas;
    private Button btnAdicionar;
    private Button btnSobre;
    //private ArrayAdapter<Cerveja> adapter;
    private CervejaAdapter adapter;

    private static final int REQUEST_CODE_ADICIONAR_CERVEJA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cervejas);

        setTitle("TAP de Cervejas");

        listViewCervejas = findViewById(R.id.listViewCervejas);
        btnAdicionar = findViewById(R.id.buttonAdicionar);
        btnSobre = findViewById(R.id.buttonSobre);

        listaCervejas = new ArrayList<>();
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCervejas);
        adapter = new CervejaAdapter(this, listaCervejas);
        listViewCervejas.setAdapter(adapter);

        listViewCervejas.setVisibility(View.GONE);

        popularListaCervejas();

        btnAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(CervejasActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_CERVEJA);
        });

        btnSobre.setOnClickListener(v -> {
            Intent intent = new Intent(CervejasActivity.this, SobreActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADICIONAR_CERVEJA && resultCode == RESULT_OK && data != null) {
            Cerveja novaCerveja = (Cerveja) data.getSerializableExtra("CERVEJA");
            if (novaCerveja != null) {
                listaCervejas.add(novaCerveja);
                adapter.notifyDataSetChanged();
                listViewCervejas.setVisibility(View.VISIBLE);
                Log.d("CervejasActivity", "Cerveja adicionada, tamanho da lista: " + listaCervejas.size());
            } else {
                Log.e("CervejasActivity", "Cerveja é null!");
            }
        } else {
            Log.w("CervejasActivity", "Condição não atendida - requestCode: " + requestCode +
                    ", resultCode: " + resultCode + ", data: " + (data != null));

        }
    }

    private void popularListaCervejas(){

        String[] cervejas_nome = getResources().getStringArray(R.array.cervejas_nome);
        String[] cervejas_estilo = getResources().getStringArray(R.array.cervejas_estilo);
        int[] cervejas_ibu = getResources().getIntArray(R.array.cervejas_ibu);
        int[] cervejas_abv = getResources().getIntArray(R.array.cervejas_abv);
        int[] cervejas_recomendacao = getResources().getIntArray(R.array.cervejas_recomendacao);
        String[] cervejas_nacionalidade = getResources().getStringArray(R.array.cervejas_nacionalidade);
        String[] cervejas_consideracoes = getResources().getStringArray(R.array.cervejas_consideracoes);
        String[] cervejas_classificacao = getResources().getStringArray(R.array.classificacao_array);

        Cerveja cerveja;
        boolean recomendacao;

        for (int cont = 0; cont < cervejas_nome.length; cont++){
            recomendacao = (cervejas_recomendacao[cont] == 1);
            cerveja = new Cerveja(
                    cervejas_nome[cont],
                    cervejas_estilo[cont],
                    cervejas_ibu[cont],
                    cervejas_abv[cont],
                    recomendacao,
                    cervejas_nacionalidade[cont],
                    cervejas_consideracoes[cont],
                    cervejas_classificacao[cont]
                    );
            listaCervejas.add(cerveja);
        }

        adapter.notifyDataSetChanged();

        ArrayAdapter<Cerveja> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listaCervejas);

        listViewCervejas.setAdapter(adapter);
    }
}