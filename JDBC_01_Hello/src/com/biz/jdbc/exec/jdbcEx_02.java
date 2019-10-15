package com.biz.jdbc.exec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.jdbc.domain.ScoreVO;

public class jdbcEx_02 {

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
      String url = "jdbc:oracle:thin:@localhost:1521:xe";
      String userName = "grade";
      String password = "grade";
      Connection dbConn = null;
      PreparedStatement pStr = null;
      List<ScoreVO> scoreList = null;
      
      try {
         Class.forName(jdbcDriver);
         dbConn = DriverManager.getConnection(url, userName, password);
         
         String sql = "SELECT * FROM tbl_score";
         pStr = dbConn.prepareStatement(sql);
         ResultSet rst = pStr.executeQuery();
         scoreList = new ArrayList<ScoreVO>();
         while(rst.next()) {
            ScoreVO vo = new ScoreVO();
            vo.setS_id(rst.getInt(1));
            vo.setS_std(rst.getString(2));
            vo.setS_score(rst.getInt(3));
            scoreList.add(vo);
         }
         rst.close();
         dbConn.close();
         
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      System.out.println("=========================");
      System.out.println("성적일람표");
      System.out.println("-------------------------");
      System.out.printf("ID\t학생\t과목\t비고");
      System.out.println("-------------------------");
      for(ScoreVO sVO : scoreList) {
    	  System.out.println(sVO.getS_id() + "\t");
    	  System.out.println(sVO.getS_std() + "\t");
    	  System.out.println(sVO.getS_score() + "\t");
    	  System.out.println(sVO.getS_rem() + "\t");
      }
      System.out.println();
   }

}