/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import BLL.DTO.handle_violations_DTO;
import DAL.UTILS.hibernate_util;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Win10pro
 */
public class violation_statistic_DAL {
    private final SessionFactory factory = hibernate_util.getSessionFactory();

    public ArrayList<handle_violations_DTO> getUnresolvedHandleViolations() {
        ArrayList<handle_violations_DTO> unresolvedViolations = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("FROM handle_violations_DTO WHERE TrangThaiXL = 0");
            unresolvedViolations = (ArrayList<handle_violations_DTO>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unresolvedViolations;
    }
    public ArrayList<handle_violations_DTO> getResolvedHandleViolations() {
        ArrayList<handle_violations_DTO> unresolvedViolations = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("FROM handle_violations_DTO WHERE TrangThaiXL = 1");
            unresolvedViolations = (ArrayList<handle_violations_DTO>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unresolvedViolations;
    }
    
    public static double calculateTotalMoneyOfResolvedViolations() {
        double totalMoney = 0.0;
        try (Session session = hibernate_util.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT SUM(SoTien) FROM handle_violations_DTO WHERE TrangThaiXL = 1");
            Object result = query.uniqueResult();
            if (result != null) {
                totalMoney = (double) result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalMoney;
    }
    
     public ArrayList<handle_violations_DTO> getViolationStatistics(int status) {
        ArrayList<handle_violations_DTO> violations = new ArrayList<>();
        try (Session session = hibernate_util.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM handle_violations_DTO WHERE TrangThaiXL = :status");
            query.setParameter("status", status);
            violations = (ArrayList<handle_violations_DTO>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return violations;
    }
    
}
