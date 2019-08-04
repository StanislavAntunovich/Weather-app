package ru.geekbrains.android1.presenters;

import java.util.Stack;

public class CurrentInfoPresenter {
    private static CurrentInfoPresenter instance;

    private static final Object lock = new Object();

    private int currentIndex;
    private Stack<String> fragments;

    public static CurrentInfoPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new CurrentInfoPresenter();
            }
            return instance;
        }
    }

    private CurrentInfoPresenter() {
        this.currentIndex = 0;
        this.fragments = new Stack<>();
    }

    public Stack<String> getFragments() {
        return fragments;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
