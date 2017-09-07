package fr.nihilus.recyclerfragment.demo.todo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.nihilus.recyclerfragment.demo.R;

class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    public static final String KEY_TODOS = "todos";
    private final ArrayList<Todo> mItems;

    public TodoAdapter() {
        mItems = new ArrayList<>();
    }

    @Override
    public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoHolder(parent);
    }

    @Override
    public void onBindViewHolder(TodoHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Todo todo) {
        mItems.add(todo);
        notifyItemInserted(mItems.size() - 1);
    }

    void remove(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    void setItems(Collection<Todo> todos) {
        mItems.clear();
        mItems.addAll(todos);
        notifyDataSetChanged();
    }

    void saveState(Bundle outState) {
        outState.putParcelableArrayList(KEY_TODOS, mItems);
    }

    void restoreState(Bundle savedState) {
        List<Todo> todos = savedState.getParcelableArrayList(KEY_TODOS);
        if (todos != null) {
            mItems.clear();
            mItems.addAll(todos);
            notifyItemRangeInserted(0, mItems.size());
        }
    }

    static class TodoHolder extends RecyclerView.ViewHolder {
        final CheckBox done;
        final TextView label;

        TodoHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_todo, parent, false));
            done = itemView.findViewById(R.id.check_done);
            label = itemView.findViewById(R.id.todo_label);
        }

        void bind(Todo todo) {
            done.setChecked(todo.isDone());
            label.setText(todo.getLabel());
        }
    }
}
