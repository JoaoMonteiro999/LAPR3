SELECT idParcela AS Parcela_Com_Mais_Operacoes_De_Rega
FROM (
    SELECT cultura.idParcela, COUNT(*) AS a1
    FROM cultura
    INNER JOIN operacao ON cultura.idCultura = operacao.idCultura
    WHERE operacao.tipoOperacao = 'Rega' AND operacao.dataOperacao >= TO_DATE('2019-10-09', 'YYYY-MM-DD') AND operacao.dataOperacao <= TO_DATE('2022-10-10', 'YYYY-MM-DD')
    GROUP BY cultura.idParcela
)
WHERE a1 = (SELECT MAX(a2)
             FROM (
                 SELECT cultura.idParcela, COUNT(*) AS a2
                 FROM cultura
                 INNER JOIN operacao ON cultura.idCultura = operacao.idCultura
                 WHERE operacao.tipoOperacao = 'Rega' AND operacao.dataOperacao >= TO_DATE('2019-10-09', 'YYYY-MM-DD') AND operacao.dataOperacao <= TO_DATE('2022-10-10', 'YYYY-MM-DD')
                 GROUP BY cultura.idParcela
             )
);