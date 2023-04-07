<?xml version="1.0" encoding="UTF-8"?>
<WebElementEntity>
   <description></description>
   <name>div_Tipo de SeleoEmissoOutras</name>
   <tag></tag>
   <elementGuidId>bb79145b-56df-40ba-920e-2f05796f9170</elementGuidId>
   <selectorCollection>
      <entry>
         <key>XPATH</key>
         <value>//div[@id='datosSeleccionContent']</value>
      </entry>
   </selectorCollection>
   <selectorMethod>XPATH</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <webElementProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>tag</name>
      <type>Main</type>
      <value>div</value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>id</name>
      <type>Main</type>
      <value>datosSeleccionContent</value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>class</name>
      <type>Main</type>
      <value>divSectionContentBody</value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>text</name>
      <type>Main</type>
      <value>






Tipo de Seleção








Emissão


















Outras Operações





























Agrupamento










    Selecione um agrupamento



















Produto










    Selecione um Produto



















Sub-Ramo










    Selecione um Sub-Ramo




























Ramo / Modalidade(*)












/




















Sub-Ramo (*)



























Moeda










    Selecione uma moeda





















						
							
											  
								
										
								 				
							Import(&quot;widgets.Errors&quot;);
Import(&quot;/drwd00/jquery/jquery.ajax.js&quot;);
Import(&quot;controls.EntryRadio&quot;);
Import(&quot;controls.EntryCombo&quot;);
Import(&quot;controls.EntryNumber&quot;);
Import(&quot;controls.EntryCurrency&quot;);
Import(&quot;/drwd00/js/class.controls.Image.js&quot;);
Import(&quot;controls.EntryDate&quot;);











Data de Emissão






















Contravalor


































											
					










Corretor
































Colaborador
























									



























	


	
  		Import(&quot;/drwd00/js/class.widgets.extendTag.ShowPolicy.js&quot;);
  	
  	

  	
		
		
          	
          	
        Import(&quot;controls.EntryNumber&quot;);
Import(&quot;controls.EntryCurrency&quot;);
Import(&quot;controls.EntrySusep&quot;);











Apólice
















Item


















        


        
	    	$(document.getElementById('polSusep_SUSEP')).blur(function() {
	    		document.getElementById('polSusep_Item').setDisabled();
	    		getPolicy(this);
	    	});
	    
        

		
        
	

									&lt;!--
										$('#polSusep_Policy').change(cambioPoliza);
										$('#polSusep_SUSEP').change(cambioPoliza);
									-->
									
								
























Proposta



























Estado


























Situação Especial























(*) Permite incluir o ramo/modalidade e sub-ramo manualmente.




















Limpar






Selecionar











</value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath</name>
      <type>Main</type>
      <value>id(&quot;datosSeleccionContent&quot;)</value>
   </webElementProperties>
   <webElementXpaths>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath:attributes</name>
      <value>//div[@id='datosSeleccionContent']</value>
   </webElementXpaths>
   <webElementXpaths>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath:idRelative</name>
      <value>//td[@id='datosSeleccionCellContent']/div</value>
   </webElementXpaths>
   <webElementXpaths>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath:neighbor</name>
      <value>(.//*[normalize-space(text()) and normalize-space(.)='SELEÇÃO DE DADOS'])[1]/following::div[1]</value>
   </webElementXpaths>
   <webElementXpaths>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath:neighbor</name>
      <value>(.//*[normalize-space(text()) and normalize-space(.)='Menu Produção'])[1]/following::div[8]</value>
   </webElementXpaths>
   <webElementXpaths>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath:position</name>
      <value>//tr[2]/td/div</value>
   </webElementXpaths>
</WebElementEntity>
