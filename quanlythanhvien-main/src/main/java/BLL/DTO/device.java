/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ACER
 */
@Setter
@Getter
@Entity
@Table(name = "thietbi")
public class device {

    @Id
    @Column(name = "MaTB")
    public String MaTB;
    
    @Column(name = "TenTB")
    public String TenTB;
    
    @Column(name = "MotaTB")
    public String MotaTB;

    @OneToMany(mappedBy = "thietbi")
    private Set<usage_information> thongtinsd;
    
    public device(String MaTB, String TenTB, String MotaTB) {
        this.MaTB = MaTB;
        this.TenTB = TenTB;
        this.MotaTB = MotaTB;
    }

    public device() {
    }

    public String getMaTB() {
        return MaTB;
    }

    public void setMaTB(String maTB) {
        MaTB = maTB;
    }

    public String getTenTB() {
        return TenTB;
    }

    public void setTenTB(String tenTB) {
        TenTB = tenTB;
    }

    public String getMotaTB() {
        return MotaTB;
    }

    public void setMotaTB(String motaTB) {
        MotaTB = motaTB;
    }

    public Set<usage_information> getThongtinsd() {
        return thongtinsd;
    }

    public void setThongtinsd(Set<usage_information> thongtinsd) {
        this.thongtinsd = thongtinsd;
    }
}
