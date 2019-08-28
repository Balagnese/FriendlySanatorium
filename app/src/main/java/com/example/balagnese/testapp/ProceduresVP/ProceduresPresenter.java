package com.example.balagnese.testapp.ProceduresVP;

import android.os.Handler;
import android.os.Looper;

import com.example.balagnese.testapp.ClientInfo;
import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.DataTypes.ClientProcedure;
import com.example.balagnese.testapp.Models.ProcedureModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProceduresPresenter {

    private ProcedureModel pm = new ProcedureModel();
    private ProceduresView pv;
    private Handler handler;
    private String publicId;

//    public ProceduresPresenter(ProceduresView procedureView){
//        pv = procedureView;
//        handler = new Handler(Looper.getMainLooper());
//        loadCurrentClientProcedures();
//    }

    public ProceduresPresenter(ProceduresView procedureView, String publicId){
        pv = procedureView;
        handler = new Handler(Looper.getMainLooper());
        loadCurrentClientProcedures(publicId);
    }

    public void loadCurrentClientProcedures(String publicId){
        if (!publicId.equals("")){
            pm.getChildProcedures(publicId, new ProcedureModel.Callback() {
                @Override
                public void onClientProceduresReceive(final List<ClientProcedure> procedures) {
                    final List<ClientProcedure> p = procedures;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            pv.showProcedures(groupByDate(procedures));
                        }
                    };
                    handler.post(runnable);
                }

                @Override
                public void onClientProceduresReceiveFailure(Exception e) {
                    pv.navigateToHomeActivity();
                }
            });
        }
        else{
            pm.getCurrentClientProcedures(new ProcedureModel.Callback() {
                @Override
                public void onClientProceduresReceive(final List<ClientProcedure> procedures) {
                    final List<ClientProcedure> p = procedures;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            pv.showProcedures(groupByDate(procedures));
                        }
                    };
                    handler.post(runnable);
                }

                @Override
                public void onClientProceduresReceiveFailure(Exception e) {
                    pv.navigateToHomeActivity();
                }
            });
        }
    }

    private Map<String, List<ClientProcedure>> groupByDate(List<ClientProcedure> procedures){
        List<ClientProcedure> p = procedures;
        HashMap<String, List<ClientProcedure>> hashMap = new HashMap<>();
        for (int i = 0; i < p.size(); i++){
            if (!hashMap.containsKey(p.get(i).getDayMonthForPrint())) {
                List<ClientProcedure> list = new ArrayList<>();
                list.add(p.get(i));
                hashMap.put(p.get(i).getDayMonthForPrint(), list);
            } else {
                hashMap.get(p.get(i).getDayMonthForPrint()).add(p.get(i));
            }
        }
        for (Map.Entry<String, List<ClientProcedure>> entry : hashMap.entrySet()) {
            List<ClientProcedure> procedureList = hashMap.get(entry.getKey());
            Collections.sort(procedureList);
        }
        return hashMap;
    }

    public void navigateToProcedureActivity(ClientProcedure procedure){
        pv.navigateToProcedureActivity(procedure);
    }

    public void navigateToClientActivity(){
        pv.navigateToHomeActivity();
    }
}
