package yumaoqiu.tick;

import lombok.Data;

/**
 * TODO(文件概要)
 *
 * <p> TODO(具体描述信息,可以不填)
 *
 * @author winter
 * @version V1.0
 * @className: CreaOrderResponse
 * @date 2023/8/31 2:45 PM
 */
@Data
public class Response<T> {

    private Integer code;
    private Boolean success;
    private String message;

    private T result;







}
