package pyq.qbank.bluearrow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class mcq_model_adapter extends RecyclerView.Adapter<mcq_model_adapter.mcqViewHolder> {

    List<mcq_model> mcqList;
    boolean ReviewMode, testMode;
    private List<mcq_model> filteredMcqList; // Filtered list for displaying
    private Set<String> checkedItems = new HashSet<>(); // Track checked items

    public mcq_model_adapter(List<mcq_model> mcqList, boolean ReviewMode, boolean testMode) {
        this.mcqList = mcqList;
        this.ReviewMode = ReviewMode;
        this.testMode = testMode;
        this.filteredMcqList = new ArrayList<>(mcqList);
    }

    @NonNull
    @Override
    public mcq_model_adapter.mcqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mcq_layout, parent, false);
        return new mcqViewHolder(itemView, ReviewMode, testMode);
    }

    @Override
    public void onBindViewHolder(@NonNull mcq_model_adapter.mcqViewHolder holder, int position) {

        mcq_model mcq = filteredMcqList.get(position);
        holder.questionTitle.setText(mcq.getQuestion_title());
        holder.questionAdd.loadData(mcq.getQuestion_add(), "text/html", "UTF-8");
        holder.questionImg.loadData(mcq.getQuestion_img(), "text/html", "UTF-8");
        holder.optionA.setText(mcq.getOptionA());
        holder.optionB.setText(mcq.getOptionB());
        holder.optionC.setText(mcq.getOptionC());
        holder.optionD.setText(mcq.getOptionD());
        holder.explanation.loadData(mcq.getExplanation(), "text/html", "UTF-8");
        holder.bookMark.setImageResource(mcq.isBookmarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);


        if (ReviewMode) {
            reviewMcqMode(mcq, holder);

            holder.filter.setVisibility(View.VISIBLE);

            holder.filter.setOnClickListener(v -> {
                Toast.makeText(holder.itemView.getContext(), "Filter button clicked", Toast.LENGTH_SHORT).show();

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(holder.itemView.getContext());

                // Inflate the custom layout for filters
                View bottomSheetView = LayoutInflater.from(holder.itemView.getContext())
                        .inflate(R.layout.bottom_sheet_filter, null);
                RecyclerView recyclerView = bottomSheetView.findViewById(R.id.bottom_sheet_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));


                List<String> items = Arrays.asList("Correct", "Incorrect", "Skipped", "bookmarked");

                Button getCheckedItemsButton = bottomSheetView.findViewById(R.id.applyFilter);


                filterAdapter fr = new filterAdapter(items);

                getCheckedItemsButton.setOnClickListener(y -> {
                    checkedItems = fr.getCheckedItems();

                    // Your HashSet
                    List<String> someList = new ArrayList<>(checkedItems); //

                    filter((List<String>) someList);
                    // Do something with checked items, e.g., display or process them
                    // For example, log checked items
                    for (String item : someList) {
                        System.out.println("Checked item: " + item);
                    }
                    bottomSheetDialog.dismiss();
                });


                recyclerView.setAdapter(fr);


                // Set the custom view to the dialog
                bottomSheetDialog.setContentView(bottomSheetView);

                // Show the dialog
                bottomSheetDialog.show();
                //creat a slide over view to add custom filters
            });


        }


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
            mcq.setBookmarked(!mcq.isBookmarked());
            holder.bookMark.setImageResource(mcq.isBookmarked() ? R.drawable.ribbon_selected : R.drawable.ribbon);
            if (mcq.isBookmarked()) {
                bookmarkMcq(mcq, holder);

            }
        });


    }

    private void bookmarkMcq(mcq_model mcq, mcqViewHolder holder) {
        // Add logic to bookmark the MCQ
        Toast.makeText(holder.itemView.getContext(), "MCQ bookmarked", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return filteredMcqList.size();
    }

    private void mcqAnswerChecker(mcq_model mcq, mcqViewHolder holder, int selectedAnswer) {
        if (testMode) {
            testModeClickHandler(holder, selectedAnswer);
            return;
        }


        holder.optionA.setEnabled(false);
        holder.optionB.setEnabled(false);
        holder.optionC.setEnabled(false);
        holder.optionD.setEnabled(false);

        holder.explanation.setVisibility(View.VISIBLE);

        // Highlight the correct answer
        int correctAnswer = mcq.getCorrectAnswer();
        if (selectedAnswer == correctAnswer) {
            holder.getOption(correctAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.teal_200));
            Toast.makeText(holder.itemView.getContext(), "Correct!", Toast.LENGTH_SHORT).show();
        } else if (selectedAnswer == 0) {
            holder.questionTitle.setText(holder.questionTitle.getText() + "\n🎈SKIPPED");
            holder.getOption(correctAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.teal_200));
        } else {
            holder.getOption(correctAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.teal_200));
            holder.getOption(selectedAnswer).setBackgroundColor(holder.itemView.getResources().getColor(R.color.purple_500));
            Toast.makeText(holder.itemView.getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    private void testModeClickHandler(mcqViewHolder holder, int selectedAnswer) {
        // Enable all options
        holder.optionA.setEnabled(true);
        holder.optionB.setEnabled(true);
        holder.optionC.setEnabled(true);
        holder.optionD.setEnabled(true);

        // Reset all option colors to default
        resetOptionColors(holder);

        // Highlight the selected option
        switch (selectedAnswer) {
            case 1:
                holder.optionA.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
                holder.optionA.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
            case 2:
                holder.optionB.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
                holder.optionB.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
            case 3:
                holder.optionC.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
                holder.optionC.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
            case 4:
                holder.optionD.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary));
                holder.optionD.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
        }
    }

    private void resetOptionColors(mcqViewHolder holder) {
        // Reset the background color of all options to default
        holder.optionA.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        holder.optionB.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        holder.optionC.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        holder.optionD.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        holder.optionA.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        holder.optionB.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        holder.optionC.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        holder.optionD.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));

    }


    private void reviewMcqMode(mcq_model mcq, mcqViewHolder holder) {

        int attemptedAnswer = mcq.getAnswer();
        mcqAnswerChecker(mcq, holder, attemptedAnswer);

    }

    public void filter(List<String> query) {
        filteredMcqList.clear(); // Clear the filtered list first
        for (mcq_model item : mcqList) {
            boolean matches = false;
            for (String condition : query) {
                if (condition.equalsIgnoreCase("bookmarked") && item.isBookmarked()) {
                    matches = true;
                    break;
                }
                if (condition.equalsIgnoreCase("correct") && item.getCorrectAnswer() == item.getAnswer()) {
                    matches = true;
                    break;
                }
                if (condition.equalsIgnoreCase("incorrect") && item.getAnswer() != 0 && item.getCorrectAnswer() != item.getAnswer()) {
                    matches = true;
                    break;
                }
                if (condition.equalsIgnoreCase("skipped") && item.getAnswer() == 0) {
                    matches = true;
                    break;
                }
            }
            if (matches) {
                filteredMcqList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    public class mcqViewHolder extends RecyclerView.ViewHolder {

        TextView questionTitle;
        Button optionA, optionB, optionC, optionD;
        WebView explanation, questionImg, questionAdd;
        ImageView bookMark, filter;

        public mcqViewHolder(@NonNull View itemView, boolean reviewMode, boolean testMode) {
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
