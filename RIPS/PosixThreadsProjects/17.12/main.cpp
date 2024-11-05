#include <iostream>
#include <vector>
#include <algorithm>
#include <random>
#include <chrono>

using namespace std;

int main() {
    int rows = 1000, cols = 100;
    vector<vector<int>> vec(rows, vector<int>(cols));

    mt19937 rng{random_device{}()};
    uniform_int_distribution<int> dist{1, 100000};
    for (auto i = 0; i < cols; ++i)
        for (auto j = 0; j < rows; ++j)
            vec[i][j] = dist(rng);

    auto start = chrono::high_resolution_clock::now();
    int Max = -1;
    for (auto i = 0; i < cols; ++i) {
        int Min = INFINITY;

        for (auto j = 0; j < rows; ++j)
            Min = min(Min, vec[i][j]);

        Max = max(Min, Max);
    }

    auto end = chrono::high_resolution_clock::now();
    auto res = chrono::duration_cast<chrono::microseconds>(end - start).count();
    cout << "maximum = " << Max << " time = " << res << "ms.\n";
}
