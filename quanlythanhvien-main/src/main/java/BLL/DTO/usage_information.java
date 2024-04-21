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
/**
 *
 * @author ACER
 */
@Setter
@Getter
@Entity
@Table(name = "thongtinsd")
public class usage_information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTT")
    public int MaTT;
    
    @Column(name = "TGVao")
    public Date TGVao;
    
    @Column(name = "TGMuon")
    public Date TGMuon;
    
    @Column(name = "TGTra")
    public Date TGTra;
    
    @Column(name = "TGDatcho", nullable = true)
    public Date TGDatcho;

    @ManyToOne
    @JoinColumn(name = "MaTB")
    private device thietbi;
    
    @ManyToOne
    @JoinColumn(name = "MaTV")
    private member thanhvien;
    
    public usage_information(int MaTT, member thanhvien, device thietbi, Date TGVao, Date TGMuon, Date TGTra,Date TGDatcho) {
        this.MaTT = MaTT;
        this.thanhvien = thanhvien;
        this.thietbi = thietbi;
        this.TGVao = TGVao;
        this.TGMuon = TGMuon;
        this.TGTra = TGTra;
        this.TGDatcho = TGDatcho;
    }

    public usage_information() {
    }
}
