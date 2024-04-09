package com.ateam.onpoint.core;

import org.jetbrains.annotations.Nullable;

public class TaskInfo {
    @Nullable private String description;

    public TaskInfo(@Nullable String description) {
        this.description = description;
    }
}