package pyq.qbank.bluearrow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//TODO: add a filter based on the lesson in chapter view
public class qbnakChapter_model_adapter extends RecyclerView.Adapter<qbnakChapter_model_adapter.chapterViewHolder>{

    private List<entityChapter> topicList;

    public qbnakChapter_model_adapter(List<entityChapter> topicList) {
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public qbnakChapter_model_adapter.chapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_layout, parent, false);
        return new chapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull qbnakChapter_model_adapter.chapterViewHolder holder, int position) {
        entityChapter topic = topicList.get(position);

        holder.title.setText(topic.getChapter());

        if(topic.isChapter_completed()){
            holder.checkMark.setVisibility(View.VISIBLE);
            String x =  "  ✅ "+topic.getCorrect_count() +" || " +
                    "  ❌ "+topic.getWrong_count() + " || " +
                    "  ⏩ "+topic.getSkip_count() ;
            holder.questionCount.setText(x);

        }else{
            holder.checkMark.setVisibility(View.INVISIBLE);
            holder.questionCount.setText(String.valueOf(topic.getMcq_count())+" Q");
        }



        holder.ribbonTextView.setText("Topic: "+topic.getLesson());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), qbank_mcq_solving.class);
                intent.putExtra("chapterName", topic.getChapter());
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
        ImageView checkMark;;

        public chapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chapterTitle);
            questionCount = itemView.findViewById(R.id.cahpterQuestions);
            checkMark = itemView.findViewById(R.id.checkMark);
            ribbonTextView = itemView.findViewById(R.id.ribbonTextView);

        }
    }

    public List<entityChapter> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<entityChapter> topicList) {
        this.topicList = topicList;
    }
}
