package com.example.cs310frontend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workout_activity);

        // Initialize your UI elements
        EditText nameEditText = findViewById(R.id.nameText);
        EditText levelEditText = findViewById(R.id.levelText);
        EditText calsBurnedEditText = findViewById(R.id.calsBurnedText);
        EditText descriptionEditText = findViewById(R.id.descriptionText);
        Button submitButton = findViewById(R.id.submitButton); // Replace with your actual submit button's ID

        submitButton.setOnClickListener(v -> {
            // Retrieve user input from the UI elements
            String name = nameEditText.getText().toString();
            String level = levelEditText.getText().toString();
            String calsBurnedString = calsBurnedEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            final double[] calsBurned = new double[1];
            if (!calsBurnedString.isEmpty()) {
                try {
                    calsBurned[0] = Double.parseDouble(calsBurnedString);
                } catch (NumberFormatException e) {
                    // Handle the case where the string cannot be parsed to a double
                    Toast.makeText(this, "Please enter a valid number for calories burned", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Get the email from the intent
            String email = getIntent().getStringExtra("email");

            Handler handler = new Handler(Looper.getMainLooper());

            new Thread(() -> {
                try {
                    URL url = new URL("http://10.0.2.2:8080/api/workouts/create");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    // Build the query string
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("name", name)
                            .appendQueryParameter("email", email)
                            .appendQueryParameter("level", level)
                            .appendQueryParameter("calsBurned", String.valueOf(calsBurned[0]))
                            .appendQueryParameter("description", description);
                    String query = builder.build().getEncodedQuery();

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();

                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    String response = ""; // Declare response string outside the if block
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder builder2 = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder2.append(line);
                        }
                        response = builder2.toString(); // Correctly capture the response
                    }

                    String finalResponse = response;
                    handler.post(() -> {
                        if (responseCode != HttpURLConnection.HTTP_CREATED) {
                            Toast.makeText(this, "Error: " + responseCode, Toast.LENGTH_SHORT).show();
                        } else {
                            String workoutId = extractWorkoutIdFromResponse(finalResponse); // Implement this method
                            if (workoutId != null) {
                                Intent intent = new Intent(this, AddExerciseActivity.class);
                                intent.putExtra("workoutId", workoutId);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Failed to retrieve workout ID", Toast.LENGTH_SHORT).show();
                            }
                        }
                        finish(); // Finish the activity after processing
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private String extractWorkoutIdFromResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            // Assuming the workout ID is stored in a field named "id"
            return jsonObject.getString("id");
        } catch (Exception e){
            Log.e("AddWorkoutActivity", "Error parsing workout ID from response", e);
            return null;
        }
    }
}