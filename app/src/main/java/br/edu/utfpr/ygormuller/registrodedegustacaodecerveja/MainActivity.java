package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEstilo, editTextIbu, editTextAbv, editTextConsideracoes;
    private RadioGroup radioGroupNacionalidade;
    private CheckBox checkBoxRecomendacao;
    private Spinner spinnerClassificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Cadastro de Cerveja");
        }

        editTextNome = findViewById(R.id.editTextTextNome);
        editTextEstilo = findViewById(R.id.editTextTextEstilo);
        editTextIbu = findViewById(R.id.editTextNumberIbu);
        editTextAbv = findViewById(R.id.editTextNumberAbv);
        editTextConsideracoes = findViewById(R.id.editTextTextConsideracoes);
        radioGroupNacionalidade = findViewById(R.id.radioGroupNacionalidade);
        checkBoxRecomendacao = findViewById(R.id.checkBoxRecomendacao);
        spinnerClassificacao = findViewById(R.id.spinnerClassificacao);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.classificacao_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassificacao.setAdapter(adapter);

        Intent intent = getIntent();
        Cerveja cervejaEditar = (Cerveja) intent.getSerializableExtra("CERVEJA_EDITAR");
        if (cervejaEditar != null) {
            editTextNome.setText(cervejaEditar.getNome());
            editTextEstilo.setText(cervejaEditar.getEstilo());
            editTextIbu.setText(String.valueOf(cervejaEditar.getIbu()));
            editTextAbv.setText(String.valueOf(cervejaEditar.getAbv()));
            editTextConsideracoes.setText(cervejaEditar.getConsideracoes());
            checkBoxRecomendacao.setChecked(cervejaEditar.isRecomendacao());
            // Configurar nacionalidade
            for (int i = 0; i < radioGroupNacionalidade.getChildCount(); i++) {
                RadioButton rb = (RadioButton) radioGroupNacionalidade.getChildAt(i);
                if (rb.getText().toString().equals(cervejaEditar.getNacionalidade())) {
                    rb.setChecked(true);
                    break;
                }
            }
            // Configurar classificação
            int pos = adapter.getPosition(cervejaEditar.getClassificacao());
            spinnerClassificacao.setSelection(pos);
        }

//        findViewById(R.id.buttonLimpar).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                limparFormulario();
//            }
//        });
//
//        findViewById(R.id.buttonSalvar).setOnClickListener(v -> salvarFormulario());

    }

    // Inflar o menu de opções
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Tratar cliques nos itens do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_salvar) {
            salvarFormulario();
            return true;
        } else if (itemId == R.id.menu_limpar) {
            limparFormulario();
            return true;
        } else if (itemId == android.R.id.home) { // Botão "Up"
            setResult(RESULT_CANCELED); // Cancelar a operação
            finish(); // Retornar à CervejasActivity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void limparFormulario() {
        editTextNome.setText("");
        editTextEstilo.setText("");
        editTextIbu.setText("");
        editTextAbv.setText("");
        editTextConsideracoes.setText("");
        radioGroupNacionalidade.clearCheck();
        checkBoxRecomendacao.setChecked(false);
        spinnerClassificacao.setSelection(0);
        Toast.makeText(this, "Formulário limpo com sucesso!", Toast.LENGTH_SHORT).show();

    }

    private void salvarFormulario() {

        String nome = editTextNome.getText().toString();
        String estilo = editTextEstilo.getText().toString();
        String ibuStr = editTextIbu.getText().toString();
        String abvStr = editTextAbv.getText().toString();
        String consideracoes = editTextConsideracoes.getText().toString();

        if (radioGroupNacionalidade.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Selecione a nacionalidade!", Toast.LENGTH_SHORT).show();
            return;
        }

//        int selectedRadioButtonId = radioGroupNacionalidade.getCheckedRadioButtonId();
//        RadioButton radioButtonSelecionado = findViewById(selectedRadioButtonId);
//        String nacionalidadeCerveja = radioButtonSelecionado != null ? radioButtonSelecionado.getText().toString() : "";

        if (nome.isEmpty()) {
            editTextNome.setError("O nome é obrigatório!");
            return;
        }
        if (estilo.isEmpty()) {
            editTextEstilo.setError("O estilo é obrigatório!");
            return;
        }
        if (ibuStr.isEmpty()) {
            editTextIbu.setError("O IBU é obrigatório!");
            return;
        }
        if (abvStr.isEmpty()) {
            editTextAbv.setError("O ABV é obrigatório!");
            return;
        }
        if (consideracoes.isEmpty()) {
            editTextConsideracoes.setError("As considerações são obrigatórias!");
            return;
        }
        if (radioGroupNacionalidade.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Selecione a nacionalidade!", Toast.LENGTH_SHORT).show();
            return;
        }

        int ibu, abv;
        try {
            ibu = Integer.parseInt(ibuStr);
            if (ibu < 0) {
                editTextIbu.setError("O IBU não pode ser negativo!");
                return;
            }
        } catch (NumberFormatException e) {
            editTextIbu.setError("Digite um número válido para o IBU!");
            return;
        }
        try {
            abv = Integer.parseInt(abvStr);
            if (abv < 0) {
                editTextAbv.setError("O ABV não pode ser negativo!");
                return;
            }
        } catch (NumberFormatException e) {
            editTextAbv.setError("Digite um número válido para o ABV!");
            return;
        }

        RadioButton radioButtonSelecionado = findViewById(radioGroupNacionalidade.getCheckedRadioButtonId());
        String nacionalidadeCerveja = radioButtonSelecionado.getText().toString();
        boolean recomendacao = checkBoxRecomendacao.isChecked();
        String classificacao = spinnerClassificacao.getSelectedItem().toString();

        if (editTextNome.getText().toString().isEmpty() ||
                editTextEstilo.getText().toString().isEmpty() ||
                editTextIbu.getText().toString().isEmpty() ||
                editTextAbv.getText().toString().isEmpty() ||
                editTextConsideracoes.getText().toString().isEmpty() ||
                radioGroupNacionalidade.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        Cerveja cerveja = new Cerveja(
                nome,
                estilo,
                ibu,
                abv,
                recomendacao,
                nacionalidadeCerveja,
                consideracoes,
                classificacao
        );

        Intent resultIntent = new Intent();
        resultIntent.putExtra("CERVEJA", cerveja);
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Cerveja cadastrada: " + cerveja.getNome(), Toast.LENGTH_SHORT).show();
        finish(); // Fecha a MainActivity
    }

//        String mensagem = "Dados salvos:\n" +
//                "Nome: " + nome + "\n" +
//                "Estilo: " + estilo + "\n" +
//                "IBU: " + ibu + "\n" +
//                "ABV: " + abv + "\n" +
//                "Considerações: " + consideracoes + "\n" +
//                "Nacionalidade da Cerveja: " + nacionalidadeCerveja + "\n" +
//                "Recomendação: " + (recomendacao ? "Sim" : "Não") + "\n" +
//                "Classificação: " + classificacao;
//
//        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
//    }
}