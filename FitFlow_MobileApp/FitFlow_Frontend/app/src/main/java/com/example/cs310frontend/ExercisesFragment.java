package com.example.cs310frontend;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class ExercisesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExercisesAdapter exercisesAdapter;
    private List<Exercises> exerciseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        exerciseList = new ArrayList<>();
        loadExercises();

        exercisesAdapter = new ExercisesAdapter(exerciseList);
        recyclerView.setAdapter(exercisesAdapter);

        exercisesAdapter.setOnItemClickListener(new ExercisesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercises exercise) {
                CustomExerciseDialogFragment dialog = new CustomExerciseDialogFragment(exercise);
                dialog.show(getParentFragmentManager(), "CustomExerciseDialogFragment");
            }
        });

        return view;
    }

    private void loadExercises() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HttpURLConnection.HTTP_OK) {
                    // Update the RecyclerView adapter
                    exercisesAdapter = new ExercisesAdapter(exerciseList);
                    recyclerView.setAdapter(exercisesAdapter);
                } else {
                    Log.d("ExercisesFragment", "GET request not worked");
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
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

                        // Parse the JSON response and create a list of Exercises objects
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String bodyPart = jsonObject.getString("bodyPart");
                            exerciseList.add(new Exercises(name, bodyPart, id));
                        }

                        // Send a message to the handler
                        handler.sendEmptyMessage(HttpURLConnection.HTTP_OK);
                    } else {
                        Log.d("ExercisesFragment", "GET request not worked. Response code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
