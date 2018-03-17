package ustc.sce.utils;

/**
 * Title:字符串工具类
 * @author 秋色天堂
 *
 */
public class StringUtil {
	
	public static boolean isNotEmpty(String str) {
		if (str != null && str.length() != 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str != null && str.length() != 0) {
			return false;
		}
		return true;
	}

}
