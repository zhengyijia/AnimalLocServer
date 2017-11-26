package service;

import contract.IAnimalLocHandler;
import contract.LocationContract;
import entity.AnimalLocMsg;
import entity.ContentMsgBean.LocMsgBean;
import entity.TableBean.AnimalLocLocInfoEntity;
import model.LocationModel;

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
        String IMEI = mAnimalLocHandler.getIMEI(sessionId);

        if (!checkIMEI(IMEI))
            return;

        LocMsgBean locMsgBean = new LocMsgBean(animalLocMsg);

        AnimalLocLocInfoEntity locInfo = new AnimalLocLocInfoEntity();
        locInfo.setImei(IMEI);
        locInfo.setTime(locMsgBean.getTime());
        locInfo.setLatitude(locMsgBean.getLatitude());
        locInfo.setLongitude(locMsgBean.getLongitude());
        locInfo.setTrack(locMsgBean.getTrack());

        mLocationModel.saveLocInfo(locInfo);
    }

    private boolean checkIMEI(String IMEI) {
        if (IMEI == null)
            return false;

        return true;
    }

}
