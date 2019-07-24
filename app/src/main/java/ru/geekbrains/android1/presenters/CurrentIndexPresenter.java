package ru.geekbrains.android1.presenters;

public class CurrentIndexPresenter {
    private static CurrentIndexPresenter instance;

    private static final Object lock = new Object();

    private int currentIndex;

    public static CurrentIndexPresenter getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new CurrentIndexPresenter();
            }
            return instance;
        }
    }

    private CurrentIndexPresenter() {
        this.currentIndex = 0;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
