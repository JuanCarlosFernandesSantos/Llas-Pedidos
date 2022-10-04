package com.llas_pedidos;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_usuarios);
    }

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void salvarCliente(View vew) {
        //Pegando os valores nos campos para serem add na coleção
        EditText nomeView = (EditText) findViewById(R.id.etNomeC);
        EditText senhaView = (EditText) findViewById(R.id.etSenhaC);
        String nomeText = nomeView.getText().toString();
        String senhaText = senhaView.getText().toString();

        //Faz um teste para ver se os campos não estão vazios
        if (nomeText.isEmpty() || senhaText.isEmpty()) { return; }

        //Criando a coleção usuario
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nome", nomeText);
        usuario.put("senha", senhaText);
        usuario.put("master", true);

        //Add o documento com um gerador de ID
        db.collection("usuarios")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Cliente cadastrado com sucesso, com o ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Erro ao adicionar o cliente", e);
                    }
                });
    }
}
