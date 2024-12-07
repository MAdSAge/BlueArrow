

package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.objectbox.Box;

public class customModuleFragTwoAdapter extends RecyclerView.Adapter<customModuleFragTwoAdapter.fragTwoViewHolder> {

    List<entityCustomModuleMcq> mcqList;
    public boolean showAnsers;


    public customModuleFragTwoAdapter(List<entityCustomModuleMcq> mcqList, boolean showAnsers) {
        this.mcqList = mcqList;
        this.showAnsers = showAnsers;
    }

    public List<entityCustomModuleMcq> getMcqList() {
        return mcqList;
    }

    public void setMcqList(List<entityCustomModuleMcq> mcqList) {
        this.mcqList = mcqList;
    }


    @NonNull
    @Override
    public customModuleFragTwoAdapter.fragTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_layout, parent, false);
        return new customModuleFragTwoAdapter.fragTwoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull fragTwoViewHolder holder, int position) {
        entityCustomModuleMcq mcq = mcqList.get(position);

        String skip = "";
        if (mcq.getUserAnswer() == 0) {
            skip = "\n❗Skipped";

        }

        holder.question_title.setText(mcq.getQuestion_title() + skip);
        holder.optionA.setText(mcq.getOptionA().replaceAll("\n", ""));
        holder.optionB.setText(mcq.getOptionB().replaceAll("\n", ""));
        holder.optionC.setText(mcq.getOptionC().replaceAll("\n", ""));
        holder.optionD.setText(mcq.getOptionD().replaceAll("\n", ""));
        String tags = mcq.getTags().replace(",", " ");
        holder.tagsMcq.setText(tags);
        holder.explanation.setVisibility(View.VISIBLE);
        holder.bookMark.setImageResource(mcq.isBookMarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);
        holder.explanation.loadDataWithBaseURL(null, getProcessedString(mcq.getExplanation()), "text/html", "UTF-8", null);

        holder.question_add.loadDataWithBaseURL(null, getProcessedString(mcq.getQuestion_add()), "text/html", "UTF-8", null);

        holder.question_img.loadDataWithBaseURL(null, getProcessedString(mcq.getQuestion_img()), "text/html", "UTF-8", null);

        holder.bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcq.setBookMarked(!mcq.isBookMarked());
                Box<entityCustomModuleMcq> red = boxStore.boxFor(entityCustomModuleMcq.class);
                red.put(mcq);
                holder.bookMark.setImageResource(mcq.isBookMarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);


            }
        });

        if(showAnsers) {
            setOptionColour(mcq, holder, holder.itemView.getContext());
        }else {
            holder.optionA.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.optionB.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.optionC.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.optionD.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));

        }


    }


    @Override
    public int getItemCount() {
        return mcqList.size();
    }

    private void setOptionColour(entityCustomModuleMcq mcq, fragTwoViewHolder holder, Context context) {
        int correctAnswer = mcq.getCorrectAnswer();
        int attemptedAnswer = mcq.getUserAnswer();

        // Resolve colors
        int colorPrimary = ContextCompat.getColor(context, R.color.primary_light); // Correct answer
        int colorSecondary = ContextCompat.getColor(context, R.color.lightRed); // Incorrect attempt
        int colorDefault = ContextCompat.getColor(context, R.color.white); // Reset option

        // Reset all option colors
        holder.optionA.setBackgroundColor(colorDefault);
        holder.optionB.setBackgroundColor(colorDefault);
        holder.optionC.setBackgroundColor(colorDefault);
        holder.optionD.setBackgroundColor(colorDefault);

        // Highlight correct and attempted answers
        if (correctAnswer == 1) holder.optionA.setBackgroundColor(colorPrimary);
        if (correctAnswer == 2) holder.optionB.setBackgroundColor(colorPrimary);
        if (correctAnswer == 3) holder.optionC.setBackgroundColor(colorPrimary);
        if (correctAnswer == 4) holder.optionD.setBackgroundColor(colorPrimary);

        if (attemptedAnswer != correctAnswer) {
            if (attemptedAnswer == 1) holder.optionA.setBackgroundColor(colorSecondary);
            if (attemptedAnswer == 2) holder.optionB.setBackgroundColor(colorSecondary);
            if (attemptedAnswer == 3) holder.optionC.setBackgroundColor(colorSecondary);
            if (attemptedAnswer == 4) holder.optionD.setBackgroundColor(colorSecondary);
        }
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

    public class fragTwoViewHolder extends RecyclerView.ViewHolder {

        TextView question_title, tagsMcq;
        WebView question_add, question_img, explanation;
        Button optionA, optionB, optionC, optionD;
        ImageView bookMark;

        public fragTwoViewHolder(@NonNull View itemView) {
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
            bookMark = itemView.findViewById(R.id.bookMark);

            configureWebViewSettings(explanation.getSettings());
            configureWebViewSettings(question_add.getSettings());
            configureWebViewSettings(question_img.getSettings());


        }
    }


}
