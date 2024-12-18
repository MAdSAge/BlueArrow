package pyq.qbank.bluearrow;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class qbankSubject_model_adapter extends RecyclerView.Adapter<qbankSubject_model_adapter.ViewHolder> {

    private List<entitySubject> topicList;

    public qbankSubject_model_adapter(List<entitySubject> topicList) {
        this.topicList = topicList;
    }


    @NonNull
    @Override
    public qbankSubject_model_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_layout, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull qbankSubject_model_adapter.ViewHolder holder, int position) {

        entitySubject topic = topicList.get(position);

        holder.title.setText(topic.getTitle());
        holder.progressIndicator.setMax(topic.getLesson_type_count_1());
        holder.progressIndicator.setProgress(topic.getSolved_lesson_type_count_1());
        holder.chapters_count.setText(String.valueOf(topic.getLesson_type_count_1()) + " Chapters");

        int targetColor = Color.parseColor("#62C8DF");

        int replacementColor = ContextCompat.getColor(holder.icon.getContext(), R.color.primary);

        //set the icon from url
        Glide.with(holder.icon.getContext())
                .asBitmap()
                .load(topic.getThumbnail())
                .into(new CustomTarget<Bitmap>() {


                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Bitmap modifiedBitmap = changeSpecificColorInBitmap(resource, targetColor, replacementColor);
                        holder.icon.setImageBitmap(modifiedBitmap);  // Set the modified bitmap to ImageView
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), qbankChapterSelection.class);
                intent.putExtra("subjectName", topic.getTitle());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    private Bitmap changeSpecificColorInBitmap(Bitmap originalBitmap, int targetColor, int replacementColor) {

        Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        for (int x = 0; x < mutableBitmap.getWidth(); x++) {
            for (int y = 0; y < mutableBitmap.getHeight(); y++) {
                int pixelColor = mutableBitmap.getPixel(x, y);
                if (pixelColor == targetColor) {
                    mutableBitmap.setPixel(x, y, replacementColor); // Change color here
                }
            }
        }
        return mutableBitmap;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, chapters_count;
        ProgressBar progressIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            chapters_count = itemView.findViewById(R.id.questions);
            progressIndicator = itemView.findViewById(R.id.subjectProgress);

        }
    }
}
