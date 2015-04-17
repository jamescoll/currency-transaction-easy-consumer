package currencyTransactionConsumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

public class Application {
	
	private static final String filename = "transactionlog.txt";

	public static void main(String args[]) {
		RestTemplate restTemplate = new RestTemplate();
		ArrayList<CurrencyTransaction> transactions = new ArrayList<>();
		String urlString = "http://localhost:8080/currencyTransaction";
		
		for (int i = 1; i < 6; i++) {
			
			CurrencyTransaction transaction = restTemplate.getForObject(urlString + "?transactionNumber=" + i, CurrencyTransaction.class);
			transactions.add(transaction);
			
		}
		
		for(CurrencyTransaction transaction : transactions){
			outputTransaction(transaction);
			
		}

		saveTransactions(transactions, filename);
	}

	private static void saveTransactions(ArrayList<CurrencyTransaction> transactions, String filename){

		try {
			File file = new File(filename);

			if(!file.exists()){
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			writeTransactionRecords(transactions, bw);
			System.out.println("Successfully written file");
			
			bw.close();
			
		} catch (IOException e){
			e.printStackTrace();
		} 
	}

	private static void writeTransactionRecords(
			ArrayList<CurrencyTransaction> transactions, BufferedWriter bw)
			throws IOException {
		for(CurrencyTransaction transaction : transactions) {
			
			bw.write("*Transaction Record*");
			bw.newLine();
			bw.write("UserId: " + transaction.getUserId());
			bw.newLine();
			bw.write("Currency From: " + transaction.getCurrencyFrom());
			bw.newLine();
			bw.write("Currency To: "+ transaction.getCurrencyTo());
			bw.newLine();
			bw.write("Amount Sell: " + transaction.getAmountSell());
			bw.newLine();
			bw.write("Amount Buy: " + transaction.getAmountBuy());
			bw.newLine();
			bw.write("Rate: " + transaction.getRate());
			bw.newLine();
			bw.write("Time Placed: " + transaction.getTimePlaced());
			bw.newLine();
			bw.write("Originating Country: " + transaction.getOriginatingCountry());
			bw.newLine();
		
		}
	}

	private static void outputTransaction(CurrencyTransaction transaction){

		System.out.println("*Transaction Record*");
		System.out.println("UserId: " + transaction.getUserId());
		System.out.println("Currency From: " + transaction.getCurrencyFrom());
		System.out.println("Currency To: "+ transaction.getCurrencyTo());
		System.out.println("Amount Sell: " + transaction.getAmountSell());
		System.out.println("Amount Buy: " + transaction.getAmountBuy());
		System.out.println("Rate: " + transaction.getRate());
		System.out.println("Time Placed: " + transaction.getTimePlaced());
		System.out.println("Originating Country: " + transaction.getOriginatingCountry());
	}

}