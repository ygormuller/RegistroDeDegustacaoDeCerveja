package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.content.Intent;
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
import java.util.List;

public class CervejasActivity extends AppCompatActivity {

    private ListView listViewCervejas;
    private List<Cerveja> listaCervejas;
    private CervejaAdapter adapter;
    private ActionMode actionMode; // Para gerenciar o menu contextual
    private int selectedPosition = -1; // Para rastrear o item selecionado

    private static final int REQUEST_CODE_ADICIONAR_CERVEJA = 1;
    private static final int REQUEST_CODE_EDITAR_CERVEJA = 2; // Novo código para edição

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cervejas);

        setTitle("TAP de Cervejas");

        listViewCervejas = findViewById(R.id.listViewCervejas);
        //btnAdicionar = findViewById(R.id.buttonAdicionar);
        //btnSobre = findViewById(R.id.buttonSobre);

        listaCervejas = new ArrayList<>();
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCervejas);
        adapter = new CervejaAdapter(this, listaCervejas);
        listViewCervejas.setAdapter(adapter);

        listViewCervejas.setVisibility(View.GONE);

        // Registrar o ListView para o menu contextual
        registerForContextMenu(listViewCervejas);

        // Configurar o listener de long press
        listViewCervejas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewCervejas.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) {
                return false; // Já está em modo de ação
            }

            selectedPosition = position; // Salvar a posição do item clicado
            actionMode = startActionMode(actionModeCallback); // Iniciar o menu contextual
            view.setSelected(true); // Destacar o item selecionado
            return true;
        });

        popularListaCervejas();

//        btnAdicionar.setOnClickListener(v -> {
//            Intent intent = new Intent(CervejasActivity.this, MainActivity.class);
//            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_CERVEJA);
//        });
//
//        btnSobre.setOnClickListener(v -> {
//            Intent intent = new Intent(CervejasActivity.this, SobreActivity.class);
//            startActivity(intent);
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cervejas, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    // Callback para o menu contextual
    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_cervejas, menu);
            mode.setTitle("Ação na cerveja"); // Título opcional do menu contextual
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Não precisamos preparar nada agora
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_editar) {
                // Abrir MainActivity com os dados da cerveja selecionada
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
                mode.finish(); // Fechar o menu contextual
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null; // Limpar o modo quando fechado
            //selectedPosition = -1; // Resetar a posição
            listViewCervejas.clearChoices(); // Remover seleção visual
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
                        actionMode.finish(); // Fechar o menu contextual após edição
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
                //actionMode.finish(); // Fechar o menu se a edição for cancelada
                selectedPosition = -1;
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CODE_ADICIONAR_CERVEJA && resultCode == RESULT_OK && data != null) {
//            Cerveja novaCerveja = (Cerveja) data.getSerializableExtra("CERVEJA");
//            if (novaCerveja != null) {
//                listaCervejas.add(novaCerveja);
//                adapter.notifyDataSetChanged();
//                listViewCervejas.setVisibility(View.VISIBLE);
//                Log.d("CervejasActivity", "Cerveja adicionada, tamanho da lista: " + listaCervejas.size());
//            } else {
//                Log.e("CervejasActivity", "Cerveja é null!");
//            }
//        } else {
//            Log.w("CervejasActivity", "Condição não atendida - requestCode: " + requestCode +
//                    ", resultCode: " + resultCode + ", data: " + (data != null));
//
//        }
//    }

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