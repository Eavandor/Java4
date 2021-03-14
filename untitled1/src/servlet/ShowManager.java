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
import java.util.Date;
import java.text.SimpleDateFormat;

@WebServlet(name = "ShowManager")
public class ShowManager extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");
        String stuff_name = request.getParameter("stuff_name");
        String owner = request.getParameter("owner");
        String previous = request.getParameter("previous");
            /*                         previous指的是当前所在的文件夹
    （注：由于安卓端，已经限制用户名以及文件夹名都是唯一值，因此不需要一个绝对路径和相对路径，
    直接按照当前文件夹的名字+用户名，就可以在数据库中得到该文件夹下一级有哪些文件（或文件夹），
    而此处的previous就是指明了当前目录，在安卓端对于一个用户来说，是唯一值，可以准确得到文件夹下的东西）
     */
        Statement st = null;
        Connection con = null;
        ResultSet res = null;
        String sql = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/depstore?serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "ti163799");
            st = con.createStatement();


            if (type.equals("f")) {   //新建文件夹时，安卓传来的type值是"f"
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = formatter.format(date);
                sql = "insert into thing values(\'f\',\'" + stuff_name + "\',\'_\',\'" + previous + "\',\'" + owner + "\',\'" + time + "\');";
                st.executeUpdate(sql);
                out.println("{\"status\":\"success\"}");

            } else {      //查询时，安卓传来的type值是"_"（短下划线）,如果上传图片，由FileDB处理
                sql = "select * from thing where owner=\'" + owner + "\' and previous=\'" + previous + "\';";
                res = st.executeQuery(sql);
                    String output = "[";
                    while (res.next()) {
                        output += "{\"stuff_name\":\"";
                        output += (res.getString(2) + "\",\"type\":\"");
                        output += (res.getString(1) + "\",\"url\":\"");   //将url返回给安卓，安卓直接通过url实现下载功能
                        output += (res.getString(3) + "\"},");
                    }
                    output = output.substring(0, output.length() - 1);
                    output += "]";
                    out.println(output);
                }

            res.close();
            st.close();
            con.close();
        } catch (Exception e) {
out.print(e.getMessage());
        }
        out.close();
    }
}