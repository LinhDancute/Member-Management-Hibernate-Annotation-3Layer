package BLL;

import BLL.DTO.device;
import BLL.DTO.member;
import BLL.DTO.usage_information;
import DAL.usage_information_DAL;
import DAL.device_DAL;
import org.hibernate.Transaction;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class usage_information_BLL {

    static ArrayList<usage_information> usage_information_list;
    private final usage_information_DAL usageInformationDal_data = new usage_information_DAL();
    private final device_DAL device_data = new device_DAL();

    public usage_information_BLL() {}

    public static ArrayList<usage_information> get_usage_information_list() {
        return usage_information_list;
    }

    public static void setUsage_information_list(ArrayList<usage_information> usage_information_list) {
        usage_information_BLL.usage_information_list = usage_information_list;
    }

    //load database into table
    public ArrayList<usage_information> load_usage_information_list() {
        return (ArrayList<usage_information>) usageInformationDal_data.load_usage_information();
    }

    //update combobox device
    public void update_device_combobox(JComboBox<String> comboBox) {
        try{
            List<device> deviceList = device_data.load_device();

            for (device device : deviceList) {
                comboBox.addItem(device.getTenTB());
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void update_return_device_time(int serviceId, Date currentTime) throws Exception{
        try{
            usage_information obj = usageInformationDal_data.getUsageInformationById(serviceId);

            if (obj != null){
                obj.setTGTra(currentTime);

                usageInformationDal_data.updateUsageInformation(obj);
            }else {
                System.out.println("No usage information found for service ID: " + serviceId);
            }
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    //add usage information
    public void update_usage_information(int serviceId, String selectedDevice, Date currentTime) throws Exception {
        try {
            // Retrieve the usage information object based on the service ID
            usage_information obj = usageInformationDal_data.getUsageInformationById(serviceId);

            // If the object is found
            if (obj != null) {
                // Set the selected device
                device existingDevice = device_data.getDeviceByName(selectedDevice);
                if (existingDevice != null){
                    obj.setThietbi(existingDevice);
                }else {
                    device deviceObj = new device();
                    deviceObj.setTenTB(selectedDevice);
                    device_data.saveDevice(deviceObj);
                    obj.setThietbi(deviceObj);
                }

                // Set the current time as the time borrowed
                obj.setTGMuon(currentTime);

                // Update the usage information in the database
                usageInformationDal_data.updateUsageInformation(obj);
            } else {
                // Handle case when no usage information is found for the given service ID
                System.out.println("No usage information found for service ID: " + serviceId);
            }
        } catch (Exception exception) {
            // Handle any exceptions
            System.out.println(exception.getMessage());
        }
    }

    public String getDeviceNameById(int deviceId) {
        device deviceObj = device_data.getDeviceById(deviceId);

        if (deviceObj != null) {
            return deviceObj.getTenTB();
        } else {
            return null;
        }
    }

}
