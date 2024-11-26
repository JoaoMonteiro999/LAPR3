SELECT operacao.tipoOperacao , COUNT(*) AS Nº_De_Operacoes
    FROM cultura
    INNER JOIN operacao ON cultura.idCultura = operacao.idCultura
WHERE cultura.idParcela = 101 AND operacao.dataOperacao >= TO_DATE('2019-10-09','YYYY-MM-DD') AND operacao.dataOperacao <= TO_DATE('2022-10-10','YYYY-MM-DD')
GROUP BY operacao.tipoOperacao;