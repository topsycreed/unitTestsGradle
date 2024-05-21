package database;

import database.jdbc.DatabaseConnection;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static final String insertAnimal = "INSERT INTO public.animal (id, \"name\", age, \"type\", sex, place) VALUES(?, ?, ?, ?, ?, ?);\n";
    private static final String insertWorkman = "INSERT INTO public.workman (id, \"name\", age, \"position\") VALUES(1, null, 23, 1);\n";

    private static final String insertPlaces = "INSERT INTO public.places (id, \"row\", place_num, \"name\") VALUES(?, ?, ?, ?);\n";

    private static final String getCountAnimalRow = "SELECT count(*) from animal";

    private static final String getCountPlacesRow = "SELECT count(*) from places";


    public static List<Animal> getAnimalData(String query) {
        List<Animal> animals = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int type = resultSet.getInt("type");
                int sex = resultSet.getInt("sex");
                int place = resultSet.getInt("place");

                animals.add(new Animal(id, name, age, type, sex, place));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return animals;
    }

    @SneakyThrows
    public static void insertAnimalData(Animal animal) {
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertAnimal);
        preparedStatement.setInt(1, animal.getId());
        preparedStatement.setString(2, animal.getName());
        preparedStatement.setInt(3, animal.getAge());
        preparedStatement.setInt(4, animal.getType());
        preparedStatement.setInt(5, animal.getSex());
        preparedStatement.setInt(6, animal.getPlace());
        preparedStatement.executeUpdate();
    }

    public static boolean insertWorkmanData() {
        try {
            Connection connection = DatabaseConnection.createConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(insertWorkman);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertPlacesData(Places place){
        try {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertPlaces);
            preparedStatement.setInt(1, place.getId());
            preparedStatement.setInt(2, place.getRow());
            preparedStatement.setInt(3, place.getPlace_num());
            preparedStatement.setString(4, place.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static int getCountRowByTable(String tableName) throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from " + tableName);
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.printf("В таблице public.%s ровно %s записей%n", tableName, count);
        return count;
    }

    public static int getZooCountRow() throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from zoo");
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.println(resultSet.getInt("count(*)"));
        return count;
    }

    @SneakyThrows
    public static int getAnimalCountRow() {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getCountAnimalRow);
        resultSet.next();
        return resultSet.getInt("count(*)");
    }

    public static int getRowCountByTable(String tableName) throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from " + tableName);
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.printf("Table public.%s has exact %s rows%n", tableName, count);
        return count;
    }

    public static int getPlacesCountRow() throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from places");
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.println(resultSet.getInt("count(*)"));
        return count;
    }

    public static List<String> getZooNameData() {
        String name;
        List<String> zoo = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,  \"name\" from public.zoo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                name = resultSet.getString("name");
                System.out.println(name);
                zoo.add(name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return zoo;
    }
}
