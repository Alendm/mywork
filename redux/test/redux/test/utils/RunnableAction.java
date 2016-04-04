package redux.test.utils;

import redux.Action;

public class RunnableAction implements Action {
    public final Runnable callBack;

    public RunnableAction(Runnable callBack) {
        this.callBack = callBack;
    }
}
