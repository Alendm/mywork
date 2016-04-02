package redux.test.utils;

import redux.Action;



public class RunableAction implements Action {
    public final Runnable callBack;

    public RunableAction(Runnable callBack) {
        this.callBack = callBack;
    }
}
