package com.popo;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class MyProjectApplication {

	@Bean
	CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// Configure allowed origins, methods, headers, etc.
		config.addAllowedOrigin("*"); // Replace with your allowed origins
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	// COMPARATOR

	// sort it for moins disant
	// Sort ASC // by default add .resersed() if you want to DESC
	// Comparator<ArticleSupplier> comparatorPriceTTC = Comparator
	// .comparingDouble(obj -> obj.getPrice_HT() + getPercent(obj.getTva(),
	// obj.getPrice_HT())); //.reversed() if you want to DESC

	// Collections.sort(articlesSuppliers, comparatorPriceTTC);

	// TIMESTAMP SORT
	// List<Timestamp> timestampList = new ArrayList<>();
	// timestampList.add(Timestamp.valueOf("2024-05-14 10:00:00"));
	// timestampList.add(Timestamp.valueOf("2024-05-14 12:30:00"));
	// timestampList.add(Timestamp.valueOf("2024-05-14 08:45:00"));

	// // Sort the list of Timestamp objects
	// Collections.sort(timestampList, Comparator.comparing(Timestamp::getTime));

	// // Print the sorted list
	// System.out.println("Sorted Timestamps:");
	// for (Timestamp timestamp : timestampList) {
	// System.out.println(timestamp);
	// }

	public static void main(String[] args) {

		// +03:00 ny ety am tsika
		// Timestamp timestamp =
		// Timestamp.from(Instant.parse("2024-05-07T09:09:10.00+03:00"));
		// System.out.println(timestamp.toString());

		// LocalDateTime l = timestamp.toLocalDateTime();
		// LocalDateTime pp = (l.minusHours(1));
		// System.out.println(l);

		// System.out.println(Timestamp.valueOf(l));

		// System.out.println(timestamp.after(Timestamp.valueOf(pp)));

		// long minutes = 120; // Example: 2 hours (120 minutes)
		// Duration duration = Duration.ofMinutes(minutes);

		// LocalDateTime currentDateTime = LocalDateTime.now();
		// LocalDateTime targetDateTime = currentDateTime.plus(duration);
		// Timestamp timestamp = Timestamp.valueOf(targetDateTime);

		SpringApplication.run(MyProjectApplication.class, args);
	}

}
