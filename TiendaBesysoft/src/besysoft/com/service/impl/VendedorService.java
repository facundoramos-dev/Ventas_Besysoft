package besysoft.com.service.impl;

import java.util.List;

import besysoft.com.entity.Vendedor;
import besysoft.com.service.IVendedorService;
import besysoft.com.util.Db;

/**
 * Esta clase extiende la Interface IVendedorService para sobreescribir sus metodos
 * @author Sebastian Ramos
 */
public class VendedorService implements IVendedorService {

	//Declaracion de la DB
	Db db;
	/*NOTA: de usar aqui Db db = new Db(); o usar cualquier constructor
	Se estaria trabajando con dos DataBases distintas*/
	
	/**
	 * Constructor sobrecargado
	 * @param db | para trabajar con una sola DB
	 */
	public VendedorService(Db db) {
		this.db = db;
	}
	//NOTA: Raro ver esto en esta capa de Servicio
	
	/**
	 * Metodo para obtener la registros de "DB" | "Tabla Vendedores"
	 * @return List vendedores
	 */
	@Override
	public List<Vendedor> getAll() {
		return db.getVendedores();
	}
	
	/**
	 * Metodo para guardar un vendedor en la "Tabla" vendedores
	 */
	@Override
	public void guardar(Vendedor vendedor) {
		db.addVendedor(vendedor);
	}

	/**
	 * Metodo para eliminar un vendedor de la "Tabla" vendedores
	 */
	@Override
	public void borrar(Vendedor vendedor) {
		db.removeVendedor(vendedor);
	}

	@Override
	public Vendedor getByCodigo(int codigo) {
		for(Vendedor vendedor : getAll())
			if(vendedor.getCodigo() == codigo)
				return vendedor;
		return null;
	}
	
}
