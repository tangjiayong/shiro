package com.shiro.testClass;

import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by tangjiayong on 2017/7/24.
 * 授权
 */
public class RolePermissionTest extends BaseTest{

    /**
     * Shiro提供了hasRole/hasRole用于判断用户是否拥有某个角色/某些权限；但是没有提供如hashAnyRole用于判断是否有某些权限中的某一个。
     */
    @Test
    public void testHaseRole(){
        login("classpath:shiro/shiro-role.ini", "zhang", "123");
        //判断是否有角色: role1
        Assert.assertTrue(subject().hasRole("role1"));
        //判断拥有角色：role1 and role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1","role2")));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1","role2","role3"));
        Assert.assertEquals(true,result[0]);
        Assert.assertEquals(true,result[1]);
        Assert.assertEquals(false,result[2]);

    }

    /**
     * Shiro提供的checkRole/checkRoles和hasRole/hasAllRoles不同的地方是它在判断为假的情况下会抛出UnauthorizedException异常。
     */
    @Test
    public void testCheckRole(){
        login("classpath:shiro/shiro-role.ini", "zhang", "123");
        //断言拥有角色：role1
        subject().checkRole("role1");
        //断言拥有角色：role1 and role3 失败抛出异常
        subject().checkRoles("role1", "role3");//Subject does not have role [role3]
    }

    /**
     * Shiro提供了isPermitted和isPermittedAll用于判断用户是否拥有某个权限或所有权限，也没有提供如isPermittedAny用于判断拥有某一个权限的接口.
     */
    @Test
    public void testIsPermitted(){
        login("classpath:shiro/shiro-role.ini", "zhang", "123");
        //判断拥有权限：user:update
        Assert.assertTrue(subject().isPermitted("user:create"));
        //判断拥有权限：user:update and user:delete
        Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));
        //判断没有权限：user:view
        Assert.assertFalse(subject().isPermitted("user:view"));
    }

    /**
     * 失败的情况下会抛出UnauthorizedException异常。
     */
    @Test(expected = UnauthorizedException.class)//加了这句不会抛异常
    public void testCheckPermission(){
        login("classpath:shiro/shiro-role.ini", "zhang", "123");
        //断言拥有权限：user:create
        subject().checkPermission("user:create");
        //断言拥有权限：user:delete and user:update
        subject().checkPermissions("user:delete", "user:update");
        //断言拥有权限：user:view 失败抛出异常
        subject().checkPermissions("user:view");
    }

    /**
     * 通配符,所有通配符
     * 通过“system:user:*”验证“system:user:create,delete,update:view”可以，但是反过来是不成立的。
     */
    @Test
    public void testTongpeifu(){
        login("classpath:shiro/shiro-role.ini", "tang", "123");
        //断言拥有权限：user:update
        // subject().checkPermission("system:user:update,delete");
        subject().checkPermissions("system:user");

    }
}
