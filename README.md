[ ![JCenter](https://api.bintray.com/packages/nihilus/android/recyclerfragment/images/download.svg) ](https://bintray.com/nihilus/android/recyclerfragment/_latestVersion)

RecyclerFragment is a small Android library that allow you to easily
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

- Fragment with a `RecyclerView` and a hidable `ProgressBar` out of the box
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
will be displayed a sifficient amount of time to avoid UI "flashes".

# Using a custom layout #

