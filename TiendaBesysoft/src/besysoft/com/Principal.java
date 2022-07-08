package besysoft.com;

import besysoft.com.entity.Producto;
import besysoft.com.entity.Vendedor;
import besysoft.com.entity.Venta;
import besysoft.com.service.IProductoService;
import besysoft.com.service.IVendedorService;
import besysoft.com.service.IVentaService;
import besysoft.com.service.impl.ProductoService;
import besysoft.com.service.impl.VendedorService;
import besysoft.com.service.impl.VentaService;
import besysoft.com.util.Db;

/**
 * Esta clase sera la principal del proyecto Java y sera desde donde corre el mismo por consola
 * Los Test Unitarios se encuentran el packete Test estan implementados en JUnit5
 * @author Sebastian Ramos
 * @version 08-07-22
 */
public class Principal {

	public static void main(String[] args) {
		
		System.out.println("Hola Equipo de BesySoft :) Espero pasar el examen");
		
		//Declaracion e Intanciacion de la Db y sus respectivos servicios
		Db db = new Db();
		IProductoService productoService = new ProductoService(db);
		IVendedorService vendedorService = new VendedorService(db);
		IVentaService ventaService = new VentaService(db);
		
		//Carga deProductos
		productoService.guardar(new Producto(100,"yogurt",150.50f,"lacteo"));
		productoService.guardar(new Producto(101,"chocolate",235.95f,"dulce"));
		productoService.guardar(new Producto(102,"coca cola",200f,"bebida"));
		productoService.guardar(new Producto(103,"alfajor",100f,"dulce"));
		productoService.guardar(new Producto(104,"papas fritas",300f,"snack"));
		productoService.guardar(new Producto(105,"chetos",300f,"snack"));
		productoService.guardar(new Producto(106,"palitos salados",250f,"snack"));
		productoService.guardar(new Producto(107,"pepsi",170f,"bebida"));
		
		//Carga de Vendedores
		vendedorService.guardar(new Vendedor(20,"Sebastian Ramos",1000f));
		vendedorService.guardar(new Vendedor(21,"Aylen Aucapiña",1000f));
		
		/**
		 * Simulacion de una Venta
		 */
		//Se crea la orden de venta
		Venta venta1 = new Venta();
		
		//Se asocia el vendedor a la venta
		venta1.setVendedor(vendedorService.getByCodigo(20));
		
		//Se asocia los productos que el cliente llevara
		venta1.agregarProducto(productoService.getByCodigo(100));
		venta1.agregarProducto(productoService.getByCodigo(100));
		venta1.agregarProducto(productoService.getByCodigo(101));
		venta1.agregarProducto(productoService.getByCodigo(104));
		
		//Detalle de la venta de la Venta
		System.out.println("---------------- Detalle de la Venta ----------------\n"
				+ venta1 + "\n\nComision Obtenida por Venta: $" + venta1.obtenerComision());
		
		//Detalles del vendedor
		System.out.println("\n------------------ Detalle del Vendedor -------------\n\n"
				+ venta1.getVendedor());
		
		//Efectuar venta y calcular comisiones
		ventaService.guardar(venta1);
		venta1.getVendedor().calcularComisiones(ventaService.obtenerVentas(venta1.getVendedor().getCodigo()));
		
		//Detalles del vendedor Actualizados al momento de calcular las comisiones
		System.out.println("\n--------- Detalle del Vendedor(ActualizadoDB) --------\n\n"
				+ vendedorService.getByCodigo(venta1.getVendedor().getCodigo()));
		
		//Declaracion de Otra venta al mismo vendedor
		Venta venta2 = new Venta();
		venta2.setVendedor(vendedorService.getByCodigo(20));
		
		//Se asocia los productos que el cliente llevara
		venta2.agregarProducto(productoService.getByCodigo(104));
		
		//Detalle de la venta de la Venta
		System.out.println("\n-----------------------------------------------------"
				+ "\n---------------- Detalle de la Venta ----------------\n"
				+ venta2 + "\n\nComision Obtenida por Venta: $" + venta2.obtenerComision());
		
		//Detalles del vendedor
		System.out.println("\n------------------ Detalle del Vendedor -------------\n\n"
				+ venta2.getVendedor());
				
		//Efectuar venta y calcular comisiones
		ventaService.guardar(venta2);
		venta2.getVendedor().calcularComisiones(ventaService.obtenerVentas(venta2.getVendedor().getCodigo()));
			
		//Detalles del vendedor Actualizados al momento de calcular las comisiones
		System.out.println("\n--------- Detalle del Vendedor(ActualizadoDB) --------\n\n"
				+ vendedorService.getByCodigo(venta2.getVendedor().getCodigo()));
				
		//Declaracion de Otra venta a otro vendedor
		Venta venta3 = new Venta();
		venta3.setVendedor(vendedorService.getByCodigo(21));
		
		//Se asocia los productos que el cliente llevara
		venta3.agregarProducto(productoService.getByCodigo(103));
		venta3.agregarProducto(productoService.getByCodigo(103));
		venta3.agregarProducto(productoService.getByCodigo(103));
		venta3.agregarProducto(productoService.getByCodigo(103));
		venta3.agregarProducto(productoService.getByCodigo(102));
				
		//Detalle de la venta de la Venta
		System.out.println("\n-----------------------------------------------------"
				+ "\n---------------- Detalle de la Venta ----------------\n"
				+ venta3 + "\n\nComision Obtenida por Venta: $" + venta3.obtenerComision());
				
		//Detalles del vendedor
		System.out.println("\n------------------ Detalle del Vendedor -------------\n\n"
				+ venta3.getVendedor());
						
		//Efectuar venta y calcular comisiones
		ventaService.guardar(venta3);
		venta3.getVendedor().calcularComisiones(ventaService.obtenerVentas(venta3.getVendedor().getCodigo()));
					
		//Detalles del vendedor Actualizados al momento de calcular las comisiones
		System.out.println("\n--------- Detalle del Vendedor(ActualizadoDB) --------\n\n"
				+ vendedorService.getByCodigo(venta3.getVendedor().getCodigo()));
		
		//Filtros implementados
		//Filtro de categoria
		String categoria = "snack"; //cambiar esta linea para probar con mas categorias
		System.out.println("\n-----------------------------------------------------"
				+ "\n---------------- Filtro de Categoria ----------------\n"
				+ "Categoria elegida: \""+categoria+"\" \nResultados: "
				+ productoService.filtrarCategoria(categoria)
				+ "\n-----------------------------------------------------");
		
		//Filtro de nombre que contienen la cadena especificada
		String nombre = "co"; //cambiar esta linea para probar mas busquedas
		System.out.println("\n-----------------------------------------------------"
				+ "\n------------- Filtro de Nombre(Parcial) -------------\n"
				+ "Buscando: \""+nombre+"\" \nResultados: "
				+ productoService.filtrarNombre(nombre)
				+ "\n-----------------------------------------------------");
		
		//Filtro de rango
		float minimo = 50f; //extremo minimo del rango
		float maximo = 250f; //extremo maximo del rango
		System.out.println("\n-----------------------------------------------------"
				+ "\n------------- Filtro de Rango de Precio -------------\n"
				+ "Rango: $"+minimo+" - $"+maximo+"\nResultados: "
				+ productoService.filtrarPrecio(minimo, maximo)
				+ "\n-----------------------------------------------------");
	}

}