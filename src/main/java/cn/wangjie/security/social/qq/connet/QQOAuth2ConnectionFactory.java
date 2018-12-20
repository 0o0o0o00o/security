/**
 * 
 */
package cn.wangjie.security.social.qq.connet;

import cn.wangjie.security.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author zhailiang
 *
 */
public class QQOAuth2ConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQOAuth2ConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
