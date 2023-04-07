<?xml version="1.0" encoding="UTF-8"?>
<WebElementEntity>
   <description></description>
   <name>html_Renovaes LANGpt CONTEXT_L</name>
   <tag></tag>
   <elementGuidId>52a0ad88-cf0f-429f-a1d7-a32ab80fb7d5</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <webElementProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>tag</name>
      <type>Main</type>
      <value>html</value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>class</name>
      <type>Main</type>
      <value> js canvas geolocation video audio localstorage sessionstorage texttrackapi track</value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>text</name>
      <type>Main</type>
      <value>










Renovações

 $LANG='pt';
 $CONTEXT_LAST_DEPLOY='20180620070145';
 $DRWD00_LAST_DEPLOY='20180719045257';
 $LANGCOUNTRY='pt_BR';
 $FONTSIZE='small';
 $CIACOD='BRA';
 $CIACOUNTRY='br';
 $CIANCODCIA='4';
 $CIAPARTNER='';
 $SESSINTERVAL='1800';
 $STYLELINE='styleLine10';
 $DEVICEPLATFORMINFO='DESKTOP';
 $DEVICEPLATFORMTYPE='';
 PAGEID='renovaciones$renovaciones';






$JS='1,1';$JS='1,2';$JS='1,3';$JS='1,4';$JS='1,5';$JS='1,6';$JS='2,0';



Import(&quot;widgets.Errors&quot;);
Import(&quot;/drwd00/jquery/jquery.ajax.js&quot;);
Import(&quot;controls.EntryCombo&quot;);
Import(&quot;/drwd00/js/class.controls.Section.js&quot;);
Import(&quot;/drwd00/js/extendInfoTable.js&quot;);
Import(&quot;/drwd00/js/class.controls.Table.js&quot;);
Import(&quot;widgets.Page&quot;);
Import(&quot;dhtml.MountsControl&quot;);
Import(&quot;dhtml.SectionButton&quot;);
Import(&quot;/drwd00/js/orderTable.js&quot;);



try {var _paq = _paq || [];_paq.push(['setCustomVariable', 1, 'env', 'pro', 'visit']);_paq.push(['setCustomVariable', 2, 'app', 'drrg08', 'visit']);_paq.push(['trackPageView']);_paq.push(['enableLinkTracking']);window.addEventListener('load', function() {var u='//webanalytics.allianzseguros.com/';_paq.push(['setTrackerUrl', u+'piwik.php']);_paq.push(['setSiteId', 18]);_paq.push(['setUserId', 'BE00151']);var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];g.type='text/javascript'; g.async=true; g.defer=true; g.src='/drwd00/js/piwik.js'; s.parentNode.insertBefore(g,s);});}catch(err) {}
#katalon{font-family:monospace;font-size:13px;background-color:rgba(0,0,0,.7);position:fixed;top:0;left:0;right:0;display:block;z-index:999999999;line-height: normal} #katalon div{padding:0;margin:0;color:#fff;} #katalon kbd{display:inline-block;padding:3px 5px;font:13px Consolas,&quot;Liberation Mono&quot;,Menlo,Courier,monospace;line-height:10px;color:#555;vertical-align:middle;background-color:#fcfcfc;border:1px solid #ccc;border-bottom-color:#bbb;border-radius:3px;box-shadow:inset 0 -1px 0 #bbb;font-weight: bold} div#katalon-spy_elementInfoDiv {color: lightblue; padding: 0px 5px 5px} div#katalon-spy_instructionDiv {padding: 5px 5px 2.5px}



 function mostrarAyuda(respuesta)
 {
    try{
		if (respuesta != '')
   	 {
      		 var ret = eval(&quot;(&quot; + respuesta + &quot;)&quot;);
   	     showProcessIcon(&quot;imgOptMenuBackHelp&quot;, false, 250)
    		 if (ret.codError != '0' || ret.ayuda != 'true') {$('#imgOptMenuBackHelp').hide(); if ($('#tdHelpButton').length) {$('#tdHelpButton').hide();}
       	 }
   	 }
 	}catch(err){$('#imgOptMenuBackHelp').hide(); if ($('#tdHelpButton').length) {$('#tdHelpButton').hide();}
   }
 }
 function criticalErrorDataAyudaAplicaciones() {$('#imgOptMenuBackHelp').hide(); if ($('#tdHelpButton').length) {$('#tdHelpButton').hide();}
   showProcessIcon(&quot;imgOptMenuBackHelp&quot;, false, 250)
 }
 function showProcessIcon ( String, Boolean, int )
 {
   var sIDLabel  = arguments[0];
   var bShowIcon = arguments[1];
   var iTime     = arguments[2];
   var sProcessIcon = &quot;&quot;;
   if ($(&quot;#&quot; + sIDLabel).length) {
     if (sIDLabel){
       setTimeout( function() {
         if (bShowIcon) {
        $('#imgOptMenuBackHelp').css('background-image', 'url(/drwd00/img/icoNaviSGEnCursoComboFinder.gif)'); 
         } else {
            $('#imgOptMenuBackHelp').css('background-color', '#ffffff'); 
            $('#imgOptMenuBackHelp').css('background-image', 'none'); 
         }
       }, iTime ); 
     }
   }
 }
$(document).ready(function(){
 if (typeof PAGEID != 'undefined' &amp;&amp; typeof $CIANCODCIA != 'undefined') {
   showProcessIcon(&quot;imgOptMenuBackHelp&quot;, true, 50)
   var datos = 'pageId='+ PAGEID + '&amp;codCia=' + $CIANCODCIA;
   AjaxRequest(&quot;/drrg08/AJAXServlet?resource=com.ats.sea.visuals.util.AyudaAplicacionesAjax&quot;,datos,mostrarAyuda,false,null,criticalErrorDataAyudaAplicaciones);
 } else {
   if ($('#imgOptMenuBackHelp').length) {$('#imgOptMenuBackHelp').hide(); if ($('#tdHelpButton').length) {$('#tdHelpButton').hide();}
   }
}
});


	
	

	
		function sendMenuEvent(option) {
			$(&quot;#formMenu input[name=option]&quot;).val(option);
			$(&quot;#formMenu input[name=action]&quot;).val(&quot;menu&quot;);
			$(&quot;#formMenu&quot;).submit();
		}
		
		function validarAceptar(){
			var acc =$(&quot;#form input[name=action]&quot;).val();
			if (acc == 'reajuste') {
				return true;
			} else {
				return false;
			}
		}
		
		// SFS-1900 Busq por nomTomador sólo si se ha informado antes el mediador
         function cambiaMediador() {
             var busqueda = false;
             var mediador = $.trim($(&quot;#estructOrg_mediador_text&quot;).val());
             if (mediador != &quot;&quot; &amp;&amp; !busqueda) {
                 habilitarCampo('nomTomadorFiltro');
             } else if (mediador != &quot;&quot; &amp;&amp; busqueda) {
                 deshabilitarCampo(&quot;nomTomadorFiltro&quot;);
             } else {
                 $(&quot;#nomTomadorFiltro&quot;).val(&quot;&quot;);
                 deshabilitarCampo(&quot;nomTomadorFiltro&quot;);
             }
         }
		
		function deshabilitarCampo(campo) {
			$(&quot;#&quot;+campo).attr(&quot;disabled&quot;, &quot;true&quot;);
			$(&quot;#&quot;+campo).attr(&quot;disabled&quot;, &quot;disabled&quot;);
			$(&quot;#&quot;+campo).attr(&quot;readonly&quot;, &quot;true&quot;);
			$(&quot;#&quot;+campo).css(&quot;border&quot;, &quot;1px solid white&quot;);
		}
		
		function habilitarCampo(campo) {
			$(&quot;#&quot;+campo).removeAttr('disabled');
			$(&quot;#&quot;+campo).removeAttr('readonly');
			$(&quot;#&quot;+campo).css(&quot;border&quot;, &quot;1px solid black&quot;);
		}
		
		function bloquearFiltro() {
			var nomTomadorFiltro = $(&quot;#nomTomadorFiltro&quot;).val();
			if (nomTomadorFiltro != &quot;&quot;) {
				// SFS-1900
				//deshabilitarCampo('estructOrg_dc_text');
				$(&quot;#agrupaRamos&quot;).val(&quot;&quot;);
				deshabilitarCampo('agrupaRamos');
				$(&quot;#ramosCombo&quot;).val(&quot;&quot;);
				deshabilitarCampo('ramosCombo');
				$(&quot;#vencimiento&quot;).val(&quot;&quot;);
				deshabilitarCampo('vencimiento');
				$(&quot;#estadoF&quot;).val(&quot;00&quot;); //todos
				deshabilitarCampo('estadoF');
				
					$(&quot;#poliza_SUSEP&quot;).val(&quot;&quot;);
					deshabilitarCampo('poliza_SUSEP');
					$(&quot;#poliza_Item&quot;).val(&quot;&quot;);
					deshabilitarCampo('poliza_Item');
				
			} else {
				// SFS-1900
				//habilitarCampo('estructOrg_dc_text');
				habilitarCampo('agrupaRamos');
				habilitarCampo('ramosCombo');
				habilitarCampo('vencimiento');
				habilitarCampo('estadoF');
				
					habilitarCampo('poliza_SUSEP');
					habilitarCampo('poliza_Item');
				
			}
		}
		
		function sendEvent(action) {
			$(&quot;#form input[name=action]&quot;).val(action);
			$(&quot;#form&quot;).submit(); 
		}
		
		function filtrar(){
			var nomTomador = $(&quot;#nomTomadorFiltro&quot;).val();
			if (nomTomador==''||nomTomador=='undefined') {
				//SFS-11387 - Asociar vista para EXCEL

				$(&quot;#form input[name=vistaExcel]&quot;).val(&quot;buscarPolizas&quot;);
				sendEvent('buscarRenovaciones');
			} else {
				$(&quot;#nomTomadorFiltro&quot;).val($.trim(nomTomador));
				//SFS-11387 - Asociar vista para EXCEL

				$(&quot;#form input[name=vistaExcel]&quot;).val(&quot;buscarTomador&quot;);
				sendEvent('buscarNombre');
			}
		}
		
		function abreBusqueda() {
			$(&quot;#form input[name=abrirBusqueda]&quot;).val(&quot;S&quot;);
			sendSEAEvent(form, &quot;limpiar&quot;);
		}
		
		function calculaComboRamos(){
			var agrupaSel = $('#agrupaRamos').val();
			//var vencimiento =  $('#vencimiento').val();
			//if (agrupaSel != '' &amp;&amp; vencimiento != '') {		
			if (agrupaSel != '' ) {	
				var URI_AJAXSERVLET = &quot;/drrg08/AJAXServlet?resource=&quot;;  
	  			var CLASE_AJAX = &quot;co.allianz.drrg08.utils.ajax.RecuperarCombosRamo&quot;;
	 			AjaxRequest(URI_AJAXSERVLET + CLASE_AJAX ,
	 			&quot;&amp;agrupaSel=&quot;+ agrupaSel
	 			//&quot;&amp;vencimiento=&quot;+ vencimiento		
	 			,respuestaCalculaComboRamos);
 			}else{
 				$('#ramosCombo').val('');
 				deshabilitarCampo('ramosCombo');
 			}
		}
		
		function respuestaCalculaComboRamos(msg){  
		
			var arrayOfObjects = eval(msg);
		
			var comboNames = [&quot;ramosCombo&quot;,&quot;vencimiento&quot;]
  			var arraySelected = ['null', 'null']
  			for(var i = 0; i &lt; comboNames.length; i++){
  				var array = arrayOfObjects[i];
  				var select = $(&quot;#&quot; + comboNames[i]);
  				var firstOpt = $(&quot;#&quot; + comboNames[i] + &quot; option&quot;)[0];
  	   	  		$(&quot;#&quot; + comboNames[i] + &quot; option&quot;).remove();
  	  			select.append(firstOpt);
  	  			var selectedVal = arraySelected[i];
  	  			for (var j = 0; j &lt; array.length; j++) {
  					var name = array[j].name;
  					var cod = array[j].cod;
  					var selected = &quot;&quot;;
  					if(cod == selectedVal){
  						selected = &quot; selected='true'&quot;;
  					}
  					select.append(&quot;&lt;option value='&quot; + cod + &quot;'&quot; + selected + &quot;>&quot; + name + &quot;&lt;/option>&quot;);
  				}
  			}
  			
  			habilitarCampo('ramosCombo');
  				
  		}
		
		
		
		function mostrarPDF(ramo, modalidad, operacion){
			Errors.clear();
			
			var uid  	  = 'BE00151';
			var PATH 	  = '/drrg08';
			var URI = PATH + &quot;/ServletGenerarPdf?uid=&quot;+uid + &quot;&amp;ramo=&quot; + ramo + &quot;&amp;modalidad=&quot; + modalidad +&quot;&amp;operacion=&quot; + operacion ;
			
 			var w = window.open(URI,&quot;&quot;,&quot;width=900,height=screen.availHeight,toolbar=no,menubar=no,status=no,titlebar=no,left=0,top=0,resizable=yes&quot;);
 			w.moveTo((screen.availWidth-900)/2,0);
			w.resizeTo(900,screen.availHeight);
		}
		
		function exportarExcel(){			
			var alertNumResult = 'INFO: O Excel gerado terá um máximo de 12000 registros.';		
			warn(alertNumResult);
			$(&quot;#excelForm&quot;).submit();
		}
		
	
	




 



BE00151



> Idioma (pt [BR])
 
> Tamanho Letra (S)
 
> Fechar





Renovações











Renovações


 
  


  

















 

 
 

function menuChg (menuHide, menuShow) {
  presentMenu = menuShow;
  document.getElementById(menuHide).style.display=&quot;none&quot;;
  document.getElementById(menuShow).style.display=&quot;block&quot;;
  backArrowCtrl();
}


function parentLabel (label) {
  var area = document.getElementById(&quot;cellTopicPromptHeaderArea&quot;);
  if(area==null){
    return;
  }
  area.innerHTML=&quot;&amp;nbsp;&quot; + label;
}



function goSelected (selection) {
  document.getElementById(&quot;menuSel&quot;).value = selection;
}


var presentMenu = &quot;tableOptMenu_&quot;;
var backPath = new Array();
var backLabels = new Array();
backArrowCtrl();
parentLabel(&quot;&amp;nbsp;&quot;);
backLabels.push(&quot;&amp;nbsp;&quot;);
function goBack() {
  var backMenu = backPath.pop();
  var backLabel = backLabels.pop();
  var oCellTopic = document.getElementById(&quot;cellTopicPromptHeaderArea&quot;);
  var txtTopic = (oCellTopic.innerText ? oCellTopic.innerText : oCellTopic.textContent);
  if (txtTopic.substr(1) == backLabel)
    if (backLabels.length > 0)
      backLabel = backLabels[backLabels.length-1];
  menuChg(presentMenu, backMenu);
  parentLabel(backLabel);
}
function backArrowCtrl() {
  if (backPath.length == 0) {
   document.getElementById(&quot;imgOptMenuBackBack&quot;).style.visibility=&quot;hidden&quot;;
  } else {
   document.getElementById(&quot;imgOptMenuBackBack&quot;).style.visibility=&quot;visible&quot;;
  }
}




 

 
 


















			
			
			
			
		






ErrorsBackup = new Array();
ErrorsPendingTranslation = new Array();

function updateErrorTranslated(result) {
  result = jQuery.parseJSON(result);
  if ((typeof(Errors)!=&quot;undefined&quot;) &amp;&amp; (Errors!=null)) {

    Errors.clear();
    ErrorsBackup[ErrorsBackup.length]= result;

    for (var i=0; i&lt;ErrorsBackup.length; i++) {
      Errors.setItem(ErrorsBackup[i].title, ErrorsBackup[i].translation, ErrorsBackup[i].type,null,true);
    }
    for (var i=0; i&lt;ErrorsPendingTranslation.length; i++) {
      if (ErrorsPendingTranslation[i]!=null &amp;&amp; ErrorsPendingTranslation[i].title == result.title &amp;&amp; ErrorsPendingTranslation[i].msgKey == result.msgKey) {
        ErrorsPendingTranslation.splice(i,1);
      }
      if (ErrorsPendingTranslation[i]!=null) {
        Errors.setItem(ErrorsPendingTranslation[i].title, ErrorsPendingTranslation[i].translation, ErrorsPendingTranslation[i].type,null,true);
      }
    }
  }
} /** end updateErrorTranslated **/

function showErrors() {
  if ((typeof(Errors)!=&quot;undefined&quot;) &amp;&amp; (Errors!=null)) {
    Errors.setTitle('Informa\u00E7\u00E3o dos avisos e ocorr\u00EAncias');

  }
} /** end showErrors **/
$(window).load(function() {showErrors();});











				
				
				
				
				
			



 






DETALHE DE PESQUISA






 








						





















































	
	Import(&quot;/drwd00/jquery/jquery.ajax.js&quot;);
    Import(&quot;/drwd00/json/json2.js&quot;);
    var eo_cia = '4';
    var mediador = 'null';
    var sucursal = 'null';
    var isMediador = '';
    var perfil = 'CENTRAL';

    function estructOrganFnc_trim(str){
		return str.replace(/^\s+/g,'').replace(/\s+$/g,'')
	}
    
    function onLoad(){
    	if(isMediador == &quot;true&quot;){
    		document.getElementById(&quot;estructOrg_sucursal_text&quot;).value = sucursal;
    		document.getElementById(&quot;estructOrg_mediador_text&quot;).value = mediador;
    		estructOrganFnc_llamar(estructOrganFnc_listenerCargarTodosCampos, 'XM');
    		
    		estructOrganFnc_llamar(estructOrganFnc_listenerDCs, 'DC');
    		if((eo_cia == 4) || (eo_cia == 8)){
    			estructOrganFnc_llamar(estructOrganFnc_listenerDTs, 'DT');
    			estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'SB');
    			estructOrganFnc_llamar(estructOrganFnc_listenerUCs, 'UC');
    			estructOrganFnc_llamar(estructOrganFnc_listenerMediadores, 'AG');
    		}else{
    			estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'SU');
    			estructOrganFnc_llamar(estructOrganFnc_listenerUCs, 'UC');
    			estructOrganFnc_llamar(estructOrganFnc_listenerMediadores, 'AG');
    		}
    		
    		//Es deshabiliten tots els camps
    		document.getElementById(&quot;estructOrg_dc_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_dc&quot;).setDisabled();
			if((eo_cia == 4) || (eo_cia == 8)){
				document.getElementById(&quot;estructOrg_dt_text&quot;).setDisabled();
				document.getElementById(&quot;estructOrg_dt&quot;).setDisabled();
			}
			document.getElementById(&quot;estructOrg_sucursal_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_sucursal&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_uc_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_uc&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador&quot;).setDisabled();

    	}else{  		
	    	if(perfil == &quot;CENTRAL&quot;){
	    		estructOrganFnc_llamar(estructOrganFnc_listenerDCs, 'DC');
	    	}else{
		    	estructOrganFnc_llamar(estructOrganFnc_listenerDCs, 'PC');
		    	if(document.getElementById(&quot;estructOrg_dc&quot;).length &lt;= 2 &amp;&amp; document.getElementById(&quot;estructOrg_dc&quot;).value != ''){
					document.getElementById(&quot;estructOrg_dc_text&quot;).setDisabled();
					document.getElementById(&quot;estructOrg_dc&quot;).setDisabled();
				}
	    	}
		    
		    if(eo_cia == 4){
			    if(estructOrganFnc_trim(document.getElementById('estructOrg_dc_text').value) != ''){
			    	if(perfil == &quot;CENTRAL&quot;){
			    		estructOrganFnc_llamar(estructOrganFnc_listenerDTs, 'DT');
			    	}else{
			    		estructOrganFnc_llamar(estructOrganFnc_listenerDTs, 'PT');
			    		if(document.getElementById(&quot;estructOrg_dt&quot;).length &lt;= 2 &amp;&amp; document.getElementById(&quot;estructOrg_dt&quot;).value != ''){
			    			document.getElementById(&quot;estructOrg_dt_text&quot;).setDisabled();
			    			document.getElementById(&quot;estructOrg_dt&quot;).setDisabled();
			    		}
			    	}
			    }
			    if(estructOrganFnc_trim(document.getElementById('estructOrg_dt_text').value) != ''){
			    	if(perfil == &quot;CENTRAL&quot;){
			    		estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'SB');
			    	}else{
			    		estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'PU');
			    		if(document.getElementById(&quot;estructOrg_sucursal&quot;).length &lt;= 2 &amp;&amp; document.getElementById(&quot;estructOrg_sucursal&quot;).value != ''){
			    			document.getElementById(&quot;estructOrg_sucursal_text&quot;).setDisabled();
			    			document.getElementById(&quot;estructOrg_sucursal&quot;).setDisabled();
			    		}
			    	}
			    }			    
			   	if(estructOrganFnc_trim(document.getElementById('estructOrg_sucursal_text').value) != ''){
			   		estructOrganFnc_llamar(estructOrganFnc_listenerUCs, 'UC');
			    }
			    
			   	if(estructOrganFnc_trim(document.getElementById('estructOrg_uc_text').value) != ''){
			   		estructOrganFnc_llamar(estructOrganFnc_listenerMediadores, 'AG');
			    } 
		    }else{
			    if(estructOrganFnc_trim(document.getElementById('estructOrg_dc_text').value) != ''){
			    	$('#estructOrg_dt').val(document.getElementById('estructOrg_dc_text').value);
					document.getElementById('estructOrg_dt_text').value = document.getElementById('estructOrg_dc_text').value;
			    	if(perfil == &quot;CENTRAL&quot;){
			    		estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'SU');
			    	}else{
			    		estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'PU');
			    		if(document.getElementById(&quot;estructOrg_sucursal&quot;).length &lt;= 2 &amp;&amp; document.getElementById(&quot;estructOrg_sucursal&quot;).value != ''){
			    			document.getElementById(&quot;estructOrg_sucursal_text&quot;).setDisabled();
			    			document.getElementById(&quot;estructOrg_sucursal&quot;).setDisabled();
			    		}
			    	}
			    }			    
			   	if(estructOrganFnc_trim(document.getElementById('estructOrg_sucursal_text').value) != ''){
			    	estructOrganFnc_llamar(estructOrganFnc_listenerUCs, 'UC');
			    }
			    
			   	if(estructOrganFnc_trim(document.getElementById('estructOrg_uc_text').value) != ''){
			    	estructOrganFnc_llamar(estructOrganFnc_listenerMediadores, 'AG');
			    }    	
		    }
    	}
	}
	$(document).ready(function(){
		setTimeout(onLoad,500);
	});
            
	function estructOrganFnc_llamar(listenerFnc, opcion){
		AjaxRequest(&quot;/drrg08/AJAXServlet?resource=es.allianz.drpc43.ajaxservices.LlamadaEstructOrgan&quot;, 
                    &quot;cia=4&quot;+
                    &quot;&amp;codCia=BRA&quot;+
                    &quot;&amp;opcion=&quot;+opcion+
                    &quot;&amp;dc=&quot;+document.getElementById('estructOrg_dc_text').value+
                    &quot;&amp;dt=&quot;+document.getElementById('estructOrg_dt_text').value+
                    &quot;&amp;suc=&quot;+document.getElementById('estructOrg_sucursal_text').value+
                    &quot;&amp;uc=&quot;+document.getElementById('estructOrg_uc_text').value+
                    &quot;&amp;mediador=&quot;+document.getElementById('estructOrg_mediador_text').value, 
                    listenerFnc,
                    true);
	}
	
	function estructOrganFnc_clearCombo(comboid){
		var selectObj = $('#' + comboid);
		selectObj.empty();
	}
	
	function estructOrganFnc_fillCombo(opts,comboid){
		estructOrganFnc_clearCombo(comboid);
		var selectObj = $('#'+comboid);
		selectObj.append('&lt;option value=&quot;&quot;>&lt;/option>');
		for(i=0; i&lt;opts.length; i++){
			selectObj.append('&lt;option value=&quot;' + opts[i].value + '&quot;>' + opts[i].value+ ' - '+opts[i].text + '&lt;/option>');
		}				
		
	}
	
	function estructOrganFnc_listenerCargarTodosCampos(cadena){
		if (cadena != '' &amp;&amp; cadena.indexOf(&quot;ERROR&quot;) == -1) {
			var opts = JSON.parse(cadena);	
			document.getElementById('estructOrg_dc_text').value = cerosTamCuatro(opts[0].value);
			document.getElementById('estructOrg_dt_text').value = cerosTamCuatro(opts[1].value);
			document.getElementById('estructOrg_sucursal_text').value = cerosTamCuatro(opts[2].value);
			document.getElementById('estructOrg_uc_text').value = opts[3].value;
			document.getElementById('estructOrg_mediador_text').value = opts[4].value;  
		}
	}
	
	function estructOrganFnc_listenerDCs(cadena){
		if (cadena != '' &amp;&amp; cadena.indexOf(&quot;ERROR&quot;) == -1) {
            var opts = JSON.parse(cadena);
            estructOrganFnc_fillCombo(opts,'estructOrg_dc');
            $('#estructOrg_dc').val(cerosTamCuatro(document.getElementById('estructOrg_dc_text').value));
	    	if(document.getElementById(&quot;estructOrg_dc&quot;).length &lt; 2){
				document.getElementById(&quot;estructOrg_dc_text&quot;).setDisabled();
				document.getElementById(&quot;estructOrg_dc&quot;).setDisabled();
			}else{
				document.getElementById(&quot;estructOrg_dc_text&quot;).setEnabled();
				document.getElementById(&quot;estructOrg_dc&quot;).setEnabled();
			}
        }
	}
	
	function estructOrganFnc_listenerDTs(cadena){
        if (cadena != '' &amp;&amp; cadena.indexOf(&quot;ERROR&quot;) == -1) {
        	var opts = JSON.parse(cadena);
            estructOrganFnc_fillCombo(opts,'estructOrg_dt');
            $('#estructOrg_dt').val(cerosTamCuatro(document.getElementById('estructOrg_dt_text').value));
    		if(document.getElementById(&quot;estructOrg_dt&quot;).length &lt; 2){
    			document.getElementById(&quot;estructOrg_dt_text&quot;).setDisabled();
    			document.getElementById(&quot;estructOrg_dt&quot;).setDisabled();
    		}else{
    			document.getElementById(&quot;estructOrg_dt_text&quot;).setEnabled();
    			document.getElementById(&quot;estructOrg_dt&quot;).setEnabled();
    		}
        }else{
        	document.getElementById(&quot;estructOrg_dt_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_dt&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_sucursal_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_sucursal&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_uc_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_uc&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador&quot;).setDisabled();
        }
	}
	
	function estructOrganFnc_listenerSucursales(cadena){
		if (cadena != '' &amp;&amp; cadena.indexOf(&quot;ERROR&quot;) == -1) {
        	var opts = JSON.parse(cadena);
            estructOrganFnc_fillCombo(opts,'estructOrg_sucursal');
            $('#estructOrg_sucursal').val(cerosTamCuatro(document.getElementById('estructOrg_sucursal_text').value));
    		if(document.getElementById(&quot;estructOrg_sucursal&quot;).length &lt; 2){
    			document.getElementById(&quot;estructOrg_sucursal_text&quot;).setDisabled();
    			document.getElementById(&quot;estructOrg_sucursal&quot;).setDisabled();
    			document.getElementById(&quot;estructOrg_uc_text&quot;).setDisabled();
    			document.getElementById(&quot;estructOrg_uc&quot;).setDisabled();
    			document.getElementById(&quot;estructOrg_mediador_text&quot;).setDisabled();
    			document.getElementById(&quot;estructOrg_mediador&quot;).setDisabled();
    		}else{
    			document.getElementById(&quot;estructOrg_sucursal_text&quot;).setEnabled();
    			document.getElementById(&quot;estructOrg_sucursal&quot;).setEnabled();
    		}
        }else{
        	document.getElementById(&quot;estructOrg_sucursal_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_sucursal&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_uc_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_uc&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador&quot;).setDisabled();
        }
	}
	
	function estructOrganFnc_listenerUCs(cadena){
		if (cadena != '' &amp;&amp; cadena.indexOf(&quot;ERROR&quot;) == -1) {
        	var opts = JSON.parse(cadena);
            if(opts.length > 0){
            	var selectObj = $('#estructOrg_uc');
				selectObj.empty();
				selectObj.append('&lt;option value=&quot;&quot;>&lt;/option>');
				for(i=0; i&lt;opts.length; i++){
					selectObj.append('&lt;option value=&quot;' + opts[i].value + '&quot;>' + opts[i].value+ ' - '+opts[i].text + '&lt;/option>');
				}				

				
				$('#estructOrg_uc').val(cerosTamCuatro(document.getElementById('estructOrg_uc_text').value));

				//Per si falla per tema de dades.
				if($('#estructOrg_uc').val() == ''){
					$('#estructOrg_uc').val(document.getElementById('estructOrg_uc_text').value);
				}
				
				
	    		if(document.getElementById(&quot;estructOrg_uc&quot;).length &lt; 2){
	    			document.getElementById(&quot;estructOrg_uc_text&quot;).setDisabled();
	    			document.getElementById(&quot;estructOrg_uc&quot;).setDisabled();
	    		}else{
	    			document.getElementById(&quot;estructOrg_uc_text&quot;).setEnabled();
	    			document.getElementById(&quot;estructOrg_uc&quot;).setEnabled();
	    		}
            } else {
				document.getElementById('estructOrg_uc').disabled = true;
				document.getElementById('estructOrg_uc_text').setDisabled();
				estructOrganFnc_llamar(estructOrganFnc_listenerMediadores, 'AG');
            }
        }else{
        	document.getElementById(&quot;estructOrg_uc_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_uc&quot;).setDisabled();
        	document.getElementById(&quot;estructOrg_mediador_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador&quot;).setDisabled();
        }
	}
	
	function estructOrganFnc_listenerMediadores(cadena){
        if (cadena != '' &amp;&amp; cadena.indexOf(&quot;ERROR&quot;) == -1) {
            var opts = JSON.parse(cadena);
            estructOrganFnc_fillCombo(opts,'estructOrg_mediador');
            $('#estructOrg_mediador').val(cerosTamCuatro(document.getElementById('estructOrg_mediador_text').value));
    		if(document.getElementById(&quot;estructOrg_mediador&quot;).length &lt; 2){
    			document.getElementById(&quot;estructOrg_mediador_text&quot;).setDisabled();
    			document.getElementById(&quot;estructOrg_mediador&quot;).setDisabled();
    		}else{
    			document.getElementById(&quot;estructOrg_mediador_text&quot;).setEnabled();
    			document.getElementById(&quot;estructOrg_mediador&quot;).setEnabled();
    		}
        }else{
        	document.getElementById(&quot;estructOrg_mediador_text&quot;).setDisabled();
			document.getElementById(&quot;estructOrg_mediador&quot;).setDisabled();
        }
	}
	
	function estructOrganFnc_cargarDTs(valor){
		$('#estructOrg_dc').val(valor);
		document.getElementById('estructOrg_dc_text').value = valor;
		if(eo_cia == 4) {
			estructOrganFnc_clearCombo('estructOrg_dt');
			document.getElementById('estructOrg_dt_text').value = '';
			estructOrganFnc_clearCombo('estructOrg_sucursal');
			document.getElementById('estructOrg_sucursal_text').value = '';
			estructOrganFnc_clearCombo('estructOrg_uc');
			document.getElementById('estructOrg_uc_text').value = '';
			estructOrganFnc_clearCombo('estructOrg_mediador');
			document.getElementById('estructOrg_mediador_text').value = '';
		
			if(perfil == &quot;CENTRAL&quot;){
				estructOrganFnc_llamar(estructOrganFnc_listenerDTs, 'DT');
			}else{
				estructOrganFnc_llamar(estructOrganFnc_listenerDTs, 'PT');
			}
		}else{
			estructOrganFnc_cargarSucursales(valor);
		}
	}
		
	function estructOrganFnc_cargarSucursales(valor){
		$('#estructOrg_dt').val(valor);
		document.getElementById('estructOrg_dt_text').value = valor;
		
		document.getElementById('estructOrg_sucursal_text').value = '';
		estructOrganFnc_clearCombo('estructOrg_uc');
		document.getElementById('estructOrg_uc_text').value = '';
		estructOrganFnc_clearCombo('estructOrg_mediador');
		document.getElementById('estructOrg_mediador_text').value = '';
		
		if(!true){
			document.getElementById('estructOrg_uc').disabled = false;
			document.getElementById('estructOrg_uc_text').setEnabled();
		}
		if((eo_cia == 4) || (eo_cia == 8)){
			if(perfil == &quot;CENTRAL&quot;){
				estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'SB');
			}else{
				estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'PU');
			}
		}else{
			if(perfil == &quot;CENTRAL&quot;){
				estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'SU');
			}else{
				estructOrganFnc_llamar(estructOrganFnc_listenerSucursales, 'PU');
			}
		}
	}
	
	function estructOrganFnc_cargarUCs(valor){
		$('#estructOrg_sucursal').val(valor);
		document.getElementById('estructOrg_sucursal_text').value = valor;
		
		document.getElementById('estructOrg_uc_text').value = '';
		estructOrganFnc_clearCombo('estructOrg_uc');
		document.getElementById('estructOrg_mediador_text').value = '';
		estructOrganFnc_clearCombo('estructOrg_mediador');
		
		document.getElementById('estructOrg_uc').disabled = false;
		document.getElementById('estructOrg_uc_text').setEnabled();
		
		estructOrganFnc_llamar(estructOrganFnc_listenerUCs, 'UC');
	}
	
	function estructOrganFnc_cargarMediadores(valor){
		$('#estructOrg_uc').val(valor);
		document.getElementById('estructOrg_uc_text').value = valor;
		document.getElementById('estructOrg_mediador_text').value = '';
		estructOrganFnc_clearCombo('estructOrg_mediador');
		estructOrganFnc_llamar(estructOrganFnc_listenerMediadores, 'AG');
	}
	
	function estructOrganFnc_changeMediador(valor){
		$('#estructOrg_mediador').val(valor);
		document.getElementById('estructOrg_mediador_text').value = valor;
	}

	function estructOrganFnc_chgDCTxt(valor){
		estructOrganFnc_ManualChg();
	}
	function estructOrganFnc_chgDTTxt(valor){
		estructOrganFnc_ManualChg();
	}
	function estructOrganFnc_chgSucursalTxt(valor){
		estructOrganFnc_ManualChg();
	}	
	function estructOrganFnc_chgUCTxt(valor){
		estructOrganFnc_ManualChg();
	}	
	function estructOrganFnc_chgMediadorTxt(valor){
		estructOrganFnc_ManualChg();
	}
	function _trimDef(str, def){
		if ( str != null){
			str = str.replace(/^\s+|\s+$/g, &quot;&quot;);
		}else{
			str = def;
		}
		return str;
	}
	function estructOrganFnc_ManualChg(){
		var dc =  _trimDef(document.getElementById('estructOrg_dc_text').value,'');
		var dt =  _trimDef(document.getElementById('estructOrg_dt_text').value,'');
		var sucu =  _trimDef(document.getElementById('estructOrg_sucursal_text').value,'');
		var uc = _trimDef(document.getElementById('estructOrg_uc_text').value,'');
		var med = _trimDef(document.getElementById('estructOrg_mediador_text').value,'');
		if ( dc.length != 0 || sucu.length != 0 || uc.length != 0 || med.length != 0) {
			estructOrganFnc_llamar(estructOrganFnc_listenerValidacion, 'VD');		
		}
	}

	
	function estructOrganFnc_listenerValidacion(cadena){
	
		if ( cadena != ''){
			var opts = JSON.parse(cadena);
			if ( cadena.indexOf(&quot;ERROR&quot;) != -1){
				void(false);
				alert(opts[0].MSG);		
			}
			else{
				void(true);
			}
		}
	}
	
	function cerosTamCuatro(num){
		if(num == '') return '';
		
		while(num.length&lt;4){
			num = &quot;0&quot;+num;
		}
		return num;
	}
	
	var estructOrganInfo = {};
    estructOrganInfo.preDisabled = {dc: true, dt: true, sucursal: true, uc: true, mediador: true};
    
	





	
							
							
		
		              
		
							
		
		                          
							
							
		
		              
							
				   		  
		
	              
							
							
			                        
	Import(&quot;controls.EntryNumber&quot;);
Import(&quot;controls.EntryCurrency&quot;);
Import(&quot;controls.EntryCombo&quot;);











DC
















0001 - REGIONAL SUL0002 - REGIONAL SÃO PAULO0003 - REGIONAL SP INTERIOR0004 - REGIONAL RJ/ES/CORPORATE0005 - REGIONAL CO/MG0006 - REGIONAL N/NE0007 - REGIONAL CORPORATE SP0008 - REGIONAL CANAIS ALTERNATIVOS0010 - COMERCIAL0011 - AGRICOLA0012 - CORPORATE0013 - ENCERRADAS0014 - OUTRAS AREAS0090 - MATRIZ














DT

















     
















Sucursal

















     
















UC

















     
















Corretor

















     

















					














Agrupamento de Ramos










    
    Acidentes Coletivo            
    Acidentes Individual          
    Agronegócios                  
    Auto Frota                    
    Automóvel                     
    AGCS                          
    Caminhão                      
    Condomínio                    
    Empresa/Master                
    Engenharia                    
    Garantia                      
    Moto                          
    Outros Industriais            
    Outros P&amp;C                    
    Prueba Simulador              
    Residência Digital            
    Residencia                    
    RC                            
    RD                            
    RN/RO/Patrimoniais            
    Saúde Administrados           
    Saúde GG                      
    Saúde PME                     
    Transportes/Náutica           
    Viagem                        
    Vida Coletivo                 
    Vida Individual               

















Ramo










    





























								



























	


	
  		Import(&quot;/drwd00/js/class.widgets.extendTag.ShowPolicy.js&quot;);
  	
  	

  	
		
		
          	
          	
        Import(&quot;controls.EntryNumber&quot;);
Import(&quot;controls.EntryCurrency&quot;);
Import(&quot;controls.EntrySusep&quot;);











Apólice
















Item


















        


        
	    	$(document.getElementById('poliza_SUSEP')).blur(function() {
	    		document.getElementById('poliza_Item').setDisabled();
	    		getPolicy(this);
	    	});
	    
        

		
        
	

							






















Nome do Segurado


























Vencimento










    
    2018-10
    2018-09
    2018-08
    2018-07
    2018-06
    2018-05
    2018-04



















Status










    Todos
    Pendente
    Recusada
    Renovada
    Não renovada
    NR com 180 días
    Prorrogada
    Anulada
    Para ser anulada
    A tarificar
    Para ser anulada (FRENO)
    Em processo





































Limpar






Filtrar














 





 






RELAÇÃO APÓLICES






 










 

Apólice
Item
CPF/Nome
Ramo
Fim de Vigencia
Prazo para Renovação
Prêmio Liq. Atual
Prêmio Liq. Anterior
Status










  Não existem dados    
 



var bExtendInfoTable=true;
var aLstExtendInfo_idPoliza = [];






















Editar filtro














 



function formValidation() {
	var bError=false;
	if ($('#form').attr('novalidate')=='novalidate' || $('#form').attr('novalidate')==true || $('#form').attr('novalidate')=='true') {
		return true;
	}
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validateBusquedaNormal() == true) {
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validateSusep() == true) {
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validarSiMediadoryVencimientoInformado() == true) {
if ( agrupaRamosObligatorio(&quot;agrupaRamos&quot;,&quot;form&quot;) == false ) {
	Errors.setItem(&quot;agrupaRamos&quot;, &quot;Agrupamento de Ramos não informado&quot;,null,&quot;form&quot;);
	bError = bError || true;
}
	}
	bError = Errors.validationRequired('form', 'vencimiento', 'Vencimento Não Informado') || bError;
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validacionesDT() == true) {
	bError = Errors.validationRequired('form', 'estructOrg_dc_text', 'Ao informar o campo DT, se deve informar anteriormente o campo DC.') || bError;
	}
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validacionesSuc() == true) {
	bError = Errors.validationRequired('form', 'estructOrg_dc_text', 'Ao informar o campo Sucursal, se deve informar anteriormente os campos DT e DC.') || bError;
	bError = Errors.validationRequired('form', 'estructOrg_dt_text', 'Ao informar o campo Sucursal, se deve informar anteriormente os campos DT e DC.') || bError;
	}
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validacionesUC() == true) {
	bError = Errors.validationRequired('form', 'estructOrg_dc_text', 'Ao informar o campo UC, se deve informar anteriormente os campos DT , DC e Sucursal.') || bError;
	bError = Errors.validationRequired('form', 'estructOrg_dt_text', 'Ao informar o campo UC, se deve informar anteriormente os campos DT , DC e Sucursal.') || bError;
	bError = Errors.validationRequired('form', 'estructOrg_sucursal_text', 'Ao informar o campo UC, se deve informar anteriormente os campos DT , DC e Sucursal.') || bError;
	}
	}
	}
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validateBusquedaTomador() == true) {
	bError = Errors.validationRequired('form', 'nomTomadorFiltro', 'Não está indicado') || bError;
if ( validaNomTomador(&quot;nomTomadorFiltro&quot;,&quot;form&quot;) == false ) {
	Errors.setItem(&quot;nomTomadorFiltro&quot;, &quot;Deve introduzir pelo menos 2 caracteres.&quot;,null,&quot;form&quot;);
	bError = bError || true;
}
	}
	var element = document.getElementById('form').elements.namedItem('idDependency');
	if(element==null || element.length!=null)
		element = document.getElementById('idDependency');
	if (validarAceptar() == true) {
	bError = Errors.validationRequired('form', 'reajuste', 'Não está indicado') || bError;
if ( validaNegativoPar(&quot;reajuste&quot;,&quot;form&quot;) == false ) {
	Errors.setItem(&quot;reajuste&quot;, &quot;O valor introduzido não deve ser negativo.&quot;,null,&quot;form&quot;);
	bError = bError || true;
}
if ( validaNCeroPar(&quot;reajuste&quot;,&quot;form&quot;) == false ) {
	Errors.setItem(&quot;reajuste&quot;, &quot;O valor introduzido não deve ser zero.&quot;,null,&quot;form&quot;);
	bError = bError || true;
}
	}
	return !bError;
}








		
		function validateBusquedaNormal(valor){
			var acc =$(&quot;#form input[name=action]&quot;).val();
			if (acc == 'buscarRenovaciones') {
				return true;
			} else {
				return false;
			}
		}
		
		function validateBusquedaTomador(valor){
			var acc =$(&quot;#form input[name=action]&quot;).val();
			if (acc == 'buscarNombre') {
				return true;
			} else {
				return false;
			}
		}
		
		function validaNegativoPar(val){
			var value = document.getElementById(val).value;
			if (value&lt; 0) {
				return false;
			} else {
				return true;
			}
		}
	
		function validaNCeroPar(val){ //No entra por alli desde pantalla combustible
			var value = document.getElementById(val).value;
			if (value == 0) {
				return false;
			} else {
				return true;
			}
		}
		
		function validaDcMed(){
			var value1 = $('#estructOrg_dc_text').val(); 
			var value2 = $('#estructOrg_mediador_text').val(); 
			if (value1 == '' &amp;&amp; value2=='') {
				return false;
			} else {
				return true;
			}
		}
		
		function validacionesDT(){
			var value1 = $('#estructOrg_dt_text').val(); 
			var value2 = $('#estructOrg_dc_text').val(); 
			if (value1 != '' &amp;&amp; value2==''){
				return true;
			}else{
				return false;
			}
		}
		
		function validacionesSuc() {
			var value1 = $('#estructOrg_dt_text').val(); 
			var value2 = $('#estructOrg_dc_text').val(); 
			var value3 = $('#estructOrg_sucursal_text').val(); 
			if (value3!='' &amp;&amp; (value1 == '' || value2=='' )){
				return true;
			}else{
				return false;
			}
		}
		
		function validacionesUC(){
			var value1 = $('#estructOrg_dt_text').val(); 
			var value2 = $('#estructOrg_dc_text').val(); 
			var value3 = $('#estructOrg_sucursal_text').val(); 
			var value4 = $('#estructOrg_uc_text').val(); 
			if (value4 != '' &amp;&amp; (value2==''||value3==''||value1=='')){
				return true;
			}else{
				return false;
			}
		}
		
		var indTabla 	= &quot;false&quot;;		
		var form		= document.getElementById('form');
		
		function irPaginaSiguiente(){
			document.getElementById('accionPaginacion').value='A';
			sendSEAEvent(document.getElementById('formMenu'),'buscarRenovaciones');
		}
		
		function irPaginaAnterior(){
			document.getElementById('accionPaginacion').value='R';
			sendSEAEvent(document.getElementById('formMenu'),'buscarRenovaciones');
		}
		
		function irPaginaSiguienteNombre(){
			document.getElementById('accionPaginacion').value='A';
			sendSEAEvent(document.getElementById('formMenu'),'buscarNombre');
		}
		
		function irPaginaAnteriorNombre(){
			document.getElementById('accionPaginacion').value='R';
			sendSEAEvent(document.getElementById('formMenu'),'buscarNombre');
		}

		function irDetallePoliza(idPoliza, id){				
			
				getPolicy(document.getElementById(&quot;poliza_SUSEP&quot;));
			
			if(indTabla == &quot;true&quot;){
				document.getElementById('contador').value = id;
			 	sendSEAEvent(form,'renovacionesDetalle');
			}
		}
		
		function consultaTomador(id){		
			document.getElementById('contador').value = id;
			sendSEAEvent(form,'buscarTomador');
		}			
		
		function cambiarEstadoNR(idPoliza, id){			
			var mensaje1 = &quot;A apolíce se tornará renovável&quot;;
			var mensaje2 = &quot;Tem a certeza de que pretende efetuar esta operação?&quot;;
 			var confirmacion = confirm(mensaje1 + &quot;\n\n&quot; + mensaje2);
			 				
			if (confirmacion) { 					
			
				getPolicy(document.getElementById(&quot;poliza_SUSEP&quot;));
			
			if (id != '' &amp;&amp; id != undefined){		
				$(&quot;#form input[name='idPolizaSel']&quot;).val(id);
			}
			sendSEAEvent(form,'cambiarEstadoNR');
			}
		}			
		
		function validarDetalle(){
			indTabla = &quot;true&quot;;
		}
		
		function buscarMediador(){
		var sucursal		= document.getElementById('sucursal').value;
			if(sucursal != &quot;&quot;){
				sendSEAEvent(form,'buscarMediador');
			}
		}
		
		function paintItBack(){
			var colors = eval(&quot;[]&quot;);
			for(var i = 0; i &lt; colors.length; i++){
				$(&quot;#row_&quot; + colors[i] + &quot;_idPoliza&quot;).css(&quot;background-color&quot;, &quot;#CFD0D2&quot;);
			}
		}
					
		function validateSusep(){
			
			return $(&quot;#poliza_SUSEP&quot;).val() == &quot;&quot; &amp;&amp; form.action.value != &quot;limpiar&quot;;
			
		}
		
		function validaNomTomador() {
			var nomTomadorFiltro = $.trim($(&quot;#nomTomadorFiltro&quot;).val());
			if (nomTomadorFiltro.length &lt; 2) {
				return false;
			} else {
				return true;
			}
		}
		
		
		function validarSiMediadoryVencimientoInformado(){
			//Si el campo mediador y la fecha de vencimiento estan informados no es obligatorio seleccionar Agrupamento Ramos
			if($(&quot;#vencimiento&quot;).val() != '' &amp;&amp; $('#estructOrg_mediador_text').val() != ''){
				// no obligatorio
				return false;
			}else{
				//obligatorio
				return true;
			}
		}
		
		function agrupaRamosObligatorio(){
			if($('#estructOrg_mediador').val() == '' || $('#estructOrg_mediador').val() == null){		
				if($('#agrupaRamos').val() == ''){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}
	
		paintItBack();
		
		addEvent(cambiaMediador, &quot;change&quot;, &quot;estructOrg_mediador_text&quot;);
		addEvent(cambiaMediador, &quot;change&quot;, &quot;estructOrg_mediador&quot;);
		
		//addLoadEvent(cambiaMediador);
		//addLoadEvent(bloquearFiltro);
		window.onload = function() {
			
		
		
				
		  cambiaMediador();
		  var nomTomadorFiltro = $(&quot;#nomTomadorFiltro&quot;).val();
			if (nomTomadorFiltro != &quot;&quot;) {
				// SFS-1900
				//deshabilitarCampo('estructOrg_dc_text');
				$(&quot;#agrupaRamos&quot;).val(&quot;&quot;);
				deshabilitarCampo('agrupaRamos');
				$(&quot;#vencimiento&quot;).val(&quot;&quot;);
				deshabilitarCampo('vencimiento');
				$(&quot;#estadoF&quot;).val(&quot;00&quot;); //todos
				deshabilitarCampo('estadoF');
				
					$(&quot;#poliza_SUSEP&quot;).val(&quot;&quot;);
					deshabilitarCampo('poliza_SUSEP');
					$(&quot;#poliza_Item&quot;).val(&quot;&quot;);
					deshabilitarCampo('poliza_Item');
				
			}
			calculaComboRamos();
			// el widgetr de esctructura org no funciona bien el disabled se hace manual
			
		};
		
		
		
	



									
			
			
		
	




Brasil 2018
Topo 












/html[@class=&quot;js canvas geolocation video audio localstorage sessionstorage texttrackapi track&quot;] ProcessingA processar ...    </value>
   </webElementProperties>
   <webElementProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath</name>
      <type>Main</type>
      <value>/html[@class=&quot;js canvas geolocation video audio localstorage sessionstorage texttrackapi track&quot;]</value>
   </webElementProperties>
</WebElementEntity>
