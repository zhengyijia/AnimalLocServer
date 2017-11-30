package model;

import contract.LocationContract;
import entity.TableBean.AnimalLocLocInfoEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class LocationModel implements LocationContract.ILocationModel {

    // 保存gps数据
    @Override
    public void saveLocInfo (AnimalLocLocInfoEntity locInfo) {

        // 打开线程安全的Session对象
        Transaction transaction = null;
        try {
            // 打开线程安全的Session对象
            Session session = HibernateUtil.currentSession();
            // 打开事务
            transaction = session.beginTransaction();
            session.save(locInfo);
            transaction.commit();
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // 通过工具类关闭Session
            HibernateUtil.closeSession();
        }
    }

}
