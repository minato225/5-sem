#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <chrono>

std::map<std::string, int> linnear(std::vector<std::string>& files_names, std::string& word) {
	std::string tmp;
	std::map<std::string, int>result;
	for (auto x : files_names) {
		int count = 0;
		std::ifstream f(x);
		while (f >> tmp)
			if (tmp == word)
				count++;

		result.insert(std::pair<std::string, int>(x, count));
		f.close();
	}

	for (auto& item : result)
		std::cout << "File Name: " << item.first << " coincidence:" << item.second << std::endl;

	return result;
}
std::map<std::string, int> delegation(std::vector<std::string>& files_names, std::string& word) {
	std::string tmp;
	std::map<std::string, int>result;
	for (auto x : files_names) {
		int count = 0;
		std::ifstream f(x);
		while (f >> tmp)
			if (tmp == word)
				count++;

		result.insert(std::pair<std::string, int>(x, count));
		f.close();
	}

	for (auto& item : result)
		std::cout << "File Name: " << item.first << " coincidence:" << item.second << std::endl;
	return result;
}
std::map<std::string, int> producer_consuner(std::vector<std::string>& files_names, std::string& word) {
	std::string tmp;
	std::map<std::string, int>result;
	for (auto x : files_names) {
		int count = 0;
		std::ifstream f(x);
		while (f >> tmp)
			if (tmp == word)
				count++;

		result.insert(std::pair<std::string, int>(x, count));
		f.close();
	}


	for (auto& item : result)
		std::cout << "File Name: " << item.first << " coincidence:" << item.second << std::endl;
	return result;
}

int main() {
	std::ifstream fin("files_names.txt");
	std::vector<std::string> files_names;
	std::string tmp;
	while (fin >> tmp) files_names.push_back(tmp);

	std::string word;
	std::cin >> word;

	std::map<std::string, int>result;

	auto start = std::chrono::high_resolution_clock::now();
	linnear(files_names, word);
	auto stop = std::chrono::high_resolution_clock::now();
	std::cout << "Linnear: " << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6 << std::endl;

	start = std::chrono::high_resolution_clock::now();
	linnear(files_names, word);
	stop = std::chrono::high_resolution_clock::now();
	std::cout << "Delegation: " << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6 << std::endl;

	start = std::chrono::high_resolution_clock::now();
	linnear(files_names, word);
	stop = std::chrono::high_resolution_clock::now();
	std::cout << "Producer Consuner: " << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6 << std::endl;

	return 0;
}