import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Revisione implements Insertable {

  Date data;
  String targa;

  public Revisione(Date data, String targa) {
    this.data = data;
    this.targa = targa;
  }

  public void insert(Connection conn) throws SQLException {
    if (SQLInjectionParser.detectSQLInjection(targa)) {
      throw new SQLException("detected a possible SQL Injection");
    }
    try (
            CallableStatement callableStatement = conn.prepareCall("{call sp_aggiungiRevisione(?, ?)}")
    ) {
      callableStatement.setDate(1, data);
      callableStatement.setString(2, targa);
      boolean result = callableStatement.execute();
      if (result) {
        throw new SQLException("a result was provided by the query when it was not supposed to");
      }
    }
  }

}
