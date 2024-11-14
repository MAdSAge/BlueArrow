package pyq.qbank.bluearrow;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class testModelAdapter extends RecyclerView.Adapter<testModelAdapter.testViewHolder> {
    List<testModel> testList;
    List<testModel> filterTestList;

    public testModelAdapter(List<testModel> testList) {
        this.testList = testList;
        this.filterTestList = new ArrayList<>(testList);
    }


    @NonNull
    @Override
    public testModelAdapter.testViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_layout, parent, false);

        return new testViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull testModelAdapter.testViewHolder holder, int position) {
        testModel item = filterTestList.get(position);

        holder.testTitle.setText(item.getTitle());
        holder.testYear.setText(getYearString(item.getYear()));
        holder.testType.setText(item.getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), mcq_solving.class);
                intent.putExtra("testTitle", item.getTitle());
                intent.putExtra("testMode", true);
                // write logic for sending data
                v.getContext().startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return filterTestList.size();
    }

    public void spinnerFilter(String selectedYear, String selectedSubject) {
        filterTestList.clear();
        if (selectedYear == null) selectedYear = "All";
        if (selectedSubject == null) selectedSubject = "All";

        if (selectedYear.equals("All") && selectedSubject.equals("All")) {
            filterTestList.addAll(testList);
        } else {
            for (testModel item : testList) {
                String itemYear = getYearString(item.getYear());
                String itemType = item.getType();

                boolean matchesYear = selectedYear.equals("All") || itemYear.equals(selectedYear);
                boolean matchesSubject = selectedSubject.equals("All") || itemType.equals(selectedSubject);

                if (matchesYear && matchesSubject) {
                    filterTestList.add(item);
                }
            }
        }
        Log.d("TestModelAdapter", "Filtered list size: " + filterTestList.size());  // Check size
        notifyDataSetChanged();
    }



    public class testViewHolder extends RecyclerView.ViewHolder {
        TextView testTitle, testYear, testType;

        public testViewHolder(@NonNull View itemView) {
            super(itemView);
            testTitle = itemView.findViewById(R.id.tvTitle);
            testYear = itemView.findViewById(R.id.tvYear);
            testType = itemView.findViewById(R.id.tvType);
            // Initialize views here

        }
    }







    public String getYearString(long timeStamp){
        // The timestamp in milliseconds

        // Create a Date object from the timestamp
        Date date = new Date(timeStamp);

        // Define the date format to extract the year
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        // Format the date to get only the year as a string
        String yearString = yearFormat.format(date);

        // Print the extracted year
        return yearString;

    }


}
