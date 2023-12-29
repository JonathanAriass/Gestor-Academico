package com.example.gestoracademico.ui.ui.home;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.Callback {

    private final SwipeToDeleteListener listener;

    public SwipeToDeleteCallback(SwipeToDeleteListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT) {
            listener.onSwipe(viewHolder.getAdapterPosition());
        }
    }
    public interface SwipeToDeleteListener {
        void onSwipe(int position);
    }
}
