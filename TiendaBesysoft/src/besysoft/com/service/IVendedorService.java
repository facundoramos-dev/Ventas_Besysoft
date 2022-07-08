package besysoft.com.service;

import java.util.List;

import besysoft.com.entity.Vendedor;

public interface IVendedorService {

	public List<Vendedor> getAll();
	public void guardar(Vendedor vendedor);
	public void borrar(Vendedor vendedor);
	public Vendedor getByCodigo(int codigo);
	
}
