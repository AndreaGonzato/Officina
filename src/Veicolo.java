import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Veicolo implements Insertable {
  String targa;
  int cliente, modello;

  public Veicolo(String targa, int cliente, int modello) {
    this.targa = targa;
    this.cliente = cliente;
    this.modello = modello;
  }

  public void insert(Connection conn) throws SQLException {
    if (SQLInjectionParser.detectSQLInjection(targa)) {
      throw new SQLException("detected a possible SQL Injection");
    }
    try (
            CallableStatement callableStatement = conn.prepareCall("{call sp_aggiungiVeicolo(?, ?, ?)}")
    ) {
      callableStatement.setString(1, targa);
      callableStatement.setInt(2, cliente);
      callableStatement.setInt(3, modello);
      boolean result = callableStatement.execute();
      if (result) {
        throw new SQLException("a result was provided by the query when it was not supposed to");
      }
    }
  }


}
