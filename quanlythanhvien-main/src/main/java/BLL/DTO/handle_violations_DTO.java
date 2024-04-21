/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package BLL.DTO;

import jakarta.persistence.Column;
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

@Setter
@Getter
@Entity
@Table(name = "xuly")
public class handle_violations_DTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXL")
    public int MaXL;
    
    @Column(name = "HinhThucXL")
    public String HinhThucXL;
    
    @Column(name = "SoTien", nullable = true)
    public double SoTien;
    
    @Column(name = "NgayXL")
    public Date NgayXL;
    
    @Column(name = "TrangThaiXL")
    public int TrangThaiXL;

    @ManyToOne
    @JoinColumn(name = "MaTV")
    private member thanhvien;
    
    
    public handle_violations_DTO(int MaXL, int MaTV, String HinhThucXL, double SoTien, Date NgayXL, int TrangThaiXL) {
        this.MaXL = MaXL;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }
    
    public handle_violations_DTO(member thanhvien, String HinhThucXL, double SoTien, Date NgayXL, int TrangThaiXL) {
        this.thanhvien = thanhvien;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }
    
    public handle_violations_DTO(int MaXL, member thanhvien, String HinhThucXL, double SoTien, Date NgayXL, int TrangThaiXL) {
        this.MaXL = MaXL;
        this.thanhvien = thanhvien;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }


    public handle_violations_DTO() {
    }
}
