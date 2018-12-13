package com.fmz.eternify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fmz.eternify.model.CreditosLog;
import com.fmz.eternify.repository.CreditosLogRepository;

import lombok.Data;

@RestController
@Data
public class CreditosLogController {

	@Autowired
	CreditosLogRepository creditosLogRepository;

	public void addLog(CreditosLog log) {

		creditosLogRepository.save(log);

	}

}
