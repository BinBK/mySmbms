package bin.service.user;

import bin.pojo.User;

public interface UserService {
    //用户登录，业务层
    public User login(String userCode, String userPassword);

}
