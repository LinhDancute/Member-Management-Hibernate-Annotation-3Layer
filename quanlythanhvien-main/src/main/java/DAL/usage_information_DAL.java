/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package DAL;

import BLL.DTO.usage_information;
import DAL.UTILS.hibernate_util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import jakarta.persistence.TemporalType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author ACER
 */
public class usage_information_DAL {

    static final SessionFactory FACTORY = hibernate_util.getSessionFactory();
    
    //LẤY DỮ LIỆU
    public List<usage_information> load_usage_information() {
        Transaction transaction = null;
        List<usage_information> usage_information_list = null;
        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT ui FROM usage_information ui JOIN FETCH ui.thanhvien";
            usage_information_list = session.createQuery(hql).list();
            System.out.println("list income member" + usage_information_list);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return usage_information_list;
    }

    //THÊM
    public void add_income_member_information(usage_information income_member) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(income_member);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //XÓA
    public void delete_income_member_information(int usage_information_id) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            usage_information u_i = session.get(usage_information.class, usage_information_id);
            if (u_i != null) {
                session.delete(u_i);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<usage_information> export_excel() {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<usage_information> income_member = session.createQuery("FROM usage_information", usage_information.class).list();
            transaction.commit();
            return income_member;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<usage_information> fetch_between_dates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Transaction transaction = null;
        List<usage_information> result = null;

        try (Session session = FACTORY.openSession()) {
            transaction = session.beginTransaction();

            Query<usage_information> query = session.createQuery(
                    "FROM usage_information WHERE TGVao BETWEEN :start AND :end",
                    usage_information.class
            );
            query.setParameter("start", startDateTime);
            query.setParameter("end", endDateTime);

            result = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }
}
