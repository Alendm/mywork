package redux.test.utils;

import redux.Listener;

public class SpyListener implements Listener {
    private int callCount = 0;

    public int getCallCount() {
        return callCount;
    }

    @Override
    public void call() {
        callCount++;
    }
}
