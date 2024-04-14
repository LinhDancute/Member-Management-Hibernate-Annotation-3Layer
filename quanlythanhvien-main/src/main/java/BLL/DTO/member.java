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
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author ACER
 */
@Setter
@Getter
@Entity
@ToString
@Table(name = "thanhvien")
@Accessors(chain = true)
public class member {
    
    @Id
    @Column(name = "MaTV")
    public int MaTV;
    
    @Column(name = "HoTen")
    public String HoTen;
    
    @Column(name = "Khoa")
    public String Khoa;
    
    @Column(name = "Nganh")
    public String Nganh;
    
    @Column(unique = true, nullable = true)
    public String SDT;
    public String Password;
    
    @Column(unique = true, nullable = true)
    public String Email;

    @OneToMany(mappedBy = "thanhvien")
    private Set<usage_information> thongtinsd;
    
    public member(int MaTV, String HoTen, String Khoa, String Nganh, String SDT, String Password, String Email) {
        this.MaTV = MaTV;
        this.HoTen = HoTen;
        this.Khoa = Khoa;
        this.Nganh = Nganh;
        this.SDT = SDT;
        this.Password = Password;
        this.Email = Email;
    }

    public member() {
    }
    
}
