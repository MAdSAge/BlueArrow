package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.objectbox.Box;

public class fragOneAdapter extends RecyclerView.Adapter<fragOneAdapter.fragOneViewHolder> {

    List<entityMcq> mcqList;
    fragLoader f2;

    public fragOneAdapter(List<entityMcq> mcqList, fragLoader f2) {
        this.mcqList = mcqList;
        this.f2 = f2;
    }

    public List<entityMcq> getMcqList() {
        return mcqList;
    }

    public void setMcqList(List<entityMcq> mcqList) {
        this.mcqList = mcqList;
    }

    @NonNull
    @Override
    public fragOneAdapter.fragOneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_mcq_list_block, parent, false);
        return new fragOneAdapter.fragOneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull fragOneAdapter.fragOneViewHolder holder, int position) {
        entityMcq mcq = mcqList.get(position);

        holder.QuestionTitle.setText(mcq.getQuestion_title());
        holder.qSubject.setText(mcq.getSubject() + " - ");
        holder.qLesson.setText(mcq.getChapter());
        holder.qbookMark.setImageResource(mcq.isBookMarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);

        holder.QuestionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f2.loadFrag(position);
            }
        });

        holder.qbookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcq.setBookMarked(!mcq.isBookMarked());
                Box<entityMcq> red = boxStore.boxFor(entityMcq.class);
                red.put(mcq);
                holder.qbookMark.setImageResource(mcq.isBookMarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);


            }});



    }

    @Override
    public int getItemCount() {
        return mcqList.size();
    }

    public class fragOneViewHolder extends RecyclerView.ViewHolder {

        TextView QuestionTitle, qSubject, qLesson;
        ImageView qbookMark;


        public fragOneViewHolder(@NonNull View itemView) {
            super(itemView);
            QuestionTitle = itemView.findViewById(R.id.QuestionTitle);
            qSubject = itemView.findViewById(R.id.qSubject);
            qLesson = itemView.findViewById(R.id.qLesson);
            qbookMark = itemView.findViewById(R.id.qbookMark);
        }
    }

}
