#include <execution>
#include <algorithm>
#include <iostream>
#include <vector>
#include <chrono>

int main() {
    for (auto n : {1000, 1000000, 1000000}) {
        auto start = std::chrono::high_resolution_clock::now();

        std::vector<int>v(n);
        std::generate(v.begin(), v.end(), [n = 0]() mutable {n++;});

        auto stop = std::chrono::high_resolution_clock::now();

        std::cout << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6
                  << std::endl;

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        start = std::chrono::high_resolution_clock::now();

        std::vector<int>v(n);
        std::generate(std::execution::par, v.begin(), v.end(), [n = 0]() mutable {n++;});

        stop = std::chrono::high_resolution_clock::now();

        std::cout << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6
                  << std::endl;
    }
}