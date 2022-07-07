package besysoft.com.entity;
/**
 * Esta clase contiene la deficion de una entidad Producto (codigo, nombre, precio, categoria)
 * @author Sebastian Ramos
 */
public class Producto {
	
	//Atributos de la Entidad Producto
	private int codigo;
	private String nombre;
	private float precio;
	private String categoria;
	
	//Constructor por defecto
	public Producto() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor Sobrecargado 
	 * @param codigo | numerico entero, identifica el producto de forma mas precisa
	 * @param nombre | cadena de caracteres, identifica el producto de forma coloquial
	 * @param precio | numerico decimal, valor del producto (precio unitario)
	 * @param categoria | cadena de caracteres, para agrupar el producto en un conjunto de los mismos
	 */
	public Producto(int codigo, String nombre, float precio, String categoria) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.categoria = categoria;
	}
	
	//Metodos Accesores (getters and setters)
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	//Sobreescritura del metodo toString() para una mejor visualizacion del objeto
	@Override
	public String toString() {
		return "\n- "+nombre + " ($"+ precio + ")";
	}
	
}
