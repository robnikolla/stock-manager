package application;

public class Products {
		
	
		private int id;
		private String name;
		private String producer;
		private int barcode;
		private double price;
		private int stock;	
		
		public Products(int id, String name, String producer, int barcode, double price, int stock) {
			super();
			this.id = id;
			this.name = name;
			this.producer = producer;
			this.barcode = barcode;
			this.price = price;
			this.stock = stock;
		}
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProducer() {
			return producer;
		}
		public void setProducer(String producer) {
			this.producer = producer;
		}
		public int getBarcode() {
			return barcode;
		}
		public void setBarcode(int barcode) {
			this.barcode = barcode;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getStock() {
			return stock;
		}
		public void setStock(int stock) {
			this.stock = stock;
		}
		
		
}
