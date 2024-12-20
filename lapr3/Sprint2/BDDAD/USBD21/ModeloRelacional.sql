CREATE TABLE parcela (idParcela number(10) NOT NULL, designacao varchar2(255) NOT NULL UNIQUE, area number(19, 2) NOT NULL, PRIMARY KEY (idParcela));
CREATE TABLE cultura (idCultura number(10) NOT NULL, idPlanta number(10) NOT NULL, idParcela number(10) NOT NULL, dataInicio date NOT NULL, dataFim date, quantidade number(19, 2) NOT NULL, idUnidade number(10) NOT NULL, PRIMARY KEY (idCultura));
CREATE TABLE planta (idPlanta number(10) NOT NULL, nome varchar2(255) NOT NULL, especie varchar2(255) NOT NULL, variedade varchar2(255) NOT NULL, idTipoProduto number(10) NOT NULL, idTipoCultura number(10) NOT NULL, PRIMARY KEY (idPlanta));
CREATE TABLE produto (idTipoProduto number(10) NOT NULL, tipoProduto varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idTipoProduto));
CREATE TABLE tipoCultura (idTipoCultura number(10) NOT NULL, tipoCultura varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idTipoCultura));
CREATE TABLE fatorDeProducao (idFatorDeProducao number(10) NOT NULL, nomeComercial varchar2(255) NOT NULL, idTipoFatorDeProducao number(10) NOT NULL, idFormulacao number(10) NOT NULL, PRIMARY KEY (idFatorDeProducao));
CREATE TABLE tipoFatorDeProducao (idTipoFatorDeProducao number(10) NOT NULL, tipoFatorDeProducao varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idTipoFatorDeProducao));
CREATE TABLE formulacao (idFormulacao number(10) NOT NULL, formulacao varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idFormulacao));
CREATE TABLE operacaoAgricula (idOperacao number(10) NOT NULL, dataOperacao date NOT NULL, idParcela number(10) NOT NULL, idCultura number(10), idTipoOperacao number(10) NOT NULL, quantidade number(19, 2) NOT NULL, idUnidade number(10) NOT NULL, PRIMARY KEY (idOperacao));
CREATE TABLE tipoOperacaoAgricula (idTipoOperacao number(10) NOT NULL, tipoOperacao varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idTipoOperacao));
CREATE TABLE colheitaPrevista (idCulturaPrevista number(10) NOT NULL, idCultura number(10) NOT NULL, quantidadePrevista number(19, 2) NOT NULL, dataPrevista date NOT NULL, PRIMARY KEY (idCulturaPrevista));
CREATE TABLE registoEstadoFenologico (dataRegisto date NOT NULL, idCultura number(10) NOT NULL, idEstadoFenologico number(10) NOT NULL, PRIMARY KEY (dataRegisto, idCultura, idEstadoFenologico));
CREATE TABLE estadoFenologico (idEstadoFenologico number(10) NOT NULL, designacao varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idEstadoFenologico));
CREATE TABLE registoFichaTecnica (idFatorDeProducao number(10) NOT NULL, idComponente number(10) NOT NULL, quantidade number(19, 4) NOT NULL, PRIMARY KEY (idFatorDeProducao, idComponente));
CREATE TABLE componente (idComponente number(10) NOT NULL, designacao varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idComponente));
CREATE TABLE setorDeRega (idSetor number(10) NOT NULL, caudalMax number(10) NOT NULL, dataInicio date NOT NULL, dataFim date, PRIMARY KEY (idSetor));
CREATE TABLE operacaoAgriculaComFator (idOperacao number(10) NOT NULL, idFatorDeProducao number(10) NOT NULL, idModoDeAplicacao number(10), PRIMARY KEY (idOperacao, idFatorDeProducao));
CREATE TABLE setorCultura (idSetor number(10) NOT NULL, idCultura number(10) NOT NULL, dataInicio date NOT NULL, dataFim date, PRIMARY KEY (idSetor, idCultura));
CREATE TABLE unidade (idUnidade number(10) NOT NULL, unidade varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idUnidade));
CREATE TABLE modoDeAplicacao (idModoDeAplicacao number(10) NOT NULL, modoDeAplicacao varchar2(255) NOT NULL UNIQUE, PRIMARY KEY (idModoDeAplicacao));
ALTER TABLE planta ADD CONSTRAINT FKplanta543564 FOREIGN KEY (idTipoProduto) REFERENCES produto (idTipoProduto);
ALTER TABLE cultura ADD CONSTRAINT FKcultura54749 FOREIGN KEY (idPlanta) REFERENCES planta (idPlanta);
ALTER TABLE cultura ADD CONSTRAINT FKcultura850567 FOREIGN KEY (idParcela) REFERENCES parcela (idParcela);
ALTER TABLE fatorDeProducao ADD CONSTRAINT FKfatorDePro277544 FOREIGN KEY (idTipoFatorDeProducao) REFERENCES tipoFatorDeProducao (idTipoFatorDeProducao);
ALTER TABLE fatorDeProducao ADD CONSTRAINT FKfatorDePro416661 FOREIGN KEY (idFormulacao) REFERENCES formulacao (idFormulacao);
ALTER TABLE operacaoAgricula ADD CONSTRAINT FKoperacaoAg269718 FOREIGN KEY (idParcela) REFERENCES parcela (idParcela);
ALTER TABLE operacaoAgricula ADD CONSTRAINT FKoperacaoAg913904 FOREIGN KEY (idCultura) REFERENCES cultura (idCultura);
ALTER TABLE operacaoAgricula ADD CONSTRAINT FKoperacaoAg449151 FOREIGN KEY (idTipoOperacao) REFERENCES tipoOperacaoAgricula (idTipoOperacao);
ALTER TABLE colheitaPrevista ADD CONSTRAINT FKcolheitaPr355475 FOREIGN KEY (idCultura) REFERENCES cultura (idCultura);
ALTER TABLE registoEstadoFenologico ADD CONSTRAINT FKregistoEst749834 FOREIGN KEY (idCultura) REFERENCES cultura (idCultura);
ALTER TABLE registoEstadoFenologico ADD CONSTRAINT FKregistoEst313914 FOREIGN KEY (idEstadoFenologico) REFERENCES estadoFenologico (idEstadoFenologico);
ALTER TABLE registoFichaTecnica ADD CONSTRAINT FKregistoFic117034 FOREIGN KEY (idFatorDeProducao) REFERENCES fatorDeProducao (idFatorDeProducao);
ALTER TABLE registoFichaTecnica ADD CONSTRAINT FKregistoFic409363 FOREIGN KEY (idComponente) REFERENCES componente (idComponente);
ALTER TABLE operacaoAgriculaComFator ADD CONSTRAINT FKoperacaoAg498031 FOREIGN KEY (idFatorDeProducao) REFERENCES fatorDeProducao (idFatorDeProducao);
ALTER TABLE operacaoAgriculaComFator ADD CONSTRAINT FKoperacaoAg13254 FOREIGN KEY (idOperacao) REFERENCES operacaoAgricula (idOperacao);
ALTER TABLE setorCultura ADD CONSTRAINT FKsetorCultu9424 FOREIGN KEY (idSetor) REFERENCES setorDeRega (idSetor);
ALTER TABLE setorCultura ADD CONSTRAINT FKsetorCultu643735 FOREIGN KEY (idCultura) REFERENCES cultura (idCultura);
ALTER TABLE operacaoAgricula ADD CONSTRAINT FKoperacaoAg368095 FOREIGN KEY (idUnidade) REFERENCES unidade (idUnidade);
ALTER TABLE planta ADD CONSTRAINT FKplanta42303 FOREIGN KEY (idTipoCultura) REFERENCES tipoCultura (idTipoCultura);
ALTER TABLE cultura ADD CONSTRAINT FKcultura787245 FOREIGN KEY (idUnidade) REFERENCES unidade (idUnidade);
ALTER TABLE operacaoAgriculaComFator ADD CONSTRAINT FKoperacaoAg895663 FOREIGN KEY (idModoDeAplicacao) REFERENCES modoDeAplicacao (idModoDeAplicacao);
