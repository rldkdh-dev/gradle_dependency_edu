package kr.go.tech.protection.admin.sample;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(RestDocumentationExtension.class)
public class SampleController {

	protected MockMvc mockMvc;

	protected ObjectMapper objectMapper = new ObjectMapper();
}
