package com.example.balagnese.testapp.ProcedureVP;

import com.example.balagnese.testapp.DataTypes.Procedure;

public interface ProcedureView {
    void showProcedureInfo(Procedure clientProcedure);
    void navigateToClientActivity();
}
