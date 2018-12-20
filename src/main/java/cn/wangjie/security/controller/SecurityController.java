package cn.wangjie.security.controller;

import cn.wangjie.security.entity.ResponseVO;
import cn.wangjie.security.enums.CommonResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-11 17:10
 **/
@Slf4j
@RestController
public class SecurityController {
    private final String URL_HTML=".html";
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy =new DefaultRedirectStrategy();
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseVO requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if (savedRequest!=null){
            String target = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是{}",target);
            if (StringUtils.endsWithIgnoreCase(target,URL_HTML)){
                redirectStrategy.sendRedirect(request,response,"");
            }
        }
        return new ResponseVO(CommonResponseEnum.UNAUTHORIZED);
    }

}
