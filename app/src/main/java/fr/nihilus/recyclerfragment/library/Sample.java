package fr.nihilus.recyclerfragment.library;

import android.app.Activity;

enum Sample {
    SINGLE_LINE_ITEM("Single line item", RecyclerActivity.class),
    LIST_FRAGMENT("ListFragment example", ListFragmentActivity.class);

    private final String mLabel;
    private final Class<? extends Activity> mClass;

    Sample(String label, Class<? extends Activity> activityClass) {
        mLabel = label;
        mClass = activityClass;
    }

    public String getLabel() {
        return mLabel;
    }

    public Class<? extends Activity> getActivityClass() {
        return mClass;
    }
}
