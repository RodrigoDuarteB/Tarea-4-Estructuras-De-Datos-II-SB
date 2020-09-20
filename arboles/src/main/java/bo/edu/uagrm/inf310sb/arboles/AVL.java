package bo.edu.uagrm.inf310sb.arboles;

import java.util.Stack;

public class AVL<K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V> {
	
	private static final byte DIFERENCIA_MAXIMA = 1;
	
	@Override
	public void insertar(K clave, V valor) throws ExcepcionClaveYaExiste {
		this.raiz = this.insertar(this.raiz, clave, valor);
	}

	private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K clave, V valor) throws ExcepcionClaveYaExiste {
		if(NodoBinario.esNodoVacio(nodoActual)) {
			NodoBinario<K, V> nuevoNodo = new NodoBinario<>(clave, valor);
			return nuevoNodo;
		}else {
			K claveActual = nodoActual.getClave();
			if(clave.compareTo(claveActual)>0) {
				NodoBinario<K, V> supuestoNuevoHijoDerecho = this.insertar(nodoActual.getHijoDerecho(), clave, valor);
				nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
				return this.balancear(nodoActual);
			}
			if(clave.compareTo(claveActual)<0) {
				NodoBinario<K, V> supuestoNuevoHijoIzquierdo = this.insertar(nodoActual.getHijoIzquierdo(), clave, valor);
				nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
				return this.balancear(nodoActual);
			}
			throw new ExcepcionClaveYaExiste();
		}
	}
	
	private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
		int alturaRamaIzquierda = this.altura(nodoActual.getHijoIzquierdo());
		int alturaRamaDerecha = this.altura(nodoActual.getHijoDerecho());
		int diferenciaEnAltura = alturaRamaIzquierda-alturaRamaDerecha;
		if(diferenciaEnAltura>this.DIFERENCIA_MAXIMA) { //hay problema en la rama izquierda
			NodoBinario<K, V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
			alturaRamaIzquierda = this.altura(hijoIzquierdo.getHijoIzquierdo());
			alturaRamaDerecha = this.altura(hijoIzquierdo.getHijoDerecho());
			if(alturaRamaDerecha>alturaRamaIzquierda) { // rotacion doble
				return rotacionDobleADerecha(nodoActual);
			}else { //rotacion simple
				return rotacionSimpleADerecha(nodoActual);
			}
		}else if(diferenciaEnAltura<-this.DIFERENCIA_MAXIMA) { //hay problema en la rama derecha
			NodoBinario<K, V> hijoDerecho = nodoActual.getHijoDerecho();
			alturaRamaIzquierda = this.altura(hijoDerecho.getHijoIzquierdo());
			alturaRamaDerecha = this.altura(hijoDerecho.getHijoDerecho());
			if(alturaRamaIzquierda>alturaRamaDerecha) {
				return this.rotacionDobleAIzquierda(nodoActual);
			}else {
				return this.rotacionSimpleAIzquierda(nodoActual);
			}
		}
		//no hay problemas
		return nodoActual;
	}

	private NodoBinario<K, V> rotacionSimpleADerecha(NodoBinario<K, V> nodoActual) {
		NodoBinario<K, V> nodoARetornar = nodoActual.getHijoIzquierdo();
		nodoActual.setHijoIzquierdo(nodoARetornar.getHijoDerecho());
		nodoARetornar.setHijoDerecho(nodoActual);
		return nodoARetornar;
	}

	private NodoBinario<K, V> rotacionDobleADerecha(NodoBinario<K, V> nodoActual) {
		NodoBinario<K, V> nuevoHijoIzquierdo = this.rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
		nodoActual.setHijoIzquierdo(nuevoHijoIzquierdo);
		return rotacionDobleADerecha(nodoActual);
	}

	private NodoBinario<K, V> rotacionSimpleAIzquierda(NodoBinario<K, V> nodoActual) {
		NodoBinario<K, V> nodoARetornar = nodoActual.getHijoDerecho();
		nodoActual.setHijoDerecho(nodoARetornar.getHijoIzquierdo());
		nodoARetornar.setHijoIzquierdo(nodoActual);
		return nodoARetornar;
	}
	
	private NodoBinario<K, V> rotacionDobleAIzquierda(NodoBinario<K, V> nodoActual) {
		NodoBinario<K, V> nuevoHijoDerecho = this.rotacionSimpleADerecha(nodoActual.getHijoDerecho());
		nodoActual.setHijoDerecho(nuevoHijoDerecho);
		return rotacionSimpleAIzquierda(nodoActual);
	}
	
	//Tarea 4
	//2. Implementar un método insertar iterativo para dicho árbol. No puede utilizar los metodos insertar o leiminar ya existentes. Tal como establece 
	//	el árbol AVL solo se debe ver la necesidad de balancear por el camino que siguió el algoritmo para insertar hasta volver a la raíz
	
	public void insertarIterativo(K clave, V valor) throws ExcepcionClaveYaExiste {
		if(this.esArbolVacio()) {	
			this.raiz = new NodoBinario<K, V>(clave, valor);
		}else{	
			Stack<NodoBinario<K, V>> pilaDeAncestros = new Stack<>();
			NodoBinario<K, V> nodoActual = this.raiz;
			NodoBinario<K, V> nodoAnterior = this.nuevoNodoVacio(); 
			while (!NodoBinario.esNodoVacio(nodoActual)) { 
				nodoAnterior = nodoActual;
				pilaDeAncestros.push(nodoActual);
				if(clave.compareTo(nodoActual.getClave()) > 0) { 
					nodoActual = nodoActual.getHijoDerecho();	
				}else if(clave.compareTo(nodoActual.getClave()) < 0) {
					nodoActual = nodoActual.getHijoIzquierdo(); 
				}else {										   
					throw new ExcepcionClaveYaExiste("La clave que quiere insertar "+clave+" ya existe en su arbol"); 
				}
			} 
			NodoBinario<K, V> nuevoNodo = new NodoBinario<K, V>(clave, valor);
			if(clave.compareTo(nodoAnterior.getClave()) > 0) {
				nodoAnterior.setHijoDerecho(nuevoNodo);
			}else {
				nodoAnterior.setHijoIzquierdo(nuevoNodo);
			}
			while (!pilaDeAncestros.isEmpty()) {
				nodoActual = pilaDeAncestros.pop();
				if (!pilaDeAncestros.isEmpty()) {
					NodoBinario<K, V> nodoPadre = pilaDeAncestros.peek();
					K claveActual = nodoActual.getClave();
					K clavePadre = nodoPadre.getClave();
					if (claveActual.compareTo(clavePadre) < 0) {
						nodoPadre.setHijoIzquierdo(this.balancear(nodoActual));
					}else{
						nodoPadre.setHijoDerecho(this.balancear(nodoActual));
					}
		            }else {
		            	this.raiz=this.balancear(nodoActual);
		            }
			}
		}
	}
		
}
