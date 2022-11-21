package com.gabrielxavier.fasttooth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gabrielxavier.fasttooth.adapter.ServicoAdapter;
import com.gabrielxavier.fasttooth.interfaces.APICall;
import com.gabrielxavier.fasttooth.model.Servico;
import com.gabrielxavier.fasttooth.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class servicosActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ServicoAdapter adapter;
    private List<Servico> itens;

    List<Servico> listaApiServicos;

    private APICall apiCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

        // Exibindo Cards de cadastro de consultas.
        itens = new ArrayList<Servico>();

        Intent inten = getIntent();

        itens = (List<Servico>) inten.getSerializableExtra("listaServices");

        for (int i = 0; i < itens.size(); i++){
            System.out.println("\nItens: " + itens.get(i));;
        }

        System.out.println("\n\n---------------------------------------");

        recycler = findViewById(R.id.recycler);


        System.out.println("\n\n\nNew ServiçoAdapter");
        adapter = new ServicoAdapter(servicosActivity.this, (ArrayList<Servico>) itens);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(servicosActivity.this,
                LinearLayoutManager.VERTICAL, false);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);

        // -----------------------------------------

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigatio_servicos);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_dashboard));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_watch));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_person));

        //se clicar em alguma opcao do menu(obrigatorio)
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(MainActivity.this, "clicou em "+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        //se clicar novamente em alguma opcao do menu(obrigatorio para nao crashar)
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(MainActivity.this, "clicou de novo em "+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        //quando animacao do menu for concluida(obrigatorio para nao crashar)
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                String nome = "";
                Fragment fragment = null;
                switch (item.getId()){
                    case 1:
                        irParaInicio();
                        break;
                    case 2:
                        break;
                    case 3:
                        irParaAgenda();
                        break;
                    case 4:
                        irParaPerfil();
                        break;
                    default: nome = "";
                }
                //Toast.makeText(servicosActivity.this, "pagina de: "+ nome, Toast.LENGTH_SHORT).show();
            }

        });
        //começando com a pagina servicos selecionada
        bottomNavigation.show(2, true);


    }

    void configurarRetrofit(){
        // Esse método cria e define algumas configurações usadas na biblioteca retrofit.

        Gson gson = new GsonBuilder()
                .setDateFormat("HH:mm:ss")
                .create();

        //LocalTime
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://clinica-tcc-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiCall = retrofit.create(APICall.class);
    }

    public void irParaInicio(){
        Intent intent = new Intent(servicosActivity.this, inicioActivity.class);
        intent.putExtra("listaServices", (Serializable) itens);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
    public void irParaAgenda(){
        Intent intent = new Intent(servicosActivity.this, com.gabrielxavier.fasttooth.agendaActivity.class);
        intent.putExtra("listaServices", (Serializable) itens);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
    public void irParaPerfil(){
        Intent intent = new Intent(servicosActivity.this, perfilActivity.class);
        intent.putExtra("listaServices", (Serializable) itens);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}