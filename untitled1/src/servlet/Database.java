package servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Database")
public class Database extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String aim = request.getParameter("aim");
        String name = request.getParameter("name");
        String pwd = request.getParameter("password");
        Statement st = null;
        Connection con = null;
        ResultSet res = null;
        String sql = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/depstore?serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "ti163799");
            st = con.createStatement();
            sql = "select * from student;";
            res = st.executeQuery(sql);
            String status = "not_match";
            while (res.next()) {
//                out.println("name:"+res.getString(1));
//                out.println("password:"+res.getString(2));
                if (res.getString(1).equals(name)) {

                    status = "name_match";
                    if (res.getString(2).equals(pwd)) {
                        status = "both_match";
                    }
                    break;
                }
            }
            if (aim.equals("login")) {
                if (status.equals("both_match")) {
                    out.println("{\"status\":\"login_success\"}");    //返回JSON:{"status":"login_success"}
                } else if (status.equals("name_match")) {
                    out.println("{\"status\":\"password_mismatch\"}");  //返回JSON:{"status":"password_mismatch"}
                } else {
                    out.println("{\"status\":\"name_not_found\"}");                    //返回JSON:{"status":"name_not_found"}
                }
            } else if (aim.equals("createAccount")) {
                if (status.equals("not_match")) {
                    sql = "insert into student values('" + name + "','" + pwd + "');";
                    st.executeUpdate(sql);
                    out.println("{\"status\":\"create_success\"}");                                          //返回JSON:{"status":"create_success"}
                } else {
                    out.println("{\"status\":\"name_duplicated\"}");                                          //返回JSON:{"status":"name_duplicated"}
                }
            } else {
                out.println("{\"status\":\"unknown_error\"}");                                          //返回JSON:{"status":"name_duplicated"}
            }
            out.close();
            res.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String aim = request.getParameter("aim");
        String name = request.getParameter("name");
        String pwd = request.getParameter("password");
        Statement st = null;
        Connection con = null;
        ResultSet res = null;
        String sql = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/depstore?serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "ti163799");
            st = con.createStatement();
            sql = "select * from student;";
            res = st.executeQuery(sql);
            String status = "not_match";
            while (res.next()) {
//                out.println("name:"+res.getString(1));
//                out.println("password:"+res.getString(2));
                if (res.getString(1).equals(name)) {

                    status = "name_match";
                    if (res.getString(2).equals(pwd)) {
                        status = "both_match";
                    }
                    break;
                }

            }
            if (aim.equals("login")) {
                if (status.equals("both_match")) {
                    out.println("{\"status\":\"login_success\"}");    //返回JSON:{"status":"login_success"}
                } else if (status.equals("name_match")) {
                    out.println("{\"status\":\"password_mismatch\"}");  //返回JSON:{"status":"password_mismatch"}
                } else {
                    out.println("{\"status\":\"name_not_found\"}");                    //返回JSON:{"status":"name_not_found"}
                }
            } else if (aim.equals("createAccount")) {
                if (status.equals("not_match")) {
                    sql = "insert into student values('" + name + "','" + pwd + "');";
                    st.executeUpdate(sql);
                    out.println("{\"status\":\"create_success\"}");                                          //返回JSON:{"status":"create_success"}
                } else {
                    out.println("{\"status\":\"name_duplicated\"}");                                          //返回JSON:{"status":"name_duplicated"}
                }
            } else {
                out.println("{\"status\":\"unknown_error\"}");                                          //返回JSON:{"status":"name_duplicated"}
            }
            out.close();
            res.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}
