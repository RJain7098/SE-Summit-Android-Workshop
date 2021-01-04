package com.summit.summitproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.summit.summitproject.prebuilt.login.LoginListener;
import com.summit.summitproject.prebuilt.login.LoginManager;
import com.summit.summitproject.prebuilt.model.Transaction;

import java.util.ArrayList;

/**
 * The first screen of our app. Takes in a username and password and interacts with the
 * {@link LoginManager} to retrieve user details. Also allows the user to check "Remember Me"
 * to locally store and recall credentials.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button signIn;
    private ProgressBar progressBar;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String inputtedUsername = username.getText().toString();
            String inputtedPassword = password.getText().toString();
            boolean enableButton = inputtedUsername.length() > 0 && inputtedPassword.length() > 0;
            signIn.setEnabled(enableButton);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    /**
     * Called the first time an Activity is created, but before any UI is shown to the user.
     * Prepares the layout and assigns UI widget variables.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //R  short for "resources"

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        progressBar = findViewById(R.id.progressBar);

        signIn.setEnabled(false);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputtedUsername = username.getText().toString();
                String inputtedPassword = password.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                LoginManager loginManager = new LoginManager(inputtedUsername, inputtedPassword, new LoginListener() {
                    @Override
                    public void onLoginSuccess(String name, String cardNum, ArrayList<Transaction> transactions) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(LoginActivity.this, "Logged in as: " + name, Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(LoginActivity.this, SummaryActivity.class); //go to the second activity screen
                        intent.putExtra("name", name);
                        intent.putExtra("cardNum", cardNum);
                        intent.putExtra("transactions", transactions);
                        startActivity(intent);
                    }

                    @Override
                    public void onLoginError(Exception exception) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(LoginActivity.this, "Login failed: " + exception.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                loginManager.execute();
            }
        });

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
    }

}
