package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QTransportador;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class TransportadorFilterData implements FilterData<QTransportador> {

	private Long id;
	
	private Long userId;
	
	private String nome;
	
	private String codVan;
	
	private Boolean fetchEndereco = false;
	
	private Boolean fetchUsuario = false;
	
	private Boolean fetchAssociacao = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	
	public TransportadorFilterData(){
	}
	
	public TransportadorFilterData(Long userId, Long id){
		setUserId(userId);
		setId(id);
	}
	
	public TransportadorFilterData(Long userId){
		setUserId(userId);
	}
	
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("nome", getNome());
		filter.put("codVan", getCodVan());
		
		return filter;
	}
	
	@Override
	public Predicate getParamsQ(QTransportador transportador){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(id != null)
			builder.and(transportador.id.eq(id));
		if(userId != null)
			builder.and(transportador.usuario.id.eq(userId));
		if(nome != null)
			builder.and(transportador.nome.containsIgnoreCase(nome));
		if(codVan != null)
			builder.and(transportador.codVan.containsIgnoreCase(codVan));
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QTransportador transportador){
		
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchEndereco)
			fetchLst.add(transportador.endereco);
		
		if(fetchUsuario)
			fetchLst.add(transportador.usuario);
		
		if(fetchAssociacao)
			fetchLst.add(transportador.associacao);
		
		return fetchLst;
	}
	
	@Override
	public OrderSpecifier<?> getOrderByQ(QTransportador transportador){
		
		if(orderBy == null)
			return null;
		
		switch (orderBy) {
			case BY_ID_ASC:
				return transportador.id.asc();
			case BY_ID_DESC:
				return transportador.id.desc();
			default:
				return null;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCodVan() {
		return codVan;
	}

	public void setCodVan(String codVan) {
		this.codVan = codVan;
	}

	public Boolean getFetchEndereco() {
		return fetchEndereco;
	}

	public void setFetchEndereco(Boolean fetchEndereco) {
		this.fetchEndereco = fetchEndereco;
	}

	public Boolean getFetchUsuario() {
		return fetchUsuario;
	}

	public void setFetchUsuario(Boolean fetchUsuario) {
		this.fetchUsuario = fetchUsuario;
	}

	public Boolean getFetchAssociacao() {
		return fetchAssociacao;
	}

	public void setFetchAssociacao(Boolean fetchAssociacao) {
		this.fetchAssociacao = fetchAssociacao;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
}
