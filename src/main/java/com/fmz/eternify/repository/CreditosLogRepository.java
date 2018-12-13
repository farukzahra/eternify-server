package com.fmz.eternify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmz.eternify.model.CreditosLog;
import com.fmz.eternify.model.Usuario;

@Repository
public interface CreditosLogRepository extends JpaRepository<CreditosLog, Long> {

	List<CreditosLog> findByUsuario(Usuario usuario);

}
