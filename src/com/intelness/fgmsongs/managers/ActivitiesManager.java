package com.intelness.fgmsongs.managers;

import java.util.Stack;

import android.app.Activity;

public class ActivitiesManager extends Stack<Activity> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public boolean empty() {
        return super.empty();
    }

    @Override
    public synchronized Activity peek() {
        return super.peek();
    }

    @Override
    public synchronized Activity pop() {
        return super.pop();
    }

    @Override
    public Activity push( Activity activity ) {
        return super.push( activity );
    }

    @Override
    public synchronized int search( Object o ) {
        return super.search( o );
    }

}
