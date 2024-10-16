package kr.go.tech.protection.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class AdminApplicationTests {


	/*@Test
	@DisplayName("일반 회원 관리 목록 조회 API.")
	void selectGenMemberList() throws Exception {
		System.out.println("test");
	}*/

	@Test
	@DisplayName("테스트 API")
	void test() throws Exception {
		System.out.println("test");
	}

}
