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

@WebServlet(name = "Delete")
public class Delete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String n = request.getParameter("owner");
        Statement st = null;
        Connection con = null;
        String sql = "";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/depstore?serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "ti163799");
            st = con.createStatement();
            sql= "delete from student where name=\'"+n+"\';";
            st.executeUpdate(sql);
            sql= "delete from thing where owner=\'"+n+"\';";
            st.executeUpdate(sql);
            out.println("{\"status\":\"success\"}");
            st.close();
            con.close();
        }catch (Exception e){
            out.println("{\"status\":\""+e.getMessage()+"\"}");
        }
        out.close();
    }
}
