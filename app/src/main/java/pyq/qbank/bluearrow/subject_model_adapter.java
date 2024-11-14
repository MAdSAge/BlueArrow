package pyq.qbank.bluearrow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class subject_model_adapter extends RecyclerView.Adapter<subject_model_adapter.ViewHolder> {

    private List<subject_model> topicList;

    public subject_model_adapter(List<subject_model> topicList) {
        this.topicList = topicList;
    }


    @NonNull
    @Override
    public subject_model_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_layout, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull subject_model_adapter.ViewHolder holder, int position) {

        subject_model topic = topicList.get(position);
        holder.title.setText(topic.getSubjectName());
        holder.icon.setImageResource(topic.getSubjectImage());
        holder.chapters_count.setText(String.valueOf(topic.getSubjectTopicCount())+" Chapters");
        holder.progressIndicator.setProgress(topic.getProgress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), qbankChapterSelection.class);
//                intent.putExtra("subjectName", topic.getSubjectName());
                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title,chapters_count;
        ProgressBar progressIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            chapters_count = itemView.findViewById(R.id.questions);
            progressIndicator = itemView.findViewById(R.id.progressIndicator);

        }
    }
}
