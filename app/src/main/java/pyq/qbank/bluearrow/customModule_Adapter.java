package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.objectbox.Box;

public class customModule_Adapter extends RecyclerView.Adapter<customModule_Adapter.ViewHolder> {

    private static final int SWIPE_ANIMATION_DURATION = 300;
    private static final float SWIPE_THRESHOLD = 0.5f; // 50% swipe threshold for triggering delete
    private final List<entityCustomModule> customModuleList;
    private final Box<entityCustomModule> customModuleBox;

    public customModule_Adapter(List<entityCustomModule> customModuleList) {
        this.customModuleList = customModuleList;
        this.customModuleBox = boxStore.boxFor(entityCustomModule.class); // Initialize once
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        entityCustomModule module = customModuleList.get(position);
        holder.customModuleTitle.setText(module.getCustomModuleName());
        holder.customModuleQuestions.setText(String.valueOf(module.getQuestionCount()));
        holder.customModuleRibbonSubjects.setText(module.getSubjectList().toString());
    }

    @Override
    public int getItemCount() {
        return customModuleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView customModuleTitle;
        private final TextView customModuleQuestions;
        private final TextView customModuleRibbonSubjects;
        private float initialX = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customModuleTitle = itemView.findViewById(R.id.chapterTitle);
            customModuleQuestions = itemView.findViewById(R.id.cahpterQuestions);
            customModuleRibbonSubjects = itemView.findViewById(R.id.ribbonTextView);

            itemView.setOnClickListener(view -> openModuleDetails());
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = event.getRawX(); // Capture initial X position
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            float deltaX = event.getRawX() - initialX;
                            // Translate item as user swipes
                            if (Math.abs(deltaX) > 0) {
                                itemView.setTranslationX(deltaX);
                            }
                            return true;

                        case MotionEvent.ACTION_UP:
                            float finalDeltaX = event.getRawX() - initialX;
                            if (Math.abs(finalDeltaX) > itemView.getWidth() * SWIPE_THRESHOLD) {
                                handleSwipeLeft(); // Trigger swipe action
                            } else {
                                resetItemPosition(); // Reset if swipe not enough
                            }
                            return true;

                        default:
                            return false;
                    }
                }
            });
        }

        private void openModuleDetails() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                entityCustomModule cm = customModuleList.get(position);
                Intent intent = new Intent(itemView.getContext(), customModule_qbankMcqReview.class);
                intent.putExtra("customModuleName", cm.getCustomModuleName());
                itemView.getContext().startActivity(intent);
            }
        }

        private void handleSwipeLeft() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                animateItemRemoval(position);
            }
        }

        private void animateItemRemoval(int position) {
            View item = itemView;
            item.animate()
                    .translationX(-item.getWidth()) // Move item off-screen
                    .alpha(0) // Fade out
                    .setDuration(SWIPE_ANIMATION_DURATION)
                    .withEndAction(() -> {
                        removeItemFromDatabase(position);
                        resetItemForRecycling(item);
                    })
                    .start();
        }

        private void resetItemPosition() {
            itemView.animate()
                    .translationX(0) // Return to original position
                    .alpha(1) // Ensure visibility
                    .setDuration(SWIPE_ANIMATION_DURATION)
                    .start();
        }

        private void removeItemFromDatabase(int position) {
            entityCustomModule cm = customModuleList.get(position);
            customModuleBox.remove(cm); // Remove from ObjectBox
            customModuleList.remove(position); // Remove from list
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, customModuleList.size());
        }

        private void resetItemForRecycling(View item) {
            item.setTranslationX(0);
            item.setAlpha(1);
        }
    }
}
