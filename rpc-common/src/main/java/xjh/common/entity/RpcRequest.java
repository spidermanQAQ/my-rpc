package xjh.common.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
//@Builder可以用来构建对象，Builder 使用创建者模式又叫建造者模式。简单来说，就是一步步创建一个对象，它对用户屏蔽了里面构建的细节，但却可以精细地控制对象的构造过程
//Student.builder()
//               .sno( "001" )
//               .sname( "admin" )
//               .sage( 18 )
//               .sphone( "110" )
//               .build();
@Builder
//服务端需要的信息来确定调用哪个方法
public class RpcRequest implements Serializable {

    //接口名
    private String interfaceName;

    //方法名
    private String methodName;

    //调用的参数
    private Object[] parameters;

    //调用参数的类型
    private Class<?>[] paramTypes;
}
