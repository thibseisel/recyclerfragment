package fr.nihilus.recyclerfragment.demo.todo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Random;

import fr.nihilus.recyclerfragment.RecyclerFragment;
import fr.nihilus.recyclerfragment.demo.R;

public class TodoListFragment extends RecyclerFragment implements View.OnClickListener {
    private TodoAdapter mAdapter;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.action_add).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        configureSwipeToDismiss();
        configureItemAnimations();

        mAdapter = new TodoAdapter();
        setAdapter(mAdapter);

        if (savedInstanceState == null) {
            // Hide RecyclerView while loading
            setRecyclerShown(false);
            loadTodosFromNetwork();
        } else {
            mAdapter.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mAdapter.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void configureSwipeToDismiss() {
        int horizontalSwipe = ItemTouchHelper.START | ItemTouchHelper.END
                | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        ItemTouchHelper.Callback swipeCallback = new ItemTouchHelper.SimpleCallback(0, horizontalSwipe) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Do not allow item moves.
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder holder, int direction) {
                int itemPosition = holder.getAdapterPosition();
                mAdapter.remove(itemPosition);
            }
        };

        ItemTouchHelper helper = new ItemTouchHelper(swipeCallback);
        helper.attachToRecyclerView(getRecyclerView());
    }

    private void configureItemAnimations() {
        getRecyclerView().setItemAnimator(new DefaultItemAnimator());
    }

    private void loadTodosFromNetwork() {
        // Simulate loading from network
        new AsyncTask<Void, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(Void... voids) {
                try {
                    // Simulate a significant network delay to show the progress indicator
                    Thread.sleep(1500L);
                } catch (InterruptedException ignored) {}
                return Todo.TODOS.subList(0, 3);
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                mAdapter.setItems(todos);
                setRecyclerShown(true);
            }
        }.execute();
    }

    @Override
    public void onClick(View view) {
        // Action performed when clicking on the + button
        int randomTodoNumber = new Random().nextInt(Todo.TODOS.size());
        mAdapter.add(Todo.TODOS.get(randomTodoNumber));
        setRecyclerShown(true);
    }
}
