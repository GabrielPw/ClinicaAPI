package com.gabrielxavier.fasttooth.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielxavier.fasttooth.R;
import com.gabrielxavier.fasttooth.adapter.ServicoAdapter;
import com.gabrielxavier.fasttooth.model.Servico;
import com.gabrielxavier.fasttooth.servicosActivity;

public class ServicoViewHolder extends RecyclerView.ViewHolder {

    public TextView tipoServico, descricaoServico;
    public TextView tipoServico2, descricaoServico2;
    public View view;
    public int tamanhoLista;

    public ServicoViewHolder(@NonNull View itemView) {
        super(itemView);

        this.view = itemView;
        System.out.println("\n\ntamannhoLista --> " + tamanhoLista);

        tipoServico = itemView.findViewById(R.id.tipoServico);
        descricaoServico = itemView.findViewById(R.id.descServico);

        tipoServico2 = itemView.findViewById(R.id.tipoServico2);
        descricaoServico2 = itemView.findViewById(R.id.descServico2);
    }
}
