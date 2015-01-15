/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.buildit3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class CPD4414BuildIt3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Statement Example");
        System.out.println("=================");
        doStatement();

        System.out.println("PreparedStatement Example");
        System.out.println("=========================");
        doPreparedStatement();

        System.out.println("End-to-End CRUD Example");
        System.out.println("=======================");
        doCRUDExample();
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception! " + e.getMessage());
        }

        String url = "jdbc:mysql://IPRO:3306/Winter2015";
        try {
            connection = DriverManager.getConnection(url,
                    "Winter2015", "P@ssw0rd");            
        } catch (SQLException e) {
            System.out.println("Failed to Connect! " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    public static void doStatement() {
        try {            
            Connection conn = getConnection();
            String query = "SELECT * FROM sample";        
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.printf("%s\t%s\n", rs.getString("name"), rs.getString("age"));
            }        
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error SELECTing: " + e.getMessage());
        }         
    }
    
    public static void doPreparedStatement() {
        try {            
            Connection conn = getConnection();
            String query = "SELECT * FROM sample WHERE id = ?";        
            PreparedStatement pstmt = conn.prepareStatement(query);
            int[] idList = {1, 3};
            for (int id : idList) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.printf("%s\t%s\n", rs.getString("name"), rs.getString("age"));
                }        
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error SELECTing: " + e.getMessage());
        }         
    }
    
    public static void doCRUDExample() {
        try {            
            Connection conn = getConnection();
            // Our Dataset
            List<Person> people = new ArrayList<>();
            people.add(new Person("John", 32));
            people.add(new Person("Frank", 29));
            people.add(new Person("Susie", 37));            
            
            // Create!
            String query = "INSERT INTO sample (name, age) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            System.out.println(query);
            for (Person person : people) {
                pstmt.setString(1, person.getName());
                pstmt.setInt(2, person.getAge());
                int updates = pstmt.executeUpdate();
                System.out.printf("Updated %d rows with %s/%d.\n", updates, 
                        person.getName(), person.getAge());                
            }
            
            // Read!
            query = "SELECT * FROM sample";        
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            while (rs.next()) {
                System.out.printf("%s\t%s\n", rs.getString("name"), rs.getString("age"));
            }        
            
            // Update!
            query = "UPDATE sample SET age = ? WHERE name = ?";
            pstmt = conn.prepareStatement(query);
            System.out.println(query);
            for (Person person : people) {
                pstmt.setInt(1, person.getAge() + 1);
                pstmt.setString(2, person.getName());                
                int updates = pstmt.executeUpdate();
                System.out.printf("Updated %d rows with %s/%d.\n", updates, 
                        person.getName(), person.getAge());                
            }
            
            // Read Again!
            query = "SELECT * FROM sample";        
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println(query);
            while (rs.next()) {
                System.out.printf("%s\t%s\n", rs.getString("name"), rs.getString("age"));
            }
            
            // DELETE!
            query = "DELETE FROM sample WHERE name = ?";
            pstmt = conn.prepareStatement(query);
            System.out.println(query);
            for (Person person : people) {
                pstmt.setString(1, person.getName());
                int updates = pstmt.executeUpdate();
                System.out.printf("Updated %d rows with %s.\n", updates, 
                        person.getName());                
            }
            
            // Read Again!
            query = "SELECT * FROM sample";        
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println(query);
            while (rs.next()) {
                System.out.printf("%s\t%s\n", rs.getString("name"), rs.getString("age"));
            }
            
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error with SQL: " + e.getMessage());
        }  
    }
    
    private static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
        
    }

}
