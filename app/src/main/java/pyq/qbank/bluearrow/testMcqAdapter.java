package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.objectbox.Box;

public class testMcqAdapter extends RecyclerView.Adapter<testMcqAdapter.testMcqViewHolder> {

    List<entityMcq> mcqList;

    public testMcqAdapter(List<entityMcq> mcqList) {
        this.mcqList = mcqList;
    }


    @NonNull
    @Override
    public testMcqAdapter.testMcqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_layout, parent, false);

        return new testMcqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull testMcqAdapter.testMcqViewHolder holder, int position) {

        entityMcq currentMcq = mcqList.get(position);

        holder.question_title.setText(currentMcq.getQuestion_title());
        holder.optionA.setText(currentMcq.getOptionA());
        holder.optionB.setText(currentMcq.getOptionB());
        holder.optionC.setText(currentMcq.getOptionC());
        holder.optionD.setText(currentMcq.getOptionD());
        holder.tagsMcq.setText(currentMcq.getTags());


        holder.question_add.loadDataWithBaseURL(null, getProcessedString(currentMcq.getQuestion_add()), "text/html", "UTF-8", null);

        holder.question_img.loadDataWithBaseURL(null, getProcessedString(currentMcq.getQuestion_img()), "text/html", "UTF-8", null);


    }

    @Override
    public int getItemCount() {
        return mcqList.size();
    }

    private void resetOptionsColour(testMcqViewHolder holder) {
        holder.optionA.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        holder.optionB.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        holder.optionC.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        holder.optionD.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
    }


    private void configureWebViewSettings(WebSettings webSettings) {
        webSettings.setLoadWithOverviewMode(true);  // Ensures WebView fits the content to the screen width
        webSettings.setUseWideViewPort(true);       // Allows content to be scaled to fit the screen width
        webSettings.setJavaScriptEnabled(false);   // If your content requires JavaScript
        webSettings.setBuiltInZoomControls(true);  // Optional: Enables pinch-to-zoom
        webSettings.setDisplayZoomControls(false); // Hides the zoom controls
    }

    private String getProcessedString(String red) {

        if (red != null) {

            return "<html>" + "<head>" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" + "\n<style>img{max-width:100%;height:auto;}</style>" + "</head>" + "<body>" + red + "</body></html>";
        }
        return "";

    }

    private void answerSubmit(int Position, int answer) {
        Box<entityMcq> mcqBox = boxStore.boxFor(entityMcq.class);
        entityMcq currentMcq = mcqList.get(Position);
        currentMcq.setUserAnswer(answer);
        mcqBox.put(currentMcq);

    }

    public class testMcqViewHolder extends RecyclerView.ViewHolder {

        TextView question_title;
        WebView question_add;
        WebView question_img;
        TextView optionA;
        TextView optionB;
        TextView optionC;
        TextView optionD;
        TextView tagsMcq;
        WebView explanation;

        public testMcqViewHolder(@NonNull View itemView) {
            super(itemView);
            question_title = itemView.findViewById(R.id.question_title);
            question_add = itemView.findViewById(R.id.question_add);
            question_img = itemView.findViewById(R.id.question_img);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            tagsMcq = itemView.findViewById(R.id.tagsMcq);

            explanation = itemView.findViewById(R.id.explanation);
            explanation.setVisibility(View.GONE);


            optionA.setOnClickListener(v -> {
                resetOptionsColour(this);
                answerSubmit(getAdapterPosition(), 1);
                optionA.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_light));
            });
            optionB.setOnClickListener(v -> {
                resetOptionsColour(this);
                answerSubmit(getAdapterPosition(), 2);

                optionB.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_light));
            });
            optionC.setOnClickListener(v -> {
                resetOptionsColour(this);
                answerSubmit(getAdapterPosition(), 3);

                optionC.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_light));
            });
            optionD.setOnClickListener(v -> {
                resetOptionsColour(this);
                answerSubmit(getAdapterPosition(), 4);

                optionD.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_light));
            });


            configureWebViewSettings(question_add.getSettings());
            configureWebViewSettings(question_img.getSettings());

        }
    }
}
