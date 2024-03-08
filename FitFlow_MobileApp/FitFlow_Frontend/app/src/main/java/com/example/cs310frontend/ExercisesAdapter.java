package com.example.cs310frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {
    private List<Exercises> exerciseList;
    private OnItemClickListener listener; // Add this line


    // Add this interface
    public interface OnItemClickListener {
        void onItemClick(Exercises exercise);
    }

    public ExercisesAdapter(List<Exercises> exerciseList) {
        this.exerciseList = exerciseList;
    }


    // Add this method
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercises exercise = exerciseList.get(position);
        holder.titleTextView.setText(exercise.getTitle());
        holder.bodyPartTextView.setText(exercise.getBodyPart());

        // Set the click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of CustomExerciseDialogFragment with the clicked exercise
                CustomExerciseDialogFragment dialogFragment = new CustomExerciseDialogFragment(exercise);

                // Show the dialog fragment
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                dialogFragment.show(fragmentManager, "CustomExerciseDialogFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView bodyPartTextView;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.exerciseTitle);
            bodyPartTextView = itemView.findViewById(R.id.exerciseBodyPart);
        }
    }
}