//package bin.dao;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.*;
//import java.util.Properties;
//
//public class BaseDao {
//    //基础，要会
//    private static String driver;
//    private static String url;
//    private static String username;
//    private static String password;
//    //静态代码快，类加载的时候就初始化了
//    static{
//        Properties properties = new Properties();
//        //通过类加载起读取对应的资源
//        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("database.properties");
//
//        try{
//            properties.load(is);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        driver = properties.getProperty("driver");
//        url = properties.getProperty("url");
//        username = properties.getProperty("username");
//        password = properties.getProperty("password");
//
//    }
//    //获取数据库的链接
//    public static Connection getConnection(){
//        Connection connection = null;
//        try{
//            Class.forName(driver);
//            connection  = DriverManager.getConnection(url,username,password);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    //******************操作
//    //编写查询公共类
//    public static ResultSet execute(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet,String sql,Object[] params) throws Exception {
////        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        //要是在括号里写了就不用像上面一样加，都写上去方便一起关闭
//        preparedStatement = connection.prepareStatement(sql);
//        for (int i=0;i<params.length;i++){
//            //setObject,占位符从1开始，但数组从0开始，所以+1
//            preparedStatement.setObject(i+1,params[i]);
//        }
//        resultSet = preparedStatement.executeQuery();
//        return  resultSet;
//    }
////增删改查公共方法
//    public static int execute(Connection connection,PreparedStatement preparedStatement,String sql,Object[] params) throws Exception {
//        int updateRows =0;
//        preparedStatement = connection.prepareStatement(sql);
//        for (int i=0;i<params.length;i++){
//            //setObject,占位符从1开始，但数组从0开始，所以+1
//            preparedStatement.setObject(i+1,params[i]);
//        }
//        //就这里和 上面的int 和括号里的resultSet变了
//        updateRows = preparedStatement.executeUpdate();
//        return  updateRows;
//    }
//    //释放资源
//    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
//        boolean flag=true;
//        if(resultSet!=null){
//            try {
//                resultSet.close();
//                //GC回收
//                resultSet=null;
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//                flag=false;
//            }
//        }
//
//        if(preparedStatement!=null){
//            try {
//                preparedStatement.close();
//                //GC回收
//                preparedStatement=null;
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//                flag=false;
//            }
//        }
//        if(connection!=null){
//            try {
//                connection.close();
//                //GC回收
//                connection=null;
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//                flag=false;
//            }
//        }
//        return flag;
//    }
//
//}
//****************上面不知道是那一部分写错了

package bin.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的基类--静态类
 * @author Administrator
 *
 */
public class BaseDao {
    //静态代码块,在类加载的时候执行
    static{
        init();
    }

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    //初始化连接参数,从配置文件里获得
    public static void init(){
        Properties params=new Properties();
        String configFile = "database.properties";
        InputStream is= BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        try {
            params.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver=params.getProperty("driver");
        url=params.getProperty("url");
        user=params.getProperty("user");
        password=params.getProperty("password");

    }


    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return connection;
    }
    /**
     * 查询操作
     * @param connection
     * @param pstm
     * @param rs
     * @param sql
     * @param params
     * @return
     */
    public static ResultSet execute(Connection connection,PreparedStatement pstm,ResultSet rs,
                                    String sql,Object[] params) throws Exception{
        pstm = connection.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            pstm.setObject(i+1, params[i]);
        }
        rs = pstm.executeQuery();
        return rs;
    }
    /**
     * 更新操作
     * @param connection
     * @param pstm
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public static int execute(Connection connection,PreparedStatement pstm,
                              String sql,Object[] params) throws Exception{
        int updateRows = 0;
        pstm = connection.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            pstm.setObject(i+1, params[i]);
        }
        updateRows = pstm.executeUpdate();
        return updateRows;
    }

    /**
     * 释放资源
     * @param connection
     * @param pstm
     * @param rs
     * @return
     */
    public static boolean closeResource(Connection connection,PreparedStatement pstm,ResultSet rs){
        boolean flag = true;
        if(rs != null){
            try {
                rs.close();
                rs = null;//GC回收
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        if(pstm != null){
            try {
                pstm.close();
                pstm = null;//GC回收
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        if(connection != null){
            try {
                connection.close();
                connection = null;//GC回收
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }

}




