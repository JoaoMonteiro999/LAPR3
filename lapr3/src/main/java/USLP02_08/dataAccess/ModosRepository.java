package USLP02_08.dataAccess;


import USLP02_08.domain.ModoDeAplicacaoFator;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModosRepository {


    public List<ModoDeAplicacaoFator> getModos() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<ModoDeAplicacaoFator> modos = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call listarModos() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            modos = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return modos;
    }


    private List<ModoDeAplicacaoFator> resultSetToList(ResultSet resultSet) throws SQLException {
        List<ModoDeAplicacaoFator> modos = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            ModoDeAplicacaoFator modo = new ModoDeAplicacaoFator(
                    resultSet.getInt("idModoDeAplicacao"),
                    resultSet.getString("modoDeAplicacao"));
            modos.add(modo);
        }
        return modos;
    }









}
