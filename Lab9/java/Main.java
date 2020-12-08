import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main
{

    public static void main(String []args) throws SQLException
    {
        String JDBC = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://10.1.10.4:3307/users";
        String USER = "sgrajper";
        String PASSWORD = "slawek";
        Connection connect = null;
        Scanner scanner = new Scanner(System.in);
        
        
        int option = 0;
        try
        {	
			Class.forName(JDBC);
            while(connect == null)
            {
                    try {
                            System.out.println("Try to connect...");
                            connect = DriverManager.getConnection(URL, USER, PASSWORD);
                        } catch (SQLException e) {
                            System.out.println("Error");
                            System.out.println(e);
                            }
                        try {
                        Thread.sleep(10000);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
            }
				
                System.out.println("Connected!");
                String name = null;
                String surname = null;
                int id = 0;
				
				
                while (option != 5)
                {
                    showMenu();
		    System.out.println("test");
                    option = scanner.nextInt();
                    switch(option)
                    {
                        case 1:
                            {
                                getUser(connect);
                                break;
                            }

                        case 2:
                            {
                                scanner.nextLine();
                                System.out.println("Podaj imie: ");
                                name = scanner.nextLine();
                                System.out.println("Podaj nazwisko: ");
                                surname = scanner.nextLine();
                                insertUser(connect, name, surname);
                                break;
                            }

                        case 3:
                            {
                                System.out.println("Podaj id:");
                                id = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Podaj imie: ");
                                name = scanner.nextLine();
                                System.out.println("Podaj nazwisko: ");
                                surname = scanner.nextLine();                                
                                updateUser(connect, id, name, surname);                                 
                                break;
                            }
                            
                        case 4:
                            {
                                System.out.println("Podaj id:");
                                id = scanner.nextInt();
                                deleteUser(connect, id);
                                break;
                            }
                        
                        case 5:
                            {
                                connect.close();
                                System.exit(0);
                                break;
                            }

                        default:
                            {
                                break;
                            }
                    }
                }
        }
            catch (Exception e) {
            System.out.println(e);
            }
            connect.close();
            
    }
        public static void deleteUser(Connection connect, int tmp_id) throws SQLException 
		{
			String sql = "DELETE FROM users WHERE ID = ?";
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setInt(1, tmp_id);
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Usunieto osobe z id: " + tmp_id);
			}
			statement.close();
		}
    
        public static void getUser(Connection connect) throws SQLException
        {
            String sql = "SELECT * FROM users";
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next())
            {
                int user_id = result.getInt("id");
                String user_name = result.getString("name");
                String user_surname = result.getString("surname");
                System.out.println("id: " + user_id + " Imie: " + user_name + " Nazwisko: " + user_surname);
            }
            result.close();
        }
        
        public static void updateUser(Connection connect, int tmp_id, String tmp_name, String tmp_username) throws SQLException 
        {
        String sql = "UPDATE users SET name = ?, surname = ? WHERE ID = ?";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setString(1, tmp_name);
        statement.setString(2, tmp_username);
        statement.setInt(3, tmp_id);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Zmieniono osobe z id: " + tmp_id);
        }
        statement.close();
    }
  
        public static void insertUser(Connection connect, String tmp_name, String tmp_surname) throws SQLException
        {
        String sql = "INSERT INTO users(name, surname) VALUES (?, ?)";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setString(1, tmp_name);
        statement.setString(2, tmp_surname);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Dodano do bazy");
        }
        statement.close();
    }    
    
        public static void showMenu() 
        {
            System.out.println();
            System.out.println("1. Wyswietlanie osoby");
            System.out.println("2. Dodaj osobe");
            System.out.println("3. Edycja osoby");
            System.out.println("4. Usun osobe");
            System.out.println("5. Wyjscie");
            System.out.println();
    }
}
