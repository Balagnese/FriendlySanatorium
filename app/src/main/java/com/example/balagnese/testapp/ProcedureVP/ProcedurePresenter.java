package com.example.balagnese.testapp.ProcedureVP;

import android.os.Handler;
import android.os.Looper;

import com.example.balagnese.testapp.DataTypes.ClientProcedure;
import com.example.balagnese.testapp.DataTypes.Procedure;
import com.example.balagnese.testapp.Models.ProcedureModel;


public class ProcedurePresenter {

    private ProcedureModel pm = new ProcedureModel();
    private ProcedureView pv;
    private Handler handler;

    public ProcedurePresenter(ProcedureView procedureView, int id){
        handler = new android.os.Handler(Looper.getMainLooper());
        this.pv = procedureView;
        loadProcedureInfo(id);

    }
    //TODO найти баг в просмотре процедуры
    public void loadProcedureInfo(int id){
        pm.getProcedure(id, new ProcedureModel.ProcedureCallback() {
            @Override
            public void onProcedureReceive(final Procedure procedure) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        pv.showProcedureInfo(procedure);
                    }
                };
                handler.post(runnable);


            }

            @Override
            public void onProcedureReceiveFailure(Exception e) {
                pv.navigateToClientActivity();
            }
        });
    }

    public void navigateToClientActivity(){
        pv.navigateToClientActivity();
    }
}
