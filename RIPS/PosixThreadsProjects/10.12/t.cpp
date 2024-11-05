#include <vector>
#include <algorithm>
#include <iostream>
#include <random>

using namespace std;

int main() {
    int rows = 100, cols = 100;

    mt19937 rng{random_device{}()};
    uniform_int_distribution<int> dist{1, 100000};

    std::vector <std::vector<int >> vec(rows, std::vector<int>(cols));
    for (auto i = 0; i < vec.size(); ++i)
        for (auto j = 0; j < vec[0].size(); ++j)
            vec[i][j] = dist(rng);

    std::vector<int> maximums(vec.size());
    for (auto i = 0; i < maximums.size(); ++i)
        maximums[i] = *std::max_element(vec[i].begin(), vec[i].end());

    int total_max = *std::max_element(maximums.begin(), maximums.end());

    std::cout << total_max << "\n";
    return 0;
}