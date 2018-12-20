package cn.wangjie.security.social;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.social.connect.ConnectionData;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-20 11:52
 **/
@Data
@NoArgsConstructor
public class MyConnectionData {
    private String providerId;

    private String providerUserId;

    private String displayName;

    private String profileUrl;

    private String imageUrl;

    private String accessToken;

    private String secret;

    private String refreshToken;

    private Long expireTime;


    public ConnectionData turnToConnectionData() {
        return new ConnectionData(providerId, providerUserId, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime);

    }

}
