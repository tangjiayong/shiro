package com.shiro.testClass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * Created by tangjiayong on 2017/7/26.
 */
public class PasswordTest extends BaseTest{

    private final Log logger = LogFactory.getLog(PasswordTest.class);

    //密码加密不含盐
    @Test
    public void testPasswordServiceWithRealm(){

        login("classpath:shiro/shiro-passwordservice.ini","tang","123");
    }

    //jdbc 密码加密不含盐
    @Test
    public void testPasswordServiceWithJdbcRealm(){

        login("classpath:shiro/shiro-jdbc-passwordservice.ini","wu","123");

    }

    //生成密码散列值
    @Test
    public void testGeneratePassword(){
        String algorithmName = "md5";
        String username = "liu";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;//迭代数

        SimpleHash hash = new SimpleHash(algorithmName, password,salt1+salt2, hashIterations);
        String encodedPassword = hash.toHex();
        logger.info("encodedPassword:"+encodedPassword);
    }

    @Test
    public void testHashedCredentialsMatcherWithMyRealm2() {
        //使用testGeneratePassword生成的散列密码
        login("classpath:shiro/shiro-hashedCredentialsMatcher.ini", "liu", "123");
    }
}
