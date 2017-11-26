package util;

import java.sql.*;
import java.util.Properties;

public class DbManager {

    //获取数据库连接
    public static Connection getConnection(){

        Connection dbConn = null;
        Properties dbProperty = PropertiesUtil.getProperty("database.properties");

        try{
            Class.forName(dbProperty.getProperty("driverName"));//注册驱动
            dbConn= DriverManager.getConnection(dbProperty.getProperty("dbURL"),
                    dbProperty.getProperty("userName"), dbProperty.getProperty("userPwd"));//获得连接对象
        }catch(ClassNotFoundException e){//捕获驱动类无法找到异常
            e.printStackTrace();
        }catch(SQLException e){//捕获SQL异常
            e.printStackTrace();
        }

        return dbConn;
    }

    //查询数据
    public static ResultSet select(String sql) throws Exception{
        try{
            Connection dbConn=getConnection();
            PreparedStatement preparedStatement = dbConn.prepareStatement(sql);
            return preparedStatement.executeQuery(sql);
        }catch(SQLException e){
            throw new SQLException("select data Exception: "+ e.getMessage());
        }catch(Exception e){
            throw new Exception("System error: "+e.getMessage());
        }
    }

    //插入数据
    public static int insert(String sql) throws Exception{

        Connection dbConn = null;
        PreparedStatement preparedStatement = null;
        int num = 0;//计数
        try{
            dbConn=getConnection();
            preparedStatement = dbConn.prepareStatement(sql);
            num = preparedStatement.executeUpdate();

        }catch(SQLException e){
            throw new SQLException("insert data Exception: "+ e.getMessage());
        }finally{
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch(Exception e){
                throw new Exception("ps close exception: "+e.getMessage());
            }
            try{
                if(dbConn != null){
                    dbConn.close();
                }
            }catch(Exception e){
                throw new Exception("conn close exception: "+ e.getMessage());
            }
        }
        return num;
    }

    //删除数据
    public static int delete(String sql) throws Exception{
        Connection dbConn = null;
        PreparedStatement preparedStatement = null;
        int num = 0;//计数
        try{
            dbConn=getConnection();
            preparedStatement=dbConn.prepareStatement(sql);
            num = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("delete data Exception: "+ e.getMessage());
        }finally{
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch(Exception e){
                throw new Exception("ps close Exception: "+e.getMessage());
            }
            try{
                if(dbConn != null){
                    dbConn.close();
                }
            }catch(Exception e){
                throw new Exception("conn close Exception: "+e.getMessage());
            }
        }

        return num;
    }

    //修改数据
    public static int update(String sql)throws Exception{
        Connection dbConn = null;
        PreparedStatement preparedStatement = null;
        int num = 0;//计数
        try{
            dbConn = getConnection();
            preparedStatement = dbConn.prepareStatement(sql);
            num = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("update data Exception: "+ e.getMessage());
        }finally{
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch(Exception e){
                throw new Exception("ps close Exception: "+e.getMessage());
            }
            try{
                if(dbConn != null){
                    dbConn.close();
                }
            }catch(Exception e){
                throw new Exception("conn close Excepiton: "+e.getMessage());
            }
        }

        return num;
    }

}
