package com.alexcaetano.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.alexcaetano.cursomc.domain.*;
import com.alexcaetano.cursomc.domain.enums.TipoCliente;
import com.alexcaetano.cursomc.dto.ClienteDTO;
import com.alexcaetano.cursomc.dto.ClienteNewDTO;
import com.alexcaetano.cursomc.repositories.EnderecoRepository;
import com.alexcaetano.cursomc.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alexcaetano.cursomc.domain.Cliente;
import com.alexcaetano.cursomc.repositories.ClienteRepository;
import com.alexcaetano.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);

		obj = repo.save(obj);

		enderecoRepository.saveAll(obj.getEnderecos());

		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());

		updateData(newObj, obj);

		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque existem entidades relacionadas");
		}

	}

	public List<Cliente> findAll(){
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);

	}

	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(),objDto.getNome(), objDto.getEmail(),null, null);
	}

	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente cliente = new Cliente(null,objDto.getNome(), objDto.getEmail(),objDto.getCpfOuCnpj(), TipoCliente.ToEnum(objDto.getTipo()));

		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);

		Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				                          objDto.getBairro(), objDto.getCep(),cidade, cliente);

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDto.getTelefone1());

		if(objDto.getTelefone2()!= null){
			cliente.getTelefones().add(objDto.getTelefone2());
		}

		if(objDto.getTelefone3()!= null){
			cliente.getTelefones().add(objDto.getTelefone3());
		}

		return cliente;
	}
}
