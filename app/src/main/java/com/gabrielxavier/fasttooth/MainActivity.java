package com.gabrielxavier.fasttooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gabrielxavier.fasttooth.interfaces.APICall;
import com.gabrielxavier.fasttooth.model.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    MaskEditText etCPF, etConfirmaCPF;
    TextInputLayout containerCpf, containerConfirmaCpf;

    Button btnAvancar;
    TextView tvErrorMessage;

    String cpfAleatorio = "";
    GeradorCPF geradorCPF = new GeradorCPF();

    boolean ehCPF;
    boolean camposCoincidem;
    ValidarCPF validadorCPF = new ValidarCPF();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCPF = (MaskEditText) findViewById(R.id.etCPF);
        etConfirmaCPF = (MaskEditText) findViewById(R.id.etConfirmaCPF);
        btnAvancar = (Button) findViewById(R.id.btnAvancar);
        tvErrorMessage = (TextView) findViewById(R.id.tvErrorMessage);
        containerCpf = (TextInputLayout) findViewById(R.id.containerCpfLogin);
        containerConfirmaCpf = (TextInputLayout) findViewById(R.id.containerConfirmaCpfLogin);

        etCPF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(etCPF.getUnMasked().length()==11)
                {
                    System.out.println("\n\n\nCompletou");
                    ValidarCPF validacao = new ValidarCPF();
                    validacao.cpf(etCPF.getUnMasked());
                    ehCPF = validacao.isCPF();
                    if(ehCPF) {
                        sucessoEtCpf();

                        if (etConfirmaCPF.getUnMasked().length()==11 && !(etConfirmaCPF.getUnMasked().matches(etCPF.getUnMasked()))){
                            erroEtConfirmaCpf();
                        } else {
                            containerCpf.setError(null);
                            etCPF.setError(null);
                            sucessoEtConfirmaCpf();
                        }
                    }
                    else {
                        erroEtCpf();
                    }
                }else
                    containerCpf.setBoxStrokeColor(getResources().getColor(R.color.azul_primario));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etConfirmaCPF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(etConfirmaCPF.getUnMasked().length()==11)
                {
                    // " Se os campos coincidem... "
                    if (etCPF.getUnMasked().matches(etConfirmaCPF.getUnMasked())){
                        sucessoEtConfirmaCpf();

                    } else {
                        erroEtConfirmaCpf();
                    }
                }
                else {
                    containerConfirmaCpf.setBoxStrokeColor(getResources().getColor(R.color.azul_primario));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void avancar(View view){

        if (camposCoincidem && ehCPF && confirmaCamposCoincidem()){
            retrofitConfigurarBuilder().enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                    if (response.code() == 200){
                        containerCpf.setBoxStrokeColor(getResources().getColor(R.color.vermelho_erro));
                        containerCpf.setHelperText("Cpf inserido já possui um cadastro.");
                    } else if (response.code() == 400){

                        String error = null;
                        try {
                            error = response.errorBody().string();   // Pega mensagem de erro da requisição.
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("\n\nError --> " + error);
                        if (error.matches("CPF inserido não foi encontrado")){
                            Intent intent = new Intent(MainActivity.this, cadastro_activity.class);
                            String cpf = etCPF.getText().toString();

                            intent.putExtra("cpf", cpf);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        } else {
            if (!ehCPF){
                // Setando cor de erro
                etCPF.getBackground().setColorFilter(getResources().getColor(R.color.vermelho_erro),
                        PorterDuff.Mode.SRC_ATOP);
                etCPF.setError("CPF invalido");
            }
            if (!camposCoincidem){
                // Setando cor de erro
                etConfirmaCPF.getBackground().setColorFilter(getResources().getColor(R.color.vermelho_erro),
                        PorterDuff.Mode.SRC_ATOP);
                etCPF.setError("Campos informados não coincidem.");
            }
        }

        // Minimiza o teclado quando usuário clicar no botão.
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etConfirmaCPF.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etCPF.getWindowToken(), 0);


    }

    Call<Usuario> retrofitConfigurarBuilder(){

        //Retrofit Builder.
        // Especificando a URL base e convertendo o Gson.

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://clinica-api-tcc.herokuapp.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Instacia da interface;
        APICall apiCall = retrofit.create(APICall.class);

        System.out.println("\nCPF --> " + etCPF.getText().toString());
        Call<Usuario> usuario = apiCall.getByCpf(etCPF.getUnMasked());
        return usuario;
    }


    public void possuiLogin(View view){

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void erroEtCpf(){
        containerCpf.setHelperText("Cpf Inválido.");
        // Setando cor de erro
        containerCpf.setBoxStrokeColor(getResources().getColor(R.color.vermelho_erro));

    }
    public void erroEtConfirmaCpf(){

        containerConfirmaCpf.setHelperText("Campos informados não coincidem.");
        // Setando cor de erro
        containerConfirmaCpf.setBoxStrokeColor(getResources().getColor(R.color.vermelho_erro));
        camposCoincidem = false;
    }
    public void sucessoEtCpf(){

        System.out.println("Entrou\n\n\n");
        containerCpf.setHelperText(null);
        containerCpf.setBoxStrokeColor(getResources().getColor(R.color.azul_primario));
    }
    public void sucessoEtConfirmaCpf(){
        containerConfirmaCpf.setHelperText(null);

        containerConfirmaCpf.setBoxStrokeColor(getResources().getColor(R.color.azul_primario));
        camposCoincidem = true;
    }
    public boolean confirmaCamposCoincidem(){

        if (etCPF.getUnMasked().matches(etConfirmaCPF.getUnMasked())){
            containerCpf.setError(null);
            return true;
        }
        erroEtConfirmaCpf();
        System.out.println("\n\nBARRADO PELA CONFIRMAÇÃO\n\n\n");
        return false;
    }
}