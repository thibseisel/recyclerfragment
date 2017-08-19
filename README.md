[ ![JCenter](https://api.bintray.com/packages/nihilus/android/recyclerfragment/images/download.svg) ](https://bintray.com/nihilus/android/recyclerfragment/_latestVersion)

RecyclerFragment is a small Android library that allow you to
display `RecyclerView` data in a `Fragment`. Easy to use, it is similar to
the framework's `ListFragment` with additional features.

# Download #
The library is available through jCenter.
You just have to include the dependency in your build.gradle file :

```gradle
dependencies {
    compile 'fr.nihilus:recyclerfragment:x.y.z'
}
```

# Features #

- Fragment with a `RecyclerView` and a hideable `ProgressBar` out of the box
- Ability to show the `ProgressBar` when waiting for asynchronous data
- Customizable layout
- Support for an "empty view" to be displayed automatically in place
of RecyclerView when the adapter contains no data.

# How to use #

The preferred way to use RecyclerFragment is to extend it to add
your behavior in `onActivityCreated(Bundle)`.

The following example show you how to use RecyclerFragment when loading
data asynchronously :

```java
public class MyFragment extends RecyclerFragment {

    private MyAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new MyAdapter();

        // You have to set your adapter with the following method
        setAdapter(adapter);

        // Like ListFragment, the RecyclerView is hidden by default.
        // Setting the adapter with setAdapter(adapter) will display it.
        // Since we load data asynchronously, we want to show
        // the progress indicator while loading.
        setRecyclerShown(false);

        // Load some data asynchronously
        new AsyncTask<Void, Void, String[]>() {

            @Override
            protected String[] doInBackground(Void... params) {
                return dataLoadedFromNetwork();
            }

            @Override
            protected void onPostExecute(String[] result) {
                // Update data in our adapter
                mAdapter.setData(result);
                mAdapter.notifyDataSetChanged();

                // Stop showing the progress indicator
                setRecyclerShown(true);
            }

        }.execute();
    }
}
```

Note that the progress indicator won't we shown if your data is loaded
in less than 500 ms. This is an expected behavior: similarly to
`ContentLoadingProgressBar`, the progress indicator is only shown if it
will be displayed a sufficient amount of time to avoid UI "flashes".

# Using a custom layout #

You may need to customize the layout of RecyclerFragment.
All you have to do is to override `onCreateView` and inflate
your custom view hierarchy. Howether, your layout has to meet
the following criterias:
- It must contain a `RecyclerView` with id `@id/recycler`
- It must contain any `View` with id `@id/progress` to be displayed
when the `RecyclerView` is hidden by `setRecyclerShown(false)`.

You may optionally specify a `View` to be automatically displayed
in place of the `RecyclerView` when the adapter is empty:
just mark it with the id `@id/empty`.

The following is an example of custom layout for RecyclerFragment:

### fragment_custom.xml ###
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout android:id="@+id/parent"
             xmlns:android="http://schemas.android.com/apk/res/android"
             android:layout_width="match_parent"
             android:layout_height="match_parent">

    <ProgressBar
        android:id="@id/progress"
        style="?android:progressBarStyleLarge"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:visibility="gone"/>

    <FrameLayout
        android:id="@id/recycler_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView
            android:id="@id/recycler"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:clipToPadding="false"
            android:paddingTop="8dp"
            android:paddingBottom="8dp"/>

        <TextView
            android:id="@id/empty"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="No items to display."
            android:textAppearance="@style/TextAppearance.AppCompat.Large"
            android:layout_gravity="center"/>
    </FrameLayout>
</FrameLayout>
```

### MyFragment.java ###

```java
public class MyFragment extends RecyclerFragment {

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom, container, false);
    }
}
```

Don't forget to set your LayoutManager in XML or via
`setLayoutManager(RecyclerView.LayoutManager)`,
as only the default implementation uses a `LinearLayoutManager` when
no other is provided.

# License #
MIT