CREATE OR REPLACE FUNCTION getDataFromID(p_id operacaoAgricola.idOperacao%TYPE) RETURN operacaoAgricola.dataOperacao%TYPE IS
   var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
   SELECT dataOperacao INTO var_data
   FROM operacaoAgricola
   WHERE idOperacao = p_id;
   RETURN var_data;
END;
/


CREATE OR REPLACE FUNCTION idOperacaoValido(p_id operacaoAgricola.idOperacao%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM operacaoAgricola
    WHERE idOperacao = p_id;
    IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20001, 'Id de Operação não Existente');
    END IF;

	RETURN valid;
END;
/


    CREATE OR REPLACE FUNCTION setorValido(p_setor setorDeRega.idSetor%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM setorDeRega
    WHERE idSetor = p_setor;
    IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20002, 'Setor Invalido');
    END IF;

	RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION mixValido(p_mix mix.idMix%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM mix
    WHERE idMix = p_mix;
    IF (ocorrencias = 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20003, 'Mix Já Existente');
    END IF;

	RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION mixValido2(p_mix mix.idMix%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM mix
    WHERE idMix = p_mix;
    IF (ocorrencias > 0 OR p_mix IS NULL) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20004, 'Mix Inválido');
    END IF;

	RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION horaValida(p_hora rega.horaDeRega_Hora%TYPE, p_minuto rega.horaDeRega_Minuto%TYPE) RETURN BOOLEAN IS
	valid BOOLEAN := FALSE;
BEGIN
    IF (p_hora >= 0 AND p_hora <= 24 AND p_minuto >= 0 AND p_minuto <= 59) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20005, 'Hora de Rega Inválida');
    END IF;

	RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION cursorCulturaSetor (p_setor setorDeRega.idSetor%TYPE) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
	valid NUMBER(10);
BEGIN

	SELECT COUNT(*) INTO valid
	FROM setorCultura
	WHERE idSetor = p_setor;

	IF(valid < 1) THEN
    RAISE_APPLICATION_ERROR(-20006, 'O Setor não tem Culturas associadas');
	END IF;
    
    OPEN lista FOR
    SELECT idCultura
	FROM setorCultura
	WHERE idSetor = p_setor;
    RETURN lista;
END;
/


CREATE OR REPLACE FUNCTION unidadeValida(p_unidade unidade.unidade%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM unidade
    WHERE unidade = p_unidade;
    IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20007, 'Unidade Inválida');
    END IF;

	RETURN valid;
END;
/


    CREATE OR REPLACE FUNCTION getIDUnidade(p_unidade unidade.unidade%TYPE) RETURN unidade.idUnidade%TYPE IS
    resultado unidade.idUnidade%TYPE;
BEGIN
    SELECT idUnidade
    INTO resultado
    FROM unidade
    WHERE unidade = p_unidade;
    RETURN resultado;
END;
/


    
CREATE OR REPLACE FUNCTION cursorListAux RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
	valid NUMBER(10);
BEGIN

	SELECT COUNT(*) INTO valid
	FROM tabelaAuxiliar;

	IF(valid is NULL OR valid < 1) THEN
    RAISE_APPLICATION_ERROR(-20008, 'Não foi atribuida uma receita para o Mix');
	END IF;
    
    OPEN lista FOR
    SELECT idFatorDeProducao, quantidade, idUnidade
	FROM tabelaAuxiliar;
    RETURN lista;
END;
/


CREATE OR REPLACE FUNCTION getTipoCulturaByID(p_id tipoCultura.idTipoCultura%TYPE) RETURN tipoCultura.tipoCultura%TYPE IS
    resultado tipoCultura.tipoCultura%TYPE;
BEGIN
    SELECT tipoCultura
    INTO resultado
    FROM tipoCultura
    WHERE idTipoCultura = p_id;
    RETURN resultado;
END;
/

    
CREATE OR REPLACE FUNCTION getNextOperacaoId RETURN NUMBER IS

    id_ret NUMBER;
BEGIN
    SELECT MAX(idOperacao) + 1
    INTO id_ret
    FROM operacaoAgricola;
    RETURN id_ret;
END;
/


CREATE OR REPLACE FUNCTION getNextCulturaId RETURN NUMBER IS

    id_ret NUMBER;
BEGIN
    SELECT MAX(idCultura) + 1
    INTO id_ret
    FROM cultura;
    RETURN id_ret;
END;
/
    
    
CREATE OR REPLACE FUNCTION parcelaValida(p_id parcela.idParcela%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM parcela
    WHERE idParcela = p_id;
    IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20009, 'Parcela Invalida');
    END IF;

	RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION culturaValida(p_parcela cultura.idParcela%TYPE, p_cultura cultura.idCultura%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM cultura
    WHERE idParcela = p_parcela AND idCultura = p_cultura;
    IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20010, 'Cultura Invalida');
    END IF;

	RETURN valid;
END;
/
    

CREATE OR REPLACE FUNCTION plantaValida(p_id planta.idPlanta%type) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*)
    INTO ocorrencias
    FROM planta
    WHERE idPlanta = p_id AND getTipoCulturaByID(idTipoCultura) = 'Temporária';

	IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20011, 'Planta Invalida');
    END IF;

	RETURN valid;
END;
/

CREATE OR REPLACE FUNCTION areaValida(p_idParcela cultura.idParcela%TYPE, p_area cultura.area%TYPE, p_dataInicio cultura.dataInicio%TYPE) RETURN BOOLEAN IS
    areaParcela parcela.area%TYPE;
	areaOcupada parcela.area%TYPE;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT area INTO areaParcela
    FROM parcela
    WHERE parcela.idParcela = p_idParcela;

	SELECT COALESCE(SUM(area),0) INTO areaocupada
    FROM cultura
    WHERE idParcela = p_idParcela AND (dataInicio <= p_dataInicio AND ((dataFim IS NULL) OR (dataFim >= p_dataInicio)));

	IF ((areaparcela - areaocupada) >= p_area AND p_area > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20012, 'Area Invalida');
    END IF;

    RETURN valid;

END;
/



CREATE OR REPLACE FUNCTION areaValida2(p_idParcela cultura.idParcela%TYPE, p_idCultura cultura.idCultura%TYPE, p_area cultura.area%TYPE) RETURN BOOLEAN IS
	areaParcela parcela.area%TYPE;
	areaCultura cultura.area%TYPE;
    valid BOOLEAN := FALSE;
BEGIN
    SELECT area INTO areaParcela
    FROM parcela
    WHERE parcela.idParcela = p_idParcela;

	SELECT area INTO areaCultura
    FROM cultura
    WHERE cultura.idCultura = p_idCultura;

	IF (areaParcela >= p_area AND areaCultura >= p_area AND p_area > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20013, 'Area Invalida');
    END IF;

    RETURN valid;

END;
/

    
CREATE OR REPLACE FUNCTION dataValida(p_dataInicio cultura.dataInicio%TYPE, p_dataFim cultura.dataFim%TYPE) RETURN BOOLEAN IS
    valid BOOLEAN := FALSE;
BEGIN
    IF (p_dataFim IS NULL OR p_dataFim > p_dataInicio) AND p_dataInicio < CURRENT_DATE THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20014, 'Data Invalida');
    END IF;

    RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION dataValida2(p_dataOperacao operacaoAgricola.dataOperacao%TYPE, p_cultura cultura.idCultura%TYPE) RETURN BOOLEAN IS
    valid BOOLEAN := FALSE;
	var_dataInicio cultura.dataInicio%TYPE;
    var_dataFim cultura.dataFim%TYPE;
BEGIN

SELECT dataInicio INTO var_dataInicio
FROM cultura
WHERE idCultura = p_cultura;

SELECT dataFim INTO var_dataFim
FROM cultura
WHERE idCultura = p_cultura;

IF (p_dataOperacao < CURRENT_DATE AND p_dataOperacao > var_dataInicio AND (var_dataFim IS NULL OR (var_dataFim >= p_dataOperacao))) THEN
        valid := TRUE;
ELSE
        
        RAISE_APPLICATION_ERROR(-20015, 'Data Invalida');
END IF;

RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION dataValida3(p_dataOperacao operacaoAgricola.dataOperacao%TYPE, p_setor setorDeRega.idSetor%TYPE) RETURN BOOLEAN IS
    valid BOOLEAN := FALSE;
	var_dataInicio setorDeRega.dataInicio%TYPE;
    var_dataFim setorDeRega.dataFim%TYPE;
BEGIN

	SELECT dataInicio INTO var_dataInicio
    FROM setorDeRega
    WHERE idSetor = p_setor;

	SELECT dataFim INTO var_dataFim
    FROM setorDeRega
    WHERE idSetor = p_setor;
    
    IF (p_dataOperacao < CURRENT_DATE AND p_dataOperacao > var_dataInicio AND ((var_dataFim is NULL) OR (var_dataFim >= p_dataOperacao))) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20016, 'Data Invalida');
    END IF;

    RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION dataValida4(p_id operacaoAgricola.idOperacao%TYPE) RETURN BOOLEAN IS
    var_data operacaoAgricola.dataOperacao%TYPE;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT dataOperacao INTO var_data
    FROM operacaoAgricola
    WHERE idOperacao = p_id;

    IF (CURRENT_DATE - var_data < 3) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20017, 'Data da operecao Invalida');
    END IF;

	RETURN valid;
END;
/

    
CREATE OR REPLACE FUNCTION fatProdValido(p_fatProd fatorDeProducao.nomeComercial%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM fatorDeProducao
    WHERE nomeComercial = p_fatProd;
    IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        RAISE_APPLICATION_ERROR(-20018, 'Fator de Produção Invalido');
    END IF;

	RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION modoValido(p_modoDeAplicacao modoDeAplicacao.modoDeAplicacao%TYPE) RETURN BOOLEAN IS
    ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM modoDeAplicacao
    WHERE modoDeAplicacao = p_modoDeAplicacao;
    IF (ocorrencias > 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20019, 'Modo de Aplicação Invalido');
    END IF;

	RETURN valid;
END;
/

    
CREATE OR REPLACE FUNCTION getIDFatProd(p_fatProd fatorDeProducao.nomeComercial%TYPE) RETURN fatorDeProducao.idFatorDeProducao%TYPE IS
    resultado fatorDeProducao.idFatorDeProducao%TYPE;
BEGIN
    SELECT idFatorDeProducao
    INTO resultado
    FROM fatorDeProducao
    WHERE nomeComercial = p_fatProd;
    RETURN resultado;
END;
/


CREATE OR REPLACE FUNCTION getIDModo(p_modoDeAplicacao modoDeAplicacao.modoDeAplicacao%TYPE) RETURN modoDeAplicacao.idModoDeAplicacao%TYPE IS
    resultado modoDeAplicacao.idModoDeAplicacao%TYPE;
BEGIN
    SELECT idModoDeAplicacao
    INTO resultado
    FROM modoDeAplicacao
    WHERE modoDeAplicacao = p_modoDeAplicacao;
    RETURN resultado;
END;
/


CREATE OR REPLACE FUNCTION getCulturaByIdOperacao(p_id operacaoAgricola.idOperacao%TYPE)
RETURN cultura.idCultura%TYPE IS
var_idCultura cultura.idCultura%TYPE;
BEGIN
    SELECT idCultura INTO var_idCultura
    FROM (
        SELECT plantacao.idCultura
        FROM operacaoAgricola
        JOIN plantacao ON operacaoAgricola.idOperacao = plantacao.idOperacao
        WHERE operacaoAgricola.idOperacao = p_id

        UNION

        SELECT semeadura.idCultura
        FROM operacaoAgricola
        JOIN semeadura ON operacaoAgricola.idOperacao = semeadura.idOperacao
        WHERE operacaoAgricola.idOperacao = p_id
    );

	RETURN var_idCultura;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            var_idCultura := NULL;
			RETURN var_idCultura;

END;
/


CREATE OR REPLACE FUNCTION dependenciasValida(p_id cultura.idCultura%TYPE) RETURN BOOLEAN IS
	ocorrencias NUMBER;
	valid BOOLEAN := FALSE;
BEGIN
    SELECT COUNT(idCultura) INTO ocorrencias
    FROM (
        SELECT idCultura
        FROM colheita
        WHERE idCultura = p_id
        	UNION
        SELECT idCultura
        FROM rega
        WHERE idCultura = p_id
        	UNION
        SELECT idCultura
        FROM monda
        WHERE idCultura = p_id  
        	UNION
        SELECT idCultura
        FROM poda
        WHERE idCultura = p_id
        UNION
        SELECT idCultura
        FROM fertilizacao
        WHERE idCultura = p_id
    );

	IF (ocorrencias = 0) THEN
        valid := TRUE;
    ELSE
        
        RAISE_APPLICATION_ERROR(-20033, 'A operação fornecida possui dependencias');
    END IF;

	RETURN valid;
END;
/


CREATE OR REPLACE FUNCTION cursorConsumoCulturas (p_data operacaoAgricola.dataOperacao%TYPE) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
	p_dataFim operacaoAgricola.dataOperacao%TYPE;
BEGIN

	p_dataFim := ADD_MONTHS(p_data, 12);
    
    OPEN lista FOR
    WITH duracaoCulturas AS (
	SELECT re.idCultura, SUM(re.duracao) AS duracaoTotal
	FROM rega re
    JOIN operacaoAgricola op ON re.idOperacao = op.idOperacao
    WHERE op.dataOperacao BETWEEN p_data AND p_dataFim
	GROUP BY idCultura)
    
	SELECT idCultura, duracaoTotal
	FROM duracaoCulturas
	WHERE duracaoTotal = (SELECT MAX(duracaoTotal) FROM duracaoCulturas);
    RETURN lista;
END;
/


CREATE OR REPLACE PROCEDURE RegistarFertilizacao(p_parcela parcela.idParcela%TYPE, p_cultura cultura.idCultura%TYPE, p_dataOperacao operacaoAgricola.dataOperacao%TYPE,
    p_modoDeAplicacao modoDeAplicacao.modoDeAplicacao%TYPE, p_area operacaoComFator.area%TYPE, p_quantidade aplicacaoDeFator.quantidade%TYPE, p_fatProd fatorDeProducao.nomeComercial%TYPE) IS

    var_idOperacao operacaoAgricola.idOperacao%TYPE;
    var_idFatProd fatorDeProducao.idFatorDeProducao%TYPE;
    var_idModo modoDeAplicacao.idModoDeAplicacao%TYPE;
    error EXCEPTION;

BEGIN
    var_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_parcela) AND culturaValida(p_parcela, p_cultura) AND dataValida2(p_dataOperacao, p_cultura) AND areaValida2(p_parcela, p_cultura, p_area)
        AND modoValido(p_modoDeAplicacao) AND fatProdValido(p_fatProd)) THEN

	BEGIN

		var_idFatProd := getIDFatProd(p_fatProd);
		var_idModo := getIDModo(p_modoDeAplicacao);
        
		INSERT INTO operacaoAgricola (idOperacao, dataOperacao, estadoOperacao)  values (var_idOperacao, p_dataOperacao, 0);
		INSERT INTO operacaoComFator (idOperacao, area)  values (var_idOperacao, p_area);
		INSERT INTO aplicacaoDeFator (idOperacao, idFatorDeProducao, quantidade)  values (var_idOperacao,var_idFatProd, p_quantidade);
		INSERT INTO fertilizacao (idOperacao, idCultura, idModoDeAplicacao)  values (var_idOperacao,p_cultura, var_idModo);

	COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                RAISE;
	END;
	
    ELSE
        RAISE error;
    END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20020, 'Operação não registada');
END;
/



    
CREATE OR REPLACE PROCEDURE RegistarColheita(p_parcela parcela.idParcela%TYPE, p_cultura cultura.idCultura%TYPE, p_dataOperacao operacaoAgricola.dataOperacao%TYPE, p_quantidadeColhida colheita.quantidadeColhida%TYPE) IS

    var_idOperacao operacaoAgricola.idOperacao%TYPE;
    error EXCEPTION;

BEGIN
    var_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_parcela) AND culturaValida(p_parcela, p_cultura) AND dataValida2(p_dataOperacao, p_cultura) AND p_quantidadeColhida > 0) THEN

	BEGIN
        
		INSERT INTO operacaoAgricola (idOperacao, dataOperacao, estadoOperacao)  values (var_idOperacao, p_dataOperacao, 0);
		INSERT INTO colheita (idOperacao, idCultura, quantidadeColhida)  values (var_idOperacao, p_cultura, p_quantidadeColhida);

	COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                RAISE;
	END;
	
    ELSE
        RAISE error;
    END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20021, 'Operação não registada');
END;
/


CREATE OR REPLACE PROCEDURE RegistarMonda(p_parcela parcela.idParcela%TYPE, p_cultura cultura.idCultura%TYPE, p_dataOperacao operacaoAgricola.dataOperacao%TYPE, p_area monda.area%TYPE) IS

    var_idOperacao operacaoAgricola.idOperacao%TYPE;
    error EXCEPTION;

BEGIN
    var_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_parcela) AND culturaValida(p_parcela, p_cultura) AND areaValida2(p_parcela, p_cultura, p_area) AND dataValida2(p_dataOperacao, p_cultura)) THEN

	BEGIN
        
		INSERT INTO operacaoAgricola (idOperacao, dataOperacao, estadoOperacao)  values (var_idOperacao, p_dataOperacao, 0);
		INSERT INTO monda (idOperacao, idCultura, area)  values (var_idOperacao, p_cultura, p_area);

	COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                RAISE;
	END;
	
    ELSE
        RAISE error;
    END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20022, 'Operação não registada');
END;
/


CREATE OR REPLACE PROCEDURE RegistarSemeadura(p_parcela cultura.idParcela%TYPE, p_planta cultura.idPlanta%TYPE, p_dataInicio cultura.dataInicio%TYPE, p_dataFim cultura.dataFim%TYPE, p_area cultura.area%TYPE, p_quantidadeSemeada semeadura.quantidadeSemeada%TYPE) IS

    var_idCultura cultura.idCultura%TYPE;
    var_idOperacao operacaoAgricola.idOperacao%TYPE;
    error EXCEPTION;

BEGIN
    var_idCultura := getNextCulturaId();
    var_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_parcela) AND plantaValida(p_planta) AND areaValida(p_parcela, p_area, p_dataInicio) AND dataValida(p_dataInicio, p_dataFim) AND p_quantidadeSemeada > 0) THEN

	BEGIN
        
        INSERT INTO cultura (idCultura, idPlanta, idParcela, dataInicio, dataFim, area)  values (var_idCultura , p_planta, p_parcela, p_dataInicio, p_dataFim, p_area);
		INSERT INTO operacaoAgricola (idOperacao, dataOperacao, estadoOperacao)  values (var_idOperacao, p_dataInicio, 0);
		INSERT INTO semeadura (idOperacao, idCultura, quantidadeSemeada)  values (var_idOperacao, var_idCultura, p_quantidadeSemeada);

	COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                RAISE;
	END;
	
    ELSE
        RAISE error;
    END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20023, 'Operação não registada');
END;
/


CREATE OR REPLACE PROCEDURE RegistarMix(p_mix mix.idMix%TYPE) IS
   error EXCEPTION;
   l SYS_REFCURSOR;
	idFP receitaDoMix.idFatorDeProducao%TYPE;
    quant receitaDoMix.quantidade%TYPE;
    idUni receitaDoMix.idUnidade%TYPE;
BEGIN
    
   BEGIN

	l := cursorListAux;

    DELETE FROM tabelaAuxiliar;


      IF (mixValido(p_mix) AND p_mix > 0 ) THEN
         INSERT INTO mix (idMix) VALUES (p_mix);
      ELSE
         RAISE error;
      END IF;


	LOOP
        FETCH l INTO idFP, quant, idUni;
        EXIT WHEN l%NOTFOUND;

		INSERT INTO receitaDoMix (idMix, idFatorDeProducao, quantidade, idUnidade) values (p_mix,idFP,quant,idUni);

    END LOOP;

    CLOSE l;

      COMMIT;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         RAISE;
   END;
EXCEPTION
   WHEN error THEN
      RAISE_APPLICATION_ERROR(-20024, 'Mix não registada');
END;
/


CREATE OR REPLACE PROCEDURE tabAux(p_fatProd fatorDeProducao.nomeComercial%TYPE, p_quantidade receitaDoMix.quantidade%TYPE, p_unidade unidade.unidade%TYPE) IS
   var_idFatProd fatorDeProducao.idFatorDeProducao%TYPE;
   var_idUnidade unidade.idUnidade%TYPE;
   error EXCEPTION;

BEGIN
    
   BEGIN
    
         IF (fatProdValido(p_fatProd) AND (p_quantidade > 0) AND unidadeValida(p_unidade)) THEN
            var_idFatProd := getIDFatProd(p_fatProd);
            var_idUnidade := getIDUnidade(p_unidade);
            INSERT INTO tabelaAuxiliar(idFatorDeProducao, quantidade, idUnidade) VALUES (var_idFatProd, p_quantidade, var_idUnidade);
         ELSE
            RAISE error;
         END IF;

      COMMIT;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         RAISE;
   END;
EXCEPTION
   WHEN error THEN
      RAISE_APPLICATION_ERROR(-20025, 'Erro nos valores fornecidos');

END;
/


CREATE OR REPLACE PROCEDURE RegistarRega(p_setor setorDeRega.idSetor%TYPE, p_dataOperacao operacaoAgricola.dataOperacao%TYPE,
    p_duracao rega.duracao%TYPE, p_hora rega.horaDeRega_Hora%TYPE, p_minuto rega.horaDeRega_Minuto%TYPE, p_mix mix.idMix%TYPE) IS

    l SYS_REFCURSOR;
	var_cultura cultura.idCultura%TYPE;
    var_idOperacao operacaoAgricola.idOperacao%TYPE;
    error EXCEPTION;

BEGIN
    
    IF (mixValido2(p_mix) AND setorValido(p_setor) AND dataValida3(p_dataOperacao, p_setor) AND (p_duracao > 0) AND horaValida(p_hora, p_minuto)) THEN

	BEGIN

		l := cursorCulturaSetor(p_setor);

		LOOP
        FETCH l INTO var_cultura;
        EXIT WHEN l%NOTFOUND;

		var_idOperacao := getNextOperacaoId();

		INSERT INTO operacaoAgricola (idOperacao, dataOperacao, estadoOperacao)  values (var_idOperacao, p_dataOperacao, 0);
		INSERT INTO rega (idOperacao, idCultura, horaDeRega_Hora, horaDeRega_Minuto, duracao)  values (var_idOperacao, var_cultura,p_hora,p_minuto, p_duracao);


		IF ((p_mix IS NOT NULL)) THEN
        
        INSERT INTO fertirega (idOperacao, idMix) values (var_idOperacao, p_mix);

		END IF;


    END LOOP;

    CLOSE l;

	COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                RAISE;
	END;
	
    ELSE
        RAISE error;
    END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20026, 'Operação não registada');
END;
/


CREATE OR REPLACE PROCEDURE anularOperacao(p_id operacaoAgricola.idOperacao%TYPE) IS

    error EXCEPTION;
	
	var_idCultura cultura.idCultura%TYPE;

BEGIN

    var_idCultura := getCulturaByIdOperacao(253);

	IF (idOperacaoValido(p_id) AND dataValida4(p_id) AND (var_idCultura IS NULL OR dependenciasValida(var_idCultura))) THEN

	BEGIN
    
	UPDATE operacaoAgricola SET estadoOperacao = 1 WHERE idOperacao = p_id;
    
	COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                RAISE;
	END;

	ELSE
        RAISE error;
    END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20027, 'Operação não anulada');
END;
/


CREATE OR REPLACE PROCEDURE listarConsumoCultura(p_data operacaoAgricola.dataOperacao%TYPE) IS
    l SYS_REFCURSOR;
    idCultura rega.idCultura%TYPE;
    duracaoTotal rega.duracao%TYPE;
BEGIN
    l := cursorConsumoCulturas(p_data);

    LOOP
        FETCH l INTO idCultura, duracaoTotal;
        EXIT WHEN l%NOTFOUND;

        dbms_output.put_line('Cultura ' || idCultura || ' | ' || duracaoTotal || ' minutos');
    END LOOP;

    CLOSE l;
END;
/


CREATE OR REPLACE TRIGGER colheita
BEFORE INSERT ON colheita
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 1, ('Id de Cultura -> '||:new.idCultura||' Quantidade Colhida -> '||:new.quantidadeColhida||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER plantacao
BEFORE INSERT ON plantacao
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 2, ('idCultura -> '||:new.idCultura||' Numero de Plantas -> '||:new.numPlantas||' Compasso -> '||:new.compasso||' Distancia entre Filas -> '||:new.numPlantas||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER rega
BEFORE INSERT ON rega
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 3, ('idCultura -> '||:new.idCultura||' Horario de Rega -> '||:new.horaDeRega_Hora||':'||:new.horaDeRega_Minuto||' Duração -> '||:new.duracao||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER semeadura
BEFORE INSERT ON semeadura
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 4, ('Id de Cultura -> '||:new.idCultura||' Quantidade Semeada -> '||:new.quantidadeSemeada||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER monda
BEFORE INSERT ON monda
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 5, ('Id de Cultura -> '||:new.idCultura||' Area de Operação -> '||:new.area||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER poda
BEFORE INSERT ON poda
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 6, ('Id de Cultura -> '||:new.idCultura||' Quantidade Podada -> '||:new.QuantidadePodada||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/

    
CREATE OR REPLACE TRIGGER fertilizacao
BEFORE INSERT ON fertilizacao
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 7, ('Id de Cultura -> '||:new.idCultura||' Id de Modo de Aplicação -> '||:new.idModoDeAplicacao||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER incorporacaoSolo
BEFORE INSERT ON incorporacaoSolo
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 8, ('Id da Parcela -> '||:new.idParcela||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER mobilizacaoSolo
BEFORE INSERT ON mobilizacaoSolo
FOR EACH ROW
DECLARE var_data operacaoAgricola.dataOperacao%TYPE;
BEGIN
    var_data := getDataFromID(:NEW.idOperacao);
    INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
    VALUES (:NEW.idOperacao, 9, ('Id da Parcela -> '||:new.idParcela||' Data da Operacao -> '|| var_data), CURRENT_TIMESTAMP);
END;
/


CREATE OR REPLACE TRIGGER naoEditarLogs
BEFORE UPDATE OR DELETE ON logDeOperacoes
FOR EACH ROW

    BEGIN
	    RAISE_APPLICATION_ERROR(-20028, 'Os registos na tabela de logs não podem ser editados');   
	END;
/



CREATE OR REPLACE TRIGGER naoApagarOperacoes
BEFORE DELETE ON operacaoAgricola
FOR EACH ROW

    BEGIN
	    RAISE_APPLICATION_ERROR(-20029, 'As operacoes agricolas não podem ser apagadas.');
	END;
/

CREATE OR REPLACE TRIGGER anulacao
BEFORE UPDATE ON operacaoAgricola
FOR EACH ROW
BEGIN
    IF (:OLD.estadoOperacao = 0 AND :NEW.estadoOperacao = 1) THEN
        INSERT INTO logDeOperacoes (idOperacao, idTipoDeOperacao, dadosOperacao, instanteDeCriacao)
        VALUES (:NEW.idOperacao, 10, ' Data da Operacao -> '|| :OLD.dataOperacao, CURRENT_TIMESTAMP);
	ELSE
        RAISE_APPLICATION_ERROR(-20030, 'Anulacao Inválida');
    END IF;
END;
/


CREATE OR REPLACE TRIGGER naoApagarOperacoes
BEFORE DELETE ON operacaoAgricola
FOR EACH ROW

    BEGIN
	    RAISE_APPLICATION_ERROR(-20031, 'As operacoes agricolas não podem ser apagadas.');
	END;
/


CREATE OR REPLACE TRIGGER verificarID
BEFORE INSERT ON operacaoAgricola
FOR EACH ROW
DECLARE id_operacao operacaoAgricola.idOperacao%TYPE;
BEGIN
    id_operacao := getNextOperacaoId();
    IF (:NEW.idOperacao <> id_operacao) THEN
        RAISE_APPLICATION_ERROR(-20032, 'Id de Operacao Inválido');
    END IF;
END;
/

create or replace FUNCTION listarParcelas RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
OPEN p_cursor FOR
SELECT DISTINCT p.idParcela, p.designacao, p.area
FROM parcela p
ORDER BY p.idParcela ASC;
RETURN p_cursor;
END;
/


create or replace FUNCTION listarPlantas RETURN SYS_REFCURSOR IS

    p_cursor SYS_REFCURSOR;
BEGIN
OPEN p_cursor FOR
SELECT DISTINCT p.idPlanta, p.nome, p.variedade, p.idTipoCultura
FROM planta p
WHERE p.idTipoCultura = 1
ORDER BY p.idPlanta ASC;
RETURN p_cursor;
END;
/


create or replace FUNCTION listarCulturas(p_idParcela cultura.idParcela%TYPE) RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
OPEN p_cursor FOR
SELECT DISTINCT c.idCultura, c.dataInicio, c.dataFim, plt.nome, plt.variedade
FROM cultura c
         JOIN parcela p ON c.idParcela = p.idParcela
         JOIN planta plt ON c.idPlanta = plt.idPlanta
WHERE c.idParcela = p_idParcela
ORDER BY c.idCultura ASC;
RETURN p_cursor;
END;
/


create or replace FUNCTION listarFatores RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
OPEN p_cursor FOR
SELECT DISTINCT f.idFatorDeProducao, f.nomeComercial
FROM fatorDeProducao f
ORDER BY f.idFatorDeProducao ASC;
RETURN p_cursor;
END;
/


create or replace FUNCTION listarModos RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
OPEN p_cursor FOR
SELECT DISTINCT m.idModoDeAplicacao,m.modoDeAplicacao
FROM modoDeAplicacao m
ORDER BY m.idModoDeAplicacao ASC;
RETURN p_cursor;
END;
/