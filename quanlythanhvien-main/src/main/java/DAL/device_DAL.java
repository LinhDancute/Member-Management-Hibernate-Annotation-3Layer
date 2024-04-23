package DAL;

import BLL.DTO.device;
import DAL.UTILS.hibernate_util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class device_DAL {
    static final SessionFactory FACTORY = hibernate_util.getSessionFactory();

    public List<device> load_device() {
        Transaction transaction = null;
        List<device> device_list = null;
        try (Session session = FACTORY.openSession()){
            transaction = session.beginTransaction();
            device_list = session.createQuery("FROM device").list();
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
        return device_list;
    }

    public device getDeviceByName(String deviceName) {
        Transaction transaction = null;
        try (Session session = FACTORY.openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM device WHERE TenTB = :deviceName");
            query.setParameter("deviceName", deviceName);
            device deviceObj = (device) query.uniqueResult();
            transaction.commit();
            return deviceObj;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public device getDeviceById(int deviceId) {
        Transaction transaction = null;
        try (Session session = FACTORY.openSession()){
            transaction = session.beginTransaction();
            device deviceObj = session.get(device.class, deviceId);
            transaction.commit();
            return deviceObj;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public void saveDevice(device deviceObj) {
        Transaction transaction = null;
        try (Session session = FACTORY.openSession()){
            transaction = session.beginTransaction();
            session.save(deviceObj); // Save the device object
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction if an exception occurs
            }
            e.printStackTrace();
        }
    }
}
