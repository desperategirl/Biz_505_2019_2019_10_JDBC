package com.biz.jdbc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.jdbc.domain.ScoreVO;

public class ScoreJDBCServiceV3 {

	// protected String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	// protected String url = "jdbc:oracle:thin:@localhost:1521:xe";
	// protected String userName = "grade";
	// protected String password = "grade";

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
		this.select(sql);
		return this.scoreList;
	}

	public List<ScoreVO> findById(int s_id, int e_id) {

		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_id BETWEEN ? AND ? ";
		


		this.dbConnection();

		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setInt(1, s_id);
			pStr.setInt(2, e_id);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.scoreList;
	
	}

	public List<ScoreVO> findByName(String s_name) {

		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_std = " + s_name;
		this.select(sql);

		return this.scoreList;

	}

	protected void select(String sql) {

		this.dbConnection();
		scoreList = new ArrayList<ScoreVO>();

		try {
			pStr = dbConn.prepareStatement(sql);

			ResultSet rst = pStr.executeQuery();
			while (rst.next()) {
				ScoreVO sVO = ScoreVO.builder()
						.s_id(rst.getInt(DBConstract.SCORE.S_ID))
						.s_std(rst.getString(DBConstract.SCORE.S_STD))
						.s_score(rst.getInt(DBConstract.SCORE.S_SCORE))
						.s_rem(rst.getString(DBConstract.SCORE.S_REM)).build();
				scoreList.add(sVO);
			}
			pStr.close();
			dbConn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	public int insert(ScoreVO scoreVO) {
		
		// INSERT를 수행할 SQL 명령문 STring
		String sql = " INSERT INTO tbl_score (";
		sql += " s_id, ";
		sql += " s_std, ";
		sql += " s_subject, ";
		sql += " s_score, ";
		sql += " s_rem ) ";
		sql += " VALUES(?, ?, 001, ?, ?)";
		
		// INSERT 수행할 VALUES 값이 채워질 부분을
		// ? 대치문자로 처리
		sql += " VALUES(?, ?, 001, ?,?)";
		
		this.dbConnection();
		try {
			pStr = dbConn.prepareStatement(sql);
			// 대치문자 위치에 scoreVO의 각 요소를 getter하여 setting
			pStr.setLong(1, scoreVO.getS_id());
			pStr.setString(2,  scoreVO.getS_std());
			pStr.setInt(3, scoreVO.getS_score());
			pStr.setString(4,  scoreVO.getS_rem());
			
			// update() method를 실행하여 INSERT 수행
			int ret = pStr.executeUpdate();
			return ret;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

}
