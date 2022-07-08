package besysoft.com.service;

import java.util.List;

import besysoft.com.entity.Venta;

public interface IVentaService {

	public List<Venta> getAll();
	public void guardar(Venta venta);
	public void borrar(Venta venta);
	public List<Venta> obtenerVentas(int codigo);
	
}
