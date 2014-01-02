package com.mksdev.transport.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.lowagie.text.pdf.codec.Base64;
import com.mksdev.framework.security.entity.LoggedUser;
import com.mksdev.transport.dto.PagamentoReportDTO;
import com.mksdev.transport.entity.Cliente;
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.entity.Transportador;
import com.mksdev.transport.facade.ClienteFacade;
import com.mksdev.transport.facade.ContratoFacade;
import com.mksdev.transport.facade.EstudanteFacade;
import com.mksdev.transport.facade.PagamentoFacade;
import com.mksdev.transport.facade.TransportadorFacade;
import com.mksdev.transport.filter.ClienteFilterData;
import com.mksdev.transport.filter.ContratoFilterData;
import com.mksdev.transport.filter.EstudanteFilterData;

//FIXME FAZER SEGURANCA DO SERVLET
@WebServlet(name = "PagamentoServlet", urlPatterns = { "/servlet/pagamento" })
public class PagamentoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TransportadorFacade transportadorFacade;
	
	@Inject
	private PagamentoFacade pagamentoFacade;
	
	@Inject
	private ContratoFacade contratoFacade;
	
	@Inject
	private ClienteFacade clienteFacade;

	@Inject
	private EstudanteFacade estudanteFacade;
	
	private Contrato contrato;
	private Transportador transportador;
	private Set<Estudante> estudantes;
	private Cliente cliente;
	private String estudantesNome;
	
	private LoggedUser loggedUser;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.loggedUser = (LoggedUser) req.getSession(false).getAttribute("LOGGED_USER");
		
		Long contratoId = Long.parseLong(req.getParameter("contratoId"));
		String[] pagamentoIds = req.getParameter("pagamentoId").split(",");
		
		setPagamentoReport(contratoId);
		
		Long[] ids = new Long[pagamentoIds.length];
		for (int i = 0; i < pagamentoIds.length; i++) {
			ids[i] = Long.parseLong(pagamentoIds[i]);
		}
		
		List<Map<String, Object>> pagamentoReportLst = getPagamentoReportByIds(ids);

		Map<String, Object> parameters = new HashMap<String, Object>(); 
		parameters.put("parcelas", new JRBeanCollectionDataSource(pagamentoReportLst));
		
		JasperPrint jasperPrint;
		try {
			String reportPath = "pagamento.jasper";

			JasperReport relatorioJasper = (JasperReport) JRLoader
					.loadObject(getClass().getClassLoader()
							.getResource(reportPath).openStream());

			jasperPrint = JasperFillManager.fillReport(relatorioJasper, parameters, new JREmptyDataSource());

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
	
	private void setPagamentoReport(Long contratoId){
		this.contrato =  contratoFacade.findByFilter(new ContratoFilterData(loggedUser.getId(), contratoId));
		this.cliente = clienteFacade.findByFilter(new ClienteFilterData(loggedUser.getId(), contrato.getClienteId()));
		this.transportador = transportadorFacade.findById(cliente.getTransportadorId());
		
		EstudanteFilterData estudanteFilter = new EstudanteFilterData(cliente.getId());
		estudanteFilter.setFetchCliente(true);
		estudanteFilter.setFetchInstituicoes(true);
		this.estudantes = this.estudanteFacade.findByCliente(estudanteFilter);
		
		this.estudantesNome = "";
		
		for (Estudante est : this.estudantes) {
			if(this.estudantesNome == "")
				this.estudantesNome = est.getNome();
			else
				this.estudantesNome += ", " + est.getNome();
		}
	}
	
	private List<Map<String, Object>> getPagamentoReportByIds(Long[] pagamentosIds){
		List<Pagamento> pagamentoLst = pagamentoFacade.findByIds(loggedUser.getId(), pagamentosIds);
		List<Map<String, Object>> pagamentoReportLst = new ArrayList<Map<String,Object>>();
		
		PagamentoReportDTO reportDTO;
		int cont = 1;
		for (Pagamento pag : pagamentoLst) {
			
			reportDTO = new PagamentoReportDTO();
			
			reportDTO.setTransportadorNome(this.transportador.getNome());
			reportDTO.setTransportadorTelefone(this.transportador.getTelefone());
			reportDTO.setLogotipo(new ByteArrayInputStream(Base64.decode(this.transportador.getLogotipo().replaceAll("data:image/jpeg;base64,", ""))));
			reportDTO.setLogotipo2(new ByteArrayInputStream(Base64.decode(this.transportador.getLogotipo().replaceAll("data:image/jpeg;base64,", ""))));
			reportDTO.setClienteNome(this.cliente.getNome());
			reportDTO.setEstudantesNome(this.estudantesNome);
			reportDTO.setDtVencimento(pag.getDtVencimento());
			reportDTO.setValor(pag.getValor().floatValue());
			reportDTO.setParcelaNumero(cont);
			
			pagamentoReportLst.add(reportDTO.convertToMap());
			
			cont++;
			
		}
		
		return pagamentoReportLst;
	}

}
