package com.hank.security.springbootsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = SecuritySpringBootApp.class)
@RunWith(value = SpringRunner.class)
public class TestPassword {

    @Test
    public void test(){
        String hashpw = BCrypt.hashpw("secret", BCrypt.gensalt());
        String hashpw1 = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(hashpw);
        System.out.println(hashpw1);
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$QTdaxX7IdPNTcj5pz3ABueoLOkUvA8cJfrgSnLFNcXBnARj3HBqqu");
        System.out.println(checkpw);
    }
}
