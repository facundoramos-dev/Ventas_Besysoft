package besysoft.com.service;

import java.util.List;

import besysoft.com.entity.Producto;

public interface IProductoService {

	public List<Producto> getAll();
	public void guardar(Producto producto);
	public void borrar(Producto producto);
	public List<Producto> filtrarCategoria(String categoria);
	public List<Producto> filtrarNombre(String nombre);
	public List<Producto> filtrarPrecio(float priceMin, float priceMax);
	
}
