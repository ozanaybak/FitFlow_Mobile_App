package com.example.cs310frontend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailActivity extends AppCompatActivity {
    private Workouts workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_detailed_activity);

        Intent intent = getIntent();
        String workoutId = intent.getStringExtra("workoutId");
        String creatorNative = intent.getStringExtra("creator");

        loadWorkoutDetails(workoutId, creatorNative);

        List<Exercises> exercises = new ArrayList<>();
        loadExercises(workoutId, exercises);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadWorkoutDetails(String workoutId, String creatorNative) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HttpURLConnection.HTTP_OK) {
                    // Update the UI
                    TextView titleTextView = findViewById(R.id.title);
                    TextView creatorTextView = findViewById(R.id.creator);
                    TextView levelTextView = findViewById(R.id.level);
                    TextView descriptionTextView = findViewById(R.id.description);
                    titleTextView.setText(workout.getTitle());
                    creatorTextView.setText(creatorNative);
                    levelTextView.setText(workout.getLevel());
                    descriptionTextView.setText(workout.getDescription());
                } else {
                    Log.d("WorkoutDetailActivity", "GET request not worked");
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8080/api/workouts/" + workoutId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        String response = builder.toString();

                        // Parse the JSON response and create a Workout object
                        JSONObject jsonObject = new JSONObject(response);
                        String title = jsonObject.getString("name");
                        String creator = jsonObject.getString("publisher");
                        String description = jsonObject.getString("description");
                        String calsBurned = jsonObject.getString("calsBurned");
                        String level = jsonObject.getString("level");
                        workout = new Workouts(title, workoutId, creator, description, calsBurned, level);

                        // Send a message to the handler
                        handler.sendEmptyMessage(HttpURLConnection.HTTP_OK);
                    } else {
                        Log.d("WorkoutDetailActivity", "GET request not worked. Response code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadExercises(String workoutId, List<Exercises> exercises) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HttpURLConnection.HTTP_OK) {
                    // Initialize RecyclerView
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    ExercisesAdapter adapter = new ExercisesAdapter(exercises);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(WorkoutDetailActivity.this));

                    adapter.setOnItemClickListener(new ExercisesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Exercises exercise) {
                            CustomExerciseDialogFragment dialog = new CustomExerciseDialogFragment(exercise);
                            dialog.show(getSupportFragmentManager(), "CustomExerciseDialogFragment");
                        }
                    });
                } else {
                    Log.d("WorkoutDetailActivity", "GET request not worked");
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8080/api/exercises/workout/" + workoutId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        String response = builder.toString();

                        // Parse the JSON response and add the exercises to the list
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("name");
                            String bodyPart = jsonObject.getString("bodyPart");
                            String exerciseID = jsonObject.getString("id");
                            exercises.add(new Exercises(title, bodyPart, exerciseID));
                        }

                        // Send a message to the handler
                        handler.sendEmptyMessage(HttpURLConnection.HTTP_OK);
                    } else {
                        Log.d("WorkoutDetailActivity", "GET request not worked. Response code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




}