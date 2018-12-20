/**
 * 
 */
package cn.wangjie.security.properties;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class QQProperties  {
	
	private String providerId = "qq";
	/**
	 * Application id.
	 */
	private String appId;

	/**
	 * Application secret.
	 */
	private String appSecret;
	
}
