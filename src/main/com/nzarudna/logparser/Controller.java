package com.nzarudna.logparser;

import com.nzarudna.logparser.model.LogAnalyser;
import com.nzarudna.logparser.model.Request;
import com.nzarudna.logparser.model.out.PrinterFactory;
import com.nzarudna.logparser.model.out.StatisticsPrinter;
import com.nzarudna.logparser.model.parser.LogParser;
import com.nzarudna.logparser.model.parser.LogParserException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

public class Controller {

	public static void main(String[] args) {
		
		String fileName = args[0];
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));

			List<Request> requests = new ArrayList<>();

			String line;
			while((line = reader.readLine()) != null) {
                Request request = LogParser.getInstance().parseLog(line);
                requests.add(request);
			}

			StatisticsPrinter printer = PrinterFactory.getInstance();
			LogAnalyser logAnalyser = new LogAnalyser(requests);

			HashMap<String, Double> requestDurationStatistics = logAnalyser.getRequestDurationStatistics();
			printer.printDurationStatistics(requestDurationStatistics);

			SortedMap<Integer, Integer> hourlyRequestNumberStatistics = logAnalyser.getHourlyRequestNumberStatistics();
			printer.drawHistogram(hourlyRequestNumberStatistics);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LogParserException e) {
            e.printStackTrace();
        } finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
