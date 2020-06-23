package com.example.myfirstapplication.repository;

import java.util.List;

import com.example.myfirstapplication.entity.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
	 List<Staff> findByCompanyId(Long company_id);
}