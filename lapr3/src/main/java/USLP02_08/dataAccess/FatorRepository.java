package USLP02_08.dataAccess;

import USLP02_08.domain.FatorProducao;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FatorRepository {


    public FatorRepository() {
    }

    public List<FatorProducao> getFatorProducao() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<FatorProducao> fatores = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call listarFatores() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            fatores = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return fatores;
    }

    private List<FatorProducao> resultSetToList(ResultSet resultSet) throws SQLException {
        List<FatorProducao> fatores = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            FatorProducao fatorProducao = new FatorProducao(
                    resultSet.getInt("idFatorDeProducao"),
                    resultSet.getString("nomeComercial"));
            fatores.add(fatorProducao);
        }
        return fatores;
    }
}
