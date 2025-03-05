package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CervejasActivity extends AppCompatActivity {

    private ListView listViewCervejas;
    private List<Cerveja> listaCervejas;
    private CervejaAdapter adapter;
    private ActionMode actionMode;
    private int selectedPosition = -1;
    private boolean isOrdenacaoCrescente = true;
    private MenuItem menuItemOrdenar;

    private static final int REQUEST_CODE_ADICIONAR_CERVEJA = 1;
    private static final int REQUEST_CODE_EDITAR_CERVEJA = 2;
    public static final String ARQUIVO_PREFERENCIAS = "CervejasPrefs";
    public static final String KEY_ORDENACAO = "ordenacaoCrescente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cervejas);

        //setTitle("TAP de Cervejas");

        listViewCervejas = findViewById(R.id.listViewCervejas);
        listaCervejas = new ArrayList<>();;
        adapter = new CervejaAdapter(this, listaCervejas);
        listViewCervejas.setAdapter(adapter);

        listViewCervejas.setVisibility(View.GONE);

        lerPreferencias();
        popularListaCervejas();
        ordenarLista();

        registerForContextMenu(listViewCervejas);

        listViewCervejas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewCervejas.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) {
                return false;
            }
            selectedPosition = position;
            actionMode = startActionMode(actionModeCallback);
            view.setSelected(true);
            return true;
        });

        popularListaCervejas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cervejas, menu);
        menuItemOrdenar = menu.findItem(R.id.menu_ordenar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuItemAdicionar) {
            Intent intent = new Intent(CervejasActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_CERVEJA);
            return true;
        } else if (itemId == R.id.menuItemSobre) {
            Intent intent = new Intent(CervejasActivity.this, SobreActivity.class);
            startActivity(intent);
            return true;
        }else if (itemId == R.id.menu_ordenar) {
            isOrdenacaoCrescente = !isOrdenacaoCrescente;
            salvarOrdenacao(isOrdenacaoCrescente);
            ordenarLista();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void lerPreferencias() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        isOrdenacaoCrescente = shared.getBoolean(KEY_ORDENACAO, isOrdenacaoCrescente);
    }

    private void salvarOrdenacao(boolean novoValor) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_ORDENACAO, novoValor);
        editor.commit();
        isOrdenacaoCrescente = novoValor;
    }

    private void ordenarLista() {
        if (isOrdenacaoCrescente) {
            Collections.sort(listaCervejas, new Comparator<Cerveja>() {
                @Override
                public int compare(Cerveja c1, Cerveja c2) {
                    return c1.getNome().compareTo(c2.getNome()); // A-Z
                }
            });
        } else {
            Collections.sort(listaCervejas, new Comparator<Cerveja>() {
                @Override
                public int compare(Cerveja c1, Cerveja c2) {
                    return c2.getNome().compareTo(c1.getNome()); // Z-A
                }
            });
        }
        adapter.notifyDataSetChanged();
        listViewCervejas.invalidateViews();
        Log.d("CervejasActivity", "Lista ordenada " + (isOrdenacaoCrescente ? "A-Z" : "Z-A"));
    }

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_cervejas, menu);
            mode.setTitle("Ação na cerveja");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_editar) {
                Cerveja cervejaSelecionada = listaCervejas.get(selectedPosition);
                Intent intent = new Intent(CervejasActivity.this, MainActivity.class);
                intent.putExtra("CERVEJA_EDITAR", cervejaSelecionada);
                startActivityForResult(intent, REQUEST_CODE_EDITAR_CERVEJA);
                //mode.finish(); // Fechar o menu contextual
                return true;
            } else if (itemId == R.id.menu_excluir) {
                // Remover a cerveja da lista
                listaCervejas.remove(selectedPosition);
                adapter.notifyDataSetChanged();
                listViewCervejas.setVisibility(listaCervejas.isEmpty() ? View.GONE : View.VISIBLE);
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            listViewCervejas.clearChoices();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("CervejasActivity", "onActivityResult chamado - requestCode: " + requestCode +
                ", resultCode: " + resultCode + ", data: " + (data != null));

        if (resultCode == RESULT_OK && data != null) {
            Cerveja novaCerveja = (Cerveja) data.getSerializableExtra("CERVEJA");
            if (novaCerveja != null) {
                if (requestCode == REQUEST_CODE_ADICIONAR_CERVEJA) {
                    Log.d("CervejasActivity", "Adicionando nova cerveja: " + novaCerveja.getNome());
                    listaCervejas.add(novaCerveja);
                    adapter.notifyDataSetChanged();
                    listViewCervejas.setVisibility(View.VISIBLE);
                } else if (requestCode == REQUEST_CODE_EDITAR_CERVEJA) {
                    Log.d("CervejasActivity", "Editando cerveja na posição " + selectedPosition + ": " + novaCerveja.getNome());
                    if (selectedPosition >= 0 && selectedPosition < listaCervejas.size()) {
                        listaCervejas.set(selectedPosition, novaCerveja);
                        Log.d("CervejasActivity", "Cerveja editada: " + listaCervejas.get(selectedPosition).getNome());
                        //adapter.notifyDataSetChanged();
                        //listViewCervejas.setVisibility(View.VISIBLE);
                    } else {
                        Log.e("CervejasActivity", "Posição inválida para edição: " + selectedPosition);
                    }
                    if (actionMode != null) {
                        actionMode.finish();
                        actionMode = null;
                    }
                }
            } else {
                Log.e("CervejasActivity", "Cerveja é null!");
            }
        } else {
            Log.w("CervejasActivity", "Condição não atendida - requestCode: " + requestCode +
                    ", resultCode: " + resultCode);
            if (requestCode == REQUEST_CODE_EDITAR_CERVEJA && actionMode != null) {
                selectedPosition = -1;
            }
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