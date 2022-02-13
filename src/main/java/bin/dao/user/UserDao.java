package bin.dao.user;

import bin.pojo.User;

import java.sql.Connection;

public interface UserDao {
    //得到需要登录的用户
    public User getLoginUser(Connection connection, String userCode) throws  Exception;

}
