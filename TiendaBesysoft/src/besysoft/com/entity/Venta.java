package besysoft.com.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * Esta clase contiene la deficion de una entidad Venta (id, vendedor, productos)
 * @author Sebastian Ramos
 */
public class Venta {
	
	//Variable compartida entre clases para autoincrementar cada que se cree un nuevo objeto de la Entidad Venta
	public static int idIncrementable = 1;
	
	//Atributos de la Entidad Venta
	private int id;
	private Vendedor vendedor;
	private List<Producto> productos;
	
	/**
	 * Metodo para obtener la comision de la Venta Actual
	 * @see obtenerTotal()
	 * @return el porcentaje de comision obtenida por la venta | 10% en lugar de ser mas de 2 productos de lo contrario 5%
	 */
	public float obtenerComision(){
		if(productos.size()>2)
			return obtenerTotal()*0.10f;
		return  obtenerTotal()*0.05f;
	}
	
	/**
	 * Metodo para obtener el total a pagar de la venta Actual
	 * @return la sumatoria de los precios unitarios de cada producto en lista
	 */
	public float obtenerTotal() {
		float total = 0;
		for (Producto producto : productos)
			total += producto.getPrecio();
		return total;
	}
	/*NOTA: para este supuesto no se considero que el usuario puede elegir mas de un producto por venta especificando
	la cantidad, pero si es posible agregar el mismo producto a la lista y asi contarlo para "comprar" varias unidades del mismo*/
	
	/**
	 * Metodo para agregar un producto a la lista en lugar de reSetear la lista entera
	 * @param producto | Objecto Producto a agregar a la lista
	 */
	public void agregarProducto(Producto producto) {
		productos.add(producto);
	}
	
	/**
	 * Metodo para remover un producto de la lista y no reSetear la lista sin el producto eliminado
	 * @param producto | Objecto Producto a remover de la lista
	 */
	public void borrarProducto(Producto producto) {
		productos.remove(producto);
	}
	
	//Constructor por defecto
	public Venta() {
		id = idIncrementable++;
		productos = new ArrayList<Producto>();
	}

	/**
	 * Constructor Sobrecargado 
	 * @param vendedor | Objecto Vendedor, identificando al vendedor asociado a dicha venta
	 * @param productos | Lista Producto, enlista los productos por Venta
	 */
	public Venta(Vendedor vendedor, List<Producto> productos) {
		super();
		id = idIncrementable++;
		this.vendedor = vendedor;
		this.productos = productos;
	}

	//Metodos Accesores (getters and setters)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	//Sobreescritura del metodo toString() para una mejor visualizacion del objeto
	@Override
	public String toString() {
		return "\nVenta Nro: " + id + " - Total: "+obtenerTotal()+"\nDetalle:" + productos;
	}
	
}
