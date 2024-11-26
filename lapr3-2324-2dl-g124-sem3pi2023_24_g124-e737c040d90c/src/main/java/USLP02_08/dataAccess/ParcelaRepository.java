package USLP02_08.dataAccess;

import USLP02_08.domain.Parcela;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParcelaRepository {

    public ParcelaRepository() {
    }

    public List<Parcela> getParcelas() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Parcela> parcelas = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call listarParcelas() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            parcelas = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return parcelas;
    }

    private List<Parcela> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Parcela> parcelas = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            Parcela parcela = new Parcela(
                    resultSet.getInt("idParcela"),
                    resultSet.getString("designacao"),
                    resultSet.getDouble("area")
            );
            parcelas.add(parcela);
        }
        return parcelas;
    }

}