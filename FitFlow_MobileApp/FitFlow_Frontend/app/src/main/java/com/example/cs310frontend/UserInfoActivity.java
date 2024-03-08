package com.example.cs310frontend;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserInfoActivity extends AppCompatActivity {

    private EditText ageInput, heightInput, weightInput;
    private Spinner genderSpinner, fitnessLevelSpinner, goalsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity_layout);

        String userEmail = getIntent().getStringExtra("userEmail");

        ageInput = findViewById(R.id.ageInput);
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        genderSpinner = findViewById(R.id.genderSpinner);
        fitnessLevelSpinner = findViewById(R.id.fitnessLevelSpinner);
        goalsSpinner = findViewById(R.id.goalsSpinner);

        Button submitInfoButton = findViewById(R.id.submitInfoButton);

        populateSpinner(genderSpinner, R.array.gender_array);
        populateSpinner(fitnessLevelSpinner, R.array.fitness_level_array);
        populateSpinner(goalsSpinner, R.array.goals_array);

        submitInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = ageInput.getText().toString();
                String height = heightInput.getText().toString();
                String weight = weightInput.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();
                String fitnessLevel = fitnessLevelSpinner.getSelectedItem().toString();
                String goals = goalsSpinner.getSelectedItem().toString();

                // Check if all fields are filled
                if (age.isEmpty() || height.isEmpty() || weight.isEmpty()) {
                    Toast.makeText(UserInfoActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if height is valid
                double heightValue = Double.parseDouble(height);
                if (heightValue < 1.00 || heightValue > 3.00) {
                    Toast.makeText(UserInfoActivity.this, "Height should be between 1.00 and 3.00", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (genderSpinner.getSelectedItemPosition() == 0 || fitnessLevelSpinner.getSelectedItemPosition() == 0 || goalsSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(UserInfoActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if weight is valid
                double weightValue = Double.parseDouble(weight);
                if (weightValue < 30 || weightValue > 300) {
                    Toast.makeText(UserInfoActivity.this, "Weight should be between 30 and 300", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if age is valid
                int ageValue = Integer.parseInt(age);
                if (ageValue < 18 || ageValue > 99) {
                    Toast.makeText(UserInfoActivity.this, "Age should be between 18 and 99", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (genderSpinner.getSelectedItemPosition() == 0 || fitnessLevelSpinner.getSelectedItemPosition() == 0 || goalsSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(UserInfoActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == HttpURLConnection.HTTP_OK) {
                            Intent intent = new Intent(UserInfoActivity.this, HomeActivity.class);
                            intent.putExtra("userEmail", userEmail);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UserInfoActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.0.2.2:8080/api/users/updateInfo");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("PUT");
                            String postData = "email=" + URLEncoder.encode(userEmail, "UTF-8") +
                                    "&age=" + URLEncoder.encode(age, "UTF-8") +
                                    "&height=" + URLEncoder.encode(height, "UTF-8") +
                                    "&weight=" + URLEncoder.encode(weight, "UTF-8") +
                                    "&gender=" + URLEncoder.encode(gender, "UTF-8") +
                                    "&fitnessLevel=" + URLEncoder.encode(fitnessLevel, "UTF-8") +
                                    "&goals=" + URLEncoder.encode(goals, "UTF-8");

                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn.setDoOutput(true);

                            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                            out.write(postData);
                            out.close();

                            int responseCode = conn.getResponseCode();
                            Log.d("UserInfoActivity", "Response code: " + responseCode);
                            handler.obtainMessage(responseCode).sendToTarget();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void populateSpinner(Spinner spinner, int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.custom_spinner_item, getResources().getTextArray(arrayResourceId)) {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setBackground(getResources().getDrawable(R.drawable.userinfo_spinner_item_background));
                return view;
            }
        };
        spinner.setAdapter(adapter);
    }


}
