package com.malik.roomdeneme3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReportingScreenAdapter extends RecyclerView.Adapter<ReportingScreenAdapter.ReportingScreentHolder> {
    public interface OnItemClickListener {
        void onItemClick(HobbyCheckmark hobby);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<Checkmark> checkmarks = new ArrayList<>();
    private OnItemClickListener listener;
    private List<HobbyCheckmark> hobbies = new ArrayList<>();
    View textViewAverage;
    View coloredButton;

    public void setHobbies(List<HobbyCheckmark> hobbies) {
        this.hobbies = hobbies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportingScreentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_reportingscreen, parent, false); //??
        return new ReportingScreentHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull ReportingScreentHolder holder, int position) {
        HobbyCheckmark currentHobby = hobbies.get(position);
        holder.textViewTitle.setTextColor(currentHobby.clr);
        holder.textViewTitle.setText(currentHobby.title);
        holder.textViewTime.setText(String.valueOf(currentHobby.time));
        holder.totalCheckmarkDuration.setText(String.valueOf(currentHobby.duration));
        holder.itemView.setBackgroundColor(currentHobby.clr);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public HobbyCheckmark getHobbyAt(int position) {
        return hobbies.get(position);
    }

    class ReportingScreentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewTime;
        private TextView totalCheckmarkDuration;

        public ReportingScreentHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_habit_name);
            textViewTime = itemView.findViewById(R.id.textView_daily_goal);
            textViewAverage = itemView.findViewById(R.id.textView_daily_goal);
            coloredButton = itemView.findViewById(R.id.button4);

            //totalCheckmarkDuration = itemView.findViewById(R.id.totalCheckmarkDuration);
        }
    }

}
