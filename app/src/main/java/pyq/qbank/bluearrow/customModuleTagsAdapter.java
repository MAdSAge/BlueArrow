package pyq.qbank.bluearrow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class customModuleTagsAdapter extends RecyclerView.Adapter<customModuleTagsAdapter.subjectCustomModuleViewHolder> {
    List<String> subjectList;
    List<String> selectedSubjectList;

    public customModuleTagsAdapter(List<String> subjectList) {
        this.subjectList = subjectList;
        this.selectedSubjectList = new ArrayList<>();
    }

    @NonNull
    @Override
    public customModuleTagsAdapter.subjectCustomModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_module_subject, parent, false);

        return new subjectCustomModuleViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull customModuleTagsAdapter.subjectCustomModuleViewHolder holder, int position) {

        String red = subjectList.get(position);

        holder.subjectName.setText(red);

        holder.subjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tagText = holder.subjectName.getText().toString();

                if (selectedSubjectList.contains(tagText)) {
                    // If the tag is already selected, remove it from the list and reset the button color
                    selectedSubjectList.remove(tagText);
                    holder.subjectName.setBackgroundColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.white)); // Set the original color
                    holder.subjectName.setTextColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.black));

                    holder.arrow.setTextColor(ContextCompat.getColor(holder.arrow.getContext(),R.color.primary));
                    holder.arrow.setBackgroundColor(ContextCompat.getColor(holder.arrow.getContext(),R.color.white));
                    holder.extra.setBackgroundColor(ContextCompat.getColor(holder.extra.getContext(),R.color.white));
                    // Set the original text color
                } else {
                    // If the tag is not selected, add it to the list and change the button color
                    selectedSubjectList.add(tagText);
                    holder.subjectName.setBackgroundColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.primary)); // Set the selected color
                    holder.subjectName.setTextColor(ContextCompat.getColor(holder.subjectName.getContext(), R.color.white));
                    holder.arrow.setTextColor(ContextCompat.getColor(holder.arrow.getContext(),R.color.white));
                    holder.arrow.setBackgroundColor(ContextCompat.getColor(holder.arrow.getContext(),R.color.primary));
                    holder.extra.setBackgroundColor(ContextCompat.getColor(holder.extra.getContext(),R.color.primary));
// Set the selected text color
                }

            }
        });

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), red+" Clicked", Toast.LENGTH_SHORT).show();

            }});


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
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
