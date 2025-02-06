import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {

    // Insert a new user
    public static void insertUser(String name, String email, int age) {
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();

            System.out.println("Utilisateur ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }

    // Get all users from the database
    public static void getUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Nom: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Age: " + rs.getInt("age"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
        }
    }

    // Update the email of a user by user ID
    public static void updateUserEmail(int userId, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, userId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Email mis à jour avec succès !");
            } else {
                System.out.println("Utilisateur non trouvé !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    // Delete a user by user ID
    public static void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé !");
            } else {
                System.out.println("Utilisateur non trouvé !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // Main method to run examples
    public static void main(String[] args) {
        // Example: Insert a user
        insertUser("David", "david@example.com", 28);

        // Example: Get all users
        getUsers();

        // Example: Update user email
        updateUserEmail(2, "new_email@example.com");

        // Example: Delete user
        deleteUser(2);
    }
}
