package pyq.qbank.bluearrow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class customModuleChapterAdapter extends RecyclerView.Adapter<customModuleChapterAdapter.chapterCustomModuleViewHolder> {

    List<String> chapterList;
    List<String> selectedChapterList;
    transferChapterData listener;
    String subjectName;
    Map<String,List<String>> receivedChapterList;






    public interface transferChapterData {
        void transferChapters(String subjectName, List<String> data);

    }

    public customModuleChapterAdapter(List<String> chapterList, transferChapterData listener, String subjectName, Map<String, List<String>> receivedChapterList) {
        this.chapterList = chapterList;
        this.selectedChapterList = new ArrayList<>();
        this.listener = listener;
        this.subjectName = subjectName;
        this.receivedChapterList = receivedChapterList;
    }

    @NonNull
    @Override
    public customModuleChapterAdapter.chapterCustomModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        List<String> chapters = receivedChapterList.get(subjectName);
        selectedChapterList = (chapters != null) ? chapters : new ArrayList<>();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_select_chekboxes, parent, false);
        return new chapterCustomModuleViewHolder(itemView);
    }


    public class chapterCustomModuleViewHolder extends RecyclerView.ViewHolder {

        TextView chapterName;
        CheckBox checkBox;

        public chapterCustomModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterName = itemView.findViewById(R.id.chapterNameSelector);
            checkBox = itemView.findViewById(R.id.checkChapterBox);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull customModuleChapterAdapter.chapterCustomModuleViewHolder holder, int position) {

        String Chapter_name = chapterList.get(position);

        List<String> rchapterList = receivedChapterList.get(subjectName);
        System.out.println(receivedChapterList);
        System.out.println(rchapterList+"hello her");

        if (rchapterList != null && rchapterList.contains(Chapter_name)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.chapterName.setText(Chapter_name);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    // Add chapter to selected list if checked
                    if (!selectedChapterList.contains(Chapter_name)) {
                        selectedChapterList.add(Chapter_name);
                    }
                } else {
                    // Remove chapter from selected list if unchecked
                    selectedChapterList.remove(Chapter_name);
                }
                listener.transferChapters(subjectName,selectedChapterList);
            }

        });



    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }


}
