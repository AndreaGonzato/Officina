import java.sql.*;

public class Produttore implements Insertable {
  String marca;
  String nazionalità;

  public Produttore(String marca, String nazionalità) {
    this.marca = marca;
    this.nazionalità = nazionalità;
  }

  public void insert(Connection conn) throws SQLException {
    if (SQLInjectionParser.detectSQLInjection(marca, nazionalità)) {
      throw new SQLException("detected a possible SQL Injection");
    }
    try (
            CallableStatement callableStatement = conn.prepareCall("{call sp_aggiungiProduttore(?, ?)}")
    ) {
      callableStatement.setString(1, marca);
      callableStatement.setString(2, nazionalità);
      boolean result = callableStatement.execute();
      if (result) {
        throw new SQLException("a result was provided by the query when it was not supposed to");
      }
    }
  }
}
