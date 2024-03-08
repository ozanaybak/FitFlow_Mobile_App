package com.example.cs310frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.AddExerciseViewHolder> {
    private List<Exercises> exerciseList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemCheck(Exercises exercise);
        void onItemUncheck(Exercises exercise);
    }

    public AddExerciseAdapter(List<Exercises> exerciseList, OnItemClickListener listener) {
        this.exerciseList = exerciseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_add, parent, false);
        return new AddExerciseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExerciseViewHolder holder, int position) {
        Exercises exercise = exerciseList.get(position);
        holder.titleTextView.setText(exercise.getTitle());
        holder.bodyPartTextView.setText(exercise.getBodyPart());
        holder.exerciseCheckBox.setOnCheckedChangeListener(null);
        holder.exerciseCheckBox.setChecked(exercise.isSelected());
        holder.exerciseCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            exercise.setSelected(isChecked);
            if (isChecked) {
                listener.onItemCheck(exercise);
            } else {
                listener.onItemUncheck(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class AddExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView bodyPartTextView;
        CheckBox exerciseCheckBox;

        public AddExerciseViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.exerciseTitle);
            bodyPartTextView = itemView.findViewById(R.id.exerciseBodyPart);
            exerciseCheckBox = itemView.findViewById(R.id.exerciseCheckBox);
        }
    }
}