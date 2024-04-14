/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTB")
    public int MaTB;
    
    @Column(name = "TenTB")
    public String TenTB;
    
    @Column(name = "MotaTB")
    public String MotaTB;

    @OneToMany(mappedBy = "thietbi")
    private Set<usage_information> thongtinsd;
    
    public device(int MaTB, String TenTB, String MotaTB) {
        this.MaTB = MaTB;
        this.TenTB = TenTB;
        this.MotaTB = MotaTB;
    }

    public device() {
    }
}
