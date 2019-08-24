package com.example.balagnese.testapp.ChildVP;

import android.os.Handler;
import android.os.Looper;

import com.example.balagnese.testapp.ClientInfo;
import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.Models.ClientModel;

public class ChildPresenter {
    private ClientModel cm = new ClientModel();
    private String publicId;
    private ChildView cv;
    private Handler handler;
    private Client child;

    public ChildPresenter(ChildView childView, String publicId){
        cv = childView;
        this.publicId = publicId;
        handler = new Handler(Looper.getMainLooper());
        loadChilddata();
    }

    public void loadChilddata(){
        Client child = ClientInfo.getInstance().getChildByPublicId(publicId);
        if (child == null){
            cv.navigateToClientActivity();
        }
        cv.showChildData(child);
        this.child = child;
    }

    public void navigateToClientActivity(){
        cv.navigateToClientActivity();
    }

    public void navigateToChildProceduresActivity(){
        cv.navigateToChildProceduresActivity(child);
    }

    public void navigateToChildMenuActivity(){
        cv.navigateToChildMenuActivity(child);
    }
}
