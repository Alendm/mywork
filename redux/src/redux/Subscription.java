package redux;

public class Subscription {
    private boolean isSubscribed = true;
    public boolean isSubscribed() {
        return isSubscribed;
    }

    private final Runnable callBack;

    public void cancel() {
        if (!isSubscribed) {
            return;
        }
        isSubscribed = false;
        callBack.run();
    }

    Subscription(Runnable unsubscribe) {
        this.callBack = unsubscribe;
    }
}
