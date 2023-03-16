#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
using namespace std;
int promptOptionFromUser(string prompt_message){
	int user_input;
	cout << prompt_message;
	cin >> user_input;
	return user_input;
}
int getRootMenuInput(){
	cout << "(1) List shop "<< endl;
	cout << "(2) View cart " << endl;
	cout << "(3) Exit " << endl;
	int option;
	cout << "Enter your option: ";
	cin >> option;
	return option;
}
int countNumberOfFileLines(string file_name){
	ifstream file_object;
	file_object.open(file_name);
	int count = 0;
	string line;
	while (!file_object.eof()){
		getline(file_object, line);
		count++;
	}
	file_object.close();
	return count - 1;
}
string* getFileLinesArray(string file_name){
	ifstream file_object;
	file_object.open(file_name);
	int number_of_shops = countNumberOfFileLines(file_name);
	string* shops = new string[number_of_shops + 1];
	string line;
	int count = 0;
	while (!file_object.eof()){
		getline(file_object, line);
		shops[count] = line;
		count++;
	}
	shops[count - 1] = "-";
	file_object.close();
	return shops;
}
int getStringArrayLength(string array[]){
	int counter = 0;
	for (;array[counter] != "-";counter++);
	return counter;
}
void listOptions(string array[]){
	int array_len = getStringArrayLength(array);
	for (int i = 0; i < array_len; i++){
		cout << "(" << i + 1 << ") " << array[i] << endl;
	}
}
void goToShopsList(){
	cout << "Choose shop to buy from."<<endl;
	string* shop_array = getFileLinesArray("shops.txt");
	listOptions(shop_array);
	cout << "(0) back" << endl;
}
void goToShopCategory(string file_name){
	cout << "Choose category." << endl;
	string* category_array = getFileLinesArray(file_name);
	listOptions(category_array);
	cout << "(0) back" << endl;
}
void goToItemsList(string file_name){
	cout << "Choose item to buy." << endl;
	string* items_array = getFileLinesArray(file_name);
	listOptions(items_array);
	cout << "(0) back" << endl;
}
string getCategoryFileName(string shop_name){
	string name = shop_name + "_category.txt";
	return name;
}
string getItemsFileName(string shop_name, string category){
	string name = shop_name + "_" + category + "_items.txt";
	return name;
}
void chooseShop();
void goToCart();
void goToMainList(){
	int option;
	do{
		option = getRootMenuInput();
	}while(!((option == 1) || (option == 2)|| (option == 3)));
	if (option == 1){
		chooseShop();
	}
	else if(option == 2){
		goToCart();
	}
}
void chooseShopCategory(string, string);
void chooseShop(){
	int shops_option;
	int shops_list_len;
	string* shops_list; 
	do{
		goToShopsList();
		shops_option = promptOptionFromUser("Enter shop option: ");
		shops_list = getFileLinesArray("shops.txt");
		shops_list_len = getStringArrayLength(shops_list);
	}while (!((shops_option >= 0) && (shops_option <= shops_list_len)));
	if (shops_option == 0){
		goToMainList();
	}
	else {
		string shop_name = shops_list[shops_option - 1];
		string category_file_name = getCategoryFileName(shop_name); 
		chooseShopCategory(category_file_name, shop_name);
	}
}
void chooseShopItems(string, string, string);
void chooseShopCategory(string file_name, string shop){
	int option;
	int list_len;
	string* category_list;
	do{
		goToShopCategory(file_name);
		option = promptOptionFromUser("Enter category: ");
		category_list = getFileLinesArray(file_name);
		list_len = getStringArrayLength(category_list);
	}while (!((option >= 0) && (option <= list_len)));
	if (option == 0){
		chooseShop();
	}
	else {
		string category_name = category_list[option - 1];
		string items_file_name = getItemsFileName(shop, category_name);
		chooseShopItems(items_file_name, shop, file_name);
	}
}
void putOnCart(string, string);
void chooseShopItems(string file_name, string shop, string category_file){
	int option;
	string* items_list;
	int list_len;
	do{
		goToItemsList(file_name);
		option = promptOptionFromUser("Enter item: ");
		items_list = getFileLinesArray(file_name);
		list_len = getStringArrayLength(items_list);
	}while (!((option >= 0) && (option <= list_len)));
	if (option == 0){
		chooseShopCategory(category_file, shop);
	}
	else {
		string item_name = items_list[option - 1];
		putOnCart(item_name, shop);
		chooseShopItems(file_name, shop, category_file);
	}
}
void putOnCart(string item_name, string shop_name){
	ofstream file_object;
	file_object.open("cart.txt", ios::out | ios::app);
	file_object << shop_name + " : " + item_name + "\n";
	file_object.close();
}
void listCartItems(){
	string* cart_items = getFileLinesArray("cart.txt");
	for (int i = 0; i < getStringArrayLength(cart_items); i++){
		cout << "(" << i + 1  << ") " << cart_items[i] << endl;
	}
}
float getTotalPrice(){
	string* cart_items = getFileLinesArray("cart.txt");
	float total_price = 0.0;
	for (int i = 0; i < getStringArrayLength(cart_items); i++){
		int dash_index = cart_items[i].find("-");
		int length = (cart_items[i].length() - (dash_index + 1));
		string string_price = cart_items[i].substr(dash_index +2, length);
		float item_price;
		stringstream(string_price) >> item_price;
		total_price += item_price;
	}
	return total_price;
}
char promptUserToRemoveAnItemOrNot(){
	cout << "Would you like to remove an item? (Y/y) or (N/n): ";
	char answer;
	cin >> answer;
	return answer;
}
void removeItemFromCartFile(int item_number){
	string* cart_items = getFileLinesArray("cart.txt");
	ofstream file_object;
	file_object.open("cart.txt", ios::out);
	for (int i = 0; i < getStringArrayLength(cart_items); i++){
		if (item_number != (i + 1)){
			file_object << cart_items[i] + "\n";
		}
		else {
			file_object << "";
		}
	}
	file_object.close();
}
char promptUserToBuyOrNot(){
	cout << "Buy all items? (Y/y) or (N/n): ";
	char answer;
	cin >> answer;
	return answer;
}
void eraseCartFile(){
	ofstream file_object;
	file_object.open("cart.txt");
	file_object << "";
	file_object.close();
}
void runItemRemovalOption();
void runBuyingOption();
void goToCart(){
	listCartItems();
	float price = getTotalPrice();
	if (price > 0){
		cout << "Total items price is R"<< price << endl;
	}
	runItemRemovalOption();
}
int getItemNumber(){
	int number;
	string* cart_array = getFileLinesArray("cart.txt");
	int array_len = getStringArrayLength(cart_array); 
	do{
		cout << "Enter item to remove: ";
		cin >> number;
	}while (!(number >= 1) && (number <= array_len));
	return number;
}
void runItemRemovalOption(){
	char answer;
	string* array = getFileLinesArray("cart.txt");
	int array_len = getStringArrayLength(array);
	if (array_len > 0){
		do{
			answer = promptUserToRemoveAnItemOrNot();
		}while (!((answer == 'Y')|| (answer == 'y') || (answer == 'N') || (answer == 'n')));
		if ((answer == 'Y') || (answer == 'y')){
			int item_number = getItemNumber();
			removeItemFromCartFile(item_number);
		}
		else if ((answer == 'N') || (answer == 'n')){
			runBuyingOption();
		}
	}
	goToMainList();
}
void runBuyingOption(){
	char answer;
	string* array = getFileLinesArray("cart.txt");
	int array_len = getStringArrayLength(array);
	if (array_len > 0){
		do {
			answer = promptUserToBuyOrNot();
			if ((answer == 'Y') || (answer == 'y')){
				eraseCartFile();
			}
		}while (!((answer == 'N') || (answer == 'n') || (answer == 'Y') || (answer == 'n')));
	}
	cout << "Go to main List " << endl;
	goToMainList();
}
int main(){
	goToMainList();
	return 0;
}
