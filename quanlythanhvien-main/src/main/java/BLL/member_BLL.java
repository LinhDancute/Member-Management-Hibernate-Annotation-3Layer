/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL;

import BLL.DTO.member;
import DAL.member_DAL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class member_BLL {

    static ArrayList<member> list_member;
    private member_DAL data = new member_DAL();

    public member_BLL() {
    }

    public static ArrayList<member> get_list_member() {
        return list_member;
    }

    public static void set_list_member(ArrayList<member> list_member) {
        member_BLL.list_member = list_member;
    }

    //HIỂN THỊ    
    public ArrayList<member> load_member() {
        return (ArrayList<member>) data.load_member();
    }
    
    //HIỂN THỊ KHÓA
    public List<String> load_batch() {
        return data.load_batch();
    }
    
    //HIỂN THỊ NGÀNH
    public List<String> load_department() {
        return data.load_department();
    }

    //HIỂN THỊ KHOA
    public List<String> load_major() {
        return data.load_major();
    }
    
    //HIỂN THỊ KHOA THEO KHÓA
    public List<String> load_departments_by_batch(String department) {
        return data.get_departments_by_batch(department);
    }

    //HIỂN THỊ NGÀNH THEO KHOA
    public List<String> load_majors_by_department(String major, String batch) {
        return data.get_majors_by_department(major, batch);
    }
    
    //THÊM
    public void add_member(member m) throws Exception {
        if (list_member == null) {
            list_member = new ArrayList<member>();
        }
        data.add_member(m);// gọi Layer DAL hàm đọc data từ CSDL
    }

    //XÓA
    public void delete_member(int member_id) throws Exception {
        for (member m : list_member) {
            if (m.getMaTV()== member_id) {
                try {
                    data.delete_member(member_id);
                    list_member.remove(m);// xoá trong arraylist
                } catch (Exception e) {
                    System.out.println("Không thể xóa thành viên khỏi CSDL");
                }
                return;
            }
        }
    }
    
    //XÓA NHIỀU THEO ĐIỀU KIỆN
    public void delete_all_member(String department, String major, String batch) throws Exception {
        try {
            data.delete_all_member(department, major, batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CẬP NHẬT
    public void update_member(int id, member m) throws Exception {
        for (int i = 0; i < list_member.size(); i++) {
            if (list_member.get(i).getMaTV()== m.getMaTV()) {
                try {
                    data.update_member(id, m);
                    list_member.set(i, m);
                } catch (Exception e) {
                    System.out.println("Không thể cập nhật thành viên vào CSDL");
                }
                return;
            }
        }
    }

    //IMPORT EXCEL
    public void import_excel(ArrayList<member> member) throws IOException, Exception {
        for (member m : member) {
            data.add_member(m);
        }
    }

    public boolean is_member_existed(int member_id) {
        try {
            return data.is_member_existed(member_id);
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    public String get_member_name(int member_id) {
        return data.get_member_name_by_member_id(member_id);
    }

    public member get_member_by_member_id(int member_id) {
        return data.get_member_by_member_id(member_id);
    }
}
