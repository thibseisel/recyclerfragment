package fr.nihilus.recyclerfragment.library;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MusicListAdapter extends CursorAdapter {

    private static final Uri ALBUM_ART_URI = Uri.parse("content://media/external/audio/albumart");

    private final Picasso mPicasso;

    public MusicListAdapter(Context context, Cursor c) {
        super(context, c, 0);
        mPicasso = Picasso.with(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.music_list_item, parent, false);
        ViewHolder holder = new ViewHolder(rootView);
        rootView.setTag(holder);
        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        Uri albumArtUri = ContentUris.withAppendedId(ALBUM_ART_URI, albumId);

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(title);
        mPicasso.load(albumArtUri).into(holder.albumArt);
    }

    private static class ViewHolder {
        final View itemView;
        final ImageView albumArt;
        final TextView title;

        ViewHolder(View itemView) {
            this.itemView = itemView;
            albumArt = (ImageView) itemView.findViewById(R.id.art);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
