package USLP02_08.dataAccess;

import USLP02_08.domain.Planta;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlantaRepository {

    public PlantaRepository() {
    }

    public List<Planta> getPlanta() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Planta> plantas = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call listarPlantas() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            plantas = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return plantas;
    }

    private List<Planta> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Planta> plantas = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            Planta planta = new Planta(
                    resultSet.getInt("idPlanta"),
                    resultSet.getString("nome"),
                    resultSet.getString("variedade"),
                    resultSet.getString("idTipoCultura")
            );
            plantas.add(planta);
        }
        return plantas;
    }

    public String getTipoCultura(int plantaId) throws SQLException {

        CallableStatement callStmt = null;
        String TipoCultura = "";
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call getTipoCultura(?) }");

            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setInt(1, plantaId);

            callStmt.execute();
            TipoCultura = callStmt.getString(1);

            connection.commit();

        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
        return TipoCultura;
    }

}