package com.example.cs310frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder> {
    private List<Workouts> workoutList;
    private OnItemClickListener listener;

    public WorkoutsAdapter(List<Workouts> workoutList) {
        this.workoutList = workoutList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        final Workouts workout = workoutList.get(position);
        holder.titleTextView.setText(workout.getTitle());
        holder.creatorTextView.setText(workout.getCreator());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(workout);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView creatorTextView;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.workoutTitle);
            creatorTextView = itemView.findViewById(R.id.workoutCreator);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Workouts workout);
    }
}
