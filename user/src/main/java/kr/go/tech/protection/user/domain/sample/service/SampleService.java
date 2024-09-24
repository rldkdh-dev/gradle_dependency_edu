package kr.go.tech.protection.user.domain.sample.service;

import org.springframework.stereotype.Service;

import kr.go.tech.protection.user.domain.sample.dao.SampleDAO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleService {
	private final SampleDAO sampleDAO;

	public void test() {
		sampleDAO.test();
	}
}
