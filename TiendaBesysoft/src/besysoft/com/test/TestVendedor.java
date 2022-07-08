package besysoft.com.test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
 * Clase Test encargada de verificar el correcto funcionamiento de los metodos y/o modulos relacionados al vendedor
 * @author Sebastian Ramos
 * @version 08-07-22
 * @see Entidad Vendedor
 * @see Service VendedorService
 * @see Util Db
 */
public class TestVendedor {

	//Declaracion e Intanciacion de la Db y sus respectivos servicios
	Db db = new Db();
	IProductoService productoService = new ProductoService(db);
	IVendedorService vendedorService = new VendedorService(db);
	IVentaService ventaService = new VentaService(db);
	
	/**
	 * Metodo encargado de cargar vendedores en la Db antes de cada Test
	 */
	@BeforeEach
	public void cargarVendedores() {
		vendedorService.guardar(new Vendedor(20,"Sebastian Ramos",1000f));
		vendedorService.guardar(new Vendedor(21,"Aylen Aucapiña",1000f));
		vendedorService.guardar(new Vendedor(22,"Geovana Gonzalez",1000f));
	}
	
	/**
	 * Metodo encargado de cargar productos en la Db antes de cada Test
	 */
	@BeforeEach
	public void cargarProductos() {
		productoService.guardar(new Producto(100,"yogurt",150.50f,"lacteo"));
		productoService.guardar(new Producto(101,"chocolate",235.95f,"dulce"));
		productoService.guardar(new Producto(102,"coca cola",200f,"bebida"));
		productoService.guardar(new Producto(103,"alfajor",100f,"dulce"));
		productoService.guardar(new Producto(104,"papas fritas",300f,"snack"));
	}
	
	/**
	 * Metodo envargado de resetear la Db despues de cada Test
	 */
	@AfterEach
	public void borrarDatos() {
		db = new Db();
	}
	
	/**
	 * Test, verifica que el metodo getAll() devuelva la misma cantidad registros en la DB
	 */
	@Test
	@DisplayName("Obtener Todos")
	public void obtenerTodosTest() {
		//Compara valores anteriores a la insercion
		assertEquals(db.getVendedores(), vendedorService.getAll());
		assertEquals(db.getVendedores().size(), vendedorService.getAll().size());
	}
	
	/**
	 * Test, verifica un correcta inserccion en la DB
	 */
	@Test
	@DisplayName("Guardar Vendedor")
	public void guardarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = vendedorService.getAll().size();
				
		//Guardo un nuevo vendedor en la Db mediante servicio
		vendedorService.guardar(new Vendedor(23,"Ayelen Calatallo",1200f));
		
		//Comparo que el nuevo vendedor se haya guardado en la Db mediante servicio
		assertEquals(db.getVendedores(), vendedorService.getAll());
		assertNotNull(vendedorService.getByCodigo(23));
		assertEquals(cantidad+1, vendedorService.getAll().size());
		
	}
	
	/**
	 * Test, verifica que no se inserten datos que rompan la regla de unicida de la DB (PK = codigo)
	 */
	@Test
	@DisplayName("Falla Guardar Vendedor")
	public void fallaGuardarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = vendedorService.getAll().size();
				
		//Intento guardar un vendedor en la Db mediante servicio
		vendedorService.guardar(new Vendedor(20,"Ayelen Calatallo",1200f));
		
		//Comparo que el nuevo vendedor NO se haya guardado en la Db
		assertEquals(db.getVendedores(), vendedorService.getAll());
		assertEquals(cantidad, vendedorService.getAll().size());
	}
	
	/**
	 * Metodo que recupera un vendedor especifico de todos los registros
	 */
	@Test
	@DisplayName("Obtener Vendedor Especifico")
	public void obtenerEspecificoTest() {
		//Declarar vendedor a buscar
		Vendedor buscar = new Vendedor(23,"Ayelen Calatallo",1200f);
		
		//Guardo vendedor para busqueda posterior 
		vendedorService.guardar(buscar);
		
		//Comparo que el metodo recupera los datos correctos
		assertNotNull(vendedorService.getByCodigo(buscar.getCodigo()));
		assertEquals(buscar, vendedorService.getByCodigo(buscar.getCodigo()));
	}
	
	/**
	 * Test, verifica que un vendedor sea borrado de la DB utilizando servicio
	 */
	@Test
	@DisplayName("Borrar Vendedor")
	public void borrarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = vendedorService.getAll().size();
		
		//Identifico el vendedor a eliminar
		Vendedor eliminar = vendedorService.getByCodigo(22);
		
		//Elimino el vendedor mediante servicio
		vendedorService.borrar(eliminar);
		
		//Comparo que el vendedor seleccionado se haya eleiminado de la Db
		assertNotEquals(cantidad, vendedorService.getAll().size());
		assertEquals(cantidad-1, vendedorService.getAll().size());
		assertNull(vendedorService.getByCodigo(eliminar.getCodigo()));
	}

	/**
	 * Test, calcula la comision de ventas de un vendedor con una sola venta de 2 productos
	 */
	@Test
	@DisplayName("Calcular Comision de 1 venta(2 prod.)")
	public void calcularComisionSolaVenta5Test() {
		//Recupero 2 productos
		Producto coca = productoService.getByCodigo(104);
		assertNotNull(coca);
		Producto alfajor = productoService.getByCodigo(103);
		assertNotNull(alfajor);
		
		//Instancia una venta
		Venta venta = new Venta();
		
		//Obtengo el vendedor con el que quiero trabajar
		Vendedor vendedor = vendedorService.getByCodigo(20);
		assertNotNull(vendedor);
		assertNull(venta.getVendedor());
		
		//Asigno vendedor
		venta.setVendedor(vendedor);
		assertNotNull(venta.getVendedor());
		
		//Agrego productos a la venta, sino la misma no se guardara
		venta.agregarProducto(coca);
		venta.agregarProducto(alfajor);
		
		//Registro la venta
		ventaService.guardar(venta);
		
		//Obtengo el calculo de comision para el vendedor seleccionado
		float comision = vendedor.calcularComisiones(ventaService.obtenerVentas(vendedor.getCodigo()));
		assertEquals(((coca.getPrecio()+alfajor.getPrecio())*0.05f),comision);
	}
	
	/**
	 * Test, calcula la comision de ventas de un vendedor con una sola venta de 3 productos
	 */
	@Test
	@DisplayName("Calcular Comision de 1 venta(3 prod.)")
	public void calcularComisionSolaVenta10Test() {
		//Recupero 3 productos
		Producto coca = productoService.getByCodigo(102);
		assertNotNull(coca);
		Producto alfajor = productoService.getByCodigo(103);
		assertNotNull(alfajor);
		Producto papas = productoService.getByCodigo(104);
		assertNotNull(papas);
		
		//Instancia una venta
		Venta venta = new Venta();
		
		//Obtengo el vendedor con el que quiero trabajar
		Vendedor vendedor = vendedorService.getByCodigo(20);
		assertNotNull(vendedor);
		assertNull(venta.getVendedor());
		
		//Asigno vendedor
		venta.setVendedor(vendedor);
		assertNotNull(venta.getVendedor());
		
		//Agrego productos a la venta, sino la misma no se guardara
		venta.agregarProducto(coca);
		venta.agregarProducto(alfajor);
		venta.agregarProducto(papas);
		
		//Registro la venta
		ventaService.guardar(venta);
		
		//Obtengo el calculo de comision para el vendedor seleccionado
		float comision = vendedor.calcularComisiones(ventaService.obtenerVentas(vendedor.getCodigo()));
		assertEquals(((coca.getPrecio()+alfajor.getPrecio()+papas.getPrecio())*0.1f), comision);
	}
	
	/**
	 * Test, calcula la comision de ventas de un vendedor con dos ventas de 2 y 3 productos
	 */
	@Test
	@DisplayName("Calcular Comision de 2 venta(2 y 3 prod.)")
	public void calcularComisionVariasVenta15Test() {
		//Recupero 3 productos
		Producto yogurt = productoService.getByCodigo(100);
		assertNotNull(yogurt);
		Producto chocolate = productoService.getByCodigo(101);
		assertNotNull(chocolate);
		Producto papas = productoService.getByCodigo(104);
		assertNotNull(papas);
		
		//Instancia una venta
		Venta ventaDosProd = new Venta();
		Venta ventaTresProd = new Venta();
		
		//Obtengo el vendedor con el que quiero trabajar
		Vendedor vendedor = vendedorService.getByCodigo(20);
		assertNotNull(vendedor);
		assertNull(ventaDosProd.getVendedor());
		assertNull(ventaTresProd.getVendedor());
		
		//Asigno vendedor
		ventaDosProd.setVendedor(vendedor);
		ventaTresProd.setVendedor(vendedor);
		assertNotNull(ventaDosProd.getVendedor());
		assertNotNull(ventaTresProd.getVendedor());
		
		//Agrego productos a la primer venta
		ventaDosProd.agregarProducto(yogurt);
		ventaDosProd.agregarProducto(chocolate);
		
		//Agrego productos a la segunda venta
		ventaTresProd.agregarProducto(papas);
		ventaTresProd.agregarProducto(papas);
		ventaTresProd.agregarProducto(yogurt);
		
		//Registro la ventas
		ventaService.guardar(ventaDosProd);
		ventaService.guardar(ventaTresProd);
		
		//Obtengo el calculo de comision para el vendedor seleccionado
		float comision = vendedor.calcularComisiones(ventaService.obtenerVentas(vendedor.getCodigo()));
		float ventaDosTotal = yogurt.getPrecio()+chocolate.getPrecio();
		float ventaTresTotal = papas.getPrecio()*2+yogurt.getPrecio();
		assertEquals(Math.round((ventaDosTotal*0.05f+ventaTresTotal*0.1f)*100.0)/100.0,Math.round(comision*100.0)/100.0);
	}
	
}
