package ua.insomnia.customrvm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>();
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list.add("this is item 0");
        list.add("this is item 1");
        list.add("this is item 2");
        list.add("this is item 3");
        list.add("this is item 4");
        list.add("this is item 5");
        list.add("this is item 6");
        list.add("this is item 7");
        list.add("this is item 8");
        list.add("this is item 9");
        list.add("this is item 10");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
//        recyclerView.setLayoutManager(layoutManager);
        final RecyclerView.LayoutManager layoutManager = new CLManager();
        recyclerView.setLayoutManager(layoutManager);
        final RVAdapter adapter = new RVAdapter(list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SwipeTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                list.add("this is item " + counter++);
//                adapter.notifyDataSetChanged();
//                layoutManager.moveView(layoutManager.getItemCount() - 1, 0);
//                layoutManager.up
//                String from = list.get(list.size() - 1);
//                list.remove(list.size() - 1);
//                list.add(0, from);
//                adapter.notifyDataSetChanged();

                if (((CLManager)layoutManager).isExpanded()) {
                    ((CLManager) layoutManager).setExpanded(false);
                } else {
                    ((CLManager) layoutManager).setExpanded(true);
                }
//                ((RVAdapter)adapter).moveItemToBack();

            }
        });


    }

}
