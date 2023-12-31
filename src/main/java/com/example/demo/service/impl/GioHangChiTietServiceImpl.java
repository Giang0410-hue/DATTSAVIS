package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.GioHangChiTietRepository;
import com.example.demo.repository.HoaDonChiTietRepository;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository repository;

    @Autowired
    private HoaDonRepository repositoryHoaDon;

    @Autowired
    private HoaDonChiTietRepository repositoryHoaDonChiTiet;

    @Override
    public List<GioHangChiTiet> findAll() {

        return repository.findAll();

    }

    @Override
    public List<GioHangChiTiet> findAllByIdGioHang(Long idGioHang) {

        return repository.getfindAllByIdGioHang(idGioHang);

    }

    @Override
    public GioHangChiTiet fillById(Long id) {

        return repository.findById(id).get();

    }

    @Override
    public Integer soLuongSPGioHangCT(Long idGioHang) {

        return repository.soLuongSpTrongGioHangCT(idGioHang);

    }

    @Override
    public List<GioHangChiTiet> findAllById(List<String> listIdString,Long idGioHang) {
        List<Long> listIdLong = new ArrayList<>();
        for (String str : listIdString) {
            try {
                Long value = Long.parseLong(str);
                listIdLong.add(value);
            } catch (NumberFormatException e) {
                e.fillInStackTrace();
                // Xử lý ngoại lệ nếu có giá trị không hợp lệ
            }
        }

        return repository.findAllByIdGHCT(listIdLong,idGioHang);

    }

    @Override
    public HoaDonChiTiet addHoaDon(List<String> listStringIdGioHangCT, Long tongTien, Long tongTienSale,
                                   String hoVaTen, String soDienThoai, String tienShip, String email,
                                   String voucher, String diaChiCuThe, String ghiChu,
                                   String phuongXaID, String quanHuyenID, String thanhPhoID,Long idGioHang) {
        Date currentAdd = new Date();

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon("HĐ" + hoaDon.getId());
        hoaDon.setLoaiHoaDon(1);
        hoaDon.setPhiShip(Long.valueOf(tienShip));
        hoaDon.setTongTien(tongTien);
        hoaDon.setTongTienKhiGiam(tongTienSale);
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setNguoiNhan(hoVaTen);
        hoaDon.setSdtNguoiNhan(soDienThoai);
        hoaDon.setDiaChiNguoiNhan(diaChiCuThe);
        hoaDon.setEmailNguoiNhan(email);
        hoaDon.setNgayTao(currentAdd);
        hoaDon.setNgaySua(currentAdd);
        hoaDon.setTrangThai(0);
        hoaDon.setPhuongXa(phuongXaID);
        hoaDon.setQuanHuyen(quanHuyenID);
        hoaDon.setThanhPho(thanhPhoID);
        hoaDon.setVoucher(Voucher.builder().id(Long.valueOf(voucher)).build());

        repositoryHoaDon.save(hoaDon);

        hoaDon.setMaHoaDon("HĐ" + hoaDon.getId());

        repositoryHoaDon.save(hoaDon);


        List<GioHangChiTiet> listGioHangChiTiet = this.findAllById(listStringIdGioHangCT,idGioHang);
        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

        for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
            hoaDonChiTiet.setSoLuong(gioHangChiTiet.getSoLuong());
            hoaDonChiTiet.setDonGia(gioHangChiTiet.getChiTietSanPham().getGiaHienHanh());//1111
            hoaDonChiTiet.setHoaDon(HoaDon.builder().id(hoaDon.getId()).build());
            hoaDonChiTiet.setChiTietSanPham(gioHangChiTiet.getChiTietSanPham());
            repositoryHoaDonChiTiet.save(hoaDonChiTiet);
            repository.delete(gioHangChiTiet);
        }


        return null;

    }

    @Override
    public void deleteById(Long id) {

        repository.deleteById(id);

    }

    @Override
    public GioHangChiTiet save(Long idGioHang, Long idChiTietSp, Integer soLuong) {

        GioHangChiTiet gioHangChiTiet = repository.getByGioHangChiTiet(idGioHang, idChiTietSp);
        GioHangChiTiet gioHangChiTietNew = new GioHangChiTiet();

        if (gioHangChiTiet == null) {
            Date currentAdd = new Date();

            gioHangChiTietNew.setSoLuong(soLuong);
            gioHangChiTietNew.setNgayTao(currentAdd);
            gioHangChiTietNew.setNgaySua(currentAdd);
            gioHangChiTietNew.setChiTietSanPham(ChiTietSanPham.builder().id(Long.valueOf(idChiTietSp)).build());
            gioHangChiTietNew.setGioHang(GioHang.builder().id(idGioHang).build());
            gioHangChiTietNew.setTrangThai(0);
            System.out.println("a1");

        } else {
            Date currentUpdate = new Date();

            gioHangChiTietNew.setId(gioHangChiTiet.getId());
            gioHangChiTietNew.setSoLuong(gioHangChiTiet.getSoLuong() + soLuong);
            gioHangChiTietNew.setGhiChu(gioHangChiTiet.getGhiChu());
            gioHangChiTietNew.setNgayTao(gioHangChiTiet.getNgayTao());
            gioHangChiTietNew.setNgaySua(currentUpdate);
            gioHangChiTietNew.setChiTietSanPham(gioHangChiTiet.getChiTietSanPham());
            gioHangChiTietNew.setGioHang(gioHangChiTiet.getGioHang());
            gioHangChiTietNew.setTrangThai(0);
            System.out.println("a2");

        }

        return repository.save(gioHangChiTietNew);
    }

    @Override
    public GioHangChiTiet update(GioHangChiTiet gioHangChiTiet) {

        return repository.save(gioHangChiTiet);

    }

    @Override
    public List<GioHangChiTiet> findByTrangThai(Integer trangThai) {

        return null;

    }
}
