SELECT
	f.tipoFatorDeProducao,  COUNT(f.nomeComercial) AS Nº_Fatores_Aplicados
FROM
	cultura c
JOIN operacao o ON c.idCultura = o.idCultura
JOIN fatorDeProducao f ON o.fatorDeProducao = f.nomeComercial
WHERE c.idParcela = 107
    AND o.fatorDeProducao IS NOT NULL
    AND o.dataOperacao >= TO_DATE('09-10-2019','DD-MM-YYYY') AND o.dataOperacao <= TO_DATE('10-10-2022','DD-MM-YYYY')
GROUP BY o.tipoOperacao,  f.tipoFatorDeProducao;