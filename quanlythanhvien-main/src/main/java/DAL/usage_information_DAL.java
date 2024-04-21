package DAL;

import BLL.DTO.usage_information;
import DAL.UTILS.hibernate_util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class usage_information_DAL {
    static final SessionFactory FACTORY = hibernate_util.getSessionFactory();

    public List<usage_information> load_usage_information() {
        Transaction transaction = null;
        List<usage_information> usage_information_list = null;
        try (Session session = FACTORY.openSession()){
            transaction = session.beginTransaction();
            usage_information_list = session.createQuery("FROM usage_information").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return usage_information_list;
    }
}
