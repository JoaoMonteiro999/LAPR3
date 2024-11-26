SELECT fatorDeProducao.tipoFatorDeProducao , COUNT(*) AS Ocorrencias
    FROM operacao
    INNER JOIN fatorDeProducao ON operacao.fatorDeProducao = fatorDeProducao.nomeComercial
    WHERE operacao.dataOperacao >= TO_DATE('09-10-2019','DD-MM-YYYY') AND operacao.dataOperacao <= TO_DATE('10-12-2022','DD-MM-YYYY')
    AND fatorDeProducao IS NOT NULL
    GROUP BY fatorDeProducao.tipoFatorDeProducao;