import java.sql.*;

public class Main{
    private Connection conn;
    private Statement stmt;

    // Tworzy połączenie z bazą danych i tworzy tabelę z listą obecności, jeśli nie istnieje
    public void createTable() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:attendance.db");
        stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS attendance " +
                "(instructor text, subject text, date text, login_time text, logout_time text, session_length text)";
        stmt.executeUpdate(sql);
    }

    // Dodaje studenta do listy obecności
    public void addStudent(String instructor, String subject, String date, String loginTime, String logoutTime, String sessionLength) throws SQLException {
        String sql = "INSERT INTO attendance (instructor, subject, date, login_time, logout_time, session_length) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, instructor);
        pstmt.setString(2, subject);
        pstmt.setString(3, date);
        pstmt.setString(4, loginTime);
        pstmt.setString(5, logoutTime);
        pstmt.setString(6, sessionLength);
        pstmt.executeUpdate();
    }

    // Wyświetla obecności wszystkich studentów na zajęciach
    public void displayAttendance() throws SQLException {
        String sql = "SELECT * FROM attendance";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String instructor = rs.getString("instructor");
            String subject = rs.getString("subject");
            String date = rs.getString("date");
            String loginTime = rs.getString("login_time");
            String logoutTime = rs.getString("logout_time");
            String sessionLength = rs.getString("session_length");
            System.out.println(instructor + " | " + subject + " | " + date + " | " + loginTime + " | " + logoutTime + " | " + sessionLength);
        }
    }
}
