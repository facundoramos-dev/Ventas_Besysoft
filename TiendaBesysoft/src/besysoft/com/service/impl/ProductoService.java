package besysoft.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import besysoft.com.entity.Producto;
import besysoft.com.service.IProductoService;
import besysoft.com.util.Db;

public class ProductoService implements IProductoService {

	//Declaracion de la DB
	Db db;
	
	/**
	 * Constructor sobrecargado
	 * @param db | para trabajar con una sola DB
	 */
	public ProductoService(Db db) {
		this.db = db;
	}
	
	/**
	 * Metodo para obtener la registros de "DB" | "Tabla Productos"
	 * @return List productos
	 */
	@Override
	public List<Producto> getAll() {
		return db.getProductos();
	}
	
	/**
	 * Metodo para guardar un producto en la "Tabla" productos
	 */
	@Override
	public void guardar(Producto producto) {
		db.addProducto(producto);
	}

	/**
	 * Metodo para eliminar un producto de la "Tabla" productos
	 */
	@Override
	public void borrar(Producto producto) {
		db.removeProducto(producto);
	}

	/**
	 * Metodo para filtrar los productos por categorias
	 * @param categoria | categoria de la cual se quiere obtener el grupo de productos
	 * @return Lista de Productos cuya categoria coincida exactamente con la recivida por parametros
	 */
	@Override
	public List<Producto> filtrarCategoria(String categoria) {
		try {
			List<Producto> productos = new ArrayList<Producto>();
			for(Producto producto : getAll())
				if(producto.getCategoria().toLowerCase() == categoria.toLowerCase())
					productos.add(producto);
			return productos;
		} catch (Exception e) {
			return new ArrayList<Producto>();
		}
	}

	/**
	 * Metodo para filtrar los productos por Nombre (parcial)
	 * @param nombre | parte del nombre del producto a buscar
	 * @return Lista de productos cuyo nombre contenga la subcadena pasada por parametros
	 */
	@Override
	public List<Producto> filtrarNombre(String nombre) {
		try {
			List<Producto> productos = new ArrayList<Producto>();
			for(Producto producto : getAll())
				if(producto.getNombre().toLowerCase().contains(nombre.toLowerCase()))
					productos.add(producto);
			return productos;
		} catch (Exception e) {
			return new ArrayList<Producto>();
		}
	}

	/**
	 * Metodo para filtrar los productos entre en un rango de precios
	 * @param priceMin | precio minimo del rango
	 * @param priceMax | precio maximo del rango
	 * @return Lista de productos cuyo precio se encuentre entre el rango formador por sus parametros
	 */
	@Override
	public List<Producto> filtrarPrecio(float priceMin, float priceMax) {
		try {
			List<Producto> productos = new ArrayList<Producto>();
			for(Producto producto : getAll())
				if(producto.getPrecio()>=priceMin && producto.getPrecio()<=priceMax)
					productos.add(producto);
			return productos;
		} catch (Exception e) {
			return new ArrayList<Producto>();
		}
	}
	
}
