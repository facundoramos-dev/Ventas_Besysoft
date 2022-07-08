package besysoft.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import besysoft.com.entity.Venta;
import besysoft.com.service.IVentaService;
import besysoft.com.util.Db;

/**
 * Esta clase extiende la Interface IVentaService para sobreescribir sus metodos
 * @author Sebastian Ramos
 */
public class VentaService implements IVentaService {

	//Declaracion de la DB
	Db db;
	
	/**
	 * Constructor sobrecargado
	 * @param db | para trabajar con una sola DB
	 */
	public VentaService(Db db) {
		this.db = db;
	}

	/**
	 * Metodo para obtener la registros de "DB" | "Tabla Ventas"
	 * @return List ventas
	 */
	@Override
	public List<Venta> getAll() {
		return db.getVentas();
	}
	
	/**
	 * Metodo para guardar una venta en la "Tabla" ventas
	 */
	@Override
	public void guardar(Venta venta) {
		db.addVenta(venta);
	}

	/**
	 * Metodo para eliminar una venta de la "Tabla" ventas
	 */
	@Override
	public void borrar(Venta venta) {
		db.removeVenta(venta);
	}

	/**
	 * Metodo para obtener las Ventas de un vendedor especifico
	 * @param codigo | identificatorio del vendedor cuyas ventas se quieren obtener
	 * @return Lista ventas cuyo codigo de vendedor sea el mismo que se esta buscando
	 */
	@Override
	public List<Venta> obtenerVentas(int codigo) {
		try {
			List<Venta> ventas = new ArrayList<Venta>();
			for(Venta venta : getAll())
				if(venta.getVendedor().getCodigo() == codigo)
					ventas.add(venta);
			return ventas;
		} catch (Exception e) {
			return new ArrayList<Venta>();
		}
	}
	
}
