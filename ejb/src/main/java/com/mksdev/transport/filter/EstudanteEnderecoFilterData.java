package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QEstudanteEndereco;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class EstudanteEnderecoFilterData implements FilterData<QEstudanteEndereco> {
	
	private Long id;
	
	private Long estudanteId;
	
	private Long enderecoId;
	
	private Boolean fetchEndereco = false;
	
	private Boolean fetchEstudante = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	
	public EstudanteEnderecoFilterData(){
	}
	
	public EstudanteEnderecoFilterData(Long id){
		setId(id);
	}
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		if (getId() != null)
			filter.put("id", getId());
		
		return filter;
	}

	@Override
	public Predicate getParamsQ(QEstudanteEndereco estudanteEndereco){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(id != null)
			builder.and(estudanteEndereco.id.eq(id));
		if(enderecoId != null)
			builder.and(estudanteEndereco.endereco.id.eq(enderecoId));
		if(estudanteId != null)
			builder.and(estudanteEndereco.estudante.id.eq(estudanteId));
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QEstudanteEndereco estudanteEndereco){
		
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchEndereco)
			fetchLst.add(estudanteEndereco.endereco);
		
		if(fetchEstudante)
			fetchLst.add(estudanteEndereco.estudante);
		
		return fetchLst;
	}
	
	@Override
	public OrderSpecifier<?> getOrderByQ(QEstudanteEndereco estudanteEndereco){
		if(orderBy == null)
			return null;
			
		switch (orderBy) {
			case BY_ID_ASC:
				return estudanteEndereco.id.asc();
			case BY_ID_DESC:
				return estudanteEndereco.id.desc();
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

	public Long getEstudanteId() {
		return estudanteId;
	}

	public void setEstudanteId(Long estudanteId) {
		this.estudanteId = estudanteId;
	}

	public Long getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Long enderecoId) {
		this.enderecoId = enderecoId;
	}

	public Boolean getFetchEndereco() {
		return fetchEndereco;
	}

	public void setFetchEndereco(Boolean fetchEndereco) {
		this.fetchEndereco = fetchEndereco;
	}

	public Boolean getFetchEstudante() {
		return fetchEstudante;
	}

	public void setFetchEstudante(Boolean fetchEstudante) {
		this.fetchEstudante = fetchEstudante;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}


}
