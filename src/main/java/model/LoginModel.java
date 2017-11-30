package model;

import contract.LoginContract;
import entity.TableBean.AnimalLocImeiEntity;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

public class LoginModel implements LoginContract.ILoginModel {

    // 验证是否存在对应IMEI
    @Override
    public boolean verifyIMEI(String IMEI) {
        boolean verify = false;

        // 打开线程安全的Session对象
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.currentSession();
            // 打开事务
            transaction = session.beginTransaction();
            Criteria c = session.createCriteria(AnimalLocImeiEntity.class);
            c.add(Restrictions.eq("imei", IMEI));
            verify = !c.list().isEmpty();
            transaction.commit();
        } catch (HibernateException e) {
            if (null != transaction) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            //通过工具类关闭Session
            HibernateUtil.closeSession();
        }

        return verify;
    }

}
