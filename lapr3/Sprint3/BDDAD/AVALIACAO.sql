-----US31-----
BEGIN

tabAux('Kiplant AllGrip', 2, 'l/ha');
tabAux('Tecniferti MOL', 60, 'l/ha');
tabAux('soluSOP 52', 2.5, 'kg/ha');

RegistarMix(22);


--tabAux('Kiplant AllFit Plus', 2, 'l/ha');
--tabAux('Tecniferti MOL', 60, 'l/ha');
    
--RegistarMix(23);

END;
/

SELECT *
FROM mix;

SELECT *
FROM receitaDoMix;


-----US32-----
BEGIN

RegistarRega (10, TO_DATE('02/09/2023' ,  'DD/MM/YYYY'), 90, 5, 0,10);

--RegistarRega (10, TO_DATE('02/09/2023' ,  'DD/MM/YYYY'), 90, 5, 0,50);

END;
/

SELECT *
FROM rega
WHERE idOperacao >351;

SELECT *
FROM fertirega
WHERE idOperacao >351;


SELECT *
FROM operacaoAgricola
WHERE idOperacao >351;


-----US30-----

BEGIN

RegistarRega (11, TO_DATE('04/01/2024' ,  'DD/MM/YYYY'), 60, 15, 0,10);

--RegistarRega (11, TO_DATE('29/12/2023' ,  'DD/MM/YYYY'), 60, 15, 0,10);
 
anularOperacao(354);
--anularOperacao(355);


END;
/

SELECT *
FROM rega
WHERE idOperacao >351;

SELECT *
FROM operacaoAgricola
WHERE idOperacao >351;


-----US24-----

SELECT *
FROM operacaoAgricola;


-----US25-----

SELECT *
FROM logDeOperacoes
WHERE idOperacao > 351;


-----US26-----

BEGIN

RegistarRega (11, TO_DATE('25/01/2023' ,  'DD/MM/YYYY'), 360, 23, 30, 11);
 
END;
/

SELECT *
FROM logDeOperacoes;


-----US27-----

BEGIN
DELETE FROM logDeOperacoes WHERE idOperacao = 356;
--UPDATE logDeOperacoes SET idOperacao = 5;

END;
/

SELECT *
FROM logDeOperacoes
WHERE idOperacao > 351;


-----US28-----

BEGIN

DELETE FROM operacaoAgricola WHERE idOperacao = 356;

END;
/

SELECT *
FROM operacaoAgricola
WHERE idOperacao >351;


-----US33-----

set serveroutput on;

BEGIN

listarConsumoCultura(TO_DATE('5/10/2023' ,  'DD/MM/YYYY'));

--listarConsumoCultura(TO_DATE('10/01/2023' ,  'DD/MM/YYYY'));
    
END;
/