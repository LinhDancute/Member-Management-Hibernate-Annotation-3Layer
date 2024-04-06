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
import lombok.ToString;

/**
 *
 * @author ACER
 */
@Setter
@Getter
@Entity
@ToString
@Table(name = "thanhvien")
public class thanh_vien {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int MaTV;
    public String HoTen;
    public String Khoa;
    public String Nganh;
    @Column(unique = true)
    public String SDT;

    @OneToMany(mappedBy = "thanhvien")
    private Set<thong_tin_sd> thongtinsd;
    
    public thanh_vien(int MaTV, String HoTen, String Khoa, String Nganh, String SDT) {
        this.MaTV = MaTV;
        this.HoTen = HoTen;
        this.Khoa = Khoa;
        this.Nganh = Nganh;
        this.SDT = SDT;
    }

    public thanh_vien() {
    }
    
}
