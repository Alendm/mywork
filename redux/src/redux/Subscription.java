package redux;

public class Subscription {
    interface CallBack {
        void call();
    }

    private boolean isSubscribed = true;

    public boolean isSubscribed() {
        return isSubscribed;
    }
    private CallBack callBack;

    public void cancel() {
        if (!isSubscribed) {
            return;
        }
        isSubscribed = false;
        callBack.call();
    }

    Subscription(CallBack unsubscribe) {
        this.callBack = unsubscribe;
    }
}
