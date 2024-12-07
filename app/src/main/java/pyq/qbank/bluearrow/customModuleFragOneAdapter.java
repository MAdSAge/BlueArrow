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

public class customModuleFragOneAdapter extends RecyclerView.Adapter<customModuleFragOneAdapter.fragOneViewHolder> {

    List<entityCustomModuleMcq> mcqList;
    fragLoader f2;

    public customModuleFragOneAdapter(List<entityCustomModuleMcq> mcqList, fragLoader f2) {
        this.mcqList = mcqList;
        this.f2 = f2;
    }

    public List<entityCustomModuleMcq> getMcqList() {
        return mcqList;
    }

    public void setMcqList(List<entityCustomModuleMcq> mcqList) {
        this.mcqList = mcqList;
    }

    @NonNull
    @Override
    public customModuleFragOneAdapter.fragOneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_mcq_list_block, parent, false);
        return new customModuleFragOneAdapter.fragOneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull customModuleFragOneAdapter.fragOneViewHolder holder, int position) {
        entityCustomModuleMcq mcq = mcqList.get(position);

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
                Box<entityCustomModuleMcq> red = boxStore.boxFor(entityCustomModuleMcq.class);
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
