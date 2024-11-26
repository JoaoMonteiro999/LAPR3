SELECT fatorDeProducao AS Fator_De_Producao_Mais_Usado
FROM (
    SELECT fatorDeProducao , COUNT(*) AS ocorrencias
    FROM operacao
    WHERE operacao.dataOperacao >= TO_DATE('09-10-2019','DD-MM-YYYY') AND operacao.dataOperacao <= TO_DATE('10-10-2022','DD-MM-YYYY') AND fatorDeProducao IS NOT NULL
    GROUP BY fatorDeProducao )
WHERE ocorrencias = (SELECT MAX(ocorrencias2)
    FROM (
    SELECT fatorDeProducao , COUNT(*) AS ocorrencias2
    FROM operacao
    WHERE operacao.dataOperacao >= TO_DATE('09-10-2019','DD-MM-YYYY') AND operacao.dataOperacao <= TO_DATE('10-10-2022','DD-MM-YYYY') AND fatorDeProducao IS NOT NULL
    GROUP BY fatorDeProducao
    )
);