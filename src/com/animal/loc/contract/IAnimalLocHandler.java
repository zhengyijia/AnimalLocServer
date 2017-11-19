package com.animal.loc.contract;

public interface IAnimalLocHandler {

    // 记录长连接线程对应的IMEI号
    void addIMEI(String IMEI, Long sessionId);
    // 获取sessionId对应IMEI号
    String getIMEI(Long sessionId);

}
