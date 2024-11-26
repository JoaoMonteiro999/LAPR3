SELECT c.plantaNomeVariedade, SUM(o.quantidade) AS QUANTIDADE
    FROM cultura c
    INNER JOIN operacao o ON c.idCultura = o.idCultura
WHERE o.tipoOperacao = 'Colheita' AND c.idParcela = 102
    AND o.dataOperacao >= TO_DATE('2019-10-09','YYYY-MM-DD')
    AND o.dataOperacao <= TO_DATE('2022-10-10','YYYY-MM-DD')
GROUP BY c.plantaNomeVariedade;

