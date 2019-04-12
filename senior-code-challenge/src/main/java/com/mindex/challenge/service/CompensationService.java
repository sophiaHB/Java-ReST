package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.dto.CompensationDto;

public interface CompensationService {
	Compensation create(CompensationDto compensationDto);
	Compensation read(String id);
}
