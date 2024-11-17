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
import java.util.Map;

public class bookMarkSelectionAdapter extends RecyclerView.Adapter<bookMarkSelectionAdapter.bookMarkSelectionViewHolder> {

    List<String> subjects;


    public bookMarkSelectionAdapter(List<String> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public bookMarkSelectionAdapter.bookMarkSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_layout, parent, false);
        return new bookMarkSelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookMarkSelectionAdapter.bookMarkSelectionViewHolder holder, int position) {

        String subject = subjects.get(position);
        holder.title.setText(subject);
        holder.questions.setText(getNumberOfBookMarks(subject) + " BookMarked");
        holder.icon.setImageResource(R.drawable.chevron);

//        set on click lister to entire holder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), mcq_solving.class);
                intent.putExtra("testMode",false);
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class bookMarkSelectionViewHolder extends RecyclerView.ViewHolder {
        TextView title,questions;
        ImageView icon;


        public bookMarkSelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            questions = itemView.findViewById(R.id.questions);
            icon = itemView.findViewById(R.id.icon);



        }
    }

    private int getNumberOfBookMarks(String subject){

        return 20;

    }
}
