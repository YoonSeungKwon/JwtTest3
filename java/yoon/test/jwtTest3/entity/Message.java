package yoon.test.jwtTest3.entity;

import lombok.Data;
import lombok.Setter;
import yoon.test.jwtTest3.enums.StatusEnum;

@Data
public class Message {

    private StatusEnum status;
    private String message;
    private Object data;

    public Message(){
        this.status = StatusEnum.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }
}
