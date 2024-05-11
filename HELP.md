# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

entityManager.persist(
new Book()
.setIsbn("978-9730228236")
.setTitle("High-Performance Java Persistence")
.setPublishedOn(YearMonth.of(2016, 10))
.setPresalePeriod(
Duration.between(
LocalDate
.of(2015, Month.NOVEMBER, 2)
.atStartOfDay(),
LocalDate
.of(2016, Month.AUGUST, 25)
.atStartOfDay()
)
)
);

## LOCALDATE TIME

LocalDateTime timestamp = LocalDateTime.of(2023, 3, 15, 10, 30, 0); // 2023-03-15T10:30:00

int hours = timestamp.getHour(); // Gets the hour component (10 in this example)

DayOfWeek dayOfWeek = timestamp.getDayOfWeek(); // Gets the day of the week (e.g., MONDAY, TUESDAY, etc.)

int dayOfMonth = timestamp.getDayOfMonth(); // Gets the day of the month (15 in this example)

LocalDate date = timestamp.toLocalDate(); // Gets the date (2023-03-15 in this example)

import java.time.format.DateTimeFormatter;

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String formattedTimestamp = timestamp.format(formatter);
