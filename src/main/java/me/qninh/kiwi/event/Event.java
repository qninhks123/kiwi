package me.qninh.kiwi.event;

public class Event {
    
    private boolean isCanceled = false;

    public void setCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

}