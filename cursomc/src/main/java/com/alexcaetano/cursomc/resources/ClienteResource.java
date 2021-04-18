package com.alexcaetano.cursomc.resources;

import com.alexcaetano.cursomc.domain.Cliente;
import com.alexcaetano.cursomc.dto.ClienteDTO;
import com.alexcaetano.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody @Valid ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDto(objDto);

		obj.setId(id);
		obj = service.update(obj);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = service.findAll();

		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC")  String direction){

		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);

		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(listDto);
	}
}
