package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import br.edu.utfpr.ygormuller.registrodedegustacaodecerveja.modelo.Cerveja;

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
            getSupportActionBar().setTitle(R.string.cadastro_de_cerveja);
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

            for (int i = 0; i < radioGroupNacionalidade.getChildCount(); i++) {
                RadioButton rb = (RadioButton) radioGroupNacionalidade.getChildAt(i);
                if (rb.getText().toString().equals(cervejaEditar.getNacionalidade())) {
                    rb.setChecked(true);
                    break;
                }
            }
            int pos = adapter.getPosition(cervejaEditar.getClassificacao());
            spinnerClassificacao.setSelection(pos);
        }
    }

    private void definirIdiomaIngles() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_salvar) {
            salvarFormulario();
            return true;
        } else if (itemId == R.id.menu_limpar) {
            limparFormulario();
            return true;
        } else if (itemId == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
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
        Toast.makeText(this, R.string.formulario_limpo_com_sucesso, Toast.LENGTH_SHORT).show();

    }

    private void salvarFormulario() {

        String nome = editTextNome.getText().toString();
        String estilo = editTextEstilo.getText().toString();
        String ibuStr = editTextIbu.getText().toString();
        String abvStr = editTextAbv.getText().toString();
        String consideracoes = editTextConsideracoes.getText().toString();

        if (radioGroupNacionalidade.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.selecione_a_nacionalidade, Toast.LENGTH_SHORT).show();
            return;
        }

        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.o_nome_obrigat_rio));
            return;
        }
        if (estilo.isEmpty()) {
            editTextEstilo.setError(getString(R.string.o_estilo_obrigat_rio));
            return;
        }
        if (ibuStr.isEmpty()) {
            editTextIbu.setError(getString(R.string.o_ibu_obrigat_rio));
            return;
        }
        if (abvStr.isEmpty()) {
            editTextAbv.setError(getString(R.string.o_abv_obrigat_rio));
            return;
        }
        if (consideracoes.isEmpty()) {
            editTextConsideracoes.setError(getString(R.string.as_considera_es_s_o_obrigat_rias));
            return;
        }
        if (radioGroupNacionalidade.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.selecione_a_nacionalidade, Toast.LENGTH_SHORT).show();
            return;
        }

        int ibu, abv;
        try {
            ibu = Integer.parseInt(ibuStr);
            if (ibu < 0) {
                editTextIbu.setError(getString(R.string.o_ibu_n_o_pode_ser_negativo));
                return;
            }
        } catch (NumberFormatException e) {
            editTextIbu.setError(getString(R.string.digite_um_n_mero_v_lido_para_o_ibu));
            return;
        }
        try {
            abv = Integer.parseInt(abvStr);
            if (abv < 0) {
                editTextAbv.setError(getString(R.string.o_abv_n_o_pode_ser_negativo));
                return;
            }
        } catch (NumberFormatException e) {
            editTextAbv.setError(getString(R.string.digite_um_n_mero_v_lido_para_o_abv));
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
        Toast.makeText(this, getString(R.string.cerveja_cadastrada) + cerveja.getNome(), Toast.LENGTH_SHORT).show();
        finish();
    }
}