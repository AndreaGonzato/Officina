import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Modello implements Insertable {
  String nome;
  int produttore;

  public Modello(String nome, int produttore) {
    this.nome = nome;
    this.produttore = produttore;
  }

  public void insert(Connection conn) throws SQLException {
    CallableStatement callableStatement = conn.prepareCall("{call sp_aggiungiModello(?, ?)}");
    callableStatement.setString(1, nome);
    callableStatement.setInt(2, produttore);
    boolean result = callableStatement.execute();
    if (result) {
      throw new SQLException();
    }
  }
}