package com.juanjose.picoyplaca;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText name, username, city, placa, password, confirmPass, email;
    private Button btn_create;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //nueva instacia de firebase
        firebaseAuth = FirebaseAuth.getInstance();
        setElements();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    /**
     * metodo para settear los elementos de la vista
     */
    public void setElements(){
        name = (EditText) findViewById(R.id.editTextNameCreate);
        username = (EditText) findViewById(R.id.editTextUsernameCreate);
        city = (EditText) findViewById(R.id.editTextCity);
        placa = (EditText) findViewById(R.id.editTextPlaca);
        password = (EditText) findViewById(R.id.editTextPasswordCreate);
        confirmPass = (EditText) findViewById(R.id.editTextConfirmPass);
        btn_create = (Button) findViewById(R.id.buttonCreateAccount);
        email = (EditText) findViewById(R.id.editTextEmail);
    }

    public void createAccount(){
        String nameUsed = name.getText().toString().trim();
        String usernameUsed = username.getText().toString().trim();
        String cityUsed = city.getText().toString().trim();
        String placaUsed = placa.getText().toString().trim();
        String passwordUsed = password.getText().toString().trim();
        String passwordConfirmationUsed = confirmPass.getText().toString().trim();
        String emailUsed = email.getText().toString().trim();

        if(nameUsed.equals("") || usernameUsed.equals("") || cityUsed.equals("") ||
                placaUsed.equals("") || passwordUsed.equals("") || passwordConfirmationUsed.equals("")
                || emailUsed.equals("")){
            Toast.makeText(this, "Complete all form", Toast.LENGTH_SHORT).show();
        }else if(!passwordUsed.equals(passwordConfirmationUsed)){
            Toast.makeText(this, "password doesn't match", Toast.LENGTH_SHORT).show();
        }else{
        firebaseAuth.createUserWithEmailAndPassword(emailUsed, passwordUsed)
                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("test", "error es...."+task);
                        if(!task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
        }

    }
}
