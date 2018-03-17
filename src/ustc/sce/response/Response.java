package ustc.sce.response;

public class Response {
	
	private static final String OK = "ok";
	private static final String ERROR = "error";

	private Meta meta;     // 元数据
	private Object data;   // 响应内容

	public Response success() {
		this.meta = new Meta(true, OK);
		this.data = new Data();
		return this;
	}

	public Response success(Object data) {
		this.meta = new Meta(true, OK);
		this.data = data;
		return this;
	}
	
	public Response success(String message) {
		this.meta = new Meta(true, message);
		this.data = new Data();
		return this;
	}

	public Response failure() {
		this.meta = new Meta(false, ERROR);
		this.data = new Data();
		return this;
	}

	public Response failure(String message) {
		this.meta = new Meta(false, message);
		this.data = new Data();
		return this;
	}

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}
	
	public class Data{
		private String returnValue;
		private String value;
		public Data() {
			this.returnValue = returnValue;
			this.value = null;
		}
	}

	public class Meta {

		private boolean success;
		private String message;

		public Meta(boolean success) {
			this.success = success;
		}

		public Meta(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}

}
