package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QEstudante;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class EstudanteFilterData implements FilterData<QEstudante> {

	private Long userId;
	private Long id;
	private String nome;
	private Long clienteId;
	
	private Boolean fetchCliente = false;
	
	private Boolean fetchInstituicoes = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	
	public EstudanteFilterData(){
	}
	
	public EstudanteFilterData(Long userId, String nome){
		setUserId(userId);
		setNome(nome);
	}
	
	public EstudanteFilterData(Long userId, Long id){
		setUserId(userId);
		setId(id);
	}
	
	public EstudanteFilterData(Long clienteId) {
		setClienteId(clienteId);
	}
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("userId", getUserId());
		filter.put("nome", "%" + getNome() + "%");
		
		return filter;
	}
	
	@Override
	public Predicate getParamsQ(QEstudante estudante){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(id != null)
			builder.and(estudante.id.eq(id));
		if(userId != null)
			builder.and(estudante.cliente.transportador.usuario.id.eq(userId));
		if(clienteId != null)
			builder.and(estudante.cliente.id.eq(clienteId));
		if(nome != null)
			builder.and(estudante.nome.containsIgnoreCase(nome));
			
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QEstudante estudante){
		
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchCliente)
			fetchLst.add(estudante.cliente);
		
		if(fetchInstituicoes)
			fetchLst.add(estudante.instituicoes);
		
		return fetchLst;
	}
	
	@Override
	public OrderSpecifier<?> getOrderByQ(QEstudante estudante){
		
		if(orderBy == null)
			return null;
		
		switch (orderBy) {
			case BY_ID_ASC:
				return estudante.id.asc();
			case BY_ID_DESC:
				return estudante.id.desc();
			default:
				return null;
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFetchCliente() {
		return fetchCliente;
	}

	public void setFetchCliente(Boolean fetchCliente) {
		this.fetchCliente = fetchCliente;
	}

	public Boolean getFetchInstituicoes() {
		return fetchInstituicoes;
	}

	public void setFetchInstituicoes(Boolean fetchInstituicoes) {
		this.fetchInstituicoes = fetchInstituicoes;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

}
