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
        
    }
    
    public static void doPreparedStatement() {
        
    }
    
    public static void doCRUDExample() {
        
    }

}
