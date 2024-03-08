package com.example.cs310frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SignUpActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity_layout);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button signUpButton = findViewById(R.id.buttonSignUp);

        // Set the 'enter' key to move to the next field
        nameInput.setOnEditorActionListener((v, actionId, event) -> {
            emailInput.requestFocus();
            return true;
        });

        emailInput.setOnEditorActionListener((v, actionId, event) -> {
            passwordInput.requestFocus();
            return true;
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Check if all fields are filled
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password should be longer than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if email is valid
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == HttpURLConnection.HTTP_CREATED) {
                            Intent intent = new Intent(SignUpActivity.this, UserInfoActivity.class);
                            intent.putExtra("userEmail", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.0.2.2:8080/api/users/signup");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            String postData = "email=" + URLEncoder.encode(email, "UTF-8") +
                                    "&password=" + URLEncoder.encode(password, "UTF-8") +
                                    "&name=" + URLEncoder.encode(name, "UTF-8");

                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn.setDoOutput(true);

                            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                            out.write(postData);
                            out.close();

                            int responseCode = conn.getResponseCode();
                            Log.d("SignUpActivity", "Response code: " + responseCode); // Added logging line
                            handler.obtainMessage(responseCode).sendToTarget();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}