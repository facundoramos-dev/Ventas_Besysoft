package besysoft.com.util;

import java.util.ArrayList;
import java.util.List;

import besysoft.com.entity.Producto;
import besysoft.com.entity.Vendedor;
import besysoft.com.entity.Venta;
/**
 * Esta clase simulara una DB en memoria RAM, debido a que se pedia los datos en memoria
 * y no con persistencia de datos
 * @author Sebastian Ramos
 */
public class Db {

	//simulacion de las tablas en listas de Objectos
	private List<Producto> productos;
	private List<Vendedor> vendedores;
	private List<Venta> ventas;
	
	/**
	 * Metodo para agregar un producto a la "Tabla" ficticia de productos
	 * antes de agregarlo verifica que no se encuentre agregado anteriormente
	 * y cumpla con la regla de unicidad respecto a PK Producto (codigo)
	 * @param producto | Objeto producto a agregar a la lista
	 */
	public void addProducto(Producto producto) {
		if(!productos.contains(producto)) {
			boolean band = true;
			for(Producto p : productos)
				if(p.getCodigo() == producto.getCodigo())
					band = false;
			if (band)
				productos.add(producto);
		}
	}
	
	/**
	 * Metodo para eliminar un producto de la "Tabla" ficticia de productos
	 * @param producto | Objeto producto a eliminar
	 */
	public void removeProducto(Producto producto) {
		productos.remove(producto);
	}
	
	/**
	 * Metodo para agregar un vendedor a la "Tabla" ficticia de vendedores
	 * antes de agregarlo verifica que no se encuentre agregado anteriormente
	 * y cumpla con la regla de unicidad respecto a PK Vendedor (codigo)
	 * @param vendedor | Objeto vendedor a agregar a la lista
	 */
	public void addVendedor(Vendedor vendedor) {
		if(!vendedores.contains(vendedor)) {
			boolean band = true;
			for(Vendedor v : vendedores)
				if(v.getCodigo() == vendedor.getCodigo())
					band = false;
			if (band)
				vendedores.add(vendedor);
		}
	}
	
	/**
	 * Metodo para eliminar un vendedor de la "Tabla" ficticia de vendedores
	 * @param vendedor | Objeto vendedor a eliminar
	 */
	public void removeVendedor(Vendedor vendedor) {
		vendedores.remove(vendedor);
	}
	
	/**
	 * Metodo para agregar una venta a la "Tabla" ficticia de ventas
	 * antes de agregarla verifica que no se encuentre agregada anteriormente
	 * y minimamente dicha venta posea un producto y este asociada a un vendedor
	 * @param venta | Objecto venta a agregar a la lista
	 */
	public void addVenta(Venta venta) {
		if(venta.getProductos().size()>0 && venta.getVendedor()!=null)
			ventas.add(venta);			
	}
	
	/**
	 * Metodo para eliminar una venta de la "Tabla" ficticia de ventas
	 * @param venta | Objeto venta a eliminar
	 */
	public void removeVenta(Venta venta) {
		ventas.remove(venta);
	}
	
	//Constructor por defecto con inicializacion de las "Tablas" (Listas)
	public Db() {
		productos = new ArrayList<Producto>();
		vendedores = new ArrayList<Vendedor>();
		ventas = new ArrayList<Venta>();
	}

	/**
	 * Constructor Sobrecargado
	 * @param productos | Lista de productos previamente definidad
	 * @param vendedores | Lista de vendedores previamente definida
	 * @param ventas | Lista de ventas previamente definida
	 */
	public Db(List<Producto> productos, List<Vendedor> vendedores, List<Venta> ventas) {
		super();
		this.productos = productos;
		this.vendedores = vendedores;
		this.ventas = ventas;
	}

	/**
	 * Simulacion de Query para obtener todos los registros de la "Tabla" Productos
	 * @return lista de todos los vendedores;
	 */
	public List<Producto> getProductos() {
		return productos;
	}

	/**
	 * Simulacion de Query para obtener todos los registros de la "Tabla" Vendedores
	 * @return lista de todos los productos;
	 */
	public List<Vendedor> getVendedores() {
		return vendedores;
	}

	/**
	 * Simulacion de Query para obtener todos los registros de la "Tabla" Ventas
	 * @return lista de todas las ventas;
	 */
	public List<Venta> getVentas() {
		return ventas;
	}

	//Sobreescritura para la visualizacion de de todas las tablas con sus respectivos registros
	@Override
	public String toString() {
		return "Datos Almacenados: "
				+ "\nProductos=" + productos
				+ "\nVendedores=" + vendedores 
				+ "\nVentas=" + ventas;
	}
	
}
