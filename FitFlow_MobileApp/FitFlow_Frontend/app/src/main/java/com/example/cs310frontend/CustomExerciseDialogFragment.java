package com.example.cs310frontend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomExerciseDialogFragment extends DialogFragment {

    private Exercises exercise;

    public CustomExerciseDialogFragment(Exercises exercise) {
        this.exercise = exercise;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_exercises, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title);
        TextView bodyPart = view.findViewById(R.id.bodyPart);
        TextView level = view.findViewById(R.id.level);
        TextView type = view.findViewById(R.id.type);
        TextView sets = view.findViewById(R.id.sets);
        TextView reps = view.findViewById(R.id.reps);
        TextView description = view.findViewById(R.id.description);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HttpURLConnection.HTTP_OK) {
                    // Update the TextViews
                    title.setText(exercise.getTitle());
                    bodyPart.setText(exercise.getBodyPart());
                    level.setText(exercise.getLevel());
                    type.setText(exercise.getType());
                    sets.setText(String.valueOf(exercise.getSets()));
                    reps.setText(String.valueOf(exercise.getReps()));
                    description.setText(exercise.getDescription());
                } else {
                    Log.d("CustomExerciseDialogFragment", "GET request not worked");
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8080/api/exercises/" + exercise.getId());
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

                        // Parse the JSON response and update the exercise
                        JSONObject jsonObject = new JSONObject(response);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("name");
                        String bodyPart = jsonObject.getString("bodyPart");
                        int reps = jsonObject.getInt("reps");
                        int sets = jsonObject.getInt("sets");
                        String description = jsonObject.getString("description");
                        String level = jsonObject.getString("level");
                        String type = jsonObject.getString("type");
                        exercise = new Exercises(title, bodyPart, id, reps, sets, description, level, type);

                        // Send a message to the handler
                        handler.sendEmptyMessage(HttpURLConnection.HTTP_OK);
                    } else {
                        Log.d("CustomExerciseDialogFragment", "GET request not worked. Response code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return builder.create();
    }
}