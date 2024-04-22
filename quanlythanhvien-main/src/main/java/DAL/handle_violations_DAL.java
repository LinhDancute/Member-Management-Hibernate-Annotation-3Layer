/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import BLL.DTO.handle_violations_DTO;
import BLL.DTO.member;
import DAL.UTILS.hibernate_util;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Win10pro
 */
public class handle_violations_DAL {
    
    private final SessionFactory factory = hibernate_util.getSessionFactory();

    public ArrayList<handle_violations_DTO> getAllHandleViolations() {
        ArrayList<handle_violations_DTO> handleViolations = new ArrayList<>();
        try (Session session = factory.openSession()) {
            handleViolations = (ArrayList<handle_violations_DTO>) session.createQuery("FROM handle_violations_DTO").list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handleViolations;
    }
    
        // Thêm mới xử lý vi phạm
    public void addHandleViolation(handle_violations_DTO newHandleViolation) throws Exception {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(newHandleViolation);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // Lấy danh sách tên thành viên
    public ArrayList<String> getMemberNames() {
        ArrayList<String> memberNames = new ArrayList<>();
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("SELECT HoTen FROM member");
            memberNames = (ArrayList<String>) query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return memberNames;
    }
    
    // Lấy mã thành viên tương ứng với tên thành viên
    public String getMemberId(String memberName) {
        int memberId = -1; // Khởi tạo giá trị mặc định là -1 hoặc giá trị không hợp lệ khác

        try (Session session = factory.openSession()) {
            Query query = session.createQuery("SELECT MaTV FROM member WHERE HoTen = :name");
            query.setParameter("name", memberName);

            // Thực hiện truy vấn và gán kết quả cho memberId
            Object result = query.uniqueResult();
            if (result != null) {
                memberId = (int) result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chuyển đổi memberId thành String (nếu cần thiết)
        return String.valueOf(memberId);
    }
    
    public List<member> getMembersByName(String memberName) {
        // Khởi tạo danh sách kết quả
        List<member> members = null;
        
        // Mở một phiên làm việc với Hibernate
        try (Session session = hibernate_util.getSessionFactory().openSession()) {
            // Bắt đầu giao dịch
            Transaction transaction = session.beginTransaction();
            
            // Tạo truy vấn để lấy danh sách thành viên theo tên
            Query query = session.createQuery("FROM member WHERE HoTen = :name");
            query.setParameter("name", memberName);
            
            // Thực hiện truy vấn và gán kết quả cho danh sách members
            members = query.list();
            
            // Kết thúc giao dịch
            transaction.commit();
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có
            e.printStackTrace();
        }
        
        // Trả về danh sách thành viên
        return members;
    }
    
    public void updateHandleViolation(handle_violations_DTO handleViolation) throws Exception {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.merge(handleViolation);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    public void deleteHandleViolation(int handleViolationId) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            handle_violations_DTO handleViolation = session.get(handle_violations_DTO.class, handleViolationId);
            if (handleViolation != null) {
                session.delete(handleViolation);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    
}
