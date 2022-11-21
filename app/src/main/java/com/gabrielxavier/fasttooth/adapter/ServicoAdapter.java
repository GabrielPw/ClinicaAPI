package com.gabrielxavier.fasttooth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielxavier.fasttooth.R;
import com.gabrielxavier.fasttooth.model.Servico;
import com.gabrielxavier.fasttooth.viewHolder.ServicoViewHolder;

import java.util.ArrayList;

public class ServicoAdapter extends RecyclerView.Adapter<ServicoViewHolder> {

    Context context;
    ArrayList<Servico> itens;
    int position;
    int count = 0;

    ArrayList<Servico> itensIndisponiveis;

    public ServicoAdapter(Context context, ArrayList<Servico> itens) {
        this.context = context;
        this.itens = itens;
    }

    @NonNull
    @Override
    public ServicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_marcar_agenda, parent, false);
        ServicoViewHolder viewHolder = new ServicoViewHolder(view);
        System.out.println("\n\n\nContagem -->" + getItemCount());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServicoViewHolder holder, int position) {

        // O layout criado para o card exibe dois cards por vez. o if abaixo trata de configurar os textos de cada card.

        if (!((count + position + 1) > itens.size() - 1) && !((count + position) > itens.size() - 1)){
            Servico servico1 = itens.get(count + position);
            Servico servico2 = itens.get(count + position + 1);

            System.out.println("\nposição --> " + position);

            holder.tipoServico.setText(servico1.getTipo());
            holder.descricaoServico.setText(servico1.getDescricao());

            holder.tipoServico2.setText(servico2.getTipo());
            holder.descricaoServico2.setText(servico2.getDescricao());

            count += 1;
        } else{
            if(!((count + position) > itens.size() - 1)){
                holder.tipoServico.setText(itens.get(count + position).getTipo());
                holder.descricaoServico.setText(itens.get(count + position).getDescricao());

                LinearLayout linearLayout = (LinearLayout) holder.tipoServico2.getParent();

                linearLayout.setVisibility(View.INVISIBLE);
                holder.descricaoServico2.setText("Indisponível");

            } else{

                LinearLayout linearLayout = (LinearLayout) holder.tipoServico.getParent();
                linearLayout.removeAllViews();

                LinearLayout linearLayout2 = (LinearLayout) holder.tipoServico2.getParent();
                linearLayout2.removeAllViews();

            }

        }


    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
