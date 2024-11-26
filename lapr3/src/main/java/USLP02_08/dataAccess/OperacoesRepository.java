package USLP02_08.dataAccess;

import USLP02_08.domain.Unidade;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperacoesRepository {

    public OperacoesRepository() {
    }

    public void sementeiraRegister(int parcelaId, int plantaId, Date dataOperacao, Date dataFim, double quantidade, double opQuantidade) throws SQLException {

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call RegistarSemeadura(?,?,?,?,?,?) }");

            callStmt.setInt(1, parcelaId);
            callStmt.setInt(2, plantaId);

            java.sql.Date sqlDataOperacao = new java.sql.Date(dataOperacao.getTime());
            java.sql.Date sqlDataFim = new java.sql.Date(dataFim.getTime());

            callStmt.setDate(3, sqlDataOperacao);
            callStmt.setDate(4, sqlDataFim);

            callStmt.setDouble(5, quantidade);

            callStmt.setDouble(6, opQuantidade);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }
    public void MondaRegister(int parcelaId, int idCultura, Date dataOperacao, double area) throws SQLException {

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call RegistarMonda(?,?,?,?) }");

            callStmt.setInt(1, parcelaId);

            callStmt.setInt(2, idCultura);

            java.sql.Date sqlDataOperacao = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(3, sqlDataOperacao);

            callStmt.setDouble(4, area);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }
    public void ColheitaRegister(int parcelaId, int idCultura, Date dataOperacao, double quantidade) throws SQLException {

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call RegistarColheita(?,?,?,?) }");

            callStmt.setInt(1, parcelaId);

            callStmt.setInt(2, idCultura);

            java.sql.Date sqlDataOperacao = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(3, sqlDataOperacao);

            callStmt.setDouble(4, quantidade);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }

    public void PodaRegister(int parcelaId, int idCultura, Date dataOperacao, double quantidade) throws SQLException {

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call RegistarPoda(?,?,?,?) }");

            callStmt.setInt(1, parcelaId);

            callStmt.setInt(2, idCultura);

            java.sql.Date sqlDataOperacao = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(3, sqlDataOperacao);

            callStmt.setDouble(4, quantidade);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }

    public void FatorPrRegister(int parcelaId, Date dataOperacao,String FatorProducao,String Modo, double area,double quantidade, int idCultura) throws SQLException {

        int unidade = 3; //kg

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call RegistarFertilizacao(?,?,?,?,?,?,?) }");

            callStmt.setInt(1, parcelaId);

            callStmt.setInt(2, idCultura);

            java.sql.Date sqlDataOperacao = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(3, sqlDataOperacao);

            callStmt.setString (4, Modo);

            callStmt.setDouble(5, area);

            callStmt.setDouble(6, quantidade);

            callStmt.setString (7, FatorProducao);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }

    public List<Unidade> getUnidades() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Unidade> unidades = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call listarUnidades() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            unidades = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return unidades;
    }

    private List<Unidade> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Unidade> unidades = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            Unidade unidade = new Unidade(
                    resultSet.getInt("idUnidade"),
                    resultSet.getString("unidade")
            );
            unidades.add(unidade);
        }
        return unidades;
    }
}