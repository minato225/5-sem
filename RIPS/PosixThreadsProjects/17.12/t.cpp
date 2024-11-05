#include <iostream>
#include <omp.h>
#include <random>
#include <climits>
#include <chrono>
#include <algorithm>

using namespace std;

int parallel_nested(const vector<vector<int>> &matrix) {
    int max_val = INT_MIN;

    omp_set_nested(true);

#pragma omp parallel for reduction(max: max_val)
    for (const auto &row : matrix) {
        int min_in_row = INT_MAX;

#pragma omp parallel for reduction(min: min_in_row)
        for (int j = 0; j < row.size(); j++) {
            min_in_row = min(min_in_row, row[j]);
        }

        max_val = max(max_val, min_in_row);
    }
    return max_val;
}

int parallel(const vector<vector<int>> &matrix) {
    int max_val = INT_MIN;

#pragma omp parallel for reduction(max: max_val)
    for (const auto &row : matrix) {
        int min_in_row = *min_element(row.begin(), row.end());
        max_val = max(min_in_row, max_val);
    }

    return max_val;
}

int main() {
    int size = 1'000;
    vector<vector<int>> vec(size, vector<int>(size));

    mt19937 rng{random_device{}()};
    uniform_int_distribution<int> dist{1, 100000};
    for (auto i = 0; i < size; ++i)
        for (auto j = 0; j < size; ++j)
            vec[i][j] = dist(rng);

    auto start = std::chrono::high_resolution_clock::now();
    parallel(vec);
    auto end = std::chrono::high_resolution_clock::now();
    auto res = (end - start).count();
    cout << res << " ms\n";

    start = std::chrono::high_resolution_clock::now();
    parallel_nested(vec);
    end = std::chrono::high_resolution_clock::now();
    res = (end - start).count();
    cout << res << " ms\n";
    return 0;
}