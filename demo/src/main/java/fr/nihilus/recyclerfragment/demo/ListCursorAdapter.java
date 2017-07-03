package fr.nihilus.recyclerfragment.demo;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListCursorAdapter extends RecyclerView.Adapter<ListCursorAdapter.BaseHolder> {

    private static final Uri ALBUM_ART_URI = Uri.parse("content://media/external/audio/albumart");

    private final Context mContext;
    private Cursor mCursor;

    public ListCursorAdapter(Context context, Cursor data) {
        mContext = context;
        mCursor = data;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_line_item, parent, false);
        return new BaseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        String title = mCursor.getString(mCursor.getColumnIndexOrThrow(Media.TITLE));
        holder.title.setText(title);

        long id = mCursor.getLong(mCursor.getColumnIndexOrThrow(Media.ALBUM_ID));
        Uri iconUri = ContentUris.withAppendedId(ALBUM_ART_URI, id);
        Picasso.with(mContext).load(iconUri).noFade().into(holder.icon);
    }

    public Cursor swapCursor(Cursor newCursor) {
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        notifyDataSetChanged();
        return oldCursor;
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    static class BaseHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final ImageView icon;

        BaseHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
