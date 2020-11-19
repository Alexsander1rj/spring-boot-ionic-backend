package com.alexcaetano.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alexcaetano.cursomc.domain.Categoria;
import com.alexcaetano.cursomc.domain.Cidade;
import com.alexcaetano.cursomc.domain.Cliente;
import com.alexcaetano.cursomc.domain.Endereco;
import com.alexcaetano.cursomc.domain.Estado;
import com.alexcaetano.cursomc.domain.ItemPedido;
import com.alexcaetano.cursomc.domain.Pagamento;
import com.alexcaetano.cursomc.domain.PagamentoComBoleto;
import com.alexcaetano.cursomc.domain.PagamentoComCartao;
import com.alexcaetano.cursomc.domain.Pedido;
import com.alexcaetano.cursomc.domain.Produto;
import com.alexcaetano.cursomc.domain.enums.EstadoPagamento;
import com.alexcaetano.cursomc.domain.enums.TipoCliente;
import com.alexcaetano.cursomc.repositories.CategoriaRepository;
import com.alexcaetano.cursomc.repositories.CidadeRepository;
import com.alexcaetano.cursomc.repositories.ClienteRepository;
import com.alexcaetano.cursomc.repositories.EnderecoRepository;
import com.alexcaetano.cursomc.repositories.EstadoRepository;
import com.alexcaetano.cursomc.repositories.ItemPedidoRepository;
import com.alexcaetano.cursomc.repositories.PagamentoRepository;
import com.alexcaetano.cursomc.repositories.PedidoRepository;
import com.alexcaetano.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	EstadoRepository estadorepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Imppressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);

		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null,"São Pauolo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadorepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678911", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("1234345678", "12345679"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "APT 203", "Jardim", "38220834", c1, cli1);
		Endereco e2 = new Endereco(null, "Av Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("16/11/2020 12:00"), e1, cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("16/11/2020 12:30"), e2, cli1);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("16/11/2020 12:35"), null);
		
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(p1, ped1, 200.00, 1, 2000.00);		
		ItemPedido ip2 = new ItemPedido(p3, ped1, 200.00, 1, 2000.00);		
		ItemPedido ip3 = new ItemPedido(p2, ped2, 200.00, 1, 2000.00);
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}
