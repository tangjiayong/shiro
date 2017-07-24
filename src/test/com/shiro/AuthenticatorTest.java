package com.shiro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by tangjiayong on 2017/7/24.
 */
public class AuthenticatorTest {

    private final Log logger = LogFactory.getLog(AuthenticatorTest.class);

    //AllSuccessfulStrategy
    @Test
    public void testAllSuccessfulStrategyWithSuccess(){
        login("classpath:shiro/shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();
        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        logger.info("size: "+principalCollection.asList().size());
        Assert.assertEquals(2, principalCollection.asList().size());
    }
    //AllSuccessfulStrategy
    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail() {
        login("classpath:shiro/shiro-authenticator-all-fail.ini");
    }

    //FirstSuccessfulStrategy\AtLeastOneSuccessfulStrategy 测试方法跟AllSuccessfulStrategy类似，略过

    @Test
    public void testAtLeastTwoSuccessfulStrategy(){
        login("classpath:shiro/shiro-authenticator-at-least-two.ini");
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        logger.info("size: "+principalCollection.asList().size());
        Assert.assertEquals(2, principalCollection.asList().size());
    }

    private void login(String configFile) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory(configFile);

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        subject.login(token);
    }


    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
}
