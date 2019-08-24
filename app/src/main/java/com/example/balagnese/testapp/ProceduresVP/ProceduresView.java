package com.example.balagnese.testapp.ProceduresVP;

import com.example.balagnese.testapp.DataTypes.ClientProcedure;

import java.util.List;
import java.util.Map;

public interface ProceduresView {
    public void showProcedures(Map<String, List<ClientProcedure>> procedures);
    public void navigateToHomeActivity();
    public void navigateToProcedureActivity(ClientProcedure procedure);
}
