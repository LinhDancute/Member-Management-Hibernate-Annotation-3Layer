/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ACER
 */
@Setter
@Getter
@Entity
@ToString
@Table(name = "thietbi")
public class thiet_bi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int MaTB;
    public String TenTB;
    public String MotaTB;

    @OneToMany(mappedBy = "thietbi")
    private Set<thong_tin_sd> thongtinsd;
    
    public thiet_bi(int MaTB, String TenTB, String MotaTB) {
        this.MaTB = MaTB;
        this.TenTB = TenTB;
        this.MotaTB = MotaTB;
    }

    public thiet_bi() {
    }
}
