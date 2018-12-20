package cn.wangjie.security.controller;

import cn.wangjie.security.entity.ResponseVO;
import cn.wangjie.security.entity.dto.UserDTO;
import cn.wangjie.security.entity.po.UserPO;
import cn.wangjie.security.enums.CommonResponseEnum;
import cn.wangjie.security.service.UserService;
import cn.wangjie.security.social.MyConnectionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-11 14:44
 **/
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private RedisTemplate<String, Object>  redisTemplate;
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
    @Autowired
    private UserService userService;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private String SIGN_UP_TOKEN = "sign-up-token-";
    @GetMapping("/get")
    UserPO getUser(){
        UserPO userPO = new UserPO() ;
        userPO.setId(1);
        userPO.setName("['a','b','c']");
        return userPO;
    }

    @RequestMapping("/sign-up")
    public ResponseVO<Map> signUp(HttpServletRequest request, HttpServletResponse response){
        ProviderSignInAttempt providerSignInAttempt = (ProviderSignInAttempt)sessionStrategy.getAttribute(new ServletWebRequest(request, response), ProviderSignInAttempt.SESSION_ATTRIBUTE);
        log.info(providerSignInAttempt.toString());
        String uuid = UUID.randomUUID().toString();
        ConnectionData connectionData =(ConnectionData)providerSignInAttempt.getConnection(connectionFactoryLocator).createData();
        MyConnectionData myConnectionData = new MyConnectionData();
        BeanUtils.copyProperties(connectionData,myConnectionData);
        redisTemplate.opsForValue().set(SIGN_UP_TOKEN+uuid,myConnectionData,5, TimeUnit.MINUTES);
        Map map = new HashMap(3){
            {
                put("signUpToken",uuid);
            }
        };
        return new ResponseVO<>(CommonResponseEnum.NEED_SIGN_UP,map);
    }

    @PostMapping("/social/register")
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseVO socialRegister(@RequestBody UserDTO userDTO,@RequestHeader("signUpToken") String signUpToken,HttpServletRequest request, HttpServletResponse response){
        UserPO userPO = userService.register(userDTO);
        MyConnectionData myConnectionData = (MyConnectionData)redisTemplate.opsForValue().get(SIGN_UP_TOKEN+signUpToken);
        if (myConnectionData == null){
            return new ResponseVO(CommonResponseEnum.FAIL,"sign_up_token失效");
        }
        Connection connection = connectionFactoryLocator.getConnectionFactory(myConnectionData.turnToConnectionData().getProviderId()).createConnection(myConnectionData.turnToConnectionData());
        ServletWebRequest servletWebRequest = new  ServletWebRequest(request, response);
        sessionStrategy.setAttribute(servletWebRequest, ProviderSignInAttempt.SESSION_ATTRIBUTE, new ProviderSignInAttempt(connection));
        providerSignInUtils.doPostSignUp(userPO.getId().toString(),servletWebRequest);
        return new ResponseVO(CommonResponseEnum.SUCCESS);
    }

    @PostMapping("/social/binding")
    public ResponseVO socialBinding(){
        return new ResponseVO(CommonResponseEnum.SUCCESS);
    }
}
