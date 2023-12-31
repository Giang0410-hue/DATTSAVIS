package com.example.demo.service.impl;

import com.example.demo.entity.SanPham;
import com.example.demo.repository.SanPhamRepository;
import com.example.demo.service.SanPhamSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamSerivceImpl implements SanPhamSerivce {

    @Autowired
    private SanPhamRepository repository;


    @Override
    public List<SanPham> getAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "ngaySua");
        return repository.findAll(sort);

    }

    @Override
    public List<SanPham> getAllDangHoatDong() {

        return repository.fillAllDangHoatDong();

    }

    @Override
    public List<SanPham> getAllNgungHoatDong() {

        return repository.fillAllNgungHoatDong();

    }

    @Override
    public SanPham add(SanPham sanPham) {
        return repository.save(sanPham);

    }

    @Override
    public SanPham update(SanPham sanPham) {

        return repository.save(sanPham);

    }

    @Override
    public void remove(Long id) {

        repository.deleteById(id);

    }

    @Override
    public SanPham getById(Long id) {

        return repository.findById(id).get();

    }

    @Override
    public Integer genMaTuDong() {

        String maStr = "";
        try {
            if (repository.index() != null) {
                maStr = repository.index().toString();
            } else {
                maStr = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (maStr == null) {
            maStr = "0";
            int ma = Integer.parseInt(maStr);
            return ++ma;
        }
        int ma = Integer.parseInt(maStr);
        return ++ma;

    }

    @Override
    public boolean checkTenTrung(String ten) {

        for (SanPham sp : repository.findAll()) {
            if (sp.getTen().equalsIgnoreCase(ten)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean checkTenTrungSua(String ma, String ten) {

        for (SanPham sp : repository.findAll()) {
            if (sp.getTen().equalsIgnoreCase(ten)) {
                if (!sp.getMa().equalsIgnoreCase(ma)) {
                    return false;
                }
            }
        }
        return true;
    }

}
