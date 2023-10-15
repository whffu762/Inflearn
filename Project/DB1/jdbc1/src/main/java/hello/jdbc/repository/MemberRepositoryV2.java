package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Member save(Member member) throws SQLException{
        String sql = "INSERT INTO member(member_id, money) VALUES (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            int cnt = pstmt.executeUpdate();    //쿼리의 영향 받은 row 수 반환

            return member;
        } catch(SQLException e){
            log.error("db error", e);
            throw e;
        } finally{
            close(con, pstmt, null);    //반드시 닫아줘야 하기 때문에 예외가 터져도 닫히게끔 해야 함
        }
    }

    public Member findById(String memberId) throws SQLException{
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }
            else{
                throw new NoSuchElementException("member not found memberId = " + memberId);

            }
        }catch (SQLException e){
            log.error("DB error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException{
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        }catch (SQLException e){
            log.error("DB error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }

    }

    public Member findById(Connection con ,String memberId) throws SQLException{
        String sql = "select * from member where member_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }
            else{
                throw new NoSuchElementException("member not found memberId = " + memberId);

            }
        }catch (SQLException e){
            log.error("DB error", e);
            throw e;
        } finally {
            //Connection은 트랜잭션이 끝난 이후에 닫아야하기 때문에 여기서 닫으면 안됨
            close(null, pstmt, rs);
        }
    }

    public void update(Connection con, String memberId, int money) throws SQLException{
        String sql = "update member set money=? where member_id=?";

        PreparedStatement pstmt = null;

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        }catch (SQLException e){
            log.error("DB error", e);
            throw e;
        } finally {
            close(null, pstmt, null);
        }

    }

    public void delete(String memberId) throws SQLException{
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        }catch (SQLException e){
            log.info("DB error", e);
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }

    private Connection getConnection() throws SQLException{
        Connection con = dataSource.getConnection();
        log.info("get connection = {}, class = {} ",con, con.getClass());
        return con;
    }
}
