package com.ateam.onpoint.core.helpers;

import java.util.Comparator;
import com.ateam.onpoint.core.Task;

import java.time.LocalTime;
import java.util.Collections;

public class SortByTime implements Comparator<Task> {

    // Method of this class
    // @Override
    public int compare(Task a, Task b)
    {

        // Returning the value after comparing the objects
        // this will sort the data in Ascending order
        //return 0;
        if(a.startTimeProperty().get() == b.startTimeProperty().get())
        {
            return 0;
        }

        return a.startTimeProperty().get().isAfter(b.startTimeProperty().get()) ? 1 : -1;
    }
}