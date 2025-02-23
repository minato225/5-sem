#include <random>
#include <algorithm>
#include <thread>
#include <iostream>

template<typename Iterator, typename T>
struct accumulate_block {
    void operator()(Iterator first, Iterator last, T &result) {
        result = *std::max_element(first, last);
    }
};

template<typename Iterator, typename T>
T parallel_max(Iterator first, Iterator last, T init) {
    unsigned long const length = std::distance(first, last);
    if (!length)
        return init;
    unsigned long const min_per_thread = 25;
    unsigned long const max_threads = (length + min_per_thread - 1) / min_per_thread;
    unsigned long const hardware_threads = std::thread::hardware_concurrency();
    unsigned long const num_threads = std::min(hardware_threads != 0 ? hardware_threads : 2, max_threads);
    unsigned long const block_size = length / num_threads;
    std::vector<T> results(num_threads);
    std::vector<std::thread> threads(num_threads - 1);
    Iterator block_start = first;
    for (auto i = 0; i < num_threads - 1; ++i) {
        Iterator block_end = block_start;
        std::advance(block_end, block_size);
        threads[i] = std::thread(accumulate_block<Iterator, T>(), block_start, block_end, std::ref(results[i]));
        block_start = block_end;
    }
    accumulate_block<Iterator, T>()(block_start, last, results[num_threads - 1]);

    for (auto &entry: threads) entry.join();

    return *std::max_element(results.begin(), results.end());
}

int main() {
    std::mt19937 rng{std::random_device{}()};
    std::uniform_int_distribution<int> dist{1, 1000};

    int size = 1000000;
    std::vector<int> vec(size);
    std::generate_n(std::back_inserter(vec), size, [&] { return dist(rng); });

    auto start = std::chrono::high_resolution_clock::now();
    int maximums = *std::max_element(vec.begin(), vec.end());
    auto end = std::chrono::high_resolution_clock::now();
    auto res = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();
    std::cout << "linear" << "maximum = " << maximums << " time = " << res << "ms.\n";

    start = std::chrono::high_resolution_clock::now();
    maximums = parallel_max(vec.begin(), vec.end(), 0);
    end = std::chrono::high_resolution_clock::now();
    res = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();
    std::cout << "parallel:" << "maximum = " << maximums << " time = " << res << "ms.\n";
}