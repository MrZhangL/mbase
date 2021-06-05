package com.tomcode.disruptor;

public class LongEvent {
    private Long value;

    public LongEvent() {
    }

    public LongEvent(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
