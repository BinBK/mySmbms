package bin.service.user;

import bin.dao.BaseDao;
import bin.dao.user.UserDao;
import bin.dao.user.UserDaoImpl;
import bin.pojo.User;

import java.sql.Connection;

public class UserServiceImpl implements UserService{
    //业务层都会的调用dao层，所以要引入dao层
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }
    public User login(String userCode, String userPassword){
        Connection connection = null;
        User user =null;


        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection,userCode);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

}
