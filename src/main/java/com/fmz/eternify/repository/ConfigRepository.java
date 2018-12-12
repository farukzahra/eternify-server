package com.fmz.eternify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmz.eternify.model.Config;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
	
	List<Config> findByChave(String chave);

}
