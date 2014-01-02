package com.mksdev.transport.servlet;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mksdev.framework.security.entity.LoggedUser;
import com.mksdev.framework.security.entity.User;
import com.mksdev.transport.entity.Associacao;
import com.mksdev.transport.entity.Cliente;
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.entity.Instituicao;
import com.mksdev.transport.entity.ModeloContrato;
import com.mksdev.transport.entity.Transportador;
import com.mksdev.transport.facade.ClienteFacade;
import com.mksdev.transport.facade.ContratoFacade;
import com.mksdev.transport.facade.EstudanteFacade;
import com.mksdev.transport.facade.ModeloContratoFacade;
import com.mksdev.transport.facade.TransportadorFacade;
import com.mksdev.transport.filter.ClienteFilterData;
import com.mksdev.transport.filter.ContratoFilterData;
import com.mksdev.transport.filter.EstudanteFilterData;
import com.mksdev.transport.filter.TransportadorFilterData;

// FIXME FAZER SEGURANCA DO SERVLET
@WebServlet(name = "ContratoServlet", urlPatterns = { "/servlet/contrato" })
public class ContratoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContratoFacade contratoFacade;
	
	@Inject
	private ClienteFacade clienteFacade;
	
	@Inject
	private EstudanteFacade estudanteFacade;
	
	@Inject
	private TransportadorFacade transportadorFacade;
	
	@Inject
	private ModeloContratoFacade modeloContratoFacade;
	
	private Contrato contrato;
	private Transportador transportador;
	private Cliente cliente;
	private Set<Estudante> estudantes;
	private ModeloContrato modelo;
	private Associacao associacao;
	private User user;
	private LoggedUser loggedUser;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		loggedUser = (LoggedUser) req.getSession(false).getAttribute("LOGGED_USER");

		Long contratoId = Long.parseLong(req.getParameter("contratoId"));

		JasperPrint jasperPrint;
		try {
			Map<String, Object> params = replaceParams(loggedUser.getId(), contratoId);

			String reportPath = (String) params.get("reportPath");

			JasperReport relatorioJasper = (JasperReport) JRLoader
					.loadObject(getClass().getClassLoader()
							.getResource(reportPath).openStream());

			jasperPrint = JasperFillManager.fillReport(relatorioJasper, params, new JREmptyDataSource());

			ServletOutputStream servletOutputStream = resp.getOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);

			resp.setContentType("application/pdf");

			servletOutputStream.flush(); 
			servletOutputStream.close(); 

		} catch (JRException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, Object> replaceParams(Long userId, Long contratoId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		this.contrato = contratoFacade.findByFilter(new ContratoFilterData(userId, contratoId));
		
		ClienteFilterData filterCliente = new ClienteFilterData(userId, contrato.getClienteId());
		filterCliente.setFetchEndereco(true);
		
		this.cliente = clienteFacade.findByFilter(filterCliente);
	
		EstudanteFilterData estudanteFilter = new EstudanteFilterData(cliente.getId());
		estudanteFilter.setFetchCliente(true);
		estudanteFilter.setFetchInstituicoes(true);
		this.estudantes = this.estudanteFacade.findByCliente(estudanteFilter);

		TransportadorFilterData transportadorFilter = new TransportadorFilterData();
		transportadorFilter.setFetchUsuario(true);
		transportadorFilter.setFetchAssociacao(true);
		transportadorFilter.setFetchEndereco(true);
		transportadorFilter.setId(cliente.getTransportadorId());
		this.transportador = transportadorFacade.findByFilter(transportadorFilter);
		this.user = transportador.getUsuario();

		if (user.getId() != userId)
			throw new Exception("Contrato não pertence ao usuário logado!");

		this.associacao = transportador.getAssociacao();

		if(associacao == null){
			throw new Exception("Transportador não está vinculado a nenhuma associação!");
		}

		this.modelo = modeloContratoFacade.findById(associacao.getModeloContratoId());

		if(this.modelo == null){
			throw new Exception("Não existe nenhum modelo de contrato para a associação do transportador!");
		}

		replaceParamsACTEJ();

		params.put("reportPath", modelo.getNome());
		params.put("titulo", modelo.getTitulo());
		params.put("corpo", modelo.getCorpo());
		params.put("rodape1", modelo.getRodape1());
		params.put("rodape2", modelo.getRodape2());
		params.put("localdata", modelo.getLocaldata());

		return params;
	}

	private void replaceParamsACTEJ(){
		int i = 0;
		int j = 0;
		String estudantesNome = "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _";
		String instituicoesNome = "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _";
		String corpo = modelo.getCorpo();
		Locale meuLocal = new Locale( "pt", "BR" );  
		NumberFormat nfVal = NumberFormat.getCurrencyInstance( meuLocal );
		try {
			MaskFormatter mf = new MaskFormatter();
			mf.setValueContainsLiteralCharacters(false);
		
			Calendar contratoDt = new GregorianCalendar();
			
			for (Estudante item : estudantes) {
				if (i == 0)
					estudantesNome = item.getNome();
				else
					estudantesNome += " / " + item.getNome();
				
				for (Instituicao instituicao : item.getInstituicoes()) {
					if(j == 0)
						instituicoesNome = instituicao.getNomeAbrev();
					else
						instituicoesNome += " / " + instituicao.getNomeAbrev(); 
					
					j++;
				}
				
				i++;
			}
	
			corpo = corpo.replace("#transportadorNome#", transportador.getNome());
			corpo = corpo.replace("#transportadorCodVan#", transportador.getCodVan());
			corpo = corpo.replace("#transportadorPlaca#", transportador.getPlacaVan());;
			corpo = corpo.replace("#transportadorRG#", transportador.getRg());
			
			if (transportador.getCgc().length() > 11)
				mf.setMask("##.###.####/####-##");
			else
				mf.setMask("###.###.###-##");
			
			corpo = corpo.replace("#transportadorCGC#", mf.valueToString(transportador.getCgc()));
			corpo = corpo.replace("#transpEndereco#", transportador.getEndereco().getEndereco());
			corpo = corpo.replace("#transpEnderecoNumero#", transportador.getEndereco().getNumero().toString());
			corpo = corpo.replace("#transpEnderecoCompl#", transportador.getEndereco().getComplemento());
			corpo = corpo.replace("#transpEnderecoBairro#", transportador.getEndereco().getBairro());
			corpo = corpo.replace("#transpEnderecoCidade#", transportador.getEndereco().getCidade());
			corpo = corpo.replace("#transpEnderecoUF#", transportador.getEndereco().getUf());
			corpo = corpo.replace("#clienteNome#", cliente.getNome());
			corpo = corpo.replace("#clienteRG#", cliente.getRg());
			
			if (cliente.getCgc().length() > 11)
				mf.setMask("##.###.####/####-##");
			else
				mf.setMask("###.###.###-##");
			
			corpo = corpo.replace("#clienteCPF#", mf.valueToString(cliente.getCgc()));
			corpo = corpo.replace("#clienteEndereco#", cliente.getEndereco().getEndereco());
			corpo = corpo.replace("#clienteEnderecoNro#", cliente.getEndereco().getNumero().toString());
			corpo = corpo.replace("#clienteEnderecoCompl#", cliente.getEndereco().getComplemento());
			corpo = corpo.replace("#clienteEnderecoBairro#", cliente.getEndereco().getBairro());
			corpo = corpo.replace("#clienteEnderecoCidade#", cliente.getEndereco().getCidade());
			corpo = corpo.replace("#clienteEnderecoUF#", cliente.getEndereco().getUf());
			corpo = corpo.replace("#estudanteNome#", estudantesNome);
			corpo = corpo.replace("#instituicaoNome#", instituicoesNome);
			corpo = corpo.replace("#contratoValor#", nfVal.format(contrato.getVlParcelas().doubleValue() * contrato.getQtParcelas()));
			corpo = corpo.replace("#contratoParcelas#", contrato.getQtParcelas().toString());
			corpo = corpo.replace("#contratoValorParcelas#", nfVal.format(contrato.getVlParcelas()));
			corpo = corpo.replace("#contratoDiaPagto#", contrato.getDiaPagto().toString());
			
			contratoDt.setTimeInMillis(contrato.getDtIni().getTime());
			corpo = corpo.replace("#contratoAno#", String.valueOf(contratoDt.get(Calendar.YEAR)));
	
			modelo.setCorpo(corpo);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	private Map<String, Object> readParams(HttpServletRequest req) {
	//		Map<String, Object> params = new HashMap<String, Object>();
	//		Enumeration<String> parameters = req.getParameterNames();
	//		while(parameters.hasMoreElements()){
	//			String param = (String) parameters.nextElement();
	//			params.put(param, req.getParameter(param));
	//		}
	//		return params;
	//	}
}
