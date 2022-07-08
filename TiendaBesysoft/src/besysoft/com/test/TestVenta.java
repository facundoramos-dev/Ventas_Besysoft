package besysoft.com.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
 * Clase Test encargada de verificar el correcto funcionamiento de los metodos y/o modulos relacionados a venta
 * @author Sebastian Ramos
 * @version 08-07-22
 * @see Entidad Venta
 * @see Service VentaService
 * @see Util Db
 */
public class TestVenta {

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
	 * Metodo encargado de cargar ventas en la Db antes de cada Test
	 */
	@BeforeEach
	public void cargarVentas() {
		Venta venta1 = new Venta();
		Venta venta2 = new Venta();
		Venta venta3 = new Venta();
		Venta venta4 = new Venta();
		Venta venta5 = new Venta();
		Venta venta6 = new Venta();
		
		venta1.setVendedor(vendedorService.getByCodigo(20));
		
		venta2.setVendedor(vendedorService.getByCodigo(21));
		venta3.setVendedor(vendedorService.getByCodigo(21));
		
		venta4.setVendedor(vendedorService.getByCodigo(22));
		venta5.setVendedor(vendedorService.getByCodigo(22));
		venta6.setVendedor(vendedorService.getByCodigo(22));
		
		venta1.agregarProducto(productoService.getByCodigo(100));
		venta1.agregarProducto(productoService.getByCodigo(100));
		venta1.agregarProducto(productoService.getByCodigo(101));
		venta1.agregarProducto(productoService.getByCodigo(104));
		
		venta2.agregarProducto(productoService.getByCodigo(104));
		
		venta3.agregarProducto(productoService.getByCodigo(103));
		venta3.agregarProducto(productoService.getByCodigo(103));
		venta3.agregarProducto(productoService.getByCodigo(103));
		venta3.agregarProducto(productoService.getByCodigo(103));
		
		venta4.agregarProducto(productoService.getByCodigo(102));
		venta4.agregarProducto(productoService.getByCodigo(100));
		
		venta5.agregarProducto(productoService.getByCodigo(104));
		
		venta6.agregarProducto(productoService.getByCodigo(102));
		
		ventaService.guardar(venta1);
		ventaService.guardar(venta2);
		ventaService.guardar(venta3);
		ventaService.guardar(venta4);
		ventaService.guardar(venta5);
		ventaService.guardar(venta6);
	}
		
	/**
	 * Metodo encargado de resetear la Db despues de cada Test
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
		assertEquals(db.getVentas(), ventaService.getAll());
		assertEquals(db.getVentas().size(),ventaService.getAll().size());
	}

	/**
	 * Test, verifica un correcta inserccion en la DB
	 */
	@Test
	@DisplayName("Guardar Venta")
	public void guardarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = ventaService.getAll().size();
		
		//Declaro y Guardo una nueva venta en la Db mediante servicio
		Venta nueva = new Venta();
		nueva.setVendedor(vendedorService.getByCodigo(20));
		nueva.agregarProducto(productoService.getByCodigo(102));
		nueva.agregarProducto(productoService.getByCodigo(102));
		ventaService.guardar(nueva);
		
		//Comparo que la nueva venta se haya guardado en la Db mediante servicio
		assertEquals(db.getVentas(), ventaService.getAll());
		assertEquals(cantidad+1,ventaService.getAll().size());
	}
	
	/**
	 * Test, verifica que no se inserten ventas sin productos
	 * NOTA: una venta sin productos, realmente se puede decir venta?
	 */
	@Test
	@DisplayName("Falla Guardar Venta")
	public void fallaGuardarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = ventaService.getAll().size();
		
		//Intento guardar una venta en la Db mediante servicio
		ventaService.guardar(new Venta());
		
		//Comparo que la nueva venta NO se haya guardado en la Db
		assertEquals(db.getVentas(), ventaService.getAll());
		assertEquals(cantidad,ventaService.getAll().size());
	}
	
	/**
	 * Test, verifica que un venta sea borrado de la DB utilizando servicio
	 */
	@Test
	@DisplayName("Borrar Venta")
	public void borrarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = ventaService.getAll().size();
		
		//Guardo una nueva venta en la Db mediante servicio
		Venta eliminar = new Venta();
		eliminar.setVendedor(vendedorService.getByCodigo(20));
		eliminar.agregarProducto(productoService.getByCodigo(102));
		eliminar.agregarProducto(productoService.getByCodigo(100));
		ventaService.guardar(eliminar);
		assertEquals(cantidad+1,ventaService.getAll().size());
		
		//Elimina la venta mediante servicio
		ventaService.borrar(eliminar);
		
		//Comparo que la venta seleccionada se haya eleiminado de la Db
		assertEquals(cantidad,ventaService.getAll().size());
	}
	
	/**
	 * Test, calcula la sumatoria (total) de todos los productos incluidos en la venta
	 */
	@Test
	@DisplayName("Calcular Total")
	public void calcularTotalPorVentaTest() {
		//Seleccion de una venta
		Venta venta = ventaService.getAll().get(0);
		List<Producto> productos = venta.getProductos();
		
		//Verifico que tenga datos
		assertNotNull(venta);
		assertNotNull(productos);
		assertNotEquals(0, productos.size());
		
		//Realiazo la sumatoria de los productos
		float total = 0f;
		for(Producto producto : productos)
			total += producto.getPrecio();
		
		//Compara los resultados obtenidos
		assertEquals(total, venta.obtenerTotal());
	}
	
	/**
	 * Test, Calcula que la comision por venta, al ser de 2 productos o menos sea del 5%
	 */
	@Test
	@DisplayName("Calcular Comision 5% Por Venta")
	public void calcularComision10PorVentaTest() {
		//Instancio 2 productos
		Producto coca = new Producto(102,"coca cola",200f,"bebida");
		Producto alfajor = new Producto(103,"alfajor",100f,"dulce");
		//Instancia dos venta
		Venta ventaDistinta = new Venta();
		Venta ventaIgual = new Venta();
		//Agrego 2 productos distintos a la venta, sino la misma no se guardara
		ventaDistinta.agregarProducto(coca);
		ventaDistinta.agregarProducto(alfajor);
		//Agrego 2 productos iguales a la venta
		ventaIgual.agregarProducto(alfajor);
		ventaIgual.agregarProducto(alfajor);
		//Declaro variable auxiliar y la inicializo
		float comision = 0f;
		//NOTA: no es necesaria pero ayuda a la legibilidad del codigo y a no perderse entre tanto comentario
		//Obtengo el calculo de comision por venta de distintos productos
		comision = ventaDistinta.obtenerComision();
		assertEquals(comision, ((coca.getPrecio()+alfajor.getPrecio())*0.05f));
		//Obtengo el calculo de comision por venta de iguales productos
		comision = ventaIgual.obtenerComision();
		assertEquals(comision, ((alfajor.getPrecio()*2)*0.05f));
	}
	
	/**
	 * Test, Calcula que la comision por venta, al ser de 3 productos o mas sea del 10%
	 */
	@Test
	@DisplayName("Calcular Comision 10% Por Venta")
	public void calcularComision5PorVentaTest() {
		//Instancio 3 productos
		Producto coca = new Producto(102,"coca cola",200f,"bebida");
		Producto alfajor = new Producto(103,"alfajor",100f,"dulce");
		Producto papas = new Producto(104,"papas fritas",300f,"snack");
		//Instancia dos venta
		Venta ventaDistinta = new Venta();
		Venta ventaIgual = new Venta();
		//Agrego 3 productos distintos a la venta, sino la misma no se guardara
		ventaDistinta.agregarProducto(coca);
		ventaDistinta.agregarProducto(alfajor);
		ventaDistinta.agregarProducto(papas);
		//Agrego 3 productos iguales a la venta
		ventaIgual.agregarProducto(alfajor);
		ventaIgual.agregarProducto(alfajor);
		ventaIgual.agregarProducto(alfajor);
		//Declaro variable auxiliar y la inicializo
		float comision = 0f;
		//NOTA: no es necesaria pero ayuda a la legibilidad del codigo y a no perderse entre tanto comentario
		//Obtengo el calculo de comision por venta de distintos productos
		comision = ventaDistinta.obtenerComision();
		assertEquals(comision, ((coca.getPrecio()+alfajor.getPrecio()+papas.getPrecio())*0.1f));
		//Obtengo el calculo de comision por venta de iguales productos
		comision = ventaIgual.obtenerComision();
		assertEquals(comision, ((alfajor.getPrecio()*3)*0.1f));
	}
	
	/**
	 * Test, Obtiene la cantidad de ventas asociadas por vendedor y verifica una a una sicha asociacion
	 */
	@Test
	@DisplayName("Obtener Ventas por codigo vendedor")
	public void obtenerVentasPorVendedorCodigoTest() {
		int totalDeVentas = ventaService.getAll().size();
		//Seleccion un vendedor
		Vendedor vendedor = vendedorService.getByCodigo(22);
		assertNotNull(vendedor);
		
		if(vendedorService.getAll().size() > 1) {
			//Cuento cuantas ventas estan asociadas al vendedor
			int contarVentas = 0;
			for(Venta venta : ventaService.getAll())
				if(venta.getVendedor().getCodigo()==vendedor.getCodigo())
					contarVentas++;
			
			//comparo los valores obtenidos
			assertEquals(contarVentas, ventaService.obtenerVentas(vendedor.getCodigo()).size());
		}else if(vendedorService.getAll().size() < 1)
			assertEquals(0, totalDeVentas);
		else
			assertEquals(totalDeVentas, ventaService.obtenerVentas(vendedor.getCodigo()).size());
	}
	
}
