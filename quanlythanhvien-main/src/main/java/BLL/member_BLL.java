/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL;

import BLL.DTO.member;
import DAL.member_DAL;
import com.mysql.cj.result.Row;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    
    public List<String> getMemberNames() {
        List<member> members = data.getAllMembers();
        List<String> memberNames = new ArrayList<>();
        for (member m : members) {
            memberNames.add(m.getHoTen());
        }
        return memberNames;
    }
    
    public int getMemberIdByName(String memberName) {
        member member = data.getMemberByName(memberName);
        if (member != null) {
            return member.getMaTV();
        } else {
            return -1; // Hoặc giá trị khác thích hợp nếu không tìm thấy
        }
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

    public void import_excel(File selectedFile) throws IOException {
//            // Load Excel file
//            FileInputStream inputStream = new FileInputStream(selectedFile);
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//
//            // Get the first sheet from the workbook
//            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
//
//            // Iterate through each row of the sheet
//            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//
//                // Skip the header row
//                if (row.getRowNum() == 0) {
//                    continue;
//                }
//
//                // Read data from each cell and create a new member object
//                int memberId = (int) row.getCell(0).getNumericCellValue();
//                String memberName = row.getCell(1).getStringCellValue();
//                String department = row.getCell(2).getStringCellValue();
//                String major = row.getCell(3).getStringCellValue();
//                String phoneNumber = row.getCell(4).getStringCellValue();
//                String password = String.valueOf(memberId);
//                String email = memberId + "@sv.sgu.edu.vn";
//
//                member newMember = new member(memberId, memberName, department, major, phoneNumber, password, email);
//
//                // Add the new member to the list
//                if (list_member == null) {
//                    list_member = new ArrayList<>();
//                }
//                list_member.add(newMember);
//            }
//
//            // Close workbook and input stream
//            workbook.close();
//            inputStream.close();
//        }
    }
}
