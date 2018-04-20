package cn.zhou.exception;

public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;

    protected String msg; // 异常信息
    protected int code; // 具体异常码

    public CommonException(String erorMsg) {
        super(erorMsg);
    }

    public CommonException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public CommonException() {
        super();
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
