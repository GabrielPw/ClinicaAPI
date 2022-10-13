package com.gabrielxavier.fasttooth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gabrielxavier.fasttooth.interfaces.APICall;
import com.gabrielxavier.fasttooth.model.Cliente;
import com.gabrielxavier.fasttooth.model.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class cadastro_activity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();
    EditText etNome , etEmail, etDataNascimento, etSenha;
    MaskEditText etTelefone;
    Button btnCadastrar;
    CheckBox cbTermos;

    TextInputLayout containerNomeCadastro, containerTelefoneCadastro, containerEmailCadastro, containerNascimentoCadastro, containerSenhaCadastro, containerTermos;
    String cpf = "";

    private APICall apiCall;
    private String nome, dataFinal, email, telefone, senha;
    boolean nomePreenchido, telefonePreenchido, emailPreenchido, dataPreenchida, senhaPreenchida, termosPreenchido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = (EditText) findViewById(R.id.etNome);
        etTelefone = (MaskEditText) findViewById(R.id.etTelefone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etDataNascimento = (EditText) findViewById(R.id.etNascimento);
        etSenha = (EditText) findViewById(R.id.etSenha);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        cbTermos = (CheckBox) findViewById(R.id.cbTermos);

        containerNomeCadastro = (TextInputLayout) findViewById(R.id.containerNomeCadastro);
        containerTelefoneCadastro = (TextInputLayout) findViewById(R.id.containerTelefoneCadastro);
        containerEmailCadastro = (TextInputLayout) findViewById(R.id.containerEmailCadastro);
        containerNascimentoCadastro = (TextInputLayout) findViewById(R.id.containerNascimentoCadastro);
        containerSenhaCadastro = (TextInputLayout) findViewById(R.id.containerSenhaCadastro);
        containerTermos = (TextInputLayout) findViewById(R.id.containerTermosCadastro);

        Bundle extras = getIntent().getExtras();
        cpf = extras.getString("cpf");

        // (abaixo) Evento de clique do campo de texto para a data de nascimento. (vai abrir um pop up para selecionar a data).
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="dd/MM/yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
                etDataNascimento.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        etDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(cadastro_activity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void cadastrar(View view){

        validarCampos();
        if (nomePreenchido && telefonePreenchido && emailPreenchido && dataPreenchida && senhaPreenchida && emailPreenchido && termosPreenchido){

            configurarRetrofit();
            Usuario novoUsuario = new Usuario();
            novoUsuario.setCpf(cpf.replace(".", "").replace("-", "")); // Tirando a formatação do cpf antes de enviar pro JSON.
            novoUsuario.setSenha(senha);

            Call<Usuario> usuario = apiCall.newUsuario(novoUsuario);
            usuario.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.code() == 200) {
                        Cliente novoCliente = new Cliente();
                        novoCliente.setNome(nome);
                        novoCliente.setEmail(email);
                        novoCliente.setTelefone(telefone);
                        novoCliente.setDtNascimento(dataFinal.replaceAll("/", "-"));
                        novoCliente.setSenha(senha);
                        novoCliente.setUsuario(novoUsuario);

                        Call<Cliente> cliente = apiCall.newCliente(novoCliente);
                        cliente.enqueue(new Callback<Cliente>() {
                            @Override
                            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                if (response.code() == 200) {
                                    System.out.println("Cliente "+ novoCliente.getNome() + " foi registrado com sucesso!");

                                    // Exibe Popup
                                    AlertDialog.Builder popUpRegistrado = new AlertDialog.Builder(cadastro_activity.this);
                                    popUpRegistrado.setTitle("Cadastramento bem sucedido");
                                    popUpRegistrado.setMessage(novoCliente.getNome() + ", você foi cadastrado(a) com sucesso!");

                                    popUpRegistrado.setCancelable(false);

                                    popUpRegistrado.setPositiveButton("Voltar para tela inical", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent telaInicio = new Intent(cadastro_activity.this, MainActivity.class);
                                            startActivity(telaInicio);
                                            finish();
                                        }
                                    });
                                    popUpRegistrado.create().show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Cliente> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        }
    }

    public void validarCampos(){
        nome = etNome.getText().toString().replaceFirst("\\s++$", "").replaceFirst("^\\s*", "");
        System.out.println("\nNome -->" + nome + "-");

        if (nome.length() >= 4){
            nomePreenchido = true;
            entradaCorreta(containerNomeCadastro);
        } else if(nome.matches("")){
            nomePreenchido = false;
            entradaInvalida(containerNomeCadastro, "Preencha o nome.");
        }else if (nome.length() < 4){
            nomePreenchido = false;
            entradaInvalida(containerNomeCadastro, "Nome inválido. Se seu nome possui menos de 4 letras, considere também inserir o sobrenome.");
        }
        // Validar telefone
        telefone = etTelefone.getUnMasked();

        System.out.println("\n\nTelefone --> " + telefone);
        if(telefone.length() > 0){
            if(telefone.length() < 11){
                entradaInvalida(containerTelefoneCadastro, "Preencha o telefone.");
                telefonePreenchido = false;
            } else {
                entradaCorreta(containerTelefoneCadastro);
                telefonePreenchido = true;
            }
        } else {
            entradaInvalida(containerTelefoneCadastro, "*Campo obrigatório.");
            telefonePreenchido = false;
        }

        // Validar Email
        email = etEmail.getText().toString().replaceFirst("\\s++$", "").replaceFirst("^\\s*", ""); // Tratando espaços em branco.
        if (email.length() > 0){
            boolean emailEhValido = isValidEmail(email);
            if(emailEhValido){
                emailPreenchido = true;
                entradaCorreta(containerEmailCadastro);
            } else{
                emailPreenchido = false;
                entradaInvalida(containerEmailCadastro, "Email inválido.");
            }
        }else {
            emailPreenchido = false;
            entradaInvalida(containerEmailCadastro, "*Campo obrigatório.");
        }

        // Validar Data
        if(etDataNascimento.getText().toString().length() > 0){
            convertDataToApi();
            LocalDate dataAtual = LocalDate.now();
            LocalDate data = LocalDate.parse(dataFinal.replace("/", "-"));
            if (data.isAfter(dataAtual.minusYears(12))){
                entradaInvalida(containerNascimentoCadastro, "Idade deve ser maior ou igual a 13 anos.");
                dataPreenchida = false;
            } else if(data.isBefore(dataAtual.minusYears(100))){                                                    // Se a pessoa nasceu antes de 100 anos atrás.
                entradaInvalida(containerNascimentoCadastro, "Data inválida.");
                dataPreenchida = false;
            } else {
                entradaCorreta(containerNascimentoCadastro);
                dataPreenchida = true;
            }
        }else {
            entradaInvalida(containerNascimentoCadastro, "*Campo obrigatório.");
            dataPreenchida = false;
        }

        // Validar Senha
        senha = etSenha.getText().toString().replaceFirst("\\s++$", "").replaceFirst("^\\s*", "");

        if(senha.length() > 0) {
            if (isPasswordValid(senha) && senha.length() > 7) {
                entradaCorreta(containerSenhaCadastro);
                senhaPreenchida = true;
            }else {
                senhaPreenchida = false;
                entradaInvalida(containerSenhaCadastro, "Senha deve conter letras, números e caracteres especiais(- ou _). (Minímo de 8 caracteres).");
            }
        } else {
            senhaPreenchida = false;
            entradaInvalida(containerSenhaCadastro, "*Campo obrigatório.");
        }

        if (cbTermos.isChecked()){
            termosPreenchido = true;
            containerTermos.setHelperText(null);
        }else{
            termosPreenchido = false;
            containerTermos.setHelperText("Preencha os termos");
        }

        System.out.println("\n\nNome preenchido " + nomePreenchido + "\nEmail preenchido " + emailPreenchido + "\nData preenchida " + dataPreenchida + "\nTelefone preenchido " + telefonePreenchido + "\nSenha preenchida " + senhaPreenchida);
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void convertDataToApi(){
        // Adaptando o formato da data para o que é requerido pelo JSON da API.
        String data = etDataNascimento.getText().toString();

        List totalChars = new ArrayList(); // Essa lista irá guardar os caracteres do texto do Campo de data.

        for(int i = 0; i < data.length(); i++){ // Adicionando os caracteres na lista criada acima.
            totalChars.add(data.charAt(i));
        }

        System.out.println("\n\n");
        for (int i = 0; i < totalChars.size(); i++){    // exibindo oque tem na lista.
            System.out.print(totalChars.get(i));
        }
        System.out.println("\n\n");

        String dia = totalChars.get(0).toString().concat(totalChars.get(1).toString());
        String mes = totalChars.get(3).toString().concat(totalChars.get(4).toString());
        String ano = totalChars.get(6).toString().concat(totalChars.get(7).toString()).concat(totalChars.get(8).toString()).concat(totalChars.get(9).toString());

        dataFinal = ano.concat("/"  + mes).concat("/" + dia);
        dataFinal.replace("/", "-");
    }

    public void entradaInvalida(TextInputLayout container, String mensagem){
        container.setBoxStrokeColor(getResources().getColor(R.color.vermelho_erro));
        container.setHelperText(mensagem);
    }

    public void entradaCorreta(TextInputLayout container){
        container.setBoxStrokeColor(getResources().getColor(R.color.azul_primario));
        container.setHelperText(null);
    }

    public boolean isPasswordValid(String s) {
        String n = ".*[0-9].*";
        String aMin = ".*[a-z].*";
        String aMaiusc = ".*[A-Z].*";
        String aSpecial = ".*[_-].*"; // Caracteres especiais permitidos na senha.
        return (s.matches(n) && s.matches(aMin) && s.matches(aSpecial)) || (s.matches(n) && s.matches(aMaiusc) && s.matches(aSpecial));
    }

    void configurarRetrofit(){
        // Esse método cria e define algumas configurações usadas na biblioteca retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://clinica-api-tcc.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCall = retrofit.create(APICall.class);
    }

}