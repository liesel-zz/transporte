package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QContrato;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class ContratoFilterData implements FilterData<QContrato> {

	private Long id;
	private Long userId;
	
	private Integer ano;
	
	private Boolean fetchCliente = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	
	public ContratoFilterData(){
	}
		
	public ContratoFilterData(Long userId){
		setUserId(userId);
	}
	
	public ContratoFilterData(Long userId, Long id){
		setId(id);
		setUserId(userId);
	}
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		if(getUserId() != null)
			filter.put("userId", getUserId());
		
		return filter;
	}
	
	@Override
	public Predicate getParamsQ(QContrato contrato){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(id != null)
			builder.and(contrato.id.eq(id));
		
		if(userId != null)
			builder.and(contrato.cliente.transportador.usuario.id.eq(userId));
		
		if(ano != null && ano != 0)
			builder.and(contrato.dtIni.year().eq(ano).or(contrato.dtFim.year().eq(ano)));
		
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QContrato contrato){
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchCliente)
			fetchLst.add(contrato.cliente);
		
		return fetchLst;
	}

	@Override
	public OrderSpecifier<?> getOrderByQ(QContrato contrato){
		if(orderBy == null)
			return null;
		
		switch (orderBy) {
			case BY_ID_ASC:
				return contrato.id.asc();
			case BY_ID_DESC:
				return contrato.id.desc();
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

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Boolean getFetchCliente() {
		return fetchCliente;
	}

	public void setFetchCliente(Boolean fetchCliente) {
		this.fetchCliente = fetchCliente;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

}
