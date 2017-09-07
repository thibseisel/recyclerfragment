package fr.nihilus.recyclerfragment.demo.loading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.nihilus.recyclerfragment.RecyclerFragment;

public class StaticDataFragment extends RecyclerFragment {

    private static final String[] MANGAS = {
            "Akame Ga Kill",
            "Bleach",
            "Fairy Tail",
            "Fullmetal Archemist",
            "Hunter x Hunter",
            "Naruto",
            "One Piece",
            "Tokyo Ghoul"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StaticAdapter adapter = new StaticAdapter(MANGAS);
        setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividers = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        getRecyclerView().addItemDecoration(dividers);
    }

    private static class StaticAdapter extends RecyclerView.Adapter<StaticHolder> {

        private final String[] mItems;

        StaticAdapter(String[] data) {
            mItems = data;
        }

        @Override
        public StaticHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new StaticHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StaticHolder holder, int position) {
            holder.text.setText(mItems[position]);
        }

        @Override
        public int getItemCount() {
            return mItems.length;
        }
    }

    static class StaticHolder extends RecyclerView.ViewHolder {
        final TextView text;

        StaticHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }
}
