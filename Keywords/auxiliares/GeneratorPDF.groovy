package auxiliares

import java.text.SimpleDateFormat

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import bean.CasoDeTeste
import bean.Cobertura
import bean.Pacote

import rpacommands.rpaCommands


public class GeneratorPDF {

	private static Font blackFont = new Font(Font.FontFamily.HELVETICA, 8,Font.BOLD, BaseColor.BLACK);
	private static Font smallList = new Font(Font.FontFamily.HELVETICA, 7);
	private static Font smallListBlack = new Font(Font.FontFamily.HELVETICA, 6);
	private static Font smallBoldBlack = new Font(Font.FontFamily.HELVETICA, 6,Font.BOLD);
	private static Font blueFont = new Font(Font.FontFamily.HELVETICA, 8,Font.BOLD, BaseColor.BLUE);
	private static Font blueFontSmall = new Font(Font.FontFamily.HELVETICA, 6,Font.BOLD, BaseColor.BLUE);
	private static Font whiteFontSmall = new Font(Font.FontFamily.HELVETICA, 6,Font.BOLD, BaseColor.WHITE);
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL, BaseColor.RED);
	private static Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL, BaseColor.GREEN);
	private static Font greenFontFontSmall = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL, BaseColor.GREEN);
	private static Font smmallRedFont = new Font(Font.FontFamily.HELVETICA, 6,Font.NORMAL, BaseColor.RED);
	private static Font smmallGreenFont = new Font(Font.FontFamily.HELVETICA, 6,Font.NORMAL, BaseColor.GREEN);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
	private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 10);
	private static Font smallEvidence = new Font(Font.FontFamily.TIMES_ROMAN, 9);
	private static StringBuffer strbffr
	private static Document document
	protected static nomePDF
	private static int noPages = 2
	protected static fabrica = 'Inmetrics'
	protected static testador = 'Automação'
	protected static projeto = "Quality Assurance"
	protected static codigoProjeto = "BDM-1169"
	def x = 'anish\044'


	/**
	 * @author Luiz André
	 * @param pacote1
	 * @param pacote2
	 * @return
	 *
	 * Cria a evidencia em PDF para o projeto RadarLive com formatação de fonte e cores
	 */

	public static evidenciaRadarLive(Object pacote1, Object pacote2, int teste, String cenario){

		try{
			CasoDeTeste validacao1 = pacote1;
			CasoDeTeste validacao2 = pacote2;

			adicionaCenario(validacao2.getStatus(), cenario+" - Execução "+teste )

			PdfPTable table = new PdfPTable(7);
			table.setHeaderRows(1);
			table.getDefaultCell().setBorder(1)

			table.setHorizontalAlignment(Element.ALIGN_CENTER)
			table.addCell(new Paragraph("Corretor", blueFont))
			table.addCell(new Paragraph("Ampliado 2", blueFont))
			table.addCell(new Paragraph("Compacto 1", blueFont))
			table.addCell(new Paragraph("Compacto 2", blueFont))
			table.addCell(new Paragraph("Ampliado 1", blueFont))
			table.addCell(new Paragraph("Especial ", blueFont))
			table.addCell(new Paragraph("Exclusivo ", blueFont))

			table.addCell(new Paragraph(validacao1.getUsuario(), blackFont))
			table.addCell(new Paragraph(validacao1.getPacoteAmpliado2(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteCompacto1(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteCompacto2(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteAmpliado1(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteEspecial(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteExclusivo(),smallList))

			table.addCell(new Paragraph(validacao2.getUsuario2(), blackFont))
			table.addCell(new Paragraph(validacao2.getPacoteAmpliado2(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteCompacto1(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteCompacto2(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteAmpliado1(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteEspecial(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteExclusivo(),smallList))


			document.add(table);
		} catch (Exception e) {
			System.err.println("evidenciaRadarLive - Falha na geração de evidência ["+e+"]")
			rpaCommands.finalizar()
		}
	}


	public static evidenciaRadarLiveMoto(Object pacote1, Object pacote2, int teste, String cenario){

		try{
			CasoDeTeste validacao1 = pacote1;
			CasoDeTeste validacao2 = pacote2;

			adicionaCenario(validacao2.getStatus(), cenario+" - Execução "+teste )

			PdfPTable table = new PdfPTable(6);
			table.setHeaderRows(1);
			table.getDefaultCell().setBorder(1)

			table.setHorizontalAlignment(Element.ALIGN_CENTER)
			table.addCell(new Paragraph("Corretor", blueFont))
			table.addCell(new Paragraph("Ampliado 2", blueFont))
			table.addCell(new Paragraph("Compacto 1", blueFont))
			table.addCell(new Paragraph("Compacto 2", blueFont))
			table.addCell(new Paragraph("Ampliado 1", blueFont))
			//table.addCell(new Paragraph("Especial ", blueFont))
			table.addCell(new Paragraph("Exclusivo ", blueFont))

			table.addCell(new Paragraph(validacao1.getUsuario(), blackFont))
			table.addCell(new Paragraph(validacao1.getPacoteAmpliado2(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteCompacto1(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteCompacto2(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteAmpliado1(),smallList))
			table.addCell(new Paragraph(validacao1.getPacoteExclusivo(),smallList))

			table.addCell(new Paragraph(validacao2.getUsuario2(), blackFont))
			table.addCell(new Paragraph(validacao2.getPacoteAmpliado2(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteCompacto1(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteCompacto2(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteAmpliado1(),smallList))
			table.addCell(new Paragraph(validacao2.getPacoteExclusivo(),smallList))


			document.add(table);
		} catch (Exception e) {
			System.err.println("evidenciaRadarLive - Falha na geração de evidência ["+e+"]")
			rpaCommands.finalizar()
		}
	}

	
	public static void cabecalhoDeclaracaoCoCorretagem(String c){
		PdfPTable table = new PdfPTable(1);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(1)
		table.setHorizontalAlignment(Element.ALIGN_CENTER)
		table.addCell(new Paragraph(c, blueFontSmall))
		document.add(table);
	}
	
	
	
	/**
	 * @author T_DCDias
	 * @param listaPacote
	 */
	public static void evidenciaRadarLiveWebService(List<Pacote> listaPacote){

		PdfPTable table = new PdfPTable(9);
		table.setHeaderRows(1);
		table.setWidthPercentage(100.0)
		table.getDefaultCell().setBorder(1)

		table.setHorizontalAlignment(Element.ALIGN_CENTER)
		table.addCell(new Paragraph("Cod", blueFontSmall))
		table.addCell(new Paragraph("Descrição", blueFontSmall))
		table.addCell(new Paragraph("Form. Pagmto.", blueFontSmall))
		table.addCell(new Paragraph("Qtd. Parc.", blueFontSmall))
		table.addCell(new Paragraph("Vlr. 1 Parc.", blueFontSmall))
		table.addCell(new Paragraph("Total Parc.", blueFontSmall))
		table.addCell(new Paragraph("Total Parc. Unic.", blueFontSmall))
		table.addCell(new Paragraph("Vlr. IOF. ", blueFontSmall))
		table.addCell(new Paragraph("Vlr. Ad. Frac. ", blueFontSmall))

		for(int i=0; i < listaPacote.size(); i++){

			if(listaPacote[i].getFormaPagamento().toString() == "1" && listaPacote[i].getQuantidadeParcelas().toString() == "1"){

				table.addCell(new Paragraph(listaPacote[i].getCodigoPacote(), smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getDescricaoPacote(),smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getFormaPagamento(),smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getQuantidadeParcelas(),smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getValorPrimeiraParcela(),smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getValorTotalParcelado(),smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getValorTotalParcelaUnica(),smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getValorIof(),smallListBlack))
				table.addCell(new Paragraph(listaPacote[i].getValorAdicionalFracionamento(),smallListBlack))
			}
		}
		document.add(table);
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static createTitlePage(){
		addTitlePage()
	}

	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePage(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		/*	String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		 String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
		 */
		String data = new SimpleDateFormat("dd-MM-yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footerw.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);

		//PdfWriter.getInstance(document, new FileOutputStream(path+"//"+ct+"_Data-"+data+"_Horario-"+hora+".pdf"));

		return document
	}


	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageMoto(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footerwmoto.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}

	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageCaminhao(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footerwcaminhao.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}

	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageResidenciaTradicional(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzresidenciatradicional.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);
		addEmptyLine(preface, 4);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageSaude(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-vidasaude-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzvidaesaude.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);
		addEmptyLine(preface, 4);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	
	
	
	public static Document addTitlePageVida(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-vidasaude-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzvida.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);
		addEmptyLine(preface, 4);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}

	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageResidenciaRC(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzresidenciarc.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	
	
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageTransporteCargoImport(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianztransportescargoimport.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageRcGeralPme(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzrcgeralpme.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}


	
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageEquipamentos(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzequipamentos.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}


	
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageNautica(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianznautica.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageResidenciaRE(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzresidenciare.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}



	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageEmpresa(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzempresarial.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}

	
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitleAgricola(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzagricola.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitleEquipamentoAgricola(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzequipamentoagricola.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePropriedadesRurais(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzpropriedadesrurais.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document addTitlePageCondominioSimples(){
		Image img = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//seguro-auto-allianz-2.jpg");
		img.scaleAbsolute(180f, 55f);

		document.add(img);
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 4);
		preface = new Paragraph("Evidência de Teste", catFont);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);

		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_LEFT)
		table.addCell(new Paragraph("Cód. Projeto: ", smallBold))
		table.addCell(new Paragraph(codigoProjeto, small))
		table.addCell(new Paragraph("Projeto: ",smallBold))
		table.addCell(new Paragraph(projeto,small))
		table.addCell(new Paragraph("Fábrica: ", smallBold))
		table.addCell(new Paragraph(fabrica, small))
		table.addCell(new Paragraph("Data: ",smallBold))
		table.addCell(new Paragraph(data,small))
		table.addCell(new Paragraph("Testador: ",smallBold))
		table.addCell(new Paragraph(testador,small))
		table.addCell(new Paragraph("Execução: ",smallBold))
		table.addCell(new Paragraph(hora,small))

		document.add(preface);
		document.add(table);

		Image imgBarr = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//barr.jpg")
		imgBarr.scaleToFit(500, 500)
		document.add(imgBarr);

		Image imgFooterw = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//allianzcondominiosimples.jpg")
		imgFooterw.scaleToFit(500, 500)
		document.add(imgFooterw);

		Image imgFooter = Image.getInstance("C://Users//Public//QA_ALLIANZ//Images//footer.jpg")
		imgFooter.scaleToFit(500, 500)
		document.add(imgFooter);
		return document
	}

	

	/**
	 * @author T_DCDias
	 * @param paragraph
	 * @param number
	 */
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private static void addEmptyLineExterno(int number) {
		Paragraph paragraph = new Paragraph();
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
			document.add(paragraph);
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static createDocument(String ct, String path){
		document = new Document();
		try {
			Date dataHoraAtual = new Date();
			String data = new SimpleDateFormat("ddMMyyyy").format(dataHoraAtual);
			String hora = new SimpleDateFormat("HHmmss").format(dataHoraAtual);
			nomePDF = ct+"_"+data+"_"+hora+".pdf"
			PdfWriter.getInstance(document, new FileOutputStream(path+"//"+ct+"_"+data+"_"+hora+".pdf"));
			document.open(); System.err.println("-[document Opened]-")
		} catch(DocumentException de) {
			closeDocument()
			System.err.println(de.getMessage());
		} catch(IOException ioe) {
			closeDocument()
			System.err.println(ioe.getMessage());
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static closeDocument(){
		System.err.println("-[document closed]-")
		document.close();
		document.finalize()
	}

	/**
	 * @author T_DCDias
	 * @param im
	 * @return
	 */
	public static adicionarEvidencia(Image im){
		document.add(im);
	}

	/**
	 * @author T_DCDias
	 * @param im
	 * @return
	 * Pega valores do arquivo pdf de cotação e adiciona como log na evidência
	 */
	public static adicionarEvidenciaContentPDF(String im){
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface = new Paragraph(im, smallEvidence);
		preface.setAlignment(Element.ALIGN_LEFT);
		document.add(preface);
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static adicionaCenario(String status, String msg){
		Paragraph prefaceCenario = new Paragraph();
		addEmptyLine(prefaceCenario, 5);
		prefaceCenario.add(new Paragraph("Cenário: ", smallBold));
		prefaceCenario.add(new Paragraph(msg, small));
		prefaceCenario.add(new Paragraph(status, corDaFonte(status)));
		document.add(prefaceCenario);
	}

	
	

	/**
	 * @author T_DCDias
	 * @param str
	 * @return
	 */
	public static Font corDaFonte(String str){
		Font font;
		if(str.equals("PASSED")){
			font= greenFont
		}else if (str.equals("FAIL")) {
			font= redFont
		}
		return font
	}

	/**
	 * @author T_DCDias
	 * @param str
	 * @return
	 */
	public static Font corDaFonteSmall(String str){
		Font font;
		if(str.equals("PASSED")){
			font= smmallGreenFont
		}else if (str.equals("FAIL")) {
			font= smmallRedFont
		}
		return font
	}

	/**
	 * @author T_DCDias
	 * @param pathFileValue
	 * @return
	 */
	public static String pdfReader(String pathFileValue){
		strbffr = new StringBuffer()
		String text
		try{
			for(int i=0; i<noPages; i++){
				PdfReader pdfReaderFiles= new PdfReader(pathFileValue);
				int j = i+1
				text = PdfTextExtractor.getTextFromPage(pdfReaderFiles, j)
				strbffr.append(text).toString()
				pdfReaderFiles.close()
				pdfReaderFiles.finalize()
			}
			return strbffr
		} catch (Exception e){
			System.err.println("pdfReader - Falha ao ler arquivo ["+e+"]")
			rpaCommands.finalizar()
		}
	}

	/**
	 * @author T_DCDias
	 * @param pathFileValue
	 * @return
	 */
	public static String pdfReader(String pathFileValue, int pages){
		strbffr = new StringBuffer()
		String text
		try{
			for(int i=0; i<pages; i++){
				PdfReader pdfReaderFiles= new PdfReader(pathFileValue);
				int j = i+1
				text = PdfTextExtractor.getTextFromPage(pdfReaderFiles, j)
				strbffr.append(text).toString()
				pdfReaderFiles.close()
				pdfReaderFiles.finalize()
			}
			return strbffr
		} catch (Exception e){
			System.err.println("pdfReader - Falha ao ler arquivo ["+e+"]")
			rpaCommands.finalizar()
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Extrai os valores do arquivo pdf
	 */
	public static String takePDFContent(String pathFileValue ){
		strbffr = new StringBuffer()
		String text
		try{
			for(int i=0; i<noPages; i++){
				PdfReader pdfReaderFiles = new PdfReader(pathFileValue);
				int j = i+1
				text = PdfTextExtractor.getTextFromPage(pdfReaderFiles, j)
				strbffr.append("\n"+text+"\n\n").toString()
				pdfReaderFiles.finalize()
				pdfReaderFiles.close()
			}
			return strbffr
		} catch (Exception e){
			System.err.println("takePDFContent - Falha ao ler arquivo ["+e+"]")
			rpaCommands.finalizar()
		}
	}

	/**
	 * @author T_DCDias
	 * @throws IOException
	 * Altera nome do arquivo
	 */
	public static void renamingFiles() throws IOException {
		FileUtils.moveFile(
				FileUtils.getFile("C://Users//Public//retrievedoc.pdf"),
				FileUtils.getFile("C://Users//Public//retrievedoc_old.pdf"));
	}

	/**
	 * @author Inmetrics
	 * @param pacote1
	 * @param pacote2
	 * @param teste
	 * @param cenario
	 * @return
	 */
	public static evidenciaRadarLivePDF(String user1, String user2, List<Pacote> pacotesUm, List<Pacote> pacotesDois, int teste, String cenario, boolean status, List<Map<String, String>> issue_info){
		try{
			if(status){
				adicionaCenario('PASSED', issue_info.get(0).get("Cenário"))
			}else {
				adicionaCenario('FAIL', issue_info.get(0).get("Cenário"))
			}
			adicionaNomesPacotes(user1, issue_info, pacotesUm)
			adicionaNomesPacotes(user2, issue_info, pacotesDois)
		} catch (Exception e) {
			System.err.println("evidenciaRadarLive - Falha na geração de evidência \n["+e+"]")
			rpaCommands.finalizar()
		}
	}

	/**
	 * @author Inmetrics
	 * @param user
	 * @param issue_info
	 * @return
	 */
	public static adicionaNomesPacotes(String user, List<Map<String, String>> issue_info, List<Pacote> pacotes){
		adicionarIdentificadorRadarLivePDF(user)
		PdfPTable table = new PdfPTable(7);
		table.setHeaderRows(2);
		table.setWidthPercentage(100.0)
		table.getDefaultCell().setBorder(1)
		table.getDefaultCell().setBorderWidthLeft(1)
		table.getDefaultCell().setBorderWidthRight(1)
		table.getDefaultCell().setBorderWidthTop(1)
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE)
		table.getDefaultCell().setBorderColor(BaseColor.WHITE)
		PdfPCell cell;
		table.setHorizontalAlignment(Element.ALIGN_CENTER)
		table.addCell(createCell(table, cell, "Forma de Pagamento\nÀ vista"))
		table.addCell(createCell(table, cell, "Compacto 1"))
		table.addCell(createCell(table, cell, "Compacto 2"))
		table.addCell(createCell(table, cell, "Ampliado 1"))
		table.addCell(createCell(table, cell, "Ampliado 2"))
		table.addCell(createCell(table, cell, "Especial"))
		table.addCell(createCell(table, cell, "Exclusivo"))

		if(existeCobertura("Casco",issue_info)){
			createTable(table,"Casco",  pacotes, 0)
		}
		if(existeCobertura("RCF - Danos Materiais",issue_info)){
			createTable(table, "RCF - Danos Materiais", pacotes, 1)
		}
		if(existeCobertura("RCF - Danos Corporais",issue_info)){
			createTable(table, "RCF - Danos Corporais",  pacotes, 2)
		}
		if(existeCobertura("RCF - Danos Morais",issue_info)){
			createTable(table, "RCF - Danos Morais",  pacotes, 3)
		}
		if(existeCobertura("APP - Morte",issue_info)){
			createTable(table, "APP - Morte",  pacotes, 4)
		}
		if(existeCobertura("APP - Invalidez Permanente",issue_info)){
			createTable(table, "APP - Invalidez Permanente",  pacotes, 5)
		}

		if(existeCobertura("Carta Verde - Danos Materiais",issue_info)){
			if(existeCobertura("Carta Verde - Danos Materiais",issue_info)){
				createTable(table, "Carta Verde - Danos Materiais", pacotes, 6)
			}
			if(existeCobertura("Carta Verde - Danos Corporais",issue_info)){
				createTable(table, "Carta Verde - Danos Corporais",  pacotes, 7)
			}
			createTable(table, "Prêmio Líquido",  pacotes, 8)
			createTable(table, "IOF",  pacotes, 9)
			createTable(table, "Total do Cálculo",  pacotes, 10)
		} else {
			createTable(table, "Prêmio Líquido",  pacotes, 6)
			createTable(table, "IOF",  pacotes, 7)
			createTable(table, "Total do Cálculo",  pacotes, 8)
		}
		document.add(table);
	}

	/**
	 * @author Inmetrics
	 * @param im
	 * @return
	 */
	public static adicionarIdentificadorRadarLivePDF(String im){
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface = new Paragraph(im, smallEvidence);
		preface.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(preface, 1);
		document.add(preface);
	}

	/**
	 * @author Inmetrics
	 * @param table
	 * @param cell
	 * @param title
	 * @return
	 */
	public static PdfPCell createCell(PdfPTable table, PdfPCell cell, String title){
		cell = new PdfPCell(new Paragraph(title, whiteFontSmall), )
		cell.setBackgroundColor(BaseColor.BLUE)
		cell.setUseBorderPadding(true);
		cell.setBorderColor(BaseColor.WHITE)
		return cell
	}

	/**
	 * @author Inmetrics
	 * @param cobertura
	 * @param issue_info
	 * @return
	 */
	public static boolean existeCobertura(String cobertura, List<Map<String, String>> issue_info){
		boolean existeCobertura = false
		List<String> coberturaLista = new ArrayList<String>();
		for(int i = 0; i < issue_info.size(); i++){
			if(!issue_info.get(i).get("Coberturas").equals('')){
				coberturaLista.add(issue_info.get(i).get("Coberturas"))
			}
		}
		for(int i = 0; i < coberturaLista.size();i++){
			if(coberturaLista.get(i).toString().equals(cobertura)){
				existeCobertura = true
			}
		}
		return existeCobertura
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Extrai os valores do arquivo pdf
	 */
	public static String takeInfoPDFContent(String pathFileValue ){

		PdfReader pdfReaderFilesT = new PdfReader(pathFileValue);
		int pages = pdfReaderFilesT.getNumberOfPages()

		pdfReaderFilesT.finalize()
		pdfReaderFilesT.close()
		System.err.println('numbers of pages ['+pages+'] ')

		strbffr = new StringBuffer()
		String text
		try{
			for(int i=0; i<pages; i++){
				PdfReader pdfReaderFiles = new PdfReader(pathFileValue);
				int j = i+1
				text = PdfTextExtractor.getTextFromPage(pdfReaderFiles, j)
				strbffr.append(""+text+"").toString()
				pdfReaderFiles.finalize()
				pdfReaderFiles.close()
			}
			return strbffr
		} catch (Exception e){
			System.err.println("takePDFContent - Falha ao ler arquivo ["+e+"]")
			rpaCommands.finalizar()
		}
	}

	/**
	 * @author Inmetrics
	 * @param table
	 * @param title
	 * @return
	 */
	public static PdfPTable createTable(PdfPTable table, String title, List<Pacote> pacotes, int index){
		String vl1
		String vl2
		String vl3
		String vl4
		String vl5
		String vl6

		table.addCell(new Paragraph(title,smallListBlack))
		for(int i = 0; i < pacotes.size(); i++){
			List<Cobertura> coberturas = new ArrayList<Cobertura>()
			coberturas.addAll(pacotes[i].getListaCobertura())
			for(int j = 0; j< coberturas.size(); j++ ){
				if(i==0 && j==index){
					vl1 = coberturas.get(j).getValorPremio()
				}
				if(i==1 && j==index){
					vl2 = coberturas.get(j).getValorPremio()
				}
				if(i==2 && j==index){
					vl3 = coberturas.get(j).getValorPremio()
				}
				if(i==3 && j==index){
					vl4 = coberturas.get(j).getValorPremio()
				}
				if(i==4 && j==index){
					vl5 = coberturas.get(j).getValorPremio()
				}
				if(i==5 && j==index){
					vl6 = coberturas.get(j).getValorPremio()
				}
			}
		}
		table.addCell(new Paragraph("R\044  " +vl1,smallListBlack))
		table.addCell(new Paragraph("R\044  " +vl2,smallListBlack))
		table.addCell(new Paragraph("R\044  " +vl3,smallListBlack))
		table.addCell(new Paragraph("R\044  " +vl4,smallListBlack))
		table.addCell(new Paragraph("R\044  " +vl5,smallListBlack))
		table.addCell(new Paragraph("R\044  " +vl6,smallListBlack))
		return table
	}

	/**
	 * @author T_DCDias
	 * @param listCotacao
	 * @return
	 * Metodo responsável por criar as colunas para cada pacote
	 */
	public static evidenciaPacotesCoberturas(List<Pacote> listCotacao){

		double somaPacoteCompacto1 = 0.0;
		double somaPacoteCompacto2 = 0.0;
		double somaPacoteAmpliado1 = 0.0;
		double somaPacoteAmpliado2 = 0.0;
		double somaPacoteEspecial = 0.0;
		double somaPacoteExclusivo= 0.0;

		//DecimalFormat formato = new DecimalFormat("#.##");

		PdfPTable table = new PdfPTable(listCotacao.size()+1);
		table.setHeaderRows(1);
		table.setWidthPercentage(100.0)
		table.getDefaultCell().setBorder(1)
		table.getDefaultCell().setBorderWidthLeft(1)
		table.getDefaultCell().setBorderWidthRight(1)
		table.getDefaultCell().setBorderWidthTop(1)
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE)
		table.getDefaultCell().setBorderColor(BaseColor.WHITE)

		PdfPCell cell;

		table.setHorizontalAlignment(Element.ALIGN_CENTER)

		cell = new PdfPCell(new Paragraph("Pacotes\nCoberturas", whiteFontSmall), )
		cell.setBackgroundColor(BaseColor.BLUE)
		cell.setBorderColor(BaseColor.WHITE)
		table.addCell(cell)

		//Adiciona valor total da parcela
		for(int i = 0; i<listCotacao.size();i++){
			cell = new PdfPCell(new Paragraph(listCotacao.get(i).getDescricaoPacote()+"\n R\044 "+ listCotacao.get(i).getValorTotalParcelado(), whiteFontSmall), )
			cell.setBackgroundColor(BaseColor.BLUE)
			cell.setBorderColor(BaseColor.WHITE)
			table.addCell(cell)
		}

		//Adiciona coberturas
		for(int a = 0; a < listCotacao.get(0).getListaCobertura().size(); a++){
			table.addCell(new Paragraph(listCotacao.get(0).getListaCobertura().get(a).getDescricaoCobertura(),smallListBlack))
			for(int i = 0; i<listCotacao.size();i++){
				table.addCell(new Paragraph("R\044 " + listCotacao.get(i).getListaCobertura().get(a).getValorPremio(),smallListBlack))
			}
		}

		table.addCell(new Paragraph("Prêmio Líquido", smallBoldBlack))
		for(int i = 0; i<listCotacao.size();i++){
			table.addCell(new Paragraph("R\044 " + String.format("%.2f", listCotacao.get(i).getTotalPremioCobertura()),smallListBlack))
		}

		table.addCell(new Paragraph("Valor IOF", smallBoldBlack))
		for(int i = 0; i<listCotacao.size();i++){
			table.addCell(new Paragraph("R\044 " + listCotacao.get(i).getValorIof(),smallListBlack))
		}

		table.addCell(new Paragraph("Juros", smallBoldBlack))
		for(int i = 0; i<listCotacao.size();i++){
			table.addCell(new Paragraph("R\044 " + listCotacao.get(i).getValorAdicionalFracionamento(),smallListBlack))
		}

		document.add(table);
	}

	/**
	 * @author T_DCDias
	 * @param status
	 * @param cenario
	 * @return
	 */
	public static adicionaMessagem(String status, String cenario){
		Paragraph prefaceCenario = new Paragraph();
		prefaceCenario.add(new Paragraph(status, corDaFonteSmall(status)));
		prefaceCenario.add(new Paragraph(cenario, smallListBlack));
		document.add(prefaceCenario);
	}

	/**
	 * @author T_DCDias
	 * @param cenario
	 * @return
	 */
	public static adicionaLinhaTexto(String cenario){
		Paragraph prefaceCenario = new Paragraph();
		addEmptyLine(prefaceCenario, 0);
		prefaceCenario.add(new Paragraph(cenario, smallListBlack));
		addEmptyLine(prefaceCenario, 0);
		document.add(prefaceCenario);
	}

	/**
	 * @author T_DCDias
	 * @param cenario
	 * @return
	 */
	public static adicionaLinhaTextoNegrito(String cenario){
		Paragraph prefaceCenario = new Paragraph();

		prefaceCenario.add(new Paragraph(cenario, smallBoldBlack));

		document.add(prefaceCenario);
	}







	public static criarMenagem(String cenario, String stepTest, List<String> cellvalueTitle, List<String> cellvalueResult, List<String> cellvalueStatus){

		try{

			Paragraph prefaceCenario = new Paragraph();
			addEmptyLine(prefaceCenario, 3);
			prefaceCenario.add(new Paragraph(cenario, blackFont));
			addEmptyLine(prefaceCenario, 1);
			document.add(prefaceCenario);

			Paragraph prefaceStepTest = new Paragraph();
			addEmptyLine(prefaceCenario, 1);
			prefaceStepTest.add(new Paragraph(stepTest, blackFont));
			addEmptyLine(prefaceStepTest, 1);
			document.add(prefaceStepTest);

			PdfPTable table = new PdfPTable(3);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setBorderColor(BaseColor.WHITE)
			table.setWidthPercentage(100.0)
			table.addCell(new Paragraph("Campo", whiteFontSmall))
			table.addCell(new Paragraph("Valor", whiteFontSmall))
			table.addCell(new Paragraph("Status", whiteFontSmall))
			table.setHeaderRows(1);

			PdfPCell[] cells = table.getRow(0).getCells();
			for (int j=0;j<cells.length;j++){
				cells[j].setBackgroundColor(BaseColor.BLUE)
				cells[j].setBorderColor(BaseColor.WHITE)
			}

			for (int i=0;i<cellvalueTitle.size();i++){
				table.addCell(new Paragraph(cellvalueTitle.get(i)+": ",blueFontSmall) )
				table.addCell(new Paragraph(cellvalueResult.get(i), smallListBlack))
				table.addCell(new Paragraph(cellvalueStatus.get(i), corDaFonteSmall(cellvalueStatus.get(i))))
			}

			document.add(table);
		} catch (Exception e) {
			System.err.println("evidencia - Falha na geração de evidência ["+e+"]")
			rpaCommands.finalizar()
		}
	}



	public static messageError(String cenario, String stepTest){
		try{
			Paragraph prefaceCenario = new Paragraph();
			addEmptyLine(prefaceCenario, 3);
			prefaceCenario.add(new Paragraph(cenario, blackFont));
			addEmptyLine(prefaceCenario, 1);
			document.add(prefaceCenario);

			Paragraph prefaceStepTest = new Paragraph();
			addEmptyLine(prefaceCenario, 1);
			prefaceStepTest.add(new Paragraph(stepTest, blackFont));
			addEmptyLine(prefaceStepTest, 1);
			document.add(prefaceStepTest);

			PdfPTable table = new PdfPTable(1);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setBorderColor(BaseColor.WHITE)
			table.setWidthPercentage(100.0)
			table.addCell(new Paragraph("Falha ao validar campos no PDF", whiteFontSmall))
			table.setHeaderRows(1);
			table.addCell(new Paragraph("Campo", whiteFontSmall))
			table.setHeaderRows(1);
			PdfPCell[] cells = table.getRow(0).getCells();
			for (int j=0;j<cells.length;j++){
				cells[j].setBackgroundColor(BaseColor.RED)
				cells[j].setBorderColor(BaseColor.WHITE)
			}
			for (int i=0;i<1;i++){
				table.addCell(new Paragraph("",blueFontSmall) )
			}
			document.add(table);
		} catch (Exception e){
			System.err.println("evidencia de falha - Falha na geração de evidência ["+e+"]")
			rpaCommands.finalizar()
		}
	}


	/**
	 * @author T_DCdias
	 * @param args
	 * @return
	 */
	public static List<String> creatList(String ...args){
		List<String> value = new ArrayList<String>();
		value.addAll(args);
		return value;
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static testCeateDocument(){
		String cenario = "Valida se PDF do Voucher está apresentando orientações sobre o processo de vistoria e não deverá apresentar numeração do voucher"
		String stepTest = "ORIENTAÇÕES - Verifica orientações sobre o processo de vistoria"
		createDocument("01", "C:/Users/Public")
		createTitlePage()
		criarMenagem(cenario, stepTest, creatList("Linha 1","Linha 2","Linha 3","Linha 4","Linha 5","Linha 6","Linha 7"),

				creatList("Para aceitação da sua apólice, é necessário realizar a vistoria prévia de seu veículo e você pode",
				"contar com toda a agilidade e comodidade da vistoria realizada através do seu celular.",
				"Em breve você receberá um SMS com um link em seu telefone (11)99171-6000 com as orientações",
				"sobre o processo. Se necessário, o link pode ser encaminhado para o celular que realizará a vistoria.",
				"O prazo máximo para a realização da vistoria é de 48H.",
				"Caso não receba o SMS em 2 horas ou o número informado esteja incorreto, entre em contato",
				"através do telefone 0800 941 39 21 de 2ª à 6ª, das 8h00 às 18h00."),

				creatList("PASSED","PASSED","FAIL","PASSED","PASSED","FAIL","PASSED")
				)

		//		criarMenagem(cenario, stepTest, creatList("ORIENTAÇÕES"),
		//
		//							   creatList("Para aceitação da sua apólice, é necessário realizar a vistoria prévia de seu veículo e você pode\n"+
		//										 "contar com toda a agilidade e comodidade da vistoria realizada através do seu celular.\n"+
		//										 "Em breve você receberá um SMS com um link em seu telefone (11)99171-6000 com as orientações\n"+
		//										 "sobre o processo. Se necessário, o link pode ser encaminhado para o celular que realizará a vistoria.\n"+
		//										 "O prazo máximo para a realização da vistoria é de 48H.\n"+
		//										 "Caso não receba o SMS em 2 horas ou o número informado esteja incorreto, entre em contato\n"+
		//										 "através do telefone 0800 941 39 21 de 2ª à 6ª, das 8h00 às 18h00."),
		//
		//							   creatList("PASSED")
		//							)

		messageError( cenario, stepTest)

		closeDocument()
	}

	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static createDocumentDeclaracaoCoCorretagem(String ct, String path){
		document = new Document();
		try {
			Date dataHoraAtual = new Date();
			String data = new SimpleDateFormat("ddMMyyyy").format(dataHoraAtual);
			String hora = new SimpleDateFormat("HHmmss").format(dataHoraAtual);
			nomePDF = ct+"_"+data+"_"+hora+".pdf"
			PdfWriter.getInstance(document, new FileOutputStream(path+"//"+ct+"_"+data+"_"+hora+".pdf"));
			document.open(); System.err.println("-[document Opened]-")
		} catch(DocumentException de) {
			closeDocument()
			System.err.println(de.getMessage());
		} catch(IOException ioe) {
			closeDocument()
			System.err.println(ioe.getMessage());
		}
	}
	
	/**
	 *
	 * @param status
	 * @param cenario
	 * @return
	 */
	public static adicionaTextoCenterDeclaracaoCoCorretagem(String txt, int l){
		Paragraph preface = new Paragraph();
		preface = new Paragraph(txt, smallBold);
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, l);
		document.add(preface);
	}
	
	/**
	 *
	 * @param status
	 * @param cenario
	 * @return
	 */
	public static adicionaTextoJUSTIFIEDDeclaracaoCoCorretagem(String txt, int l){
		Paragraph preface = new Paragraph();
		preface = new Paragraph(txt, small);
		preface.setAlignment(Element.ALIGN_JUSTIFIED);
		addEmptyLine(preface, l);
		document.add(preface);
	}
	
	/**
	 * @author T_DCDias
	 * @param document
	 * @param img
	 * @return
	 */
	public static Document rodapeDeclaracaoCoCorretagem(String nome, String subnome){
		PdfPTable table = new PdfPTable(2);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(0)
		table.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)
		table.addCell(new Paragraph("________________________________________", smallBold))
		table.addCell(new Paragraph("________________________________________", smallBold))
		table.addCell(new Paragraph(nome, smallBold))
		table.addCell(new Paragraph("SGA CORRETORA DE SEGUROS LTDA", smallBold))
		table.addCell(new Paragraph(subnome,smallBold))
		table.addCell(new Paragraph("               CORRETORA LÍDER",smallBold))
		document.add(table);
		return document
	}


}