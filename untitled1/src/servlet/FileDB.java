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
    /*                               previous指的是当前所在的文件夹
    （注：由于安卓端，已经限制用户名以及文件夹名都是唯一值，因此不需要一个绝对路径和相对路径，
    直接按照当前文件夹的名字+用户名，就可以在数据库中得到该文件夹下一级有哪些文件（或文件夹），
    而此处的previous就是指明了当前目录，在安卓端对于一个用户来说，是唯一值，可以准确得到文件夹下的东西）
     */
    String owner=req.getParameter("owner");
    File smartFile=smartUpload.getFiles().getFile(0);
String fileName=smartFile.getFileName();
String formatName=fileName.substring(fileName.length()-4,fileName.length());

    if(formatName.equals(".jpg")||formatName.equals(".png")||formatName.equals(".gif")||formatName.equals(".jpg")||formatName.equals(".bmp")){
        //检查格式

        smartFile.saveAs("/pictures/"+smartFile.getFileName(),smartUpload.SAVE_VIRTUAL);
        String urlForPictures="http://192.168.43.146:8080/untitled1_war/pictures/"+smartFile.getFileName();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/depstore?serverTimezone=UTC";
        con = DriverManager.getConnection(url, "root", "ti163799");
        st = con.createStatement();
        sql = "insert into thing values(\'i\',\'"+smartFile.getFileName()+"\',\'"+urlForPictures+"\',\'"+previous+"\',\'"+owner+"\',\'"+time+"\');";                                   ;
        st.executeUpdate(sql);
        out.println("{\"status\":\"success\"}");
    }else{
        out.println("{\"status\":\"Format_mismatch\"}");//给安卓端返回JSON：{"status":"Format_mismatch"}，表示格式不支持

    }





}catch (Exception e){
    out.print(e.getMessage());
}

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}