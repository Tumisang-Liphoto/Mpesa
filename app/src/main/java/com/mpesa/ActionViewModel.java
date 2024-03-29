package com.mpesa;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.actions.HoverAction;

import java.util.List;

public class ActionViewModel extends AndroidViewModel {

    private HoverRepository mRepository;
    private LiveData<List<HoverAction>> mAllActions;

    public ActionViewModel(Application application) {
        super(application);

        mRepository = new HoverRepository(application);
        mAllActions = mRepository.getAllActions();
    }

    LiveData<List<HoverAction>> getAllActions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HoverAction action = mRepository.getAnyAction();
                if (action == null) {
                    mRepository.loadActions();
                }
            }
        }).start();

        return mAllActions;
    }

    void loadAllActions() { mRepository.loadActions();}
}
