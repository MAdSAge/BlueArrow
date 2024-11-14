package pyq.qbank.bluearrow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class filterAdapter extends RecyclerView.Adapter<filterAdapter.filterViewHolder> {

    private List<String> topicList;
    private Set<String> checkedItems = new HashSet<>();

    public filterAdapter(List<String> topicList) {
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public filterAdapter.filterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter, parent, false);

        return new filterAdapter.filterViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull filterAdapter.filterViewHolder holder, int position) {

        String topic = topicList.get(position);
        holder.checkBox.setText(topic);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedItems.add(topic);
            } else {
                checkedItems.remove(topic);
            }
        });

    }


    public Set<String> getCheckedItems() {
        return checkedItems;
    }


    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class filterViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public filterViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);

        }
    }
}
