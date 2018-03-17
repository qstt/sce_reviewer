package ustc.sce.authorization;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.domain.Token;
import ustc.sce.utils.StringUtil;
import ustc.sce.utils.TokenUtil;

/**        
 * Title: TokenManager的默认实现    
 * Description: 管理 Token
 * 
 */    
@Transactional
public class DefaultTokenManager implements TokenManager {
	
	/**
	 * 将token存储到JVM内存(ConcurrentHashMap)中
	 */
	private static Map<String,String> tokenMap = new ConcurrentHashMap<String,String>();
	
	private TokenUtil tokenUtil;
	public void setTokenUtil(TokenUtil tokenUtil) {
		this.tokenUtil = tokenUtil;
	}

	/**
	 * 利用UUID创建Token(用户注册时，创建Token)
	 */
	public Token createToken(String userName) {
		String token = UUID.randomUUID().toString();
		
		Token token2 = tokenUtil.tokenSave(token, userName);
//		tokenMap.put(token, userName);
		
		return token2;
	}
	
	/**
	 * 利用UUID改变Token(用户登录时，改变Token)
	 * @param userName
	 * @return
	 */
	public Token changeToken(String userName) {
		String token = UUID.randomUUID().toString();
		
		Token token2 = tokenUtil.tokenChange(token, userName);
//		tokenMap.put(token, userName);
		
		return token2;
	}
	

	/**
	 * Token验证(用户登录验证)
	 */
	public boolean checkToken(String token) {
		return !StringUtil.isEmpty(token) && tokenMap.containsKey(token);
	}

	/**
	 * @description Token删除(用户登出时，删除Token)
	 */
	public void deleteToken(String token) {
		tokenMap.remove(token);
		
	}


}
