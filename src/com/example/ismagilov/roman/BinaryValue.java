package com.example.ismagilov.roman;

public enum BinaryValue {
    ZERO, ONE;

    public static BinaryValue fromInt(int value) {
        if (value == 0) {
            return BinaryValue.ZERO;
        }
        return BinaryValue.ONE;
    }
}
