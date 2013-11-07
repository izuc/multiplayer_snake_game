package cms;
import java.sql.*;

public class DBStatement {
        private static final String PROCEDURE_START = "{ call ";
        private static final String PROCEDURE_END = " }";
        private static Statement statement;
                
        private static void setStatement() {
                try {
                    DBStatement.statement = DBConnection.getConnection().createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
        }
        
        private static Statement getStatement() {
                DBStatement.setStatement();
                return DBStatement.statement;
        }
        
        private static void addParams(Statement statement, Object[] params) throws SQLException {
                for (int i = 0; i < (params.length); i++) {
                     if (statement instanceof CallableStatement) {
                         ((CallableStatement)statement).setObject((i + 1), params[i]);
                     } else if (statement instanceof PreparedStatement) {
                         ((PreparedStatement)statement).setObject((i + 1), params[i]);
                     }
                }
        }
        
        static void doPreparedStatment(String sql, Object[] params) {
               try {
                    PreparedStatement p = DBConnection.getConnection().prepareStatement(sql);
                    addParams(p, params);
                    p.executeUpdate(); 
                    p.close();
               } catch (SQLException e) {
                    System.err.println(e.getMessage());
               }
        }
                
        static ResultSet callStoredProcedure(String procedure, Object[] params) {
                try {
                    CallableStatement proc = DBConnection.getConnection().prepareCall(PROCEDURE_START + procedure + PROCEDURE_END);
                    addParams(proc, params);
                    return proc.executeQuery();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    return null;
                }
        }
        
        static ResultSet getResultSet(String query) {
                try {
                    return DBStatement.getStatement().executeQuery(query);
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                    return null;
                }
        }
        
        static void doUpdateQuery(String query) {
                try {
                    DBStatement.getStatement().executeUpdate(query);
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
        }
        
        static ResultSet getRSFromPreparedStatment(String sql, Object[] params) {
                try {
                    PreparedStatement p = DBConnection.getConnection().prepareStatement(sql);
                    addParams(p, params);
                    return p.executeQuery();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    return null;
                }
        }
}
