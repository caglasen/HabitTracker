package com.malik.roomdeneme3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HobbyAdapterReporting extends RecyclerView.Adapter<HobbyAdapterReporting.HobbyHolder> {
    public interface OnItemClickListener {
        void onItemClick(HobbyCheckmark hobby);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<HobbyCheckmark> hobbies = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public HobbyAdapterReporting.HobbyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sade_hobby_item, parent, false);
        return new HobbyHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull HobbyHolder holder, int position) {
        //Checkmark checkmark = hobbies.get(position).getCheckmark();
        HobbyCheckmark currentHobby = hobbies.get(position);
        holder.textViewTitle.setTextColor(currentHobby.clr);
        holder.textViewTitle.setText(currentHobby.title);
        holder.textViewDescription.setText(currentHobby.description);
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


        public HobbyHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(hobbies.get(position));
                    }
                }
            });

        }
    }

}
