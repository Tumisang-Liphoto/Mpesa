package com.workers;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.mpesa.GetHoverActionsWorker;
import com.actions.HoverAction;
import com.actions.HoverActionDao;
import com.transactions.HoverTransaction;
import com.transactions.HoverTransactionDao;

import java.util.List;


public class HoverRepository {

    private HoverActionDao mActionDao;
    private HoverTransactionDao mTransactionDao;
    private LiveData<List<HoverAction>> mAllActions;
    private WorkManager mWorkManager;


    public HoverRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mActionDao = db.actionDao();
        mTransactionDao = db.transactionDao();
        mAllActions = mActionDao.getAllActions();
        mWorkManager = WorkManager.getInstance();
    }

    public LiveData<List<HoverAction>> getAllActions() {
        return mAllActions;
    }

    public HoverAction getAction(String actionId) {
        return mActionDao.getAction(actionId);
    }

    public HoverAction getAnyAction() {
        return mActionDao.getAnyAction();
    }

    public void loadActions() {
        mWorkManager.enqueue(OneTimeWorkRequest.from(GetHoverActionsWorker.class));
    }

    public LiveData<List<HoverTransaction>> getAllTransactionsByActionId(String actionId) {
        return mTransactionDao.getTransactionsByActionId(actionId);
    }

    public void insertTransaction(HoverTransaction transaction) {
        mTransactionDao.insert(transaction);
    }

    public LiveData<List<HoverTransaction>> getAllTransactions() {
        return mTransactionDao.getAllTransactions();
    }
}


