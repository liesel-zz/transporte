package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QAssociacao;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class AssociacaoFilterData implements FilterData<QAssociacao> {

	private String nomeAbrev;
	
	private Boolean fetchModeloContrato = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	
	public AssociacaoFilterData(String nomeAbrev){
		setNomeAbrev(nomeAbrev);
	}
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("nomeAbrev", "%" + getNomeAbrev() + "%");
		
		return filter;
	}

	@Override
	public Predicate getParamsQ(QAssociacao associacao){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(nomeAbrev != null)
			builder.and(associacao.nomeAbrev.containsIgnoreCase(nomeAbrev));
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QAssociacao associacao){
		
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchModeloContrato)
			fetchLst.add(associacao.modeloContrato);
		
		
		return fetchLst;
	}
	
	@Override
	public OrderSpecifier<?> getOrderByQ(QAssociacao associacao){
		switch (orderBy) {
			case BY_ID_ASC:
				return associacao.id.asc();
			case BY_ID_DESC:
				return associacao.id.desc();
			default:
				return null;
		}
	}

	public String getNomeAbrev() {
		return nomeAbrev;
	}

	public void setNomeAbrev(String nomeAbrev) {
		this.nomeAbrev = nomeAbrev;
	}

	public Boolean getFetchModeloContrato() {
		return fetchModeloContrato;
	}

	public void setFetchModeloContrato(Boolean fetchModeloContrato) {
		this.fetchModeloContrato = fetchModeloContrato;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	
}

