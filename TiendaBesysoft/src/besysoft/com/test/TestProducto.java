package besysoft.com.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import besysoft.com.util.Db;
import besysoft.com.entity.Producto;
import besysoft.com.service.IProductoService;
import besysoft.com.service.impl.ProductoService;

/**
 * Clase Test encargada de verificar el correcto funcionamiento de los metodos y/o modulos relacionados al producto
 * @author Sebastian Ramos
 * @version 08-07-22
 * @see Entidad Producto
 * @see Service ProductoService
 * @see Util Db
 */
public class TestProducto{

	//Declaracion e Intanciacion de la Db y sus respectivos servicios
	Db db = new Db();
	IProductoService productoService = new ProductoService(db);
	
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
		productoService.guardar(new Producto(200,"chupetin",50f,"dulce"));
		productoService.guardar(new Producto(106,"menta",120f,"dulce"));
		productoService.guardar(new Producto(107,"leche",380f,"lacteo"));
		productoService.guardar(new Producto(108,"pepsi",300f,"bebida"));
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
		assertEquals(db.getProductos(), productoService.getAll());
		assertEquals(db.getProductos().size(),productoService.getAll().size());
	}
	
	/**
	 * Test, verifica un correcta inserccion en la DB
	 */
	@Test
	@DisplayName("Guardar Producto")
	public void guardarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = productoService.getAll().size();
		
		//Guardo un nuevo producto en la Db mediante servicio
		productoService.guardar(new Producto(105,"Gomitas",80f,"dulce"));
		//Comparo que el nuevo producto se haya guardado en la Db mediante servicio
		assertEquals(db.getProductos(), productoService.getAll());
		assertNotNull(productoService.getByCodigo(105));
		assertEquals(cantidad+1, productoService.getAll().size());
	}
	
	/**
	 * Test, verifica que no se inserten datos que rompan la regla de unicida de la DB (PK = codigo)
	 */
	@Test
	@DisplayName("Falla Guardar Producto")
	public void fallaGuardarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = productoService.getAll().size();
		
		//Intento guardar un producto en la Db mediante servicio
		productoService.guardar(new Producto(100,"Gomitas",80f,"dulce"));
		
		//Comparo que el nuevo producto NO se haya guardado en la Db
		assertEquals(db.getProductos(), productoService.getAll());
		assertEquals(cantidad, productoService.getAll().size());
	}
	
	/**
	 * Metodo que recupera un producto especifico de todos los registros
	 */
	@Test
	@DisplayName("Obtener Producto Especifico")
	public void obtenerEspecificoTest() {
		//Declarar producto a buscar
		Producto buscar = new Producto(105,"Gomitas",80f,"dulce");
		
		//Guardo producto para busqueda posterior 
		productoService.guardar(buscar);
		
		//Comparo que el metodo recupera los datos correctos
		assertNotNull(productoService.getByCodigo(buscar.getCodigo()));
		assertEquals(buscar, productoService.getByCodigo(buscar.getCodigo()));
	}
	
	/**
	 * Test, verifica que un producto sea borrado de la DB utilizando servicio
	 */
	@Test
	@DisplayName("Borrar Producto")
	public void borrarTest() {
		//Guarda la cantidad de registros previos a la eliminacion
		int cantidad = productoService.getAll().size();
		
		//Identifico el producto a eliminar
		Producto eliminar = productoService.getByCodigo(101);
		
		//Elimino el producto mediante servicio
		productoService.borrar(eliminar);
		
		//Comparo que el producto seleccionado se haya eleiminado de la Db
		assertNotEquals(cantidad, productoService.getAll().size());
		assertEquals(cantidad-1, productoService.getAll().size());
		assertNull(productoService.getByCodigo(eliminar.getCodigo()));
	}
	/**
	 * Test, verifica que el filtro de funcionamiento por categorias
	 */
	@Test
	@DisplayName("Filtro Por Categoria")
	public void filtrarPorCategoriaTest() {
		//obtengo la cantidad total de productos
		int cantidadTotal = productoService.getAll().size();
		assertNotEquals(0, cantidadTotal);
		
		//guarda todas las categorias
		List<String> categorias = new ArrayList<String>();
		for(Producto producto : productoService.getAll())
			if(!categorias.contains(producto.getCategoria()))
				categorias.add(producto.getCategoria());
		
		//Agrego un nuevo porducto y aseguro que el metodo traiga las categorias correctas 
		String newCate = "cigarrillo";
		String otherCate = "lacteo";
		int cantidadCigarros = productoService.filtrarCategoria(newCate).size();
		int cantidadNoCigarrillos = productoService.filtrarCategoria(otherCate).size();
		productoService.guardar(new Producto(10,"Malboro Comun",340f,newCate));
		assertEquals(cantidadCigarros+1, productoService.filtrarCategoria(newCate).size());
		assertEquals(cantidadNoCigarrillos, productoService.filtrarCategoria(otherCate).size());
		
		//Verifica que los valores encontrados sean unicamente de la categoria buscada
		for(Producto producto : productoService.filtrarCategoria(newCate))
			assertEquals(newCate, producto.getCategoria());	
	}
	/**
	 * Test, verifica que el filtro por nombre tome valores parciales y no solo totales
	 */
	@Test
	@DisplayName("Filtro Por Nombre")
	public void filtrarPorNombreParcial() {
		//Agrego un nuevo producto y aseguro que el metodo traiga los nombre correctos 
		String newName = "Malb";
		String otherName = "gomitas";
		int cantidadMalb= productoService.filtrarNombre(newName).size();
		int cantidadNoMalb = productoService.filtrarNombre(otherName).size();
		productoService.guardar(new Producto(10,newName+"oro Comun",340f,"cigarrillo"));
		assertEquals(cantidadMalb+1, productoService.filtrarNombre(newName).size());
		assertEquals(cantidadNoMalb, productoService.filtrarNombre(otherName).size());
		
		//Verifica que los valores encontrados contengan la cadena buscada dentro del nombre
		for(Producto producto : productoService.filtrarNombre(newName))
			assertTrue(producto.getNombre().contains(newName));	
	}
	
	/**
	 * Test, Filtra por rango de precio entre un valor minimo y maximo (incluyendolos)
	 */
	@Test
	@DisplayName("Filtro Por Rango de Precios")
	public void filtrarPorRangoDePrecios() {
		float minimo = 0f;
		float maximo = 200f;
		
		int cantidadRango = productoService.filtrarPrecio(minimo, maximo).size();
		productoService.guardar(new Producto(10,"Malboro Comun",100.51f,"cigarrillo"));
		assertEquals(cantidadRango+1, productoService.filtrarPrecio(minimo, maximo).size());
		
		for(Producto producto : productoService.filtrarPrecio(minimo, maximo)) 
			assertTrue(minimo<=producto.getPrecio() && producto.getPrecio()<=maximo);
	}
	
}
