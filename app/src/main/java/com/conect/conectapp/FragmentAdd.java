package com.conect.conectapp;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FragmentAdd extends Fragment {

    private Button gobutton;
    private EditText editName, editCode;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add,container,false);

        //Start components
        gobutton = v.findViewById(R.id.goButton);
        editName = v.findViewById(R.id.editName);
        editCode = v.findViewById(R.id.editCode);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Pega a referencia do banco de dados no path TORNEIRAS
        //Start components




        //OnClickFunctions
        gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String code = editCode.getText().toString().trim();

                databaseReference = FirebaseDatabase.getInstance().getReference("Torneiras");

                //Query que confere qual torneira tem o mesmo codigo da digitada.
                databaseReference.orderByChild("code").equalTo(code).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(v.getContext(),"Torneira Existe", Toast.LENGTH_LONG).show();
                            databaseReference.child(code).child("nome").setValue(name);
                            databaseReference.child(code).child("dono").setValue(firebaseUser.getUid()); //Sera substituido pelo user
                        }else {
                            Toast.makeText(v.getContext(),"Torneira não existe", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });








        return v;
    }


}
