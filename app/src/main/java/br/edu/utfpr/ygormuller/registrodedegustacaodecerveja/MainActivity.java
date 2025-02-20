package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.os.Bundle;
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

        findViewById(R.id.buttonLimpar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparFormulario();
            }
        });

        findViewById(R.id.buttonSalvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarFormulario();
            }
        });
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
        String ibu = editTextIbu.getText().toString();
        String abv = editTextAbv.getText().toString();
        String consideracoes = editTextConsideracoes.getText().toString();

        int selectedRadioButtonId = radioGroupNacionalidade.getCheckedRadioButtonId();
        RadioButton radioButtonSelecionado = findViewById(selectedRadioButtonId);
        String nacionalidadeCerveja = radioButtonSelecionado != null ? radioButtonSelecionado.getText().toString() : "";

        boolean recomendacao = checkBoxRecomendacao.isChecked();

        String classificacao = spinnerClassificacao.getSelectedItem().toString();

        if (nome.isEmpty() || estilo.isEmpty() || ibu.isEmpty() || abv.isEmpty() || consideracoes.isEmpty() || nacionalidadeCerveja.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        String mensagem = "Dados salvos:\n" +
                "Nome: " + nome + "\n" +
                "Estilo: " + estilo + "\n" +
                "IBU: " + ibu + "\n" +
                "ABV: " + abv + "\n" +
                "Considerações: " + consideracoes + "\n" +
                "Nacionalidade da Cerveja: " + nacionalidadeCerveja + "\n" +
                "Recomendação: " + (recomendacao ? "Sim" : "Não") + "\n" +
                "Classificação: " + classificacao;

        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}