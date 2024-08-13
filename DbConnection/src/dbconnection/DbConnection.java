import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DbConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=JavaPractice;encrypt=false;trustServerCertificate=true";
        String user = "sa"; 
        String password = "123"; 

        try {
            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa.");

            // Insertamos 5 registros 
            String[] nombres = {"Producto1", "Producto2", "Producto3", "Producto4", "Producto5"};
            String[] descripciones = {"Descripción1", "Descripción2", "Descripción3", "Descripción4", "Descripción5"};
            BigDecimal[] precios = {new BigDecimal("10.99"), new BigDecimal("20.99"), new BigDecimal("30.99"), new BigDecimal("40.99"), new BigDecimal("50.99")};

            String insertQuery = "INSERT INTO Producto (ProductoNombre, ProductoDesc, Precio) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (int i = 0; i < 5; i++) {
                preparedStatement.setString(1, nombres[i]);
                preparedStatement.setString(2, descripciones[i]);
                preparedStatement.setBigDecimal(3, precios[i]);
                preparedStatement.executeUpdate();
            }
            System.out.println("5 registros insertados exitosamente.");

            // Consultamos un producto específico por ID
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el ID del producto a consultar: ");
            int productoId = scanner.nextInt();

            String selectQuery = "SELECT * FROM Producto WHERE ProductoId = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, productoId);
            ResultSet resultSet = selectStatement.executeQuery();

            //Mostrammos en pantalla 
            if (resultSet.next()) {
                String productoNombre = resultSet.getString("ProductoNombre");
                String productoDesc = resultSet.getString("ProductoDesc");
                BigDecimal precio = resultSet.getBigDecimal("Precio");

                System.out.println("ID: " + productoId);
                System.out.println("Nombre: " + productoNombre);
                System.out.println("Descripción: " + productoDesc);
                System.out.println("Precio: " + precio);
            } else {
                System.out.println("No se encontró el producto con ID: " + productoId);
            }

            // Cerrar conexiones
            resultSet.close();
            selectStatement.close();
            preparedStatement.close();
            connection.close();
            scanner.close();

        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos.");
            e.printStackTrace();
        }
    }
}
