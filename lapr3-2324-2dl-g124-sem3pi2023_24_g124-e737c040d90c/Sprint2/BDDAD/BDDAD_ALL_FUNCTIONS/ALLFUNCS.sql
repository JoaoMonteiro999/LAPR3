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

create or replace FUNCTION listarUnidades RETURN SYS_REFCURSOR IS

    u_cursor SYS_REFCURSOR;
BEGIN
OPEN u_cursor FOR
SELECT DISTINCT u.idUnidade, u.unidade
FROM unidade u
ORDER BY u.idUnidade ASC;
RETURN u_cursor;
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


CREATE OR REPLACE FUNCTION getNextOperacaoId RETURN NUMBER IS

    id_ret NUMBER;
BEGIN
SELECT MAX(idOperacao) + 1
INTO id_ret
FROM operacaoAgricula;
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


CREATE OR REPLACE FUNCTION parcelaValida(p_id parcela.idParcela%type) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
SELECT COUNT(*) INTO ocorrencias
FROM parcela
WHERE idParcela = p_id;
RETURN ocorrencias > 0;
END;
/

CREATE OR REPLACE FUNCTION plantaValida(p_id planta.idPlanta%type) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
SELECT COUNT(*)
INTO ocorrencias
FROM planta
WHERE idPlanta = p_id;
RETURN ocorrencias > 0;
END;
/


CREATE OR REPLACE FUNCTION area_valida(p_idParcela operacaoAgricula.idParcela%TYPE, p_area parcela.area%TYPE) RETURN BOOLEAN IS
    area_parcela parcela.area%TYPE;
BEGIN
SELECT area INTO area_parcela
FROM parcela
WHERE parcela.idParcela = p_idParcela;

RETURN (area_parcela >= p_area AND p_area > 0);
END;
/


CREATE OR REPLACE FUNCTION dataValida(dataOperacao operacaoAgricula.dataOperacao%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    p_dataInicio cultura.dataInicio%TYPE;
    p_dataFim cultura.dataFim%TYPE;
BEGIN
    IF (cultura_id IS NULL) THEN
        RETURN (dataOperacao < CURRENT_DATE);
END IF;

SELECT dataInicio INTO p_dataInicio
FROM cultura
WHERE idCultura = cultura_id;

SELECT dataFim INTO p_dataFim
FROM cultura
WHERE idCultura = cultura_id;
RETURN (dataOperacao < CURRENT_DATE AND dataOperacao > p_dataInicio AND((p_dataFim IS NULL) OR (p_dataFim >= dataOperacao)));
END;
/


CREATE OR REPLACE FUNCTION dataValida2(p_dataOperacao operacaoAgricula.dataOperacao%TYPE,p_dataFimCultura cultura.dataFim%TYPE) RETURN BOOLEAN IS
BEGIN
RETURN ((p_dataFimCultura IS NULL OR p_dataFimCultura > p_dataOperacao) AND p_dataOperacao < CURRENT_DATE);
END;
/


CREATE OR REPLACE FUNCTION fatorValida(p_idFator operacaoAgriculaComFator.idFatorDeProducao%TYPE) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
SELECT COUNT(*) INTO ocorrencias
FROM fatorDeProducao
WHERE idFatorDeProducao = p_idFator;
RETURN ocorrencias > 0;
END;
/

CREATE OR REPLACE FUNCTION modoValida(p_idModo modoDeAplicacao.idModoDeAplicacao%TYPE) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
SELECT COUNT(*) INTO ocorrencias
FROM modoDeAplicacao
WHERE idModoDeAplicacao = p_idModo;
RETURN ocorrencias > 0;
END;
/


CREATE OR REPLACE FUNCTION unidadeValida(p_idUnidade unidade.idUnidade%TYPE) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
SELECT COUNT(*) INTO ocorrencias
FROM unidade
WHERE idUnidade = p_idUnidade;
RETURN ocorrencias > 0;
END;
/


CREATE OR REPLACE FUNCTION culturaValida(parcela_id IN cultura.idParcela%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    ocorrencias INT;
BEGIN
    IF (cultura_id IS NULL) THEN
        RETURN TRUE;
ELSE
SELECT COUNT(*) INTO ocorrencias
FROM cultura c
WHERE idParcela = parcela_id AND idCultura = cultura_id;
RETURN ocorrencias > 0;
END IF;
END;
/


CREATE OR REPLACE FUNCTION getTipoCultura(p_idPlanta cultura.idPlanta%TYPE) RETURN VARCHAR2 IS
    tp tipoCultura.tipoCultura%type;
BEGIN
SELECT t.tipoCultura INTO tp
FROM planta p
         JOIN tipoCultura t ON p.idTipoCultura = t.idTipoCultura
WHERE p.idPlanta = p_idPlanta;
RETURN tp;
END;
/


CREATE OR REPLACE PROCEDURE RegistarColheita(p_idParcela operacaoAgricula.idParcela%TYPE, p_idCultura cultura.idPlanta%TYPE,
    p_dataOperacao operacaoAgricula.dataOperacao%TYPE, p_quantidade operacaoAgricula.quantidade%TYPE) IS

    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;
BEGIN
    p_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_idParcela) AND
        culturaValida(p_idParcela,p_idCultura) AND
        dataValida(p_dataOperacao, p_idCultura) AND (p_quantidade > 0)
        ) THEN

        INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade)
        VALUES (p_idOperacao, p_dataOperacao, p_idParcela, p_idCultura, 7, p_quantidade, 3);
COMMIT;
ELSE
        RAISE error;
END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20001, 'Variáveis fornecidas inválidas');
END;
/


CREATE OR REPLACE PROCEDURE RegistarMonda(p_idParcela operacaoAgricula.idParcela%TYPE, p_idCultura cultura.idPlanta%TYPE,
    p_dataOperacao operacaoAgricula.dataOperacao%TYPE, p_area operacaoAgricula.quantidade%TYPE) IS

    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;
BEGIN
    p_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_idParcela) AND
        culturaValida(p_idParcela,p_idCultura) AND
        dataValida(p_dataOperacao, p_idCultura) AND (area_valida(p_idParcela, p_area))
        ) THEN

        INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade)
        VALUES (p_idOperacao, p_dataOperacao, p_idParcela, p_idCultura, 10, p_area, 4);
COMMIT;
ELSE
        RAISE error;
END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20001, 'Variáveis fornecidas inválidas');
END;
/


CREATE OR REPLACE PROCEDURE RegistarSemeadura(p_idParcela operacaoAgricula.idParcela%TYPE,p_idPlanta cultura.idPlanta%TYPE,p_dataOperacao operacaoAgricula.dataOperacao%TYPE,p_dataFimCultura cultura.dataFim%TYPE,p_cQuantidade cultura.quantidade%TYPE,p_quantidade operacaoAgricula.quantidade%TYPE) IS

    p_idCultura operacaoAgricula.idCultura%TYPE;
    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;

BEGIN
    p_idCultura := getNextCulturaId();
    p_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_idParcela) AND plantaValida(p_idPlanta) AND area_valida(p_idParcela, p_cQuantidade) AND dataValida2(p_dataOperacao, p_dataFimCultura)) THEN

		IF (getTipoCultura(p_idPlanta) = 'Permanente') THEN
            INSERT INTO cultura (idCultura, idPlanta, idParcela, dataInicio, dataFim, quantidade, idUnidade) VALUES (p_idCultura, p_idPlanta, p_idParcela, p_dataOperacao, NULL, p_cQuantidade, 1);
ELSE
            INSERT INTO cultura (idCultura, idPlanta, idParcela, dataInicio, dataFim, quantidade, idUnidade) VALUES (p_idCultura, p_idPlanta, p_idParcela, p_dataOperacao, p_dataFimCultura,  p_cQuantidade, 4);
END IF;

INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade) VALUES (p_idOperacao, p_dataOperacao, p_idParcela, p_idCultura, 6, p_quantidade, 3);

COMMIT;

ELSE
        RAISE error;
END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20001, 'Variáveis fornecidas inválidas');
END;
/


    CREATE OR REPLACE PROCEDURE RegistarAplicacaoFator(
    p_idParcela operacaoAgricula.idParcela%TYPE,
    p_dataOperacao operacaoAgricula.dataOperacao%TYPE,
    p_idFatorDeProducao operacaoAgriculaComFator.idFatorDeProducao%TYPE,
    p_modo operacaoAgriculaComFator.idModoDeAplicacao%TYPE,
    p_area parcela.area%TYPE,
    p_quantidade operacaoAgricula.quantidade%TYPE,
    p_idUnidade operacaoAgricula.idUnidade%TYPE,
    p_idCultura cultura.idCultura%TYPE
) IS
    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;
	var_p_idCultura cultura.idCultura%TYPE;

BEGIN
    IF (p_idCultura = 0) THEN
  	var_p_idCultura := NULL;
ELSE var_p_idCultura := p_idCultura;
END IF;


    p_idOperacao := getNextOperacaoId();

    IF (
        parcelaValida(p_idParcela) AND
        culturaValida(p_idParcela, var_p_idCultura) AND
        fatorValida(p_idFatorDeProducao) AND
        modoValida(p_modo) AND
        area_valida(p_idParcela, p_area) AND
        (p_quantidade > 0) AND
        unidadeValida(p_idUnidade) AND
        dataValida(p_dataOperacao, var_p_idCultura)
    ) THEN
        INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade)
        VALUES (p_idOperacao, p_dataOperacao, p_idParcela, var_p_idCultura, 4, p_quantidade, p_idUnidade);

INSERT INTO operacaoAgriculaComFator (idOperacao, idFatorDeProducao, idModoDeAplicacao)
VALUES (p_idOperacao, p_idFatorDeProducao, p_modo);
COMMIT;
ELSE
        RAISE error;
END IF;


EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20004, 'Variáveis fornecidas inválidas');
END;
/

CREATE OR REPLACE PROCEDURE listarProdutosPorParcela(p_idParcela parcela.idParcela%TYPE, p_dataInicio cultura.dataInicio%TYPE, p_dataFim cultura.dataFim%TYPE) IS
    var_especie planta.especie%TYPE;
    var_nome produto.tipoProduto%TYPE;
    var_variedade planta.variedade%TYPE;

    listaEspecie SYS_REFCURSOR;
    listaFormatada SYS_REFCURSOR;

BEGIN

	DBMS_OUTPUT.ENABLE(1000000);

OPEN listaEspecie FOR
SELECT p.especie
FROM operacaoAgricula op
         JOIN cultura c ON op.idCultura = c.idCultura
         JOIN planta p ON c.idPlanta = p.idPlanta
         JOIN produto tp ON p.idTipoProduto = tp.idTipoProduto
WHERE op.idTipoOperacao = 7 AND op.idParcela = p_idParcela AND op.dataOperacao < p_dataFim AND op.dataOperacao > p_dataInicio
GROUP BY p.especie;

LOOP
FETCH listaEspecie INTO var_especie;
        EXIT WHEN listaEspecie%notfound;

            dbms_output.put_line(var_especie);
OPEN listaFormatada FOR
SELECT tp.tipoProduto, p.variedade
FROM operacaoAgricula op
         JOIN cultura c ON op.idCultura = c.idCultura
         JOIN planta p ON c.idPlanta = p.idPlanta
         JOIN produto tp ON p.idTipoProduto = tp.idTipoProduto
WHERE op.idTipoOperacao = 7 AND op.idParcela = p_idParcela AND op.dataOperacao < p_dataFim AND op.dataOperacao > p_dataInicio AND p.especie = var_especie
GROUP BY tp.tipoProduto, p.variedade;

LOOP
FETCH listaFormatada INTO var_nome, var_variedade;
            EXIT WHEN listaFormatada%notfound;

            dbms_output.put_line('	' || var_nome || ' ' || var_variedade);
END LOOP;
END LOOP;
END;
/


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

	DBMS_OUTPUT.ENABLE(1000000);

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


CREATE OR REPLACE FUNCTION listaFator(
    p_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE,
    p_parcela parcela.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, ft.nomeComercial, op.quantidade, un.unidade
FROM operacaoAgricula op
         JOIN operacaoAgriculaComFator opf ON op.idOperacao = opf.idOperacao
         JOIN fatorDeProducao ft ON opf.idFatorDeProducao = ft.idFatorDeProducao
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN unidade un ON op.idUnidade = un.idUnidade
         JOIN tipoOperacaoAgricula tipoOp ON op.idTipoOperacao = tipoOp.idTipoOperacao
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tipoOp.tipoOperacao = p_tipoOperacaoAgricula AND p_parcela = p.idParcela
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/



CREATE OR REPLACE FUNCTION listaPorTipoDeOperacao(
    p_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE,
    p_parcela parcela.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, pl.nome, pl.variedade, op.quantidade, un.unidade
FROM operacaoAgricula op
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN cultura c ON op.idCultura = c.idCultura
         JOIN planta pl ON c.idPlanta = pl.idPlanta
         JOIN unidade un ON op.idUnidade = un.idUnidade
         JOIN tipoOperacaoAgricula tipoOp ON op.idTipoOperacao = tipoOp.idTipoOperacao
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tipoOp.tipoOperacao = p_tipoOperacaoAgricula AND p_parcela = p.idParcela
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/




CREATE OR REPLACE FUNCTION listaMobilizacao(
    p_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE,
    p_parcela parcela.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, op.quantidade, un.unidade
FROM operacaoAgricula op
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN unidade un ON op.idUnidade = un.idUnidade
         JOIN tipoOperacaoAgricula tipoOp ON op.idTipoOperacao = tipoOp.idTipoOperacao
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tipoOp.tipoOperacao = p_tipoOperacaoAgricula AND p_parcela = p.idParcela
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/

CREATE OR REPLACE PROCEDURE OperacaoPorTipoOperacao(
    p_parcela operacaoAgricula.idParcela%TYPE,
    dataInicio operacaoAgricula.dataOperacao%TYPE,
    dataFim operacaoAgricula.dataOperacao%TYPE
) IS
    operacaoAgriculaCursor SYS_REFCURSOR;
    tipoDeOperacaoCursor SYS_REFCURSOR;
    var_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE;
    var_dataOperacao operacaoAgricula.dataOperacao%TYPE;
    var_nomeComercial fatorDeProducao.nomeComercial%TYPE;
    var_nome planta.nome%TYPE;
    var_variedade planta.variedade%TYPE;
    var_quantidade operacaoAgricula.quantidade%TYPE;
    var_unidade unidade.unidade%TYPE;
BEGIN

	DBMS_OUTPUT.ENABLE(1000000);


OPEN operacaoAgriculaCursor FOR
SELECT tipoOperacao
FROM tipoOperacaoAgricula;

LOOP
FETCH operacaoAgriculaCursor INTO var_tipoOperacaoAgricula;
        EXIT WHEN operacaoAgriculaCursor%notfound;

        IF (var_tipoOperacaoAgricula = 'Fertilização' ) THEN
            tipoDeOperacaoCursor := listaFator(var_tipoOperacaoAgricula, p_parcela, dataInicio, dataFim);

            dbms_output.put_line('-------------------');
            dbms_output.put_line('Aplicação de fator de produção');
            dbms_output.put_line('');

            LOOP
FETCH tipoDeOperacaoCursor INTO var_dataOperacao, var_nomeComercial, var_quantidade, var_unidade;
                EXIT WHEN tipoDeOperacaoCursor%notfound;
                dbms_output.put_line(var_dataOperacao || '||' || var_nomeComercial ||  '||' ||  var_quantidade || ' ' || var_unidade);
END LOOP;
ELSE
            IF  (var_tipoOperacaoAgricula = 'Mobilização do solo' ) THEN
				tipoDeOperacaoCursor := listaMobilizacao(var_tipoOperacaoAgricula, p_parcela, dataInicio, dataFim);

            	dbms_output.put_line('-------------------');
            	dbms_output.put_line(var_tipoOperacaoAgricula);
            	dbms_output.put_line('');

				LOOP
FETCH tipoDeOperacaoCursor INTO var_dataOperacao, var_quantidade, var_unidade;
                EXIT WHEN tipoDeOperacaoCursor%notfound;
                dbms_output.put_line(var_dataOperacao || '||' || var_quantidade || ' ' || var_unidade);
END LOOP;
ELSE



                tipoDeOperacaoCursor := listaPorTipoDeOperacao(var_tipoOperacaoAgricula, p_parcela, dataInicio, dataFim);

                dbms_output.put_line('-------------------');
                dbms_output.put_line(var_tipoOperacaoAgricula);
                dbms_output.put_line('');

                LOOP
FETCH tipoDeOperacaoCursor INTO var_dataOperacao, var_nome, var_variedade, var_quantidade, var_unidade;
                    EXIT WHEN tipoDeOperacaoCursor%notfound;
                    dbms_output.put_line(var_dataOperacao || '||' || var_nome || ' ' || var_variedade || '||' || var_quantidade || ' ' || var_unidade);
END LOOP;

END IF;
END IF;

END LOOP;
END;
/


CREATE OR REPLACE FUNCTION listaPorTipoDeFator(
    p_tipoFatorProducao tipoFatorDeProducao.tipoFatorDeProducao%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, ft.nomeComercial, p.designacao, pl.nome, pl.variedade
FROM operacaoAgricula op
         JOIN operacaoAgriculaComFator opf ON op.idOperacao = opf.idOperacao
         JOIN fatorDeProducao ft ON opf.idFatorDeProducao = ft.idFatorDeProducao
         JOIN tipoFatorDeProducao tft ON ft.idTipoFatorDeProducao = tft.idTipoFatorDeProducao
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN cultura c ON op.idCultura = c.idCultura
         JOIN planta pl ON c.idPlanta = pl.idPlanta
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tft.tipoFatorDeProducao = p_tipoFatorProducao
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/

CREATE OR REPLACE PROCEDURE OperacaoPorTipoFator(
    dataInicio operacaoAgricula.dataOperacao%TYPE,
    dataFim operacaoAgricula.dataOperacao%TYPE
) IS
    fatProdCursor SYS_REFCURSOR;
    tipoDeFatorOpsCursor SYS_REFCURSOR;
    var_tipoFatorProducao tipoFatorDeProducao.tipoFatorDeProducao%TYPE;
    var_dataOperacao operacaoAgricula.dataOperacao%TYPE;
    var_nomeComercial fatorDeProducao.nomeComercial%TYPE;
    var_designacao parcela.designacao%TYPE;
    var_nome planta.nome%TYPE;
    var_variedade planta.variedade%TYPE;
BEGIN

DBMS_OUTPUT.ENABLE(1000000);

OPEN fatProdCursor FOR
SELECT tipoFatorDeProducao
FROM tipoFatorDeProducao;

LOOP
FETCH fatProdCursor INTO var_tipoFatorProducao;
        EXIT WHEN fatProdCursor%notfound;

        tipoDeFatorOpsCursor := listaPorTipoDeFator(var_tipoFatorProducao, dataInicio, dataFim);

		dbms_output.put_line('-------------------');
        dbms_output.put_line(var_tipoFatorProducao);
        dbms_output.put_line('');

        LOOP
FETCH tipoDeFatorOpsCursor INTO var_dataOperacao, var_nomeComercial, var_designacao, var_nome, var_variedade;
            EXIT WHEN tipoDeFatorOpsCursor%notfound;
            dbms_output.put_line(var_dataOperacao || '||' || var_nomeComercial || '||' || var_designacao || '||' || var_nome || '||' || var_variedade);
END LOOP;
END LOOP;
END;
/


BEGIN

RegistarColheita(101,2,TO_DATE('30-05-2021', 'DD-MM-YYYY'), 7);
RegistarMonda(102,11,TO_DATE('5-10-2023', 'DD-MM-YYYY'), 2.5);
RegistarSemeadura(101,1,TO_DATE('30-04-2021', 'DD-MM-YYYY'),TO_DATE('30-04-2026', 'DD-MM-YYYY'),1.1,5.5);
RegistarAplicacaoFator(108,TO_DATE('6-10-2023', 'DD-MM-YYYY'),12,1,1.1,4000,3,null);


ListarProdutosPorParcela(108, TO_DATE('20-05-2023', 'DD-MM-YYYY'), TO_DATE('06-11-2023', 'DD-MM-YYYY'));
FatorPorParcela(108,TO_DATE('12-01-2019', 'DD-MM-YYYY'), TO_DATE('12-12-2023', 'DD-MM-YYYY'));
OperacaoPorTipoOperacao(108,TO_DATE('01-07-2023', 'DD-MM-YYYY'), TO_DATE('02-10-2023', 'DD-MM-YYYY'));
OperacaoPorTipoFator(TO_DATE('30-04-2020', 'DD-MM-YYYY'), TO_DATE('30-04-2022', 'DD-MM-YYYY'));
END;