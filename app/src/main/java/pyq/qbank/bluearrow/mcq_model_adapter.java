package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.objectbox.Box;


public class mcq_model_adapter extends RecyclerView.Adapter<mcq_model_adapter.mcqViewHolder> {

    private final List<entityMcq> filteredMcqList; // Filtered list for displaying
    List<entityMcq> mcqList;
    boolean ReviewMode;
    Box<entityMcq> mcq_box;
    private Set<String> checkedItems = new HashSet<>(); // Track checked items
    private pasueProgressBar pasueProgressBar; // Reference to the interface


    public mcq_model_adapter(List<entityMcq> mcqList, boolean ReviewMode, pasueProgressBar p) {
        this.mcqList = mcqList;
        this.ReviewMode = ReviewMode;
        this.filteredMcqList = new ArrayList<>(mcqList);
        this.pasueProgressBar = p;
        this.mcq_box = boxStore.boxFor(entityMcq.class);
    }

    @NonNull
    @Override
    public mcq_model_adapter.mcqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_layout, parent, false);
        return new mcqViewHolder(itemView, ReviewMode);
    }

    @Override
    public void onBindViewHolder(@NonNull mcq_model_adapter.mcqViewHolder holder, int position) {
        entityMcq mcq = filteredMcqList.get(position);
        holder.questionTitle.setText(mcq.getQuestion_title());

        holder.bookMark.setImageResource(mcq.isBookMarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);
        holder.optionA.setText(mcq.getOptionA().replaceAll("\\n", ""));
        holder.optionB.setText(mcq.getOptionB().replaceAll("\\n", ""));
        holder.optionC.setText(mcq.getOptionC().replaceAll("\\n", ""));
        holder.optionD.setText(mcq.getOptionD().replaceAll("\\n", ""));

        holder.explanation.loadDataWithBaseURL(null, getProcessedString(mcq.getExplanation()), "text/html", "UTF-8", null);

        holder.questionAdd.loadDataWithBaseURL(null, getProcessedString(mcq.getQuestion_add()), "text/html", "UTF-8", null);

        holder.questionImg.loadDataWithBaseURL(null, getProcessedString(mcq.getQuestion_img()), "text/html", "UTF-8", null);

        configureWebViewSettings(holder.explanation.getSettings());
        configureWebViewSettings(holder.questionAdd.getSettings());
        configureWebViewSettings(holder.questionImg.getSettings());

        String tags = mcq.getTags().replace(","," ");
        holder.tagsMcq.setText(tags);


        holder.optionA.setOnClickListener(v -> {
            mcqAnswerChecker(mcq, holder, 1);
        });

        holder.optionB.setOnClickListener(v -> {
            mcqAnswerChecker(mcq, holder, 2);
        });
        holder.optionC.setOnClickListener(v -> {
            mcqAnswerChecker(mcq, holder, 3);
        });
        holder.optionD.setOnClickListener(v -> {
            mcqAnswerChecker(mcq, holder, 4);
        });


        holder.bookMark.setOnClickListener(v -> {
            // Toggle the bookmark status
            boolean isCurrentlyBookmarked = mcq.isBookMarked();
            mcq.setBookMarked(!isCurrentlyBookmarked);

            // Update the icon based on the new state
            holder.bookMark.setImageResource(mcq.isBookMarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);

            // Perform database operations based on the new state
            if (mcq.isBookMarked()) {
                bookmarkMcq(mcq, holder); // Handle bookmarking
            } else {
                unBookMarkMcq(mcq, holder); // Handle unbookmarking
            }
        });


    }

    private void bookmarkMcq(entityMcq mcq, mcqViewHolder holder) {

        // Update the database
        mcq_box.put(mcq);

        // Notify the user
        Toast.makeText(holder.itemView.getContext(), "MCQ bookmarked", Toast.LENGTH_SHORT).show();
    }

    private void unBookMarkMcq(entityMcq mcq, mcqViewHolder holder) {


        // Update the database
        mcq_box.put(mcq);

        // Notify the user
        Toast.makeText(holder.itemView.getContext(), "Bookmark removed", Toast.LENGTH_SHORT).show();
    }


    private String getProcessedString(String red) {

        if (red != null) {

            return "<html>" + "<head>" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" + "\n<style>img{max-width:100%;height:auto;}</style>" + "</head>" + "<body>" + red + "</body></html>";
        }
        return "";

    }


    private void configureWebViewSettings(WebSettings webSettings) {
        webSettings.setLoadWithOverviewMode(true);  // Ensures WebView fits the content to the screen width
        webSettings.setUseWideViewPort(true);       // Allows content to be scaled to fit the screen width
        webSettings.setJavaScriptEnabled(false);   // If your content requires JavaScript
        webSettings.setBuiltInZoomControls(true);  // Optional: Enables pinch-to-zoom
        webSettings.setDisplayZoomControls(false); // Hides the zoom controls
    }


    @Override
    public int getItemCount() {
        return filteredMcqList.size();
    }

    private void mcqAnswerChecker(entityMcq mcq, mcqViewHolder holder, int selectedAnswer) {

        mcq.setUserAnswer(selectedAnswer);



        pasueProgressBar.sendData(true); // Call the interface method


        holder.optionA.setEnabled(false);
        holder.optionB.setEnabled(false);
        holder.optionC.setEnabled(false);
        holder.optionD.setEnabled(false);

        holder.explanation.setVisibility(View.VISIBLE);

        // Highlight the correct answer
        int correctAnswer = mcq.getCorrectAnswer();
        if (selectedAnswer == correctAnswer) {
            holder.getOption(correctAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.primary_light));
            Toast.makeText(holder.itemView.getContext(), "Correct!", Toast.LENGTH_SHORT).show();
            mcq.setStatus("C");
        } else if (selectedAnswer == 0) {
            holder.questionTitle.setText(holder.questionTitle.getText());
            holder.getOption(correctAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.primary_light));
            mcq.setStatus("S");
        } else {
            holder.getOption(correctAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.primary_light));
            holder.getOption(selectedAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.lightRed));
            Toast.makeText(holder.itemView.getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
            mcq.setStatus("I");
        }

        mcq_box.put(mcq);
    }

    public void timeUp(entityMcq currentMcq, mcqViewHolder currentHolder, int i) {
        mcqAnswerChecker(currentMcq, currentHolder, i);
    }


    public class mcqViewHolder extends RecyclerView.ViewHolder {

        TextView questionTitle,tagsMcq;
        Button optionA, optionB, optionC, optionD;
        WebView explanation, questionImg, questionAdd;
        ImageView bookMark, filter;  // Declare the CountDownTimer


        public mcqViewHolder(@NonNull View itemView, boolean reviewMode) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.question_title);
            questionAdd = itemView.findViewById(R.id.question_add);
            questionImg = itemView.findViewById(R.id.question_img);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            explanation = itemView.findViewById(R.id.explanation);
            bookMark = itemView.findViewById(R.id.bookMark);
            tagsMcq = itemView.findViewById(R.id.tagsMcq);

            if (reviewMode) {
                filter = itemView.findViewById(R.id.filter);
            }

        }

        private View getOption(int answerIndex) {
            switch (answerIndex) {
                case 1:
                    return optionA;
                case 2:
                    return optionB;
                case 3:
                    return optionC;
                case 4:
                    return optionD;
                default:
                    return null;
            }
        }


    }


    // Helper method to get option view by answer index


}
