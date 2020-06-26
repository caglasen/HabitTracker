package com.malik.roomdeneme3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    private List<Alarm> alarms = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new AlarmHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {


        Alarm currentAlarm = alarms.get(position);
        holder.textViewTitle.setText(currentAlarm.getDate());
        holder.textViewDescription.setText(currentAlarm.getName());
        holder.textViewTime.setText(currentAlarm.getDateTime());

    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    public Alarm getAlarmAt(int position) {
        return alarms.get(position);
    }

    class AlarmHolder extends RecyclerView.ViewHolder {


        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewTime;

        public AlarmHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewTime=itemView.findViewById(R.id.timetextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(alarms.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Alarm alarm);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
