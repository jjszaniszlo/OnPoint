package com.ateam.onpoint.core.helpers;

import java.util.Comparator;
import com.ateam.onpoint.core.Task;

import java.time.LocalDate;
import java.util.Collections;

public class SortByDate implements Comparator<Task> {

    // Method of this class
    // @Override
    public int compare(Task a, Task b)
    {

        // Returning the value after comparing the objects
        // this will sort the data in Ascending order
        //return 0;
        if(a.startDateProperty().get() == b.startDateProperty().get())
        {
            return 0;
        }

        return a.startDateProperty().get().isAfter(b.startDateProperty().get()) ? 1 : -1;
    }
}