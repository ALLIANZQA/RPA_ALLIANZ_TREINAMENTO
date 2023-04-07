package sinistros

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.List

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.ExtractDates
import backoffice.backofficeFuncionalidades
import bean.MassaDados
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import com.kms.katalon.core.testdata.TestData
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


public class RelatorioSinistroDash  extends backofficeFuncionalidades{

	
	public static String massa = 'Criterios_consulta.xlsx'
	private static TestData data
	private static String testDataPath = 'Data Files/Sinistro/RelatorioSinistroDashData'
	private static String path
	public static String camihoDiretorioDownload;
	public static String msgError
	public static String dataRecuperada
	public static String diretorio;
	public static String nomeArquivo;
	public static String processados
	private static int dias
	public static String arquivoJson
	private static String criterio
	private static String arquivoTXT
	private static String caminho_arquivo_xlsx = 'Z:\\template_excel\\Criterios_consulta.xlsx'
	
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}
	
	
	
	public static fluxoSinistroDash(String tipoAba){
		println dataRecuperada
		fluxoCompletoSinistroDash(tipoAba)
		finalizar();
	}

	/**
	 * @author Dvlopes
	 * @param tipoAba
	 * @return
	 * Fluxo completo Sinistro DASH
	 */
	public static fluxoCompletoSinistroDash(String tipoAba){
		initDataFiles()
		try{
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				if(data.getValue(7, i).equals('x')){
				   System.err.println('\n\n['+i+"] Tipo Extração  ["+ data.getValue(1, i)+"] Periodo (Dias) ["+ data.getValue(2, i)+"] Filtro Critério ["+ data.getValue(3, i)+"] Usuário ["+ data.getValue(4, i)+"] Nome Arquivo JSON ["+ data.getValue(5, i)+"] Nome Arquivo TXT ["+ data.getValue(6, i)+"] Executar  ["+ data.getValue(7, i)+']\n\n')
				   dataRecuperada = ''
				   msgError = ''
				   dataRecuperada = getDateHourGenerico("dd/MM/yyyy HH:mm:ss");
				   arquivoJson = data.getValue(5, i)
				   dias = Integer.parseInt(data.getValue(2, i))
				   criterio = data.getValue(3, i)
				   arquivoTXT = data.getValue(6, i)
				   iniciaFluxo(tipoAba);
				   fluxoDash(tipoAba);
				   limpaDiretorio();
				   if(msgError.contains('Não há registros'.toString())){
					  auxiliares.WriteExcelFactory.updateSheetLine('', i-1 , caminho_arquivo_xlsx, msgError +' - '+dataRecuperada, 7, tipoAba);
				   } else {
					  auxiliares.WriteExcelFactory.updateSheetLine('', i-1 , caminho_arquivo_xlsx, 'Relatório gerado - '+dataRecuperada, 7, tipoAba);
				   }
				}
			}
		} catch (Exception e){
	  }
	}

	/**
	 * Edição de filtros / download do arquivo / unzip  e finalização
	 * @author Dvlopes
	 * @param listaExcel
	 * @return
	 */
	public static void fluxoDash(String tipoAba){
		try{
			editaFiltros()
			downloadUnzip();
		} catch (Exception e) {
				System.err.println('<<'+e+'>>')
		
		//	iniciaFluxo(tipoAba)
		}
	}

	/**Inicia aplicação e loga no sitema CRIVO INTRANET
	 * @author T_Dvlopes
	 *
	 */
	public static void iniciaFluxo(tipoAba){
		iniciaAplicacaoMatriz('IntranetSinistro');
		realizaLoginCrivoIntraNet(tipoAba);
	}

	/**
	 * Fluxo completo de edição de filtro
	 * @author T_Dvlopes
	 */
	public static void editaFiltros(){
		fluxoEditarFiltros();
		fluxoConfig();
		fluxoEditarColunas();
	}

	/**
	 * Fluxo Completo download & descompactação
	 * @author T_Dvlopes
	 *
	 */
	public static void downloadUnzip(){
		fluxoDownload();
		fluxoDescompacta();
	}

	/**
	 * @author T_Dvlopes
	 * Altera todos os campos para igual
	 * Option 1 = igual / Option 2 = Diferente
	 *
	 */
	public static void listaIgual(){
		clickXpath('//*/filtro[1]/div/div[2]/div/div/div[2]/select', 5);
		clickXpath('//*/filtro[1]/div/div[2]/div/div/div[2]/select/option[1]',2);

		clickXpath('//*/filtro[2]/div/div[2]/div/div/div[2]/select' ,2);
		clickXpath('//*/filtro[2]/div/div[2]/div/div/div[2]/select/option[1]',2);

		clickXpath('//*/filtro[4]/div/div[2]/div/div/div[2]/select' ,2);
		clickXpath('//*/filtro[4]/div/div[2]/div/div/div[2]/select/option[1]',2);

		clickXpath('//*/filtro[5]/div/div[2]/div/div/div[2]/select',2);
		clickXpath('//*/filtro[5]/div/div[2]/div/div/div[2]/select/option[1]',2);

		clickXpath('//*/filtro[6]/div/div[2]/div/div/div[2]/select',2);
		clickXpath('//*/filtro[6]/div/div[2]/div/div/div[2]/select/option[1]',2);

		clickXpath('//*/filtro[7]/div/div[2]/div/div/div[2]/select',2);
		clickXpath('//*/filtro[7]/div/div[2]/div/div/div[2]/select/option[1]',2);

		clickXpath('//*/div[2]/div/filtro-campo/div/div[1]/div[2]',2);
		clickXpath('//*/div/filtro-campo/div/div[1]/div[2]/select/option[1]',2);
	}

	/**
	 * @author T_Dvlopes
	 *
	 * 	//ACESSAR O MENU LATERAL ESQUERDO /EXTRAÇÕS > OPERAÇÕES
	 //3.1 CÓDIGO DA OPERAÇÃO: (NÃO PREENCHER)
	 //3.2 PERÍODO (CALENDÁRIO)  // 28/11/2019 23:59:59
	 //3.3 SERVIDORES: (NÃO PREENCHER)
	 //3.4 CRITÉRIOS: POLÍTICA SINISTRO AUTO AVISO
	 //3.5 GRUPOS: (NÃO PREENCHER)
	 //3.6 USUÁRIOS: (NÃO PREENCHER)
	 //3.7 RESPOSTAS: (NÃO PREENCHER)
	 //3.8 SISTEMAS: (NÃO PREENCHER)
	 //3.9 CAMPO: (NÃO PREENCHER)
	 //3.10 TIPO DE EXECUÇÃO DE OPERAÇÕES: (NÃO PREENCHER)
	 //3.11 EXISTE UM SUB FILTRO EM CADA LINHA (COMBO DE SELEÇÃO): SELECIONAR (Igual)
	 */
	public static void fluxoEditarFiltros(){
		//ACESSAR O MENU LATERAL ESQUERDO /EXTRAÇÕS > OPERAÇÕES
		clickXpath('//sidebar/div[1]/div/ul/li[2]/a', 10);
		//3.2
		limpaCampo('//*/filtros-extracao/fieldset/div[1]/div/input',5);
		dataRecuperada = ExtractDates.retornaDataInferior(dias)+' '+'00:00:00'+' - '+dataRecuperada
		System.err.println('\ndataRecuperada ['+dataRecuperada+']\n')

		preencheXpath('//*/filtros-extracao/fieldset/div[1]/div/input', dataRecuperada, 2);

		commandTAB('//*/filtros-extracao/fieldset/div[1]/div/input');
		//3.4
		selecionaCriteirios(criterio);
		limparUsuarios()
		
		//3.11
		listaIgual();
	}

	/**
	 * @author T_Dvlopes
	 *
	 //4.1 IDENTIFICAÇÃO: (NOME PREENCHIDO AUTOMATICAMENTE PELO SISTEMA)
	 //4.2 SEPARAR EM VÁRIOS ARQUIVOS (CAIXA DE SELEÇÃO): NÃO SELECIONAR
	 //4.3 GRAVAR ARQUIVO DE SAÍDA EM DISCO (CAIXA DE SELEÇÃO): NÃO SELECIONAR
	 //4.4 FORMATO DATA (COMBO DE SELEÇÃO): SELECIONAR FORMATO (yyyy-MM-dd HH:mm:ss (2016-01-25 23:59:59))
	 //4.5 FUSO HORÁRIO (COMBO DE SELEÇÃO): SELECIONAR (Fuso horário do servidor)
	 //4.6 FORMATO LÓGICO (COMBO DE SELEÇÃO): SELECIONAR (True/False)
	 //4.7 FORMATO NÚMERICO (COMBO DE SELEÇÃO): SELECIONAR (2 casas decimais)
	 //4.8 UTILIZAR VÍRGULA COMO SEPARADOR DECIMAL (CAIXA DE SELEÇÃO): SELECIONAR
	 */
	public static void fluxoConfig(){
		//Clicar Configurações
		clickXpath('//*[@id="headingTwo"]/h4/a/span', 2);
		//4.4
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[1]/select',2);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[1]/select/option[2]', 2);
		//4.5
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[2]/select', 2);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[2]/select/option[1]', 3);
		//4.6
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[8]/div/select',3);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[8]/div/select/option[1]', 2);
		//4.7
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[10]/div[1]/select',2);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[10]/div[1]/select/option[3]',2);
		//4.8
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[10]/div[2]/div[1]/label/input',2);
	}

	/**
	 *  @author T_Dvlopes
	 *
	 * //5.1 CONFIGURAR COLUNAS DO ARQUIVO DE SAÍDA (CAIXA DE SELEÇÃO): SELECIONAR
	 //5.1.1 FORMATO DO CABEÇALHO (COMBO DE SELEÇÃO): SELECIONAR (Formato Crivo 4.5)
	 //5.1.2 UTILIZAR ARQUIVO DE CONFIGURAÇÃO: ESCOLHER ARQUIVO (OPÇÃO PARA SELECIONAR UM ARQUIVO NA REDE COM LAYOUT JÁ DEFINIDO DE COLUNAS).
	 //5.1.2.1 ACESSAR A PASTA DE REDE PARA INSERIR O ARQUIVO COM LAYOUT JÁ DEFINIDO.
	 //CAMINHO DE REDE: \\ntxnas1.intrallianz.es\Brasil\DIRETORIAS\DIR EXE SINISTROS\DESENV_ESTRATEGICO\BUSINESS_INTELLIGENCE\Relatorios_Gerenciais\Projetos\Rotinas\Diario\Operacoes_Crivo\config_filtro
	 //OBS.: PASTA DE REDE DEPARTAMENTAL (SOLICITAR LIBERAÇÃO DE ACESSO)
	 //5.1.2.2 SELECIONAR ARQUIVO DE LAYOUT: (colunas_dash.json)
	 //(O SISTEMA IRÁ SELECIONAR OS FILTROS DE COLUNAS AUTOMATICAMENTE COM BASE NO ARQUIVO INSERIDO).
	 //5.1.3 PESQUISAR: (NÃO PREENCHER – “Vazio”)
	 //5.1.4 COMBO DE SELEÇÃO: (Selecionadas / Não Selecionadas);
	 //5.1.5 COMBO DE SELEÇÃO: (“Vazio”);
	 //5.1.6 INICIAR (BOTÃO): CLICAR
	 */
	public static void fluxoEditarColunas(){
		// COLUNAS
		clickXpath('//*[@id="headingThree"]/h4/a',5);
		
		//5.1
		clickXpath('//*[@id="collapseThree"]/div/div[1]/div/div/label/input',5);
		WebUI.delay(40)
		
		//5.1.1
		clickXpath('//*[@id="collapseThree"]/div/div[2]/div[2]/select',25);
		clickXpath('//*[@id="collapseThree"]/div/div[2]/div[2]/select/option[1]',5);
		
		//5.1.2.2
		WebUI.delay(5)
		fileUpload('//*[@id="collapseThree"]/div/div[3]/div[2]/customizacao-colunas/div[1]/div/div/input','Z:\\json_entrada\\'+arquivoJson+'.json', 2);
		//5.1.4
		clickXpath('//*[@id="collapseThree"]/div/div[3]/div[2]/customizacao-colunas/div[4]/div[2]/select',2);
		clickXpath('//*[@id="collapseThree"]/div/div[3]/div[2]/customizacao-colunas/div[4]/div[2]/select/option[1]',2);
		//5.1.5
		clickXpath('//*[@id="btn-iniciar"]', 5);
	}




	/**
	 * @author T_Dvlopes
	 *
	 * 		String valorExtracao
	 //		6.1 ACESSAR O MENU LATERAL ESQUERDO
	 //		EXTRAÇÕS > HISTÓRICO
	 //		6.2 FILTROS GERAIS: NÃO HÁ NECESSIDADE DE CONFIGURAÇÃO
	 //		6.3 IDENTIFICAÇÃO DA EXTRAÇÃO
	 //		6.3.1 CÓDIGO DA EXTRAÇÃO
	 //		6.4 DOWNLOAD EXTRAÇÃO (BOTÃO): CLICAR (ESSE BOTÃO SÓ FICA DISPONÍVEL APÓS A CONCLUSÃO DO DOWNLOAD, OU SEJA, DEVE-SE AGUARDAR <XX SEGUNDOS> PARA ESSA AÇÃO)
	 //		OBS.: DEVERÁ FAZER O DOWNLOAD DA EXTRAÇÃO MAIS RECENTE, OU SEJA, A PRIMEIRA DA LISTA.
	 //		PODE SER USADO O CAMPO 4.1 IDENTIFICAÇÃO COM O CAMPO 6.2 IDENTIFICAÇÃO DA EXTRAÇÃO PARA VALIDAR SE É O MESMO ARQUIVO.
	 //		6.5 ARQUIVO BAIXADO (O ARQUIVO BAIXADO VAI AUTOMATICAMENTE PARA A PASTA DE DOWNLOADS DO USUÁRIO SOLICITANTE).
	 */
	public static void fluxoDownload(){
		
		//		6.3.1
		nomeArquivo = buscaNomeArquivo();
		camihoDiretorioDownload = '‪C:\\Users\\Public\\extracao_operacoes_'+nomeArquivo+'.zip';
		limpaDiretorio();
		esperaDownload();

		//		6.1
		clickXpath('/html/body/main/div[2]/div[2]/ng-view/div/div[2]/div[7]/div[1]/div/div[3]/div/a[1]',5);

		//6.4 DOWNLOAD EXTRAÇÃO (BOTÃO): CLICAR (ESSE BOTÃO SÓ FICA DISPONÍVEL APÓS A CONCLUSÃO DO DOWNLOAD, OU SEJA, DEVE-SE AGUARDAR <XX SEGUNDOS> PARA ESSA AÇÃO)
		//		OBS.: DEVERÁ FAZER O DOWNLOAD DA EXTRAÇÃO MAIS RECENTE, OU SEJA, A PRIMEIRA DA LISTA.
		//		PODE SER USADO O CAMPO 4.1 IDENTIFICAÇÃO COM O CAMPO 6.2 IDENTIFICAÇÃO DA EXTRAÇÃO PARA VALIDAR SE É O MESMO ARQUIVO.
		//		6.5 ARQUIVO BAIXADO (O ARQUIVO BAIXADO VAI AUTOMATICAMENTE PARA A PASTA DE DOWNLOADS DO USUÁRIO SOLICITANTE).
	}




	/**
	 * @author T_Dvlopes
	 *
	 *
	 //	7 – SALVANDO O ARQUIVO BAIXADO
	 //	7.1 ABRIR PASTA NA REDE E ABRIR O ARQUIVO “extracao_operacoes_<CÓDIGO DA EXTRAÇÃO>.zip”
	 //	CAMINHO DE REDE: \Downloads
	 //	OBS.: O ARQUIVO BAIXADO É UM .txt E VEM ZIPADO.
	 //	7.2 SELECIONAR O ARQUIVO “001.txt”
	 //	7.3 COPIAR PARA
	 //	7.4 INSERIR CAMINHO DE REDE
	 //	CAMINHO DE REDE: \\ntxnas1.intrallianz.es\Brasil\DIRETORIAS\DIR EXE SINISTROS\COMPART_INTERNO\RELATORIOS\bd\dash_crivo
	 //	7.5 OK (BOTÃO): CLICAR
	 */
	public static void fluxoDescompacta(){
		descompacta();
		WebUI.delay(10)
		moveArquivo('C:\\Users\\Public\\001.txt', "Z:/")
		WebUI.delay(5)
		deletaArquivoGenerico('Z:\\'+arquivoTXT+'.txt');
		WebUI.delay(5)
		renomearArquivo('Z:\\001.txt', arquivoTXT)
	}

	/**
	 * Procura nome da operação
	 * @Autor Dvlopes
	 * @return
	 */
	public static String buscaNomeArquivo(){
		String valorExtracao
		String nomeArquivo
		String qtdArquivo
		WebUI.delay(10);
		String xpath = '/html/body/main/div[2]/div[2]/ng-view/div/div[2]/div[7]'
		String tag ="h4";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag)
		for(int i=0; i<listOfElements.size(); i++){
			valorExtracao = recuperaValor('/html/body/main/div[2]/div[2]/ng-view/div/div[2]/div[7]/div['+i+']/h4', 'textContent', 0);
			if(!valorExtracao.equals('atributo nao encontrado')){
				nomeArquivo = codBuilder(valorExtracao);
				qtdArquivo = recuperaValor('/html/body/main/div[2]/div[2]/ng-view/div/div[2]/div[7]/div['+i+']/div/div[2]/div', 'outerText', 0);
				if(!qtdArquivo.contains('Não há registros 0 processados de 0 (0%)')){
					processados = recuperaValor('/html/body/main/div[2]/div[2]/ng-view/div/div[2]/div[7]/div[1]/div/div[2]/div/acompanhamento-extracao', 'innerText', 0);
					println '[ '+processados+' ]'
					return nomeArquivo;
				}else{
					return nomeArquivo;
				}
			}
		}
		println 'Nome Arquivo [ '+nomeArquivo+' ]'
		return nomeArquivo;
	}

	/**
	 * @autor Dvlopes
	 * Antes de finalizar download verifica se existe arquivo na base;
	 * Caso exista apaga o arquivo
	 */
	public static void limpaDiretorio(){
		deletaArquivoGenerico('C:\\Users\\Public\\001.txt');
		deletaArquivoGenerico('C:\\Users\\Public\\config.json');
		deletaArquivoGenerico('C:\\Users\\Public\\filtro.json');
	}

	/**
	 * @autor Dvlopes
	 * Realiza a descompactação do arquivo, Tenta localizar e descompatar arquivo 3 vezes
	 */
	public static void descompacta(){
		System.err.println('camihoDiretorioDownload [ C:\\Users\\Public\\extracao_operacoes_'+nomeArquivo+'.zip ]')
		WebUI.delay(30)
		try{
			String roperties = System.getProperties()
			WebUI.delay(5)
			if(roperties.contains('Windows 10'.toString())){
				descompactaArquivo('C:\\Users\\Public\\extracao_operacoes_'+nomeArquivo+'.zip',"C:\\Users\\Public\\")
			} else if (roperties.contains('Windows 7'.toString())){
				unzip('C:\\Users\\Public\\extracao_operacoes_'+nomeArquivo+'.zip','C:\\Users\\Public\\')
			} else {
				descompactaArquivo('C:\\Users\\Public\\extracao_operacoes_'+nomeArquivo+'.zip','C:\\Users\\Public\\')
			}


			WebUI.delay(5)
			deletaArquivoGenerico('C:\\Users\\Public\\extracao_operacoes_'+nomeArquivo+'.zip')
		} catch (Exception e){
			//			finalizar()   Windows Server 2016
		}
	}

	/**
	 * @autor Dvlopes
	 * Wating PROGRESS BAR 100%
	 * Enquanto downloadOk = true, download ainda não esta completo.
	 */
	public static void esperaDownload(){
		while(!recuperaValorProgressBar()){
			WebUI.delay(2);
			recuperaValorProgressBar()
		}
	}

	/**
	 * Verifica andamento do ProgressBar
	 * Quando chegar a 100% retorna boolean [FALSE]
	 * @autor Dvlopes
	 *
	 * @return
	 */
	public static boolean recuperaValorProgressBar(){
		String valorProgressBar = recuperaValor('/html/body/main/div[2]/div[2]/ng-view/div/div[2]/div[7]/div[1]/div/div[2]/div/acompanhamento-extracao', 'textContent', 0);
		println '[ '+valorProgressBar+' ]'
		if(valorProgressBar.contains('100%'.toString())){
			return true;
		}else if (valorProgressBar.contains('Não há registros'.toString())){
			return true;
			msgError = 'Não há registros'
		}else {
			return false;
		}
	}

	/**
	 * Limpa a string e retorna apenas o valor da operação
	 * Retornando [ 280059 ]
	 * @author T_Dvlopes
	 * @param valor
	 * @return
	 */
	public static String codBuilder(String valor) {
		StringBuilder build = new StringBuilder();
		String valorPadronizado = valor.replaceAll("\\s+" , " ").replaceAll("-" , " ").replaceAll("/" , " ").replaceAll(" " , "").trim();
		String [] vertor = valorPadronizado.split('');
		for(int i = 0; i<vertor.size(); i++){
			if(vertor[i].trim().toString().equals(']')){
				break;
			}
			if(!vertor[i].trim().toString().equals('[') && !vertor[i].trim().toString().equals('#')){
				build.append(vertor[i])
			}
		}
		println 'codBuilder - String Limpada ['+build.toString()+']';
		return build.toString();
	}
	
	/**
	 * @author BE53298
	 */
	public static limparUsuarios(){
		WebUI.delay(10);
		String xpath = '//*[@id="collapseOne"]/div/filtros-extracao/fieldset/filtro[5]/div/div[2]/div/div/div[1]/div'
		String tag ="span";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag)
		for(int i = listOfElements.size()  ; i > 0  ; i-- ){
			String valor =Integer.toString(i)
			System.err.println(valor)
			try{
				 clickXpath('//*[@id="collapseOne"]/div/filtros-extracao/fieldset/filtro[5]/div/div[2]/div/div/div[1]/div/div/span/span['+valor+']/span/span[1]',2);
			} catch (Exception e){}
		}
	}
	
	
	/**
	 * Renomeia arquivo recebendo o PATH e o nome de saida
	 * @param pathFileValue
	 * @param namePdf
	 * @return
	 */
	public static renomearArquivo(String pathFileValue, String name){
		File f = new File(pathFileValue);
		if (f.renameTo(new File("Z:\\"+name+".txt")))
		{
			System.out.println("Arquivo renomeado");
		}
		else{
			System.out.println("Arquivo renomeado");
		}

	}
	
	
}
