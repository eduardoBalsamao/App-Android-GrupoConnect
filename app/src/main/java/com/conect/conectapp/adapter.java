package com.conect.conectapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter extends FirebaseRecyclerAdapter<Torneiras, adapter.viewholder> {
    public adapter(@NonNull FirebaseRecyclerOptions<Torneiras> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull Torneiras torneiras) {

        viewholder.name.setText(torneiras.getNome());
        viewholder.code.setText(torneiras.getCode());
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.caixa, parent,false);
        return new viewholder(view);
    }

    class  viewholder extends RecyclerView.ViewHolder{
        TextView name, code;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.nameTorneira);
            code = (TextView)itemView.findViewById(R.id.codeTorneira);
        }
    }


}
