package fr.nihilus.recyclerfragment.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.nihilus.recyclerfragment.R;

public class SingleLineHolder extends RecyclerView.ViewHolder {
    public final ImageView icon;
    public final TextView text;

    public static SingleLineHolder create(ViewGroup parent) {
        SingleLineHolder holder = withIcon(parent);
        holder.icon.setVisibility(View.GONE);
        return holder;
    }

    public static SingleLineHolder withIcon(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SingleLineHolder(inflater.inflate(R.layout.single_line_item, parent, false));
    }

    public static SingleLineHolder withAvatar(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SingleLineHolder(inflater.inflate(R.layout.avatar_single_line_item, parent, false));
    }

    public SingleLineHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        text = (TextView) itemView.findViewById(R.id.text);
    }
}
