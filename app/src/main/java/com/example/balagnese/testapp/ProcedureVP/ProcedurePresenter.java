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
        this.pv = procedureView;
        loadProcedureInfo(id);
        handler = new android.os.Handler(Looper.getMainLooper());
    }

    public void loadProcedureInfo(int id){
        pm.getProcedure(id, new ProcedureModel.ProcedureCallback() {
            @Override
            public void onProcedureReceive(Procedure procedure) {
                pv.showProcedureInfo(procedure);
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
