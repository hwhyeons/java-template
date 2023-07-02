
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DBTestOnly {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mariadb://주소:3306/DB명",
                    "유저이름",
                    "비밀번호");
            pstmt = con.prepareStatement("select * from test_table");

            rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println(rs.getString(1));
                //.
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close(); 
                }

                if(con != null) {
                    con.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
