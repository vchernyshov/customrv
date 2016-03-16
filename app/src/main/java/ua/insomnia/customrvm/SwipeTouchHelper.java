package ua.insomnia.customrvm;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class SwipeTouchHelper extends ItemTouchHelper.Callback{

    private static final String TAG = "SwipeTouchHelper";

    private RecyclerView.Adapter adapter;

    public SwipeTouchHelper(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        switch (direction){
            case ItemTouchHelper.START:
            case ItemTouchHelper.END:
                ((RVAdapter) adapter).removeItem(viewHolder.getAdapterPosition());
                Log.d(TAG, "onSwiped: right or left");
                break;
        }
    }

}
