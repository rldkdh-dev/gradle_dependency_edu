package kr.go.tech.protection.user.domain.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.go.tech.protection.user.domain.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sample")
public class SampleController {

	private final SampleService sampleService;

	@GetMapping("")
	public void sample () {
		sampleService.test();
	}
}
