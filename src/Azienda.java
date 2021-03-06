import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Azienda extends Cliente implements Insertable {
  String partitaIva;

  public Azienda(String nome, String telefono, String email, String partitaIva) {
    super(nome, telefono, email);
    this.partitaIva = partitaIva;
  }

  public void insert(Connection conn) throws SQLException {
    if (SQLInjectionParser.detectSQLInjection(nome, telefono, email, partitaIva)) {
      throw new SQLException("detected a possible SQL Injection");
    }
    try (
            CallableStatement callableStatement = conn.prepareCall("{call sp_aggiungiAzienda(?, ?, ?, ?)}")
    ) {
      callableStatement.setString(1, nome);
      callableStatement.setString(2, telefono);
      callableStatement.setString(3, email);
      callableStatement.setString(4, partitaIva);
      boolean result = callableStatement.execute();
      if (result) {
        throw new SQLException("a result was provided by the query when it was not supposed to");
      }
    }
  }
}
