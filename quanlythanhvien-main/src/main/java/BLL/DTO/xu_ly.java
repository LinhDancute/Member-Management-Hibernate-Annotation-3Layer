/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL.DTO;

import jakarta.persistence.Entity;
import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@Table(name = "xuly")
public class xu_ly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int MaXL;
    public String HinhThucXL;
    public double SoTien;
    public Date NgayXL;
    public int TrangThaiXL;

    @ManyToOne
    @JoinColumn(name = "MaTV")
    private thanh_vien thanhvien;
    
    
    public xu_ly(int MaXL, int MaTV, String HinhThucXL, double SoTien, Date NgayXL, int TrangThaiXL) {
        this.MaXL = MaXL;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }

    public xu_ly() {
    }
}
