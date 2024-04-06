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
/**
 *
 * @author ACER
 */
@Setter
@Getter
@Entity
@ToString
@Table(name = "thongtinsd")
public class thong_tin_sd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int MaTT;
    public Date TGMuon, TGTra;

    @ManyToOne
    @JoinColumn(name = "MaTB")
    private thiet_bi thietbi;
    
    @ManyToOne
    @JoinColumn(name = "MaTV")
    private thanh_vien thanhvien;
    
    public thong_tin_sd(int MaTT, int MaTV, int MaTB, Date TGMuon, Date TGTra) {
        this.MaTT = MaTT;
        this.TGMuon = TGMuon;
        this.TGTra = TGTra;
    }

    public thong_tin_sd() {
    }
}
