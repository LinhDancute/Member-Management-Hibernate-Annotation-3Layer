/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package DAL;

import BLL.DTO.member;
import DAL.UTILS.hibernate_util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author ACER
 */
public class member_DAL {
    static final SessionFactory FACTORY = hibernate_util.getSessionFactory();
    
    //LẤY DỮ LIỆU
    public List<member> load_member() {
        Transaction transaction = null;
        List<member> mem_list = null;
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            mem_list = session.createQuery("FROM member").list();
            System.out.println("list of members: "+mem_list);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return mem_list;
    }
    
    //LẤY KHÓA (CẮT)
    public List<String> load_batch() {
        Transaction transaction = null;
        List<member> batch_list = null;
        Set<String> unique_values = new HashSet<>(); 
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            batch_list = session.createQuery("FROM member").list();
            transaction.commit();
            for (member m : batch_list) {
                String maTV = String.valueOf(m.getMaTV());
                if (maTV.length() >= 4) {
                    String extracted_value = maTV.substring(2, 4);
                    unique_values.add(extracted_value); 
                }
            }
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        List<String> sorted_values = new ArrayList<>(unique_values); 
        Collections.sort(sorted_values); 
        return sorted_values;
    }
    
    //LẤY KHOA
    public List<String> load_department() {
        Transaction transaction = null;
        List<member> department_list = null;
        Set<String> unique_values = new HashSet<>(); 
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            department_list = session.createQuery("FROM member").list();
            transaction.commit();
            for (member m : department_list) {
                String department = String.valueOf(m.getKhoa());
                if (department != null && !department.isEmpty() && !department.equalsIgnoreCase("null")) {
                    unique_values.add(department);
                }
            }
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        List<String> sorted_values = new ArrayList<>(unique_values); 
        Collections.sort(sorted_values); 
        return sorted_values;
    }

    
    //LẤY NGÀNH
    public List<String> load_major() {
        Transaction transaction = null;
        List<member> department_list = null;
        Set<String> unique_values = new HashSet<>(); 
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            department_list = session.createQuery("FROM member").list();
            transaction.commit();
            for (member m : department_list) {
                String major = m.getNganh();
                if (major != null && !major.isEmpty() && !major.equalsIgnoreCase("null")) {
                    unique_values.add(major);
                }
            }
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        List<String> sorted_values = new ArrayList<>(unique_values); 
        Collections.sort(sorted_values); 
        return sorted_values;
    }


    //LẤY KHOA CÓ TRONG KHÓA 
    public List<String> get_departments_by_batch(String batch) {
        Transaction transaction = null;
        List<String> departments = new ArrayList<>();
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            
            Query<String> query = session.createQuery(
                "SELECT DISTINCT m.Khoa FROM member m WHERE substring(cast(m.MaTV as string), 3, 2) = :batch",
                String.class
            );
            query.setParameter("batch", batch);
            departments = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return departments;
    }
    
    //LẤY NGÀNH CÓ TRONG KHOA
    public List<String> get_majors_by_department(String major, String batch) {
        Transaction transaction = null;
        List<String> majors = new ArrayList<>();
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            
            Query<String> query = session.createQuery(
                "SELECT DISTINCT m.Nganh FROM member m WHERE m.Khoa = :department AND substring(cast(m.MaTV as string), 3, 2) = :batch",
                String.class
            );
            query.setParameter("department", major);
            query.setParameter("batch", batch);
            majors = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return majors;
    }

    //THÊM
    public void add_member(member m) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(m);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //XÓA
    public void delete_member(int member_id) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            member m = session.get(member.class, member_id);
            if (m != null) {
                session.delete(m);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //XÓA NHIỀU
    public void delete_all_member(String department, String major, String batch) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Department: " + department);
            System.out.println("Major: " + major);
            System.out.println("Batch: " + batch);
            
            Query query = session.createQuery("DELETE FROM member WHERE Khoa = :department AND Nganh = :major AND substring(cast(MaTV as string), 3, 2) = :batch");
            query.setParameter("department", department);
            query.setParameter("major", major);
            query.setParameter("batch", batch);

            int rowsAffected = query.executeUpdate();

            transaction.commit();

            System.out.println(rowsAffected + " member(s) deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CẬP NHẬT
    public void update_member(int id, member m) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            member existingMember = session.get(member.class, id);
            if (existingMember != null) {
                existingMember.setMaTV(Integer.max(m.getMaTV(), id));
                existingMember.setHoTen(m.getHoTen());
                existingMember.setKhoa(m.getKhoa());
                existingMember.setNganh(m.getNganh());
                existingMember.setSDT(m.getSDT());
                existingMember.setPassword(m.getPassword());
                existingMember.setEmail(m.getEmail());
                session.update(existingMember);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean is_member_existed(int member_id) {
        try (Session session = FACTORY.openSession()) {
            member existing_member = session.get(member.class, member_id);
            return existing_member != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    public String get_member_name_by_member_id(int member_id) {
        try (Session session = FACTORY.openSession()) {
            member member = session.get(member.class, member_id);

            if (member != null) {
                String memberName = member.getHoTen();
                System.out.println("Retrieved member name: " + memberName); // Add logging
                return memberName;
            } else {
                System.out.println("Member with ID " + member_id + " not found."); // Add logging
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    public member get_member_by_member_id(int member_id) {
        try (Session session = FACTORY.openSession()) {
            String hql = "FROM member WHERE MaTV = :member_id";
            Query<member> query = session.createQuery(hql, member.class);
            query.setParameter("member_id", member_id);
            List<member> result_list = query.getResultList();
            
            if (!result_list.isEmpty()) {
                return result_list.get(0); 
            } else {
                return null; 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
