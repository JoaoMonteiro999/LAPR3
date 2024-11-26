 set serveroutput on;


CREATE OR REPLACE FUNCTION listarFatProdParcela(
    p_idParcela operacaoAgricula.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    IdFatProdCursor SYS_REFCURSOR;
BEGIN

OPEN IdFatProdCursor FOR
    SELECT FatProd.nomeComercial, FatProd.idFatorDeProducao
    FROM fatorDeProducao FatProd
        JOIN operacaoAgriculaComFator OpFat ON OpFat.idFatorDeProducao = FatProd.idFatorDeProducao
        JOIN operacaoAgricula Op ON OpFat.idOperacao = Op.idOperacao
        WHERE Op.idParcela = p_idParcela AND Op.dataOperacao >= p_dataInicio
        AND Op.dataOperacao <= p_dataFim
        GROUP BY FatProd.nomeComercial, FatProd.idFatorDeProducao;
		 
RETURN IdFatProdCursor;
END;
/

CREATE OR REPLACE PROCEDURE FatorPorParcela(
    p_idParcela operacaoAgricula.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) IS
	ListaComponentes SYS_REFCURSOR;
    IdFatProdCursor SYS_REFCURSOR;
	CompQuantCursor SYS_REFCURSOR;
	nomeComercial_var fatorDeProducao.nomeCOmercial%TYPE;
	IdFatProd_var tipoFatorDeProducao.idTipoFatorDeProducao%TYPE;
	designacaoComponente componente.designacao%TYPE;
	quantidadeComponente registoFichaTecnica.quantidade%TYPE;

BEGIN
	IdFatProdCursor := listarFatProdParcela(p_idParcela, p_dataInicio, p_dataFim);
    
LOOP
FETCH IdFatProdCursor INTO nomeComercial_var, IdFatProd_var;
        EXIT WHEN IdFatProdCursor%notfound;

OPEN CompQuantCursor FOR
    SELECT c.designacao, fichaTec.quantidade
        FROM registoFichaTecnica fichaTec
        JOIN componente c ON fichaTec.idComponente = c.idComponente
    WHERE fichaTec.idFatorDeProducao = IdFatProd_var;

		dbms_output.put_line('-----------');
        dbms_output.put_line(nomeComercial_var);
		dbms_output.put_line('-----------');
        LOOP
FETCH CompQuantCursor INTO designacaoComponente, quantidadeComponente;
            EXIT WHEN CompQuantCursor%notfound;
        dbms_output.put_line(designacaoComponente || RPAD(' ', 5 - LENGTH(designacaoComponente)) || ' --> ' || quantidadeComponente);
END LOOP;
END LOOP;
END;
/

BEGIN
	FatorPorParcela(108,TO_DATE('12-01-2019', 'DD-MM-YYYY'), TO_DATE('12-12-2023', 'DD-MM-YYYY'));
END;