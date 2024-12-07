package pyq.qbank.bluearrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class fragTwoReviewMcqFullView extends Fragment {

    private List<entityMcq> mcqList;
    private NestedScrollView scrollViewX;
    private fragTwoAdapter fragTwoAdapter;
    protected static RecyclerView recyclerView;
    private Button previous, next;
    public  boolean showAnswer;

    public fragTwoReviewMcqFullView(List<entityMcq> currentMcq) {
        this.mcqList = currentMcq;
    }

    public List<entityMcq> getMcqList() {
        return mcqList;
    }
    public void setMcqList(List<entityMcq> mcqList) {
        this.mcqList = mcqList;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_review_mcq_full_view, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewLoadFullQuestion);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        fragTwoAdapter = new fragTwoAdapter(mcqList,showAnswer);
        recyclerView.setAdapter(fragTwoAdapter);
        recyclerView.setItemViewCacheSize(mcqList.size());
        scrollViewX = view.findViewById(R.id.scrollViewX);

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("scrollPosition", 0);
            recyclerView.scrollToPosition(position);
        }




        previous = view.findViewById(R.id.prevButton);
        next = view.findViewById(R.id.nextButton);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current first visible position
                int currentPosition = layoutManager.findFirstVisibleItemPosition();
                if (currentPosition > 0) {
                    recyclerView.smoothScrollToPosition(currentPosition - 1);
                    scrollViewX.smoothScrollTo(0, 0); // Scroll to previous position
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current first visible position
                int currentPosition = layoutManager.findFirstVisibleItemPosition();
                if (currentPosition < mcqList.size() - 1) {
                    recyclerView.smoothScrollToPosition(currentPosition + 1);
                    scrollViewX.smoothScrollTo(0, 0); // Scroll to next position
                }
            }
        });





        return view;

    }

    public void setShowAnswers(boolean showAnswers) {
        // Only notify the adapter if the boolean value has changed
        if (this.showAnswer != showAnswers) {
            this.showAnswer = showAnswers;
            fragTwoAdapter.showAnsers = showAnswers; // Update the adapter with the new boolean
            fragTwoAdapter.notifyDataSetChanged(); // Notify only when the boolean changes
        }
    }
}