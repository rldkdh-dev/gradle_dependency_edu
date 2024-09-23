package kr.go.tech.protection.user.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acns.docu.converter.api.DocuGenClient;
import com.acns.docu.converter.api.DocuGenFile;
import com.acns.docu.converter.exception.DocuGenException;

@RestController
@RequestMapping("/api/v1")
public class TempController {
	@GetMapping("/temp")
	public String connectTest() {

		return "success";
	}

	public static void main(String[] args) throws Exception {
		DocuGenClient client = new DocuGenClient( "conf.properties" );
		DocuGenFile file;

		try
		{
			client.connect();
			file = client.sendFile( "C:\\saycore\\01.docs\\01.Ultari\\05.아키텍처 설계서\\(SMEPIS-2211)아키텍처설계서_v1.1_PMO.hwp" );

			file = client.createPDF( file );
			client.saveOutputFileTo( file, "c:/temp/결과.pdf" );
		}
		catch( DocuGenException e )
		{
			throw e;
		}
		catch( Exception e )
		{
			throw e;
		}
		finally
		{
			client.disconnect();
		}
	}

}
