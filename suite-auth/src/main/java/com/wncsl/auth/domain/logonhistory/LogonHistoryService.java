package com.wncsl.auth.domain.logonhistory;


import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LogonHistoryService {

    private LogonHistoryRepository logonHistoryRepository;

    private UserService userService;

    public LogonHistoryService(LogonHistoryRepository logonHistoryRepository, UserService userService) {

        this.logonHistoryRepository = logonHistoryRepository;
        this.userService = userService;
    }

    public LogonHistory create(LogonHistory logonHistory){

        return logonHistoryRepository.save(logonHistory);
    }

    public List<LogonHistory> listAll(){

        return logonHistoryRepository.findAll();
    }

    public void auditLogon(String username, UUID userUuid, String status){

        LogonHistory lg = LogonHistory.builder()
                .username(username)
                .userUuid(userUuid)
                .status(status)
                .dateLogon(LocalDateTime.now())
                .created(LocalDateTime.now())
                .build();

        bindCause(lg);

        bindRequestInfo(lg);

        create(lg);
    }

    private void bindRequestInfo(LogonHistory logonHistory){
        String agent = null;
        String address = null;
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            token = request.getHeader("Authorization");
            agent = request.getHeader("User-Agent");
            address = request.getRemoteAddr();
        } catch (Exception e) {
            System.err.println("Error get values request:.." + e.getMessage());
        }
        logonHistory.setDevice(identifyDevice(agent));
        logonHistory.setBrowser(identifyBrowser(agent));
        logonHistory.setAddress(address);
        if ("INVALID_TOKEN".equals(logonHistory.getCause())){
            logonHistory.setInvalidToken(token);
        }
    }

    private void bindCause(LogonHistory logonHistory) {
        if ("FAILURE".equals(logonHistory.getStatus())){
            if ("access-token".equals(logonHistory.getUsername())){
                logonHistory.setCause("INVALID_TOKEN");
            }else {
                User user = userService.getByUsernameOrNull(logonHistory.getUsername());
                if (user == null){
                    logonHistory.setCause("INVALID_USER");
                }else {
                    logonHistory.setCause("INVALID_PASSWORD");
                    logonHistory.setUserUuid(user.getUuid());
                }
            }
        }
    }

    private String identifyDevice(String value) {
        if (value == null)
            return "UNKNOWN";

        if (value.toLowerCase().contains("mobile"))
            return "MOBILE";

        if (value.toLowerCase().contains("mozilla"))
            return "BROWSER";

        return "API";

    }

    private String identifyBrowser(String value) {
        // PostmanRuntime/7.26.3
        // Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36 = BRAVE
        // Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36
        // Edg/86.0.622.68 - EDGE
        // Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36
        // OPR/72.0.3815.320 - OPERA
        // Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36 - CHROME
        // Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0 - FIREFOX
        // Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko - IE
        // Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2 - SAFARI

        if (value == null)
            return "UNKNOWN";

        if (value.toLowerCase().contains("postman"))
            return "POSTMAN";

        if (value.toLowerCase().contains("java"))
            return "JAVA_API";

        if (value.toLowerCase().contains("firefox"))
            return "FIREFOX";

        if (value.toLowerCase().contains("safari"))
            return "SAFARI";

        if (value.toLowerCase().contains("trident"))
            return "IE";

        if (value.toLowerCase().contains("chrome")) {
            return extractValueChrome(value);
        }

        if (value.toLowerCase().contains("curl")){
            return "CURL";
        }

        return "API";
    }

    private static String extractValueChrome(String value) {
        String version = "";
        int idx = value.toLowerCase().indexOf("chrome");

        if (idx > 0) {
            String vrl = value.substring(idx, value.length());
            String[] arr = vrl.split("/");
            if (arr.length > 1)
                version = "/" + arr[1].split(" ")[0];
        }

        if (value.toLowerCase().contains("edg") || value.toLowerCase().contains("edge"))
            return "EDGE" + version;

        if (value.toLowerCase().contains("opr") || value.toLowerCase().contains("opera"))
            return "OPERA" + version;

        return "CHROME" + version;
    }

}
