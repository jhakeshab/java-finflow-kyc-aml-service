package com.finflow.kyc.repository;

import com.finflow.kyc.entity.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KycRepository extends JpaRepository<KycDocument, Long> {
    List<KycDocument> findByUserId(Long userId);
}