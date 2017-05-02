package fr.nihilus.recyclerfragment.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import fr.nihilus.recyclerfragment.viewholder.SingleLineHolder;

class MenuAdapter extends RecyclerView.Adapter<SingleLineHolder> {

    private final Sample[] mItems = Sample.values();
    private OnSampleSelectedListener mListener;

    @Override
    public SingleLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SingleLineHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(final SingleLineHolder holder, int position) {
        holder.text.setText(mItems[position].getLabel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    Sample selectedSample = mItems[holder.getAdapterPosition()];
                    mListener.onSampleSelected(selectedSample);
                }
            }
        });
    }

    void setOnSampleSelectedListener(OnSampleSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }

    interface OnSampleSelectedListener {
        void onSampleSelected(Sample sample);
    }
}
