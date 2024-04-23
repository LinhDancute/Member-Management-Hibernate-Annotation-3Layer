/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package DAL;

import BLL.DTO.member;
import DAL.UTILS.hibernate_util;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author ACER
 */
public class member_DAL {
    static final SessionFactory FACTORY = hibernate_util.getSessionFactory();
    
    public List<member> load_member() {
        Transaction transaction = null;
        List<member> mem_list = null;
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            mem_list = session.createQuery("FROM member").list();
            transaction.commit();
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return mem_list;
    }

    public void add_member(member m) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(m);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public member getMemberById(int member_id) throws Exception {
        Transaction transaction = null;
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            member memberObj = session.get(member.class, member_id);
            transaction.commit();
            return memberObj;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
}
