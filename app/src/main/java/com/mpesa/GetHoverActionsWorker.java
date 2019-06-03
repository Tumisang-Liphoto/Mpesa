package com.mpesa;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.actions.HoverAction;
import com.actions.HoverActionDao;
import com.workers.AppDatabase;
import com.network.NetworkOps;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class GetHoverActionsWorker extends Worker {
    private static final String TAG = "GetHoverActionsWorker";
    private final HoverActionDao mDao;
    private final NetworkOps mNetworkOps;

    public GetHoverActionsWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        mDao = AppDatabase.getInstance(context).actionDao();
        mNetworkOps = new NetworkOps(context);
    }

    @Override
    public ListenableWorker.Result doWork() {
        try {
            String actions = mNetworkOps.download("custom_actions");
            JSONArray jsonArray = new JSONArray(actions);
            mDao.deleteAll();
            for (int i = 0; i < jsonArray.length(); i++) {
                HoverAction action = new HoverAction(jsonArray.getJSONObject(i));
                mDao.insert(action);
            }
        } catch (IOException e) {
            Log.d(TAG, "download failed: " + e.getMessage(), e);
            return ListenableWorker.Result.failure();
        } catch (JSONException e) {
            Log.d(TAG, "Failed to parse json array:" + e.getMessage(), e);
            return ListenableWorker.Result.failure();
        }

        return ListenableWorker.Result.success();
    }
}

