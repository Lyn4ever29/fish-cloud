package cn.lyn4ever.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class NoLoginInfoException extends RuntimeException {

    private Integer status = BAD_REQUEST.value();

    public NoLoginInfoException(String msg) {
        super(msg);
    }

    public NoLoginInfoException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }
}