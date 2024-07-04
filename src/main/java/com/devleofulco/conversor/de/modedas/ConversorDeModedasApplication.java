package com.devleofulco.conversor.de.modedas;

import com.devleofulco.conversor.de.modedas.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ConversorDeModedasApplication implements CommandLineRunner {

	@Autowired
	private ExchangeRateService exchangeRateService;

	public static void main(String[] args) {
		SpringApplication.run(ConversorDeModedasApplication.class, args);
	}

	@Override
	public void run (String... args){
		Scanner scanner = new Scanner(System.in);

		while (true){
			System.out.println("Escolha uma opção de conversão: ");
			System.out.println("1: USD para EUR");
			System.out.println("2: USD para GBP");
			System.out.println("3: USD para BRL");
			System.out.println("4: EUR para USD");
			System.out.println("5: GBP para USD");
			System.out.println("6: BRL para USD");
			System.out.println("7: USD para CAD");
			System.out.println("8: CAD para USD");
			System.out.println("0: Sair");

			int option = scanner.nextInt();
			if (option == 0){
				break;
			}

			double amount;
			switch (option){
				case 1:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"USD","EUR");
					break;
				case 2:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"USD","GBP");
					break;
				case 3:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"USD","BRL");
					break;
				case 4:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"EUR","USD");
					break;
				case 5:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"GBP","USD");
					break;
				case 6:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"BRL","USD");
					break;
				case 7:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"USD","CAD");
					break;
				case 8:
					amount = getAmountFromUser(scanner);
					convertAndPrint(amount,"CAD","USD");
					break;
				default:
					System.out.println("Opção Inválida. Tente novamente...");
			}

		}
	}
		private double getAmountFromUser(Scanner scanner){
			System.out.println("Digite o valor a ser convertido: ");
			return scanner.nextDouble();
		}

		private void convertAndPrint(double amount, String fromCurrency, String toCurrency){
			Double rate = exchangeRateService.getExchangeRate(toCurrency);
			if (rate != null){
				double convertedAmount = amount * rate;
				System.out.printf("O valor convertido de %s para %s é: %.2f%n", fromCurrency,toCurrency,convertedAmount);
			}else {
				System.out.println("Erro ao obter taxa e câmbio.");
			}
		}
}
