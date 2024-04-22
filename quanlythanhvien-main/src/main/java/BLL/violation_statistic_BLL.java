/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import BLL.DTO.handle_violations_DTO;
import java.util.ArrayList;
import DAL.violation_statistic_DAL;

/**
 *
 * @author Win10pro
 */
public class violation_statistic_BLL {
    
    private final violation_statistic_DAL dal = new violation_statistic_DAL();

    public ArrayList<handle_violations_DTO> getUnresolvedHandleViolations() {
        return dal.getUnresolvedHandleViolations();
    }
    
    public ArrayList<handle_violations_DTO> getResolvedHandleViolations() {
        return dal.getResolvedHandleViolations();
    }
    
     public static double calculateTotalMoneyOfResolvedViolations() {
        return violation_statistic_DAL.calculateTotalMoneyOfResolvedViolations();
    }
     
     public ArrayList<handle_violations_DTO> getViolationStatistics(int status) {
        return dal.getViolationStatistics(status);
    }
    
}
