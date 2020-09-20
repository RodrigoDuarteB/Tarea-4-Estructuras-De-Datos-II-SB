package bo.edu.uagrm.inf310sb.UI;

import bo.edu.uagrm.inf310sb.arboles.AVL;
import bo.edu.uagrm.inf310sb.arboles.ArbolBinarioBusqueda;
import bo.edu.uagrm.inf310sb.arboles.ArbolMViasBusqueda;
import bo.edu.uagrm.inf310sb.arboles.ExcepcionClaveNoExiste;
import bo.edu.uagrm.inf310sb.arboles.ExcepcionClaveYaExiste;
import bo.edu.uagrm.inf310sb.arboles.ExcepcionOrdenInvalido;
import bo.edu.uagrm.inf310sb.arboles.IArbolBusqueda;

public class TestArbol {
	
	public static void main(String[] args) throws ExcepcionClaveYaExiste, ExcepcionOrdenInvalido, ExcepcionClaveNoExiste{
		IArbolBusqueda<Integer, String> arbolPrueba;
		//Tarea 4
		
		// 1. Implementar un método que retorne verdadero si un árbol binario es un montículo, falso en caso contrario. En un montículo el dato que 
		//     almacena un nodo cualquiera siempre es menor que los datos de sus descendientes
		arbolPrueba = new ArbolBinarioBusqueda<>();
		try {
			arbolPrueba.insertar(45, "Cuarenta y Cinco");
			arbolPrueba.insertar(47, "Cuarenta y Siete");
			arbolPrueba.insertar(62, "Sesenta y Dos");
		} catch (ExcepcionClaveYaExiste e) {
			System.out.println(e);
		}
		System.out.println("1. Verificar si el árbol es montículo: "+((ArbolBinarioBusqueda)arbolPrueba).esMonticulo());
		
		
		//2. Implementar un método insertar iterativo para dicho árbol. No puede utilizar los metodos insertar o leiminar ya existentes. Tal como establece 
		//	el árbol AVL solo se debe ver la necesidad de balancear por el camino que siguió el algoritmo para insertar hasta volver a la raíz
		arbolPrueba = new AVL<>();
		try {
			((AVL)arbolPrueba).insertarIterativo(3, "Tres");
			((AVL)arbolPrueba).insertarIterativo(2, "Dos");
			((AVL)arbolPrueba).insertarIterativo(1, "Uno");
			((AVL)arbolPrueba).insertarIterativo(4, "Cuatro");
			((AVL)arbolPrueba).insertarIterativo(5, "Cinco");
			((AVL)arbolPrueba).insertarIterativo(6, "Seis");
			((AVL)arbolPrueba).insertarIterativo(7, "Siete");
		}catch (ExcepcionClaveYaExiste e) {
			System.out.println(e);
		}
		System.out.println("2. Prueba: "+arbolPrueba.recorridoPorNiveles()); //para prueba, el recorrido por niveles
		
		
		//3. Implementar un método para un árbol de m-vías que retorne cuantos nodos del árbol son padres fuera del nivel n
		arbolPrueba = new ArbolMViasBusqueda<>(4);
		try {
			arbolPrueba.insertar(50, "Cincuenta");
			arbolPrueba.insertar(45, "Cuarenta y Cinco");
			arbolPrueba.insertar(47, "Cuarenta y Siete");
			arbolPrueba.insertar(62, "Sesenta y Dos");
			arbolPrueba.insertar(53, "Cincuenta y Tres");
			arbolPrueba.insertar(40, "Cuarenta");
			arbolPrueba.insertar(67, "Sesenta y Siete");
			arbolPrueba.insertar(46, "Cuarenta y Seis");
			arbolPrueba.insertar(52, "Cincuenta y Dos");
			arbolPrueba.insertar(51, "Cincuenta y Uno");
			arbolPrueba.insertar(48, "Cuarenta y Ocho");
		} catch (ExcepcionClaveYaExiste e) {
			System.out.println(e);
		}
		System.out.println("3. Nodos Padre fuera del nivel 2: "+((ArbolMViasBusqueda)arbolPrueba).nodosPadreFueraDelNivel(2));
	}

}
