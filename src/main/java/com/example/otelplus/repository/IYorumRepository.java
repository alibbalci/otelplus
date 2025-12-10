package com.example.otelplus.repository;

import com.example.otelplus.model.OtelYorum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IYorumRepository extends JpaRepository<OtelYorum,Integer> {

    List<OtelYorum> findByOtel_OtelId(Integer otelOtelId);
}
