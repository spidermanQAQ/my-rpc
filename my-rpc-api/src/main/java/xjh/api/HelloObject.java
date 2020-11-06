package xjh.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
//包含所有参数的构造函数
@AllArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;
}
