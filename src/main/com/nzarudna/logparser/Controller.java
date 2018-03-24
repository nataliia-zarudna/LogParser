package com.nzarudna.logparser;

import com.nzarudna.logparser.model.LogAnalyser;
import com.nzarudna.logparser.model.Request;
import com.nzarudna.logparser.model.out.PrinterFactory;
import com.nzarudna.logparser.model.out.StatisticsPrinter;
import com.nzarudna.logparser.model.parser.LogParser;
import com.nzarudna.logparser.model.parser.LogParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.SortedMap;

public class Controller {

	public static void main(String[] args) {
        Instant start = Instant.now();
		
		String fileName = args[0];
		int topDurationRequestsCount = Integer.parseInt(args[1]);

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

			NavigableMap<Double, String> requestDurationStatistics
					= logAnalyser.getRequestDurationStatistics();
			printer.printDurationStatistics(requestDurationStatistics, topDurationRequestsCount);

			SortedMap<Integer, Integer> hourlyRequestNumberStatistics = logAnalyser.getHourlyRequestNumberStatistics();
			printer.drawHistogram(hourlyRequestNumberStatistics);

		} catch (IOException | LogParserException e) {
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

		Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        PrinterFactory.getInstance().printProgramExecuteDuration(duration.toMillis());
	}
}
