package com.alexcaetano.cursomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;		
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente ToEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoCliente c: TipoCliente.values()) {
			if(c.getCod() == cod) {
				return c;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + cod);	
	}
}
