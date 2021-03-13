package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
@WebServlet(name = "FileDB")
public class FileDB extends HttpServlet {

    static int count=0;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out=response.getWriter();
SmartUpload smartUpload=new SmartUpload();
ServletConfig config=this.getServletConfig();
smartUpload.initialize(config,request,response);
        Statement st = null;
        Connection con = null;
        ResultSet res = null;
        String sql = "";
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=formatter.format(date);
try {
    smartUpload.upload();
    Request req=smartUpload.getRequest();
    String previous=req.getParameter("previous");
    String owner=req.getParameter("owner");
//    out.println(msg0);
    File smartFile=smartUpload.getFiles().getFile(0);
    smartFile.saveAs("/pictures/"+smartFile.getFileName(),smartUpload.SAVE_VIRTUAL);
//    count++;
//    out.println(smartFile.getFileName());
String urlForPictures="http://192.168.43.146:8080/untitled1_war/pictures/"+smartFile.getFileName();


    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://127.0.0.1:3306/depstore?serverTimezone=UTC";
    con = DriverManager.getConnection(url, "root", "ti163799");
    st = con.createStatement();
    sql = "insert into thing values(\'i\',\'"+smartFile.getFileName()+"\',\'"+urlForPictures+"\',\'"+previous+"\',\'"+owner+"\',\'"+time+"\');";                                   ;
    st.executeUpdate(sql);

}catch (Exception e){

    out.println(e.getMessage());
}

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}