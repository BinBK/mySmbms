import bin.pojo.User;
import bin.service.user.UserServiceImpl;
import org.junit.Test;

public class tt {
    @Test
    public void test(){
        UserServiceImpl us = new UserServiceImpl();
        User am = us.login("admin","12dd3456");
        System.out.println(am.getUserPassword());

    }

}
