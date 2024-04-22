/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL;

import BLL.DTO.usage_information;
import DAL.usage_information_DAL;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class usage_information_BLL {
    static ArrayList<usage_information> list_usage_information;
    private usage_information_DAL data = new usage_information_DAL();

    public static ArrayList<usage_information> get_list_usage_information() {
        return list_usage_information;
    }

    public static void set_list_usage_information(ArrayList<usage_information> usage_information_list) {
        usage_information_BLL.list_usage_information = list_usage_information;
    }

    public ArrayList<usage_information> load_usage_information() {
        return (ArrayList<usage_information>) data.load_usage_information();
    }
    
    //THÊM
    public void add_income_member_information(usage_information u_i) throws Exception {
        if (list_usage_information == null) {
            list_usage_information = new ArrayList<usage_information>();
        }
        data.add_income_member_information(u_i);
    }
    
    //XÓA
    public void delete_income_member_information(int usage_information_id) {
        for (usage_information m : list_usage_information) {
            if (m.getMaTT()== usage_information_id) {
                try {
                    data.delete_income_member_information(usage_information_id);
                    list_usage_information.remove(m);
                } catch (Exception e) {
                    System.out.println("Không thể xóa thông tin khỏi CSDL");
                }
                return;
            }
        }
    }

    public List<usage_information> export_excel() {
        return data.export_excel();
    }

    public List<usage_information> filter_by_date(Date start_date, Date end_date) {
        Instant start_instant = start_date.toInstant();
        Instant end_instant = end_date.toInstant();
        LocalDateTime start_date_time = LocalDateTime.ofInstant(start_instant, ZoneId.systemDefault());
        LocalDateTime end_date_time = LocalDateTime.ofInstant(end_instant, ZoneId.systemDefault());

        return data.fetch_between_dates(start_date_time, end_date_time);
    }
}
