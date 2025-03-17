# RPA_ALLIANZ 2021.04.01

RPA Robotic Process Automation

Agendamento e Inspeções 
 
Salvar documento do e-mail no diretório downloads 
Localizar o documento “RoboAgendamento.xlsx” no diretório “C:\Users\Public\RPA_ALLIANZ\MassaAuto” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
Observação: Completar as colunas “status” com o valor “Nenviado” e “linha” até o termino dos dados:
 
Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.

 



Agendamento Fiel Depositário 

 
Salvar a pasta do e-mail no diretório downloads 
Localizar o documento “EmissaoFielDepositario.xlsx” no diretório “C:\Users\Public\RPA_ALLIANZ\Data Files\Backoffce\FielDepositarioData” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
Apagar os dados das colunas “SUSEP” e “STATUS” e substituir os dados da coluna “STProc” pela letra “B”
 
No documento original enviado pelo usuário é diferente da utilizada  pelo RPA, temos dois processos de seleção de dados 
Primeira seleção
 


Segunda seleção 
 
Control+shift+a para limpar as linhas abaixo
 
Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.
 











Envios de Pendencias GO!

 
Salvar documento do e-mail no diretório downloads 
Renomear o documento enviado pelo usuário “Produção 23-04” por “RoboPendenciaGo” e adiciona-lo no diretório “C:\Users\Public\RPA_ALLIANZ\MassaAuto”. Substituir o status de “enviado” para “Nenviado”conforme modelo: 
 


Reprocessa emissão de apólices 

 
O processo reprocessamento de emissão de apólices é executado conforme a data da apólice, podendo ter um documento para uma data ou outros documentos com outras datas para processar 
Salvar documento do e-mail no diretório downloads 
Localizar o documento “ReprocessamentoEmissaoApolice.xlsx” no diretório “C:\Users\Public\RPA_ALLIANZ\Data Files\Auto\ReprocessamentoEmissaoApoliceData” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
Alterar o campo “Status” para “Processar” e limpar as colunas “SUSEP”e “ERRO”
Observações de formatação:
Coluna “SUSEP” formatação “Geral”.
Coluna “erro” formatação “texto”.



Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.
 

Envio de PDF Corporate
 
Salvar documento do e-mail no diretório downloads 
Localizar o documento “RoboEnvioPdf.xlsx” no diretório “C:\Users\Public\RPA_ALLIANZ\Data Files\Backoffce\EnvioPDF” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
O processo de envio de pdf é dividido em dois processos, download e envio de e-mail. A coluna “Status” sempre estará com o valor “Processar”, a coluna “Download” alterar o valor para “Processar” e a coluna “email” apagar os dados (o RPA após os downloads ele preenche esta coluna com o valor “Enviar”), coluna “Erro” apagar os dados.
 
Completar os dados de “Processar”, “Enviar” e números da linha até o termino dos dados 
 
Observação Importante: 
Os dados da coluna “FENVIO” é a dada utilizada para encontrar o arquivo á ser enviado na plataforma, estes dados estão sempre personalizados ou no formato data, precisamos alterar o seu tipo para texto, caso a mudança não seja feita a automação não processa os registros
 
Dica: copie e salve os valores da coluna em um arquivo txt, delete os valores da colune e formate-a para texto, utilizando o botão direito do mouse, após a formatação copie as datas do txt para a coluna do documento. 
Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.
 



Seguro Funcionário Auto / Residência
Recebemos os documentos para processar por período de renovação juntamente com uma tabela de datas de execução. 
 
Localizar o documento “RoboRenovAuto.xlsx” e “RoboRenovResidencia.xlsx”no diretório “C:\Users\Public\RPA_ALLIANZ\MassaAuto” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
Obs.: os arquivos são renomados a cada execução, ficando no formato “RoboRenovAuto20-04-2020-21-11-10” para Auto e “RoboRenovResidencia21-04-2020-00-03-43” para Residência, renomear para “RoboRenovAuto” para Auto e “RoboRenovResidencia” . Caso não tenha registro para processar não precisa alterar o nome do arquivo.
Preparar os registros para execução: 
Identificar na aba “Principal” os registros á serem processados, exemplo: 22/04/2020 a data á ser processada é 07/05/2020 
 
 



Copiar os itens para aba “SeguroRenovAuto” para Auto 
 
Preencher as colunas “Status” com o valor “Processar” e “linha“
 
Observe que na última linha tem a informação “Stop” ela é importante para o controle da execução pelo RPA.
Se certifique que as colunas “premioAtual”, “premioAnterior” e “Observações” esteja com a formação para texto. 
A formatação dos registros para Auto e Residência são idênticos.
Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.
 





Recusa de Frota 
 
Salvar a pasta do e-mail no diretório downloads 
Localizar o documento “RecusaDeFrota.xlsx” no diretório “C:\Users\Public\RPA_ALLIANZ\Data Files\Auto\RecusaDeFrotaData” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
Preparar os registros para execução: 
Selecionar os itens selecionados conforme modelo e adiciona-los na planilha de execução.
 
Selecionar a coluna “EXECUTAR” com o valor “x” e apagar os demais dados das colunas “EXECUTADO”,”LOG” e “RESULTADO”.
Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.

 


PROCESSO DE INSPEÇÃO DE RISCO 
 
Salvar a pasta do e-mail no diretório downloads 
Localizar o documento “RoboInspecaoRisco.xlsx” no diretório “C:\Users\Public\RPA_ALLIANZ\MassaAuto” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
Observação Importante: 
Os dados das colunas “FECHA” e “FECHCARGO” são personalizados ou no formato data, precisamos alterar o seu tipo para texto, caso a mudança não seja feita a automação não processa os registros
 
Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.
 



 
