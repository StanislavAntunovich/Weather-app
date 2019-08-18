package ru.geekbrains.android1.utils;

public class UnitsConverter {
    public static String getUntis(int index) {
        switch (index) {
            case 1: return "I";
            case 2: return "S";
            default: return "M";
        }
    }
}
