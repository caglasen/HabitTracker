package com.malik.roomdeneme3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HobbyAdapter extends RecyclerView.Adapter<HobbyAdapter.HobbyHolder> {
    public interface OnItemClickListener {
        void onItemClick(HobbyCheckmark hobby);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnAddCheckmarkButtonClickListener(OnItemClickListener listener) {
        this.addCheckmarkButtonListener = listener;
    }

    private List<HobbyCheckmark> hobbies = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemClickListener addCheckmarkButtonListener;

    @NonNull
    @Override
    public HobbyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hobby_item, parent, false);
        return new HobbyHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull HobbyHolder holder, int position) {

        //Checkmark checkmark = hobbies.get(position).getCheckmark();
        HobbyCheckmark currentHobby = hobbies.get(position);
        holder.textViewTitle.setTextColor(currentHobby.clr);
        holder.textViewTitle.setText(currentHobby.title);
        holder.textViewDescription.setText(currentHobby.description);
        holder.textViewTime.setText(String.valueOf(currentHobby.time));
        holder.textViewPriority.setText(String.valueOf(currentHobby.priority));
        holder.totalCheckmarkDuration.setText(String.valueOf(currentHobby.duration));

    }

    @Override
    public int getItemCount() {
        return hobbies.size();
    }

    public void setHobbies(List<HobbyCheckmark> hobbies) {
        this.hobbies = hobbies;
        notifyDataSetChanged();
    }

    public HobbyCheckmark getHobbyAt(int position) {
        return hobbies.get(position);
    }

    class HobbyHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewTime;
        private TextView totalCheckmarkDuration;

        public HobbyHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            totalCheckmarkDuration = itemView.findViewById(R.id.totalCheckmarkDuration);

            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(hobbies.get(position));
                    }
                }
            });

            Button addCheckmarkButton = (Button) itemView.findViewById(R.id.addCheckmarkButton);
            addCheckmarkButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if (addCheckmarkButtonListener != null && position != RecyclerView.NO_POSITION) {
                        addCheckmarkButtonListener.onItemClick(hobbies.get(position));
                    }
                }
            } );


        }
    }

}
