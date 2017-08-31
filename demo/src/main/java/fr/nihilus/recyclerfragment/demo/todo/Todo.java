package fr.nihilus.recyclerfragment.demo.todo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

class Todo implements Parcelable {
    static final List<Todo> TODOS = Arrays.asList(
            new Todo("Use the RecyclerFragment library whenever I have to deal " +
                    "with a RecyclerView and a Fragment"),
            new Todo("Watch the latest Game of Throne episode"),
            new Todo("Go to the swimming pool on Friday"),
            new Todo("Have a beer with friends"),
            new Todo("Save the World before Superman does"),
            new Todo("Develop my revolutionary app"),
            new Todo("Understand Dependency Injection with Dagger 2")
    );

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    private String label;
    private boolean done;

    private Todo(String label) {
        this.label = label;
    }

    protected Todo(Parcel in) {
        label = in.readString();
        done = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeByte((byte) (done ? 1 : 0));
    }

    String getLabel() {
        return label;
    }

    void setLabel(String label) {
        this.label = label;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }


}
