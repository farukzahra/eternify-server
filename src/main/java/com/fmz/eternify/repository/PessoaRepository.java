package com.fmz.eternify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fmz.eternify.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query(value = "SELECT * FROM PESSOA WHERE ID > 1 and ID < 3", nativeQuery = true)
	List<Pessoa> findAllActiveUsersNative();

}
