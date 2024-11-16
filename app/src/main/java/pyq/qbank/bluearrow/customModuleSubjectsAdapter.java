package pyq.qbank.bluearrow;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class customModuleSubjectsAdapter extends RecyclerView.Adapter<customModuleSubjectsAdapter.subjectCustomModuleViewHolder> {

    public List<String> selectedSubjectList;
    List<String> subjectList;
    private transferSubjectData subjectData;
    private loadChapterRecycler chapterListener;


    public customModuleSubjectsAdapter(List<String> subjectList, transferSubjectData subjectData, loadChapterRecycler chapterListener) {
        this.subjectList = subjectList;
        this.selectedSubjectList = new ArrayList<>();
        this.subjectData = subjectData;
        this.chapterListener = chapterListener;
    }

    @NonNull
    @Override
    public customModuleSubjectsAdapter.subjectCustomModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_module_subject, parent, false);

        return new subjectCustomModuleViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull customModuleSubjectsAdapter.subjectCustomModuleViewHolder holder, int position) {

        String red = subjectList.get(position);

        holder.subjectName.setText("◀  ️"+red);

        holder.subjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chapterListener.loadChapterRecycler(red);
                holder.subjectName.setText(Html.fromHtml("<u><b>"+red+"</b></u?",Html.FROM_HTML_MODE_LEGACY));



            }
        });


        holder.arrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (selectedSubjectList.contains(red)) {
                    selectedSubjectList.remove(red);
                    holder.subjectName.setBackgroundColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.white)); // Set the selected color
                    holder.subjectName.setTextColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.black));
                    holder.arrow.setTextColor(ContextCompat.getColor(holder.arrow.getContext(), R.color.primary));
                    holder.arrow.setBackgroundColor(ContextCompat.getColor(holder.arrow.getContext(), R.color.white));
                    holder.extra.setBackgroundColor(ContextCompat.getColor(holder.extra.getContext(), R.color.white));

                } else {
                    // If the tag is not selected, add it to the list and change the button color
                    selectedSubjectList.add(red);
                    holder.subjectName.setBackgroundColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.primary)); // Set the selected color
                    holder.subjectName.setTextColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.white));
                    holder.arrow.setTextColor(ContextCompat.getColor(holder.arrow.getContext(), R.color.white));
                    holder.arrow.setBackgroundColor(ContextCompat.getColor(holder.arrow.getContext(), R.color.primary));
                    holder.extra.setBackgroundColor(ContextCompat.getColor(holder.extra.getContext(), R.color.primary));
// Set the selected text color
                }

                subjectData.transferSubjects(selectedSubjectList);

            }
        });


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }


    public interface transferSubjectData {
        void transferSubjects(List<String> red);

    }

    public interface loadChapterRecycler {

        void loadChapterRecycler(String subjectName);
    }


    public class subjectCustomModuleViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        Button arrow;
        LinearLayout extra;

        public subjectCustomModuleViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.customModuleSubjectName);
            arrow = itemView.findViewById(R.id.customModuleArrow);
            extra = itemView.findViewById(R.id.extraCustomSubject);

        }
    }
}
