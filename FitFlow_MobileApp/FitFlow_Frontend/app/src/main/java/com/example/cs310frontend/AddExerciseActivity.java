package com.example.cs310frontend;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddExerciseActivity extends AppCompatActivity {
    private List<Exercises> exercises = new ArrayList<>();
    private Set<Exercises> selectedExercises = new HashSet<>();
    private String workoutId;
    private RecyclerView exerciseRecyclerView;
    private AddExerciseAdapter addExercisesAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private List<Exercises> exerciseQueue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercises_workout);

        workoutId = getIntent().getStringExtra("workoutId");

        exerciseRecyclerView = findViewById(R.id.exerciseRecyclerView);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addExercisesAdapter = new AddExerciseAdapter(exercises, new AddExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemCheck(Exercises exercise) {
                selectedExercises.add(exercise);
            }

            @Override
            public void onItemUncheck(Exercises exercise) {
                selectedExercises.remove(exercise);
            }
        });
        exerciseRecyclerView.setAdapter(addExercisesAdapter);
        loadExercises();

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> submitExercises());
    }

    private void loadExercises() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/exercises/dto");
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
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String bodyPart = jsonObject.getString("bodyPart");
                        exercises.add(new Exercises(name, bodyPart, id));
                    }

                    new Handler(Looper.getMainLooper()).post(() -> {
                        addExercisesAdapter.notifyDataSetChanged();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void submitExercises() {
        Log.d("AddExerciseActivity", "submitExercises() called with: " + selectedExercises.size() + " exercises");
        exerciseQueue.addAll(selectedExercises);
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!exerciseQueue.isEmpty()) {
                    addExerciseToWorkout(exerciseQueue.remove(0));
                } else {
                    finish();
                }
            }
        };
        handler.post(runnable);
    }

    private void addExerciseToWorkout(Exercises exercise) {
        Log.d("AddExerciseActivity", "addExerciseToWorkout() called with: " + exercise.getTitle());
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/workouts/addExercise/" + workoutId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String body = "exerciseId=" + exercise.getId();
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(outputInBytes);
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> Toast.makeText(this, "Exercise added successfully", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Failed to add exercise", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handler.post(runnable);
            }
        }).start();
    }
}