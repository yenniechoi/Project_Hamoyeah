package favorites;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conn.DBConnect;

public class FavoritesDao {
	private DBConnect dbconn;

	public FavoritesDao() {
		dbconn = DBConnect.getInstance();
	}
	
	// insert -- 별표 누르면 추가됨
	public void insert(FavoritesVo vo) {
		Connection conn = dbconn.conn();
		String sql = "insert into H_favorites values(?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMemberId());
			pstmt.setInt(2, vo.getBoardNum());

			int num = pstmt.executeUpdate();
			System.out.println(num + "줄이 추가되었습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// delete
	public void delete(String memberId, int boardNum){
		Connection conn = dbconn.conn();
		String sql = "delete from H_favorites where member_id = ? and board_num = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, boardNum);
			
			int number = pstmt.executeUpdate();
			System.out.println(number + "줄이 삭제되었습니다.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// id로 select
	public ArrayList<FavoritesVo> selectBoard(String memberId) {
		ArrayList<FavoritesVo> list = new ArrayList<>();
		Connection conn = dbconn.conn();
		String sql = "select board_num from H_favorites where memberId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			ResultSet rs = pstmt.executeQuery(); // select 실행
			while (rs.next()) { // 첫 줄로 이동하여 데이터 있는지 확인
				list.add(new FavoritesVo(rs.getString(1), rs.getInt(2)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
