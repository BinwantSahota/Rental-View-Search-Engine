package rse;
//C:\Users\Binwant Singh\eclipse-workspace\RentalVIEW\TextFile
//C:\Users\Binwant Singh\eclipse-workspace\RentalVIEW\HtmlFiles
//C:\Users\Binwant Singh\Desktop\RentalView

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class URLavailableListings {

	public static void propertyDeal(String streetName) throws IOException {
		String folderPath = "C:\\Users\\Binwant Singh\\eclipse-workspace\\RentalVIEW";
		File folder = new File(folderPath);

		File[] fileNames = folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".txt");
			}
		});

		List<Property> properties = new ArrayList<>();

		for (File fileName : fileNames) {
			try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
				String line;
				while ((line = reader.readLine()) != null) {
					line = line.toLowerCase();
					if (line.contains(streetName.toLowerCase())) {
						Property property = parseProperty(line);
						properties.add(property);
					}
				}
			}
		}

		List<Property> cheapestProperties = properties.stream().sorted(Comparator.comparingDouble(Property::getPrice))
				.limit(5).collect(Collectors.toList());

		if (!cheapestProperties.isEmpty()) {
			for (Property property : cheapestProperties) {
				System.out.println(property);
			}
		} else {
			System.out.println("No Rental properties available in the street " + streetName);
		}
	}

	private static Property parseProperty(String line) {
		String[] parts = line.split("\\|");
		String address = parts[0].replace("address:", "").trim();
		String bedrooms = parts[1].replace("bedrooms:", "").trim();
		String priceStr = parts[2].replaceAll("[^0-9\\.\\-]", "");

		double price;
		try {
			if (priceStr.contains("-")) {
				String[] priceParts = priceStr.split("-");
				price = Double.parseDouble(priceParts[1].trim());
			} else {
				price = Double.parseDouble(priceStr);
			}
		} catch (NumberFormatException e) {
			// Handle the case where parsing the price fails
			// You can set a default value, e.g., -1, to indicate an invalid price.
			price = 1500;
		}

		String areaStr = parts[3].replaceAll("[^0-9]", "");
		int area;
		try {
			area = Integer.parseInt(areaStr);
		} catch (NumberFormatException e) {
			// Handle the case where parsing the area fails
			// You can set a default value, e.g., -1, to indicate an invalid area.
			area = 1280;
		}

		return new Property(address, bedrooms, price, area);
	}
}

class Property {
	private String address;
	private String bedrooms;
	private double price;
	private int area;

	public Property(String address, String bedrooms2, double price, int area) {
		this.address = address;
		this.bedrooms = bedrooms2;
		this.price = price;
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public String getBedrooms() {
		return bedrooms;
	}

	public double getPrice() {
		return price;
	}

	public int getArea() {
		return area;
	}

	@Override
	public String toString() {
		return "Address: " + address + " | Bedrooms: " + bedrooms + " | Price: $" + price + " | Area: " + area;
	}
}
