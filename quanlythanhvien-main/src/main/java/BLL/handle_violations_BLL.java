/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import BLL.DTO.handle_violations_DTO;
import BLL.DTO.member;
import DAL.handle_violations_DAL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Win10pro
 */
public class handle_violations_BLL {
    private final handle_violations_DAL data = new handle_violations_DAL();

    public ArrayList<handle_violations_DTO> getAllHandleViolations() {
        return data.getAllHandleViolations();
    }
    
    // Thêm mới xử lý vi phạm
    public void addHandleViolation(handle_violations_DTO newHandleViolation) throws Exception {
        data.addHandleViolation(newHandleViolation);
    }
    
    // Lấy danh sách tên thành viên
    public ArrayList<String> getMemberNames() {
        return data.getMemberNames();
    }
    
    // Lấy mã thành viên tương ứng với tên thành viên
    public String getMemberId(String memberName) {
        return data.getMemberId(memberName);
    }
    
    // Phương thức để lấy thông tin thành viên từ tên thành viên
    public member getMemberByName(String memberName) {
        // Gọi phương thức của lớp DAL để lấy thông tin thành viên từ tên
        List<member> members = data.getMembersByName(memberName);
        
        // Kiểm tra nếu danh sách thành viên không rỗng
        if (members != null && !members.isEmpty()) {
            // Trả về thành viên đầu tiên trong danh sách (giả sử tên thành viên là duy nhất)
            return members.get(0);
        } else {
            // Nếu không tìm thấy thành viên, trả về null
            return null;
        }
    }
    
    // Cập nhật thông tin xử lý vi phạm
    public void updateHandleViolation(handle_violations_DTO handleViolation) throws Exception {
        try {
            // Gọi phương thức từ lớp DAL để cập nhật thông tin
            data.updateHandleViolation(handleViolation);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void deleteHandleViolation(int handleViolationId) throws Exception {
        data.deleteHandleViolation(handleViolationId);
    }


}
