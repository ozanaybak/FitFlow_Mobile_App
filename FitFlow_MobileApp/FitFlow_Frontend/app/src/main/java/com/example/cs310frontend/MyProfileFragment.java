package com.example.cs310frontend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyProfileFragment extends Fragment{

    private RecyclerView recyclerView;
    private WorkoutsAdapter workoutsAdapter;
    private List<Workouts> userWorkoutList; // Replace with the appropriate data type for user workouts

    // User info views
    private TextView nameTextView;
    private TextView userWeight;
    private TextView userAge;
    private TextView userHeight;
    private TextView goals;
    private TextView level;

    private String userEmail;

    public MyProfileFragment(String userEmail) {
        this.userEmail = userEmail;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        // Initialize the TextViews and other views
        nameTextView = view.findViewById(R.id.tvUserName);
        userWeight = view.findViewById(R.id.userWeight);
        userAge = view.findViewById(R.id.userAge);
        userHeight = view.findViewById(R.id.userHeight);
        goals = view.findViewById(R.id.goals);
        level = view.findViewById(R.id.level);

        Button addWorkoutButton = view.findViewById(R.id.addWorkoutsButton);
        addWorkoutButton.setOnClickListener(v -> openAddWorkoutActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userWorkoutList = new ArrayList<>();
        workoutsAdapter = new WorkoutsAdapter(userWorkoutList);
        recyclerView.setAdapter(workoutsAdapter);

        workoutsAdapter.setOnItemClickListener(new WorkoutsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Workouts workout) {
                Intent intent = new Intent(getActivity(), WorkoutDetailActivity.class);
                intent.putExtra("workoutId", workout.getId());
                intent.putExtra("creator", workout.getCreator());
                startActivity(intent);
            }
        });

        fetchUserData();

        return view;
    }


    private void fetchUserData() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HttpURLConnection.HTTP_OK) {
                    Users user = (Users) msg.obj;
                    nameTextView.setText(user.getName());
                    userWeight.setText("Weight:\n" + user.getWeight());
                    userAge.setText("Age:\n" + user.getAge());
                    userHeight.setText("Height:\n" + user.getHeight());
                    goals.setText(user.getGoals());
                    level.setText(user.getFitnessLevel());
                } else {
                    Log.d("MyProfileFragment", "GET request not worked");
                }
            }
        };

        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/users/" + userEmail);
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

                    // Parse the JSON response and update the user
                    JSONObject jsonObject = new JSONObject(response);
                    String name = jsonObject.getString("name");
                    int age = jsonObject.getInt("age");
                    double weight = jsonObject.getDouble("weight");
                    double height = jsonObject.getDouble("height");
                    String goals = jsonObject.getString("goals");
                    String fitnessLevel = jsonObject.getString("fitnessLevel");

                    Users user = new Users(name, age, weight, height, goals, fitnessLevel);

                    // Send a message to the handler
                    Message msg = Message.obtain();
                    msg.what = HttpURLConnection.HTTP_OK;
                    msg.obj = user;
                    handler.sendMessage(msg);
                } else {
                    Log.d("MyProfileFragment", "GET request not worked. Response code: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        refreshUserWorkouts();
    }

    private void openAddWorkoutActivity() {
        Intent intent = new Intent(getActivity(), AddWorkoutActivity.class);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }


    private void refreshUserWorkouts() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/users/" + userEmail + "/workoutsdto");
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
                    userWorkoutList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("name");
                        String creator = jsonObject.getString("publisherName");
                        String workoutId = jsonObject.getString("id");
                        userWorkoutList.add(new Workouts(title, creator, workoutId));
                    }

                    getActivity().runOnUiThread(() -> workoutsAdapter.notifyDataSetChanged());
                } else {
                    Log.d("MyProfileFragment", "GET request not worked. Response code: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUserWorkouts();
    }
}