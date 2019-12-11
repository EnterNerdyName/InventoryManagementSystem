//Inventory
- private allParts:ObservableList<Part>
- private allProducts:ObservableList<Product>
+ public addPart(newPart:Part):void
+ public addProduct(newProduct:Product):void
+ public lookupPart(partId:int):Part
+ public lookupProduct(productId:int):Product
+ public lookupPart(partName:String):ObservableList<Part>
+ public lookupProduct(productName:String):ObservableList<Product>
+ public updatePart(index:int, selectedPart:Part):void
+ public updateProduct(index:int, selectedProduct:Product):void
+ public deletePart(selectedPart:Part):void
+ public deleteProduct(selectedProduct:Product):void
+ public getAllParts():ObservableList<Part>
+ public getAllProducts():ObservableList<Product>
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
public abstract class Part {
/**
* A new product with params
* @param id -Product ID, int
* @param name -Product Name, String
* @param price -Product Price, double
* @param stock -Current stock level of Product, int
* @param min -Minimum allowable stock level of product, int
* @param max -Maximum allowable stock level of product, int
*/
private int id;
private String name;
private double price;
private int stock;
private int min;
private int max;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
Part(int _id, String _name, double _price, int _stock, int _min, int _max){}; //constructor
Part(){}; //Default constructor
public void setId(int _id){
	system.out.print("ID:"+id+"-->");
	id = _id; 
	system.out.println(id+"//");
	//x.setId(Integer.parseInt(in.nextLine()));
};
public void setName(String _name){
	system.out.print("Name:"+name+"-->");
	name = _name;
	system.out.println(name+"//");
	//x.setId(in.nextLine());
};
public void setPrice(double _price){
	system.out.print("Price:"+price+"-->");
	price = _price;
	system.out.println(price+"//");
	//x.setPrice(Double.parseDouble(in.nextLine()));
};
public void setStock(int _stock){
	system.out.print("Stock:"+stock+"-->");
	stock = _stock;
	system.out.println(stock+"//");
	//x.setStock(Integer.parseInt(in.nextLine()));
};
public void setMin(int _min){
	system.out.print("Min:"+min+"-->");
	min = _min;
	system.out.println(min+"//");
	//x.setMin(Integer.parseInt(in.nextLine()));
};
public void setMax(int _max){
	system.out.print("Max:"+max+"-->");
	max = _max;
	system.out.println(max+"//");
	//x.setMax(Integer.parseInt(in.nextLine()));
};
public int getId(){
	return id;
};
public String getName(){
	return name;
};
public double getPrice(){
	return price;
};
public int getStock(){
	return stock;
};
public int getMin(){
	return min;
};
public int getMax(){
	return max;
};
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Product{
/**
* A new product with params
* @param id -Product ID, int
* @param name -Product Name, String
* @param price -Product Price, double
* @param stock -Current stock level of Product, int
* @param min -Minimum allowable stock level of product, int
* @param max -Maximum allowable stock level of product, int
*/
//TODO
//- private associatedParts:ObservableList<Part>
private int id;
private String name;
private double price;
private int stock;
private int min;
private int max;
//Below dependent on Parts
Product(int _id, String _name, double _price, int _stock, int _min, int _max){}; //constructor
Product(){}; //Default constructor
public void setId(int _id){
	system.out.print("ID:"+id+"-->");
	id = _id; 
	system.out.println(id+"//");
	//x.setId(Integer.parseInt(in.nextLine()));
};
public void setName(String _name){
	system.out.print("Name:"+name+"-->");
	name = _name;
	system.out.println(name+"//");
	//x.setId(in.nextLine());
};
public void setPrice(double _price){
	system.out.print("Price:"+price+"-->");
	price = _price;
	system.out.println(price+"//");
	//x.setPrice(Double.parseDouble(in.nextLine()));
};
public void setStock(int _stock){
	system.out.print("Stock:"+stock+"-->");
	stock = _stock;
	system.out.println(stock+"//");
	//x.setStock(Integer.parseInt(in.nextLine()));
};
public void setMin(int _min){
	system.out.print("Min:"+min+"-->");
	min = _min;
	system.out.println(min+"//");
	//x.setMin(Integer.parseInt(in.nextLine()));
};
public void setMax(int _max){
	system.out.print("Max:"+max+"-->");
	max = _max;
	system.out.println(max+"//");
	//x.setMax(Integer.parseInt(in.nextLine()));
};
public int getId(){
	return id;
};
public String getName(){
	return name;
};
public double getPrice(){
	return price;
};
public int getStock(){
	return stock;
};
public int getMin(){
	return min;
};
public int getMax(){
	return max;
};
/*
//TODO
+ public addAssociatedPart(part:Part):void
+ public deleteAssociatedPart(associatedPart:Part):void
+ public getAllAssociatedParts():ObservableList<Part>
*/
}
public class InHouse extends Part{
/*	super()?
public Subclass(int a,int b, int c,int x) {
    super(a,b,c);
    this.x = x;
	
	or
	
	public Subclass(Superclass s) {
    super(s.getA(), s.getB(), s.getC()); // constructor  may simplify
}
	*/
private int machineId;
public InHouse(int _id, String _name, double _price, int _stock, int _min, int _max, int _machineId)
{
	super( _id, _name, _price, _stock, _min, _max);
	machineId = _machineId;
};
public int getMachineId(){
	return machineId;
}
public void setMachineId(int _machineId){
	system.out.print("Machine ID:"+machineId+"-->");
	machineId = _machineId;
	system.out.println(machineId+"//");
	//x.setMin(Integer.parseInt(in.nextLine()));
}
}
//////////////////////////////////
public class Outsourced extends Part{
private String companyName;
public Outsourced(int _id, String _name, double _price, int _stock, int _min, int _max, string _companyName){
	super( _id, _name, _price, _stock, _min, _max);
	companyName = _companyName;
};
public int getCompanyName(){
	return companyName;
}
public void setCompanyName(int _companyName){
	system.out.print("Machine ID:"+companyName+"-->");
	companyName = _companyName;
	system.out.println(companyName+"//");
	//x.setMin(Integer.parseInt(in.nextLine()));
}
}