package pyq.qbank.bluearrow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class customModuleFragOneReviewMcqsListQuestions extends Fragment implements fragLoader {

    private List<entityCustomModuleMcq> mcqList;
    private customModuleFragOneAdapter fragOneAdapter;
    private RecyclerView recyclerView;
    private fragLoader fragLoader;

    public customModuleFragOneReviewMcqsListQuestions(List<entityCustomModuleMcq> currentMcq) {
        this.mcqList = currentMcq;
    }

    public List<entityCustomModuleMcq> getMcqList() {
        return mcqList;
    }

    public void setMcqList(List<entityCustomModuleMcq> mcqList) {
        this.mcqList = mcqList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_mcq_list_questions, container, false);

        recyclerView = view.findViewById(R.id.recylerViewLoadQuestion);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fragOneAdapter = new customModuleFragOneAdapter(mcqList, this);
        recyclerView.setAdapter(fragOneAdapter);






        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof fragLoader) {
            fragLoader = (fragLoader) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnItemClickedListener");
        }
    }



    @Override
    public void loadFrag(int position) {

        fragLoader.loadFrag(position);

    }
}