package pyq.qbank.bluearrow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//TODO: add a filter based on the lesson in chapter view
public class chapter_model_adapter extends RecyclerView.Adapter<chapter_model_adapter.chapterViewHolder>{

    private List<chapter_model> topicList;

    public chapter_model_adapter(List<chapter_model> topicList) {
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public chapter_model_adapter.chapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_layout, parent, false);
        return new chapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull chapter_model_adapter.chapterViewHolder holder, int position) {
        chapter_model topic = topicList.get(position);

        holder.title.setText(topic.getChapterName());
        holder.questionCount.setText(String.valueOf(topic.getChapterQuestionCount())+" Q");
        holder.progressBar.setProgress(topic.getProgress());
        holder.ribbonTextView.setText("Topic: "+topic.getLesson());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), mcq_solving.class);
//                intent.putExtra("subjectName", topic.getSubjectName());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class chapterViewHolder extends RecyclerView.ViewHolder {

        TextView title,ribbonTextView;
        TextView questionCount;
        ProgressBar progressBar;

        public chapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chapterTitle);
            questionCount = itemView.findViewById(R.id.cahpterQuestions);
            progressBar = itemView.findViewById(R.id.chapterProgressIndicator);
            ribbonTextView = itemView.findViewById(R.id.ribbonTextView);

        }
    }
}
