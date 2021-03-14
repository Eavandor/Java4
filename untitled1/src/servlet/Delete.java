package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.File;
@WebServlet(name = "Delete")
public class Delete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String n = request.getParameter("owner");
        String pictureName = request.getParameter("picName");
        Statement st = null;
        Connection con = null;
        String sql = "";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/depstore?serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "ti163799");
            st = con.createStatement();
            sql= "delete from thing where owner=\'"+n+"\' and name=\'"+pictureName+"\';";
            st.executeUpdate(sql);
            String filePath="D:\\apache-tomcat-9.0.41\\webapps\\untitled1_war\\pictures\\"+pictureName;
            File f1=new File(filePath);
            if(f1.exists()&&f1.isFile()) {
                if(f1.delete()) {       //删除服务器文件夹下对应的文件
                    out.println("{\"status\":\"success\"}");
                }else {
                    out.println("{\"status\":\"fail\"}");
                }

            }else {
                out.println("{\"status\":\"fail\"}");
            }

            st.close();
            con.close();
        }catch (Exception e){

            out.println("{\"status\":\""+e.getMessage()+"\"}");
        }
        out.close();
    }
}
