package com.gabrielxavier.fasttooth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gabrielxavier.fasttooth.model.Servico;
import com.gabrielxavier.fasttooth.slides.carroselFotosAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class inicioActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<Integer> images = new ArrayList<>();
    carroselFotosAdapter adapter;

    List<Servico> servicos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        //barra de notificacoes preta
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.fundo_header));

        //resolvendo carrosel de fotos
        viewPager = findViewById(R.id.viewPager_carroselFotos_inicio);
        images.add(R.drawable.foto_carrosel1);
        images.add(R.drawable.foto_carrosel1);
        images.add(R.drawable.foto_carrosel1);

        adapter = new carroselFotosAdapter(this, images);
        viewPager.setAdapter(adapter);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigatio_inicio);

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
                        nome = "inicio";
                        break;
                    case 2:
                        irParaServicos();
                        break;
                    case 3:
                        irParaAgenda();
                        break;
                    case 4:
                        irParaPerfil();
                        break;
                    default: nome = "";
                }
                //Toast.makeText(inicioActivity.this, "pagina de: "+ nome, Toast.LENGTH_SHORT).show();
            }

        });
        //começando com a pagina home selecionada
        bottomNavigation.show(1, true);
    }

    public void irParaServicos(){

        // Recebendo lista de serviços.
        Intent i = getIntent();

        servicos = (List<Servico>) i.getSerializableExtra("listaServices");
        System.out.println("\n\nLista obtida!\nLista[0] --> " + servicos.get(0) + "\n");

        Intent intent = new Intent(inicioActivity.this, com.gabrielxavier.fasttooth.servicosActivity.class);
        intent.putExtra("listaServices", (Serializable) servicos);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
    public void irParaAgenda(){

        // Recebendo lista de serviços.
        Intent i = getIntent();

        servicos = (List<Servico>) i.getSerializableExtra("listaServices");

        Intent intent = new Intent(inicioActivity.this, com.gabrielxavier.fasttooth.agendaActivity.class);
        intent.putExtra("listaServices", (Serializable) servicos);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
    public void irParaPerfil(){
        // Recebendo lista de serviços.
        Intent i = getIntent();

        servicos = (List<Servico>) i.getSerializableExtra("listaServices");
        Intent intent = new Intent(inicioActivity.this, com.gabrielxavier.fasttooth.perfilActivity.class);
        intent.putExtra("listaServices", (Serializable) servicos);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void agendar(View v){
        // Recebendo lista de serviços.
        Intent i = getIntent();

        servicos = (List<Servico>) i.getSerializableExtra("listaServices");

        Intent intent = new Intent(inicioActivity.this, com.gabrielxavier.fasttooth.servicosActivity.class);
        intent.putExtra("listaServices", (Serializable) servicos);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
    public void minhaAgenda(View v){
        // Recebendo lista de serviços.
        Intent i = getIntent();

        servicos = (List<Servico>) i.getSerializableExtra("listaServices");

        Intent intent = new Intent(inicioActivity.this, com.gabrielxavier.fasttooth.agendaActivity.class);
        intent.putExtra("listaServices", (Serializable) servicos);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}