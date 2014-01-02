package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QInstituicao;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class InstituicaoFilterData implements FilterData<QInstituicao> {
	
	private Long id;
	private Long userId;
	private String nome;
	
	private Boolean fetchEndereco = false;
	
	private Boolean fetchTransportador = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	
	public InstituicaoFilterData(){
	}
	
	public InstituicaoFilterData(Long userId, Long id){
		setUserId(userId);
		setId(id);
	}
	
	public InstituicaoFilterData(Long userId, String nome){
		setUserId(userId);
		setNome(nome);
	}
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("userId", getUserId());
		
		if (getId() != null)
			filter.put("id", getId());
		
		if (getNome() != null)
			filter.put("nome", "%" + getNome() + "%");
		
		return filter;
	}
	
	@Override
	public Predicate getParamsQ(QInstituicao instituicao){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(id != null)
			builder.and(instituicao.id.eq(id));
		if(userId != null)
			builder.and(instituicao.transportador.usuario.id.eq(userId));
		if(nome != null)
			builder.and(instituicao.nome.containsIgnoreCase(nome));
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QInstituicao instituicao){
		
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchEndereco)
			fetchLst.add(instituicao.endereco);
		
		if(fetchTransportador)
			fetchLst.add(instituicao.transportador);
		
		return fetchLst;
	}
	
	@Override
	public OrderSpecifier<?> getOrderByQ(QInstituicao instituicao){
		
		if(orderBy == null)
			return null;
		
		switch (orderBy) {
			case BY_ID_ASC:
				return instituicao.id.asc();
			case BY_ID_DESC:
				return instituicao.id.desc();
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

	public Boolean getFetchEndereco() {
		return fetchEndereco;
	}

	public void setFetchEndereco(Boolean fetchEndereco) {
		this.fetchEndereco = fetchEndereco;
	}

	public Boolean getFetchTransportador() {
		return fetchTransportador;
	}

	public void setFetchTransportador(Boolean fetchTransportador) {
		this.fetchTransportador = fetchTransportador;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
}
