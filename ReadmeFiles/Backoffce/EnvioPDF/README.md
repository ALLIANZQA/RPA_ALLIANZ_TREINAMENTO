# RPA ALLIANZ Envio de PDF Corporate

 
Salvar documento do e-mail no diretório downloads 
Localizar o documento “RoboEnvioPdf.xlsx” no diretório “C:\Users\Public\RPA_ALLIANZ\Data Files\Backoffce\EnvioPDF” e substituir os dados selecionados existentes pelos novos do documento novo conforme modelo: 
 
O processo de envio de pdf é dividido em dois processos, download e envio de e-mail. A coluna “Status” sempre estará com o valor “Processar”, a coluna “Download” alterar o valor para “Processar” e a coluna “email” apagar os dados (o RPA após os downloads ele preenche esta coluna com o valor “Enviar”), coluna “Erro” apagar os dados.
 
Completar os dados de “Processar”, “Enviar” e números da linha até o termino dos dados 
 
Observação Importante: 
Os dados da coluna “FENVIO” é a dada utilizada para encontrar o arquivo á ser enviado na plataforma, estes dados estão sempre personalizados ou no formato data, precisamos alterar o seu tipo para texto, caso a mudança não seja feita a automação não processa os registros
 
Dica: copie e salve os valores da coluna em um arquivo txt, delete os valores da colune e formate-a para texto, utilizando o botão direito do mouse, após a formatação copie as datas do txt para a coluna do documento. 
Execução
Para executar abrir o projeto no Katalon e clicar em executar no menu superior.
 

