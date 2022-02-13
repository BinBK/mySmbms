package bin.servlet.user;

import bin.pojo.User;
import bin.service.user.UserService;
import bin.service.user.UserServiceImpl;
import bin.tools.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    //控制层调用业务层
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("longService------------");

        //获取用户名密码，对应jsp页面里的name
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库中的密码进行对比，调用业务层
        UserService userService = new UserServiceImpl();
        User user =userService.login(userCode,userPassword);
        if(user!=null){
            //将用户信息放到Session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转
            resp.sendRedirect("jsp/frame.jsp");

        }else{
            //没有查到，返回提示
            req.setAttribute("error","用户名或密码错误");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
