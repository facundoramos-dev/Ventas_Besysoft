package besysoft.com.entity;

import java.util.List;
/**
 * Esta clase contiene la deficion de una entidad Vendedor (codigo, nombre, sueldo, comision)
 * @author Sebastian Ramos
 */
public class Vendedor {

	//Atributos de la Entidad Vendedor
	private int codigo;
	private String nombre;
	private float sueldo;
	private float comision;
	
	/**
	 * Metodo para calcular las comisiones de todas las ventas realizadas por Vendedor
	 * @param ventas | lista de Ventas asociadas al Vendedor
	 * @return sumatoria de comsiones para todas las ventas realizadas por el vendedor : float
	 */
	public float calcularComisiones(List<Venta> ventas) {
		float comision = 0;
	    for(Venta venta : ventas )
	    	comision += venta.obtenerComision();
	    this.comision = comision;
		return comision;
	}
	/*NOTA: no se utilizo una lista de Ventas como atributo para evitar, a futuro, si se pidiera, la complicacion en el
	 CRUD por no estar relacionadas como lo es una DB*/
	//NOTA PERSONAL: lo hiciste y "refactorisaste"
    
	//Constructor por defecto
	public Vendedor() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor Sobrecargado 
	 * @param codigo | numerico entero, identifica el vendedor de forma mas precisa
	 * @param nombre | cadena de caracteres, identifica el vendedor de forma coloquial
	 * @param sueldo | numerico decimal, sueldo base de vendedor
	 */
	public Vendedor(int codigo, String nombre, float sueldo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.sueldo = sueldo;
		this.comision = 0f;
	}
	//NOTA: para el presente supuesto se asumio que el vendedor poseo un sueldo base y gana "bonifiaciones" (comisiones) EXTRA

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

	public float getSueldo() {
		return sueldo;
	}

	public void setSueldo(float sueldo) {
		this.sueldo = sueldo;
	}
	
	public float getComision() {
		return comision;
	}

	public void setComision(float comision) {
		this.comision = comision;
	}

	//Sobreescritura del metodo toString() para una mejor visualizacion del objeto
	@Override
	public String toString() {
		return codigo+" - "+nombre
				+"\nInformacion Financiera:"
				+ "\nsueldo Base: $" + sueldo
				+ "\ncomisiones: $" + comision
				+ "\nTotal a cobrar: $"+ (sueldo+comision);
	}
	
}
