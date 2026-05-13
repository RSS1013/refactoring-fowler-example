package ubu.gii.dass.refactoring;

import java.util.*;
import ubu.gii.dass.refactoring.Statement.HtmlStatement;
import ubu.gii.dass.refactoring.Statement.TextStatement;

/**
 * Tema Refactorizaciones * Clase Customer refactorizada para delegar el cálculo
 * de importes y puntos. Se han eliminado las dependencias directas con la
 * lógica de precios (Feature Envy). * @author M. Fowler y
 * <A HREF="mailto:clopezno@ubu.es">Carlos López</A>
 * 
 * @version 1.2
 */
public class Customer {
	private String _name;
	private List<Rental> _rentals;

	public Customer(String name) {
		_name = name;
		_rentals = new ArrayList<Rental>();
	}

	public void addRental(Rental arg) {
		_rentals.add(arg);
	}

	public String getName() {
		return _name;
	}

	/**
	 * Genera el informe de alquileres en formato texto plano.
	 */
	public String statement() {
		return createStatement(new TextStatement());
	}

	/**
	 * Requerimiento 1: Genera el informe de alquileres en formato HTML.
	 */
	public String htmlStatement() {
		return createStatement(new HtmlStatement());
	}

	public String createStatement(Statement statement) {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		
		String result = statement.header(getName());
		// Refactorización: uso de for-each para simplificar la iteración
		for (Rental each : _rentals) {
			

			double thisAmount = each.getCharge();
			frequentRenterPoints += each.getFrequentRenterPoints();

			result += statement.rentalStatement(each.getMovie().getTitle(), thisAmount);
			totalAmount += thisAmount;
		}

		// Pie del informe
		result += statement.footer(totalAmount, frequentRenterPoints);
		return result;
	}
}