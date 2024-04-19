/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import BLL.DTO.device;
import DAL.device_DAL;
import java.util.ArrayList;

/**
 *
 * @author Win10pro
 */
public class device_BLL {
    
    private final device_DAL data = new device_DAL();

    public device_BLL() {
    }
    
    // LẤY DANH SÁCH THIẾT BỊ
    public  ArrayList<device> getAllDevices() {
        return data.getAllDevices();
    }
    
    // THÊM MỚI THIẾT BỊ
    public void addDevice(device d) throws Exception {
        data.addDevice(d);
    }
    
    // CẬP NHẬT THÔNG TIN THIẾT BỊ
    public void updateDevice(int id, device updatedDevice) throws Exception {
        data.updateDevice(id, updatedDevice);
    }
    
    // XÓA THIẾT BỊ
    public void deleteDevice(int device_id) throws Exception {
        data.deleteDevice(device_id);
    }
    
    public void addDevices(ArrayList<device> devices) throws Exception {
        for (device d : devices) {
            // Kiểm tra xem mã thiết bị đã tồn tại chưa
            if (data.isDeviceExists(d.getMaTB())) {
                throw new Exception("Mã thiết bị đã tồn tại: " + d.getMaTB());
            }
            data.addDevice(d);
        }
    }
    
}
