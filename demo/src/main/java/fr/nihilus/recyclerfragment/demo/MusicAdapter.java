package fr.nihilus.recyclerfragment.demo;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.SongHolder> {

    private static final Uri ALBUM_ART_URI = Uri.parse("content://media/external/audio/albumart");

    private Cursor mCursor;
    private int mColId;
    private int mColTitle;
    private int mColAlbumId;

    private final Picasso mPicasso;

    public MusicAdapter(@NonNull Context context, @Nullable Cursor cursor) {
        mPicasso = Picasso.with(context);
        mCursor = cursor;
        if (mCursor != null) {
            mColId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            mColTitle = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            mColAlbumId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
        }
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_list_item, parent, false);
        return new SongHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.title.setText(mCursor.getString(mColTitle));

        long albumId = mCursor.getLong(mColAlbumId);
        Uri albumArtUri = ContentUris.withAppendedId(ALBUM_ART_URI, albumId);
        mPicasso.load(albumArtUri).into(holder.albumArt);
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (hasStableIds() && mCursor != null) {
            mCursor.moveToPosition(position);
            return mCursor.getLong(mColId);
        }
        return RecyclerView.NO_ID;
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return newCursor;
        }

        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            mColId = newCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            mColTitle = newCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            mColAlbumId = newCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
        }
        notifyDataSetChanged();
        return oldCursor;
    }

    static class SongHolder extends RecyclerView.ViewHolder {
        final ImageView albumArt;
        final TextView title;

        SongHolder(View itemView) {
            super(itemView);
            albumArt = (ImageView) itemView.findViewById(R.id.art);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
