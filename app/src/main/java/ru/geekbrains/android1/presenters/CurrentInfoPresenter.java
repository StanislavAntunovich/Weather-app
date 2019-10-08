package ru.geekbrains.android1.presenters;

import java.util.Stack;

public class CurrentInfoPresenter {
    private static CurrentInfoPresenter instance;

    private static final Object lock = new Object();

    private int currentIndex;
    private Stack<Integer> fragmentsIndexes;

    public static CurrentInfoPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new CurrentInfoPresenter();
            }
            return instance;
        }
    }

    private CurrentInfoPresenter() {
        this.currentIndex = -1;
        this.fragmentsIndexes = new Stack<>();
    }

    public Stack<Integer> getFragmentsIndexes() {
        return fragmentsIndexes;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
