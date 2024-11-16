package pyq.qbank.bluearrow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class customModuleTagsAdapter extends RecyclerView.Adapter<customModuleTagsAdapter.tagsViewHolder> {
     List<String> tagsList;
     List<String> selectedTags;
     private transferTagData listener;


    public interface transferTagData{
        void transferTags(List<String> selectedTags);
    }

    public customModuleTagsAdapter(List<String> tagsList, transferTagData listener) {
        this.tagsList = tagsList;
        this.selectedTags = new ArrayList<>();
        this.listener = listener;
    }


    @NonNull
    @Override
    public customModuleTagsAdapter.tagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_layout, parent, false);

        return new tagsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull customModuleTagsAdapter.tagsViewHolder holder, int position) {

        String red = tagsList.get(position);
        holder.tagButton.setText(red);

        holder.tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagText = holder.tagButton.getText().toString();
                if (selectedTags.contains(tagText)) {
                    // If the tag is already selected, remove it from the list and reset the button color
                    selectedTags.remove(tagText);
                    holder.tagButton.setBackgroundColor(ContextCompat.getColor(holder.tagButton.getContext(), R.color.card_border)); // Set the original color
                    holder.tagButton.setTextColor(ContextCompat.getColor(holder.tagButton.getContext(), R.color.black)); // Set the original text color
                } else {
                    // If the tag is not selected, add it to the list and change the button color
                    selectedTags.add(tagText);
                    holder.tagButton.setBackgroundColor(ContextCompat.getColor(holder.tagButton.getContext(), R.color.light_green)); // Set the selected color
                    holder.tagButton.setTextColor(ContextCompat.getColor(holder.tagButton.getContext(), R.color.white)); // Set the selected text color
                }
                listener.transferTags(selectedTags);
            }

        });

    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }


    public class tagsViewHolder extends RecyclerView.ViewHolder {
        Button tagButton;
        public tagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tagButton = itemView.findViewById(R.id.tag_button);

        }
    }


}
