package com.fmz.eternify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmz.eternify.model.Fatura;
import com.fmz.eternify.model.Pessoa;
import com.fmz.eternify.model.Usuario;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

	List<Fatura> findByUsuario(Usuario usuario);

}
