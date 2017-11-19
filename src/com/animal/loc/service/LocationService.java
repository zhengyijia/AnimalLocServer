package com.animal.loc.service;

import com.animal.loc.contract.IAnimalLocHandler;
import com.animal.loc.contract.LocationContract;
import com.animal.loc.entity.AnimalLocMsg;
import com.animal.loc.entity.ContentMsgBean.LocMsgBean;
import com.animal.loc.model.LocationModel;

public class LocationService implements LocationContract.ILocationService {

    private IAnimalLocHandler mAnimalLocHandler;
    private LocationContract.ILocationModel mLocationModel;

    public LocationService(IAnimalLocHandler animalLocHandler) {
        mAnimalLocHandler = animalLocHandler;
        mLocationModel = new LocationModel();
    }

    // 处理接收到的消息
    @Override
    public void handleMsg(AnimalLocMsg animalLocMsg, Long sessionId) {
        LocMsgBean locMsgBean = new LocMsgBean(animalLocMsg);


    }

}
