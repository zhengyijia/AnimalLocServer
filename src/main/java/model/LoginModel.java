package model;

import contract.LoginContract;
import entity.TableBean.AnimalLocImeiEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

public class LoginModel implements LoginContract.ILoginModel {

    // 验证是否存在对应IMEI
    @Override
    public boolean verifyIMEI(String IMEI) {
        // 打开线程安全的Session对象
        Session session = HibernateUtil.currentSession();
        // 打开事务
        Transaction transaction = session.beginTransaction();
        Criteria c = session.createCriteria(AnimalLocImeiEntity.class);
        c.add(Restrictions.eq("imei", IMEI));
        boolean verify = !c.list().isEmpty();
        transaction.commit();
        //通过工具类关闭Session
        HibernateUtil.closeSession();

        return verify;
    }

}
