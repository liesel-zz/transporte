package com.mksdev.transport.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.transport.entity.QCliente;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

public class ClienteFilterData implements FilterData<QCliente> {
	
	private Long userId;
	
	private Long id;
	
	private String nome;
	
	private String cgc;
	
	private Boolean fetchEndereco = false;
	
	private Boolean fetchTransportador = false;
	
	private Boolean fetchUsuario = false;
	
	private Integer orderBy;
	
	public static final int BY_ID_ASC = 1;
	public static final int BY_ID_DESC = 2;
	public static final int BY_NOME_ASC = 3;
	public static final int BY_NOME_DESC = 4;
	
	public ClienteFilterData(){
	}
	
	public ClienteFilterData(Long userId, Long id){
		setUserId(userId);
		setId(id);
	}
	
	public ClienteFilterData(Long userId, String nome){
		setUserId(userId);
		setNome(nome);
	}
	
	public ClienteFilterData(Long userId, String nome, String cgc){
		setUserId(userId);
		setNome(nome);
		setCgc(cgc);
	}
	
	public ClienteFilterData(Long userId, Long id, String nome, String cgc){
		setUserId(userId);
		setId(id);
		setNome(nome);
		setCgc(cgc);
	}
	
	@Override
	public Map<String, Object> getFilterData() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("userId", getUserId());
		if (getId() != null)
			filter.put("id", getId());
		if(getNome() != null)
			filter.put("nome", "%" + getNome() + "%");
		if(getCgc() != null)
			filter.put("cgc", "%" + getCgc() + "%");
		
		return filter;
	}

	@Override
	public Predicate getParamsQ(QCliente cliente){
		BooleanBuilder builder = new BooleanBuilder();
		
		if(id != null)
			builder.and(cliente.id.eq(id));
		if(userId != null)
			builder.and(cliente.transportador.usuario.id.eq(userId));
		if(nome != null)
			builder.and(cliente.nome.containsIgnoreCase(nome));
		if(cgc != null)
			builder.and(cliente.cgc.containsIgnoreCase(cgc));
		
		return builder;
		
	}
	
	@Override
	public List<Expression<?>> getJoinFetchQ(QCliente cliente){
		
		List<Expression<?>> fetchLst = new ArrayList<Expression<?>>();
		
		if(fetchEndereco)
			fetchLst.add(cliente.endereco);
		
		if(fetchTransportador)
			fetchLst.add(cliente.transportador);
		
		if(fetchUsuario)
			fetchLst.add(cliente.usuario);
		
		return fetchLst;
	}
	
	@Override
	public OrderSpecifier<?> getOrderByQ(QCliente cliente){
		if(orderBy == null)
			return null;
			
		switch (orderBy) {
			case BY_ID_ASC:
				return cliente.id.asc();
			case BY_ID_DESC:
				return cliente.id.desc();
			case BY_NOME_ASC:
				return cliente.nome.asc();
			case BY_NOME_DESC:
				return cliente.nome.asc();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCgc() {
		return cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
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

	public Boolean getFetchUsuario() {
		return fetchUsuario;
	}

	public void setFetchUsuario(Boolean fetchUsuario) {
		this.fetchUsuario = fetchUsuario;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

}
