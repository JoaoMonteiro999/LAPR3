package USLP02_08.dataAccess;

import USLP02_08.domain.Cultura;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CulturaRepository {

    public CulturaRepository() {
    }

    public List<Cultura> getCulturas(int parcelaId) throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Cultura> culturas = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call listarCulturas(?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, parcelaId);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            culturas = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return culturas;
    }

    public List<Cultura> getCulturasPermanantes(int parcelaId) throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Cultura> culturas = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call listarCulturasPermanentes(?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, parcelaId);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            culturas = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return culturas;
    }

    private List<Cultura> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Cultura> culturas = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            Cultura cultura = new Cultura(
                    resultSet.getInt("idCultura"),
                    resultSet.getDate("dataInicio"),
                    resultSet.getDate("dataFim"),
                    resultSet.getString("nome"),
                    resultSet.getString("variedade"),
                    resultSet.getDouble("area"));
            culturas.add(cultura);
        }
        return culturas;
    }
}