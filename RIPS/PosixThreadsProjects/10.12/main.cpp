#include <vector>
#include <algorithm>
#include <random>
#include <future>
#include <iostream>

using namespace std;

//int vector_max(vector<int> &vec) {
//    return *max_element(vec.begin(), vec.end());
//}
//
//int subMatrixMax(vector<vector<int>> &matrix, int i, int t) {
//    int n = matrix.size();
//    int r = n % t;
//    int start = min(i, r) + (n / t) * i;
//    int end = min(i + 1, r) + (n / t) * (i + 1);
//
//    vector<int> maximums;
//    for (i = start; i < end; ++i)
//        maximums.emplace_back(vector_max(matrix[i]));
//
//    return vector_max(maximums);
//}

int main() {
    int rows = 100, cols = 100;
    const int n_threads = 4;

    mt19937 rng{random_device{}()};
    uniform_int_distribution<int> dist{1, 100000};

    vector<vector<int>> vec(rows, vector<int>(cols));
    for (auto i = 0; i < cols; ++i)
        for (auto j = 0; j < rows; ++j)
            vec[i][j] = dist(rng);


    auto start = std::chrono::high_resolution_clock::now();
    vector<future<int>> futures;
    vector<thread> threads;
    vector<int> maximums(n_threads);

    for (auto i = 0; i < n_threads; ++i) {
        auto task = packaged_task<int(vector<vector<int>> &, int, int)>(subMatrixMax);
        futures.emplace_back(task.get_future());
        threads.emplace_back(move(task), ref(vec), i, n_threads);
    }

    for (auto i = 0; i < n_threads; ++i) {
        maximums[i] = futures[i].get();
        threads[i].join();
    }
    auto end = std::chrono::high_resolution_clock::now();
    auto res = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();
    cout << "maximum = " << vector_max(maximums)
         << " time = " << res << "ms.\n";
}
