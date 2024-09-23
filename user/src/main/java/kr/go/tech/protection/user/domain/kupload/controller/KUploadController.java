package kr.go.tech.protection.user.domain.kupload.controller;

import com.raonwiz.kupload.RAONKHandler;
import com.raonwiz.kupload.event.EventClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class KUploadController {
    @RequestMapping(value = "/kupload", method = {RequestMethod.GET, RequestMethod.POST})
    public void handler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        RAONKHandler upload = new RAONKHandler();

        upload.settingVo.setDebugMode(true, "C");
        EventClass event = new EventClass();

        upload.settingVo.setVirtualPath("/raonkuploaddata");

        //임시파일 물리적 경로설정 ( setVirtualPath에 설정된 경로 + raonktemp )
        upload.settingVo.setTempPath(request.getSession().getServletContext().getRealPath("/raonkuploaddata") + java.io.File.separator + "raonktemp");

        //***************보안 설정 : 업로드 가능한 경로 설정 - 이외의 경로로 업로드 불가능***************
        String[] arrAllowUploadDirectoryPath = {request.getSession().getServletContext().getRealPath("/raonkuploaddata")};
        upload.settingVo.setAllowUploadDirectoryPath(arrAllowUploadDirectoryPath);

        //***************보안 설정 : 다운로드 가능한 경로 설정 - 이외의 경로에서 다운로드 불가능***************
        //context.Request.MapPath("/") 값은 샘플 동작을 위한 설정으로 실제 적용 시 제외하시면 됩니다.
        String[] arrAllowDownloadDirectoryPath = {request.getSession().getServletContext().getRealPath("/raonkuploaddata"), request.getSession().getServletContext().getRealPath("/")};
        upload.settingVo.setAllowDownloadDirectoryPath(arrAllowDownloadDirectoryPath);
        //-------------------- [설정방법2] 가상경로 설정 끝 --------------------//

        //위에 설정된 임시파일 물리적 경로에 불필요한 파일을 삭제 처리하는 설정 (단위: 일)
        upload.settingVo.setGarbageCleanDay(2);


        String[] arrUploadCheckFileExtension = {"exe", "bat", "sh", "jsp", "php"};
        upload.settingVo.setUploadCheckFileExtension(0, arrUploadCheckFileExtension);

        ServletContext application = request.getSession().getServletContext();

        String result = "";
        try {
            result = upload.Process(request, response, application, event);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!result.equals("")) {
            response.setContentType("text/html");
            ServletOutputStream out = response.getOutputStream();
            out.print(result);
            out.close();
        }
    }
}
