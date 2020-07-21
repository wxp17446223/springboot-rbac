package cn.com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.com.mapper")
@EnableTransactionManagement//开始事务管理
public class SpringbootRbacApplication {
    public static void main(String[] args) {
//改端口 System.setProperty("server.port","80");
        SpringApplication.run(SpringbootRbacApplication.class, args);

    }
}
