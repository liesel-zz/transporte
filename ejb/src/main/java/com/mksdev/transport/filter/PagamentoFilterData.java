package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QPagamento;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class PagamentoFilterData implements FilterData<QPagamento> {

	private Long id;
	private Long contratoId;
	private Long userId;
	
	private Boolean fetchContrato = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	
	public PagamentoFilterData(){
	}
	
	public PagamentoFilterData(Long contratoId){
		setContratoId(contratoId);
	}
	
	public PagamentoFilterData(Long contratoId, Long userId){
		setContratoId(contratoId);
		setUserId(userId);
	}
	
	public PagamentoFilterData(Long contratoId, Long userId, Long id){
		setContratoId(contratoId);
		setUserId(userId);
		setId(id);
	}
	
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("contratoId", getContratoId());
		
		return filter;
	}
	
	@Override
	public Predicate getParamsQ(QPagamento pagamento){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(id != null)
			builder.and(pagamento.id.eq(id));
		if(userId != null)
			builder.and(pagamento.contrato.cliente.transportador.usuario.id.eq(userId));
		if(contratoId != null)
			builder.and(pagamento.contrato.id.eq(contratoId));
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QPagamento pagamento){
		
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchContrato)
			fetchLst.add(pagamento.contrato);
		
		return fetchLst;
	}
	
	@Override
	public OrderSpecifier<?> getOrderByQ(QPagamento pagamento){
		
		if(orderBy == null)
			return null;
		
		switch (orderBy) {
			case BY_ID_ASC:
				return pagamento.id.asc();
			case BY_ID_DESC:
				return pagamento.id.desc();
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

	public Long getContratoId() {
		return contratoId;
	}

	public void setContratoId(Long contratoId) {
		this.contratoId = contratoId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getFetchContrato() {
		return fetchContrato;
	}

	public void setFetchContrato(Boolean fetchContrato) {
		this.fetchContrato = fetchContrato;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	
}
