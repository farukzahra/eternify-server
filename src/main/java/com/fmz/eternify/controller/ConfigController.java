package com.fmz.eternify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fmz.eternify.model.Config;
import com.fmz.eternify.repository.ConfigRepository;

import lombok.Data;

@RestController
@Data
public class ConfigController {

	@Autowired
	ConfigRepository configRepository;

	public Config getValorTransacao() {
		return configRepository.findByChave(Config.VALOR_TRANSACAO).get(0);
	}

}
