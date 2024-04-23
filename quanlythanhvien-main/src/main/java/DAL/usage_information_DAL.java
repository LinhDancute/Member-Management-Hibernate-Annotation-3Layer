package DAL;

import BLL.DTO.device;
import BLL.DTO.usage_information;
import DAL.UTILS.hibernate_util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class usage_information_DAL {
    public static final SessionFactory FACTORY = hibernate_util.getSessionFactory();

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

    public usage_information getUsageInformationById(int serviceId) {
        Transaction transaction = null;
        try (Session session = FACTORY.openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM usage_information WHERE MaTT = :serviceId");
            query.setParameter("serviceId", serviceId);
            usage_information usageInfo = (usage_information) query.uniqueResult();
            transaction.commit();
            return usageInfo;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateUsageInformation(usage_information usageInfoToUpdate) throws Exception {
        Transaction transaction = null;
        try (Session session = FACTORY.openSession()){
            transaction = session.beginTransaction();
            session.merge(usageInfoToUpdate); // Update the usage information
            transaction.commit();
            return true; // Return true to indicate successful update
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction if an exception occurs
            }
            e.printStackTrace();
            return false; // Return false to indicate update failure
        }
    }
}
