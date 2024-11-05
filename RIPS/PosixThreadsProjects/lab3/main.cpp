#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <chrono>

std::map<std::string, int> linear();

void *delegation(void *args);

void *producer_consumer(void *args);

typedef struct bounds {
    int start;
    int finish;
} bounds;

std::vector<bounds> slice(int len, int count);

#define THREAD_COUNT 4
std::map<std::string, int> result;
std::vector<std::string> files_names;
const std::string word = "hello";

int main() {
    std::ifstream fin("files_names.txt");
    std::string tmp;
    while (fin >> tmp) files_names.push_back(tmp);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /*linear*/
    std::cout << files_names.size();
    auto start = std::chrono::high_resolution_clock::now();
    //linear();
    auto stop = std::chrono::high_resolution_clock::now();
    std::cout << "Linear: "
              << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6
              << std::endl;
    result.clear();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /*delegation*/
    start = std::chrono::high_resolution_clock::now();

    pthread_t thread_id[THREAD_COUNT];
    std::vector<bounds> params = slice(files_names.size(), THREAD_COUNT);

    for (int i = 0; i < THREAD_COUNT; ++i)
        if (pthread_create(&thread_id[i], nullptr, delegation, (void *) &params[i]))
            return -1;

    for (const auto &k : thread_id)
        pthread_join(k, nullptr);

    stop = std::chrono::high_resolution_clock::now();

    for (auto &item : result)
        std::cout << "File Name: " << item.first << " coincidence:" << item.second << std::endl;

    std::cout << "Delegation: "
              << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6
              << std::endl;

    result.clear();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /*producer_consumer*/
//    start = std::chrono::high_resolution_clock::now();
//    producer_consumer(files_names, word);
//    stop = std::chrono::high_resolution_clock::now();
//    std::cout << "Producer Consumer: "
//              << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6
//              << std::endl;

    return 0;
}


std::map<std::string, int> linear() {
    std::string tmp;
    for (const auto &x : files_names) {
        int count = 0;
        std::ifstream f(x);
        while (f >> tmp)
            if (tmp == word)
                count++;

        result.insert(std::pair<std::string, int>(x, count));
        f.close();
    }

    for (auto &item : result)
        std::cout << "File Name: " << item.first << " coincidence:" << item.second << std::endl;

    return result;
}

void *delegation(void *args) {
    auto *params = (bounds *) args;

    std::string tmp;
    for (int i = params->start; i < params->finish; ++i) {
        int count = 0;
        std::string x = files_names[i];
        std::ifstream f(x);
        while (f >> tmp)
            if (tmp == word)
                count++;

        result.insert(std::pair<std::string, int>(x, count));
        f.close();
    }

    return nullptr;
}

void *producer_consumer(void *args) {
    std::string tmp;
    for (const auto &x : files_names) {
        int count = 0;
        std::ifstream f(x);
        while (f >> tmp)
            if (tmp == word)
                count++;

        result.insert(std::pair<std::string, int>(x, count));
        f.close();
    }

    return nullptr;
}

std::vector<bounds> slice(int len, int count) {
    std::vector<bounds> vector;
    int hop = len / count;
    for (int i = 0; i < len; i += hop) {
        if (i + hop > len)
            vector.push_back({.start = i, .finish = len - (i + hop)});

        vector.push_back({.start = i, .finish = i + hop});
    }

    return vector;
}