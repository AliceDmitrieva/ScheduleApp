package ru.dmitrieva.scheduleapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.dmitrieva.scheduleapp.R;
import ru.dmitrieva.scheduleapp.models.Section;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ScheduleViewHolder> {

    private List<Section> sectionItemList;

    public DayAdapter(List<Section> sectionItemList) {
        this.sectionItemList = sectionItemList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_model, parent, false);
        ScheduleViewHolder holder = new ScheduleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bindProduct(sectionItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return sectionItemList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView name, startTime, endTime, teacher, place, description;
        Section section;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            teacher = itemView.findViewById(R.id.teacher);
            place = itemView.findViewById(R.id.place);
            description = itemView.findViewById(R.id.description);
        }

        void bindProduct(Section section) {
            this.section = section;
            name.setText(section.getName());
            startTime.setText(section.getStartTime());
            endTime.setText(section.getEndTime());
            teacher.setText(section.getTeacher());
            place.setText(section.getPlace());
            description.setText(section.getDescription());
        }
    }
}
