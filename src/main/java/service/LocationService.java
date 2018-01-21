package service;

import contract.IAnimalLocHandler;
import contract.LocationContract;
import entity.AnimalLocMsg;
import entity.ContentMsgBean.LocMsgBean;
import entity.TableBean.AnimalLocLocInfoEntity;
import model.LocationModel;
import org.apache.mina.core.session.IoSession;

public class LocationService implements LocationContract.ILocationService {

    private IAnimalLocHandler mAnimalLocHandler;
    private LocationContract.ILocationModel mLocationModel;

    public LocationService(IAnimalLocHandler animalLocHandler) {
        mAnimalLocHandler = animalLocHandler;
        mLocationModel = new LocationModel();
    }

    // 处理接收到的消息
    @Override
    public void handleMsg(AnimalLocMsg animalLocMsg, IoSession session) {
        String IMEI = mAnimalLocHandler.getIMEI(session.getId());

        LocMsgBean locMsgBean = new LocMsgBean(animalLocMsg);

        AnimalLocLocInfoEntity locInfo = new AnimalLocLocInfoEntity();
        locInfo.setImei(IMEI);
        locInfo.setTime(locMsgBean.getTime());
        locInfo.setLatitude(locMsgBean.getLatitude());
        locInfo.setLongitude(locMsgBean.getLongitude());
        locInfo.setTrack(locMsgBean.getTrack());

        mLocationModel.saveLocInfo(locInfo);
    }

}
