package com.biz.jdbc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.jdbc.domain.ScoreVO;

public class ScoreJDBCServiceV2 {

	protected Connection dbConn = null;
	protected PreparedStatement pStr = null;

	protected List<ScoreVO> scoreList = null;

	public List<ScoreVO> getScoreList() {
		return this.scoreList;
	}

	protected void dbConnection() {

		try {
			Class.forName(DBConstract.DB_INFO.JdbcDriver);
			dbConn = DriverManager.getConnection(DBConstract.DB_INFO.URL, DBConstract.DB_INFO.USER,
					DBConstract.DB_INFO.PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<ScoreVO> SelectAll() {

		String sql = " SELECT * FROM tbl_score ";
		this.dbConnection();

		try {
			pStr = dbConn.prepareStatement(sql);
			this.setScoreList(pStr);
			dbConn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		return this.scoreList;
	}

	public List<ScoreVO> findById(int s_id) {

		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_id = ? " ;


		this.dbConnection();

		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setInt(1, s_id);
			
			this.setScoreList(pStr);
			dbConn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.scoreList;
	

	}

	public List<ScoreVO> findByName(String s_name) {

		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_std = ? " ;
		this.dbConnection();

		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, s_name);
			this.setScoreList(pStr);
			dbConn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.dbConnection();

		try {
			pStr = dbConn.prepareStatement(sql);
			this.setScoreList(pStr);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.scoreList;
	

	}

	// ResultSet에서 데이터를 추출하여 List로 변환 method
	protected void setScoreList(PreparedStatement pStr) throws SQLException {

		this.scoreList = new ArrayList<ScoreVO>();
        ResultSet rst = pStr.executeQuery();
		
        while (rst.next()) {
			ScoreVO sVO = ScoreVO.builder().s_id(rst.getInt(DBConstract.SCORE.S_ID))
					.s_std(rst.getString(DBConstract.SCORE.S_STD)).s_score(rst.getInt(DBConstract.SCORE.S_SCORE))
					.s_rem(rst.getString(DBConstract.SCORE.S_REM)).build();
			scoreList.add(sVO);

		}
		rst.close();

	}

}
