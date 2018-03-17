package ustc.sce.authorization;

import ustc.sce.domain.Token;

public interface TokenManager {
	
	Token createToken(String userName);
	
	Token changeToken(String userName);
	
	boolean checkToken(String token);
	
	void deleteToken(String token);
	
}
