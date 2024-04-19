/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import BLL.DTO.device;
import DAL.UTILS.hibernate_util;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Win10pro
 */
public class device_DAL {
    
    private final SessionFactory factory = hibernate_util.getSessionFactory();

    public device_DAL() {
    }
    
    // LẤY DANH SÁCH THIẾT BỊ
    public ArrayList<device> getAllDevices() {
        ArrayList<device> devices = new ArrayList<>();
        Session session = factory.openSession();
        try {
            devices = (ArrayList<device>) session.createQuery("FROM device").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return devices;
    }
    
    // THÊM MỚI THIẾT BỊ
    public void addDevice(device newDevice) throws Exception {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(newDevice);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    // CẬP NHẬT THÔNG TIN THIẾT BỊ
    public void updateDevice(int id, device updatedDevice) throws Exception {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            device existingDevice = session.get(device.class, id);
            existingDevice.setTenTB(updatedDevice.getTenTB());
            existingDevice.setMotaTB(updatedDevice.getMotaTB());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    // XÓA THIẾT BỊ
    public void deleteDevice(int device_id) throws Exception {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            device deviceToDelete = session.get(device.class, device_id);
            session.delete(deviceToDelete);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    public boolean isDeviceExists(int deviceID) {
        Session session = factory.openSession();
        try {
            device existingDevice = session.get(device.class, deviceID);
            return existingDevice != null;
        } finally {
            session.close();
        }
    }

    
}
