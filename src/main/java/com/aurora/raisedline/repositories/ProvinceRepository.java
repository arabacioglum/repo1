package com.aurora.raisedline.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurora.raisedline.entities.Province;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
	public List<Province> findAll();
	public Province findOne(Long id);
	public Province findByProvinceCode(String provinceCode);
}
