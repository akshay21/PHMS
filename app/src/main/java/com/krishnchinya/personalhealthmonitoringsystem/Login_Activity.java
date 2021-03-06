package com.krishnchinya.personalhealthmonitoringsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by KrishnChinya on 2/11/17.
 */

public class Login_Activity extends Activity {
    private TextInputLayout usernameInputLayout, passwordInputLayout;
    private Button loginButton, newUser;
    private EditText usernameEditText, passwordEditText;
    myTextWatcher watcher1, watcher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getActionBar().hide();
        final AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);

        usernameInputLayout = (TextInputLayout) findViewById(R.id.input_username);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.input_password);

        usernameEditText = (EditText) findViewById(R.id.userName);
        passwordEditText = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.login);
        newUser = (Button) findViewById(R.id.NewUser);

        watcher1 = new myTextWatcher(usernameEditText,usernameInputLayout, Login_Activity.this);
        watcher2 = new myTextWatcher(passwordEditText,passwordInputLayout,Login_Activity.this);

        usernameEditText.addTextChangedListener(watcher1);
        passwordEditText.addTextChangedListener(watcher2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!watcher1.validateUserName()) {
                    return;
                }
                if (!watcher2.validatePassword()) {
                    return;
                }

                DB_Setter_Getter dbSetterGetter = new DB_Setter_Getter(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                DB_Handler db_handler = new DB_Handler(Login_Activity.this);
                String[] details = db_handler.getcredentials(dbSetterGetter);

                if(details[0].equals(dbSetterGetter.getMailID()) && details[1].equals(dbSetterGetter.getPassword()))
                {
                    builder.setTitle("Login Successful");
                    builder.setMessage("Login successful");
                    builder.show();
                }else
                {
                    builder.setTitle("");
                    builder.setMessage("Wrong Username/Password");
                    builder.show();
                }

            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this ,Registration.class);
                startActivity(intent);
            }
        });



    }


}
