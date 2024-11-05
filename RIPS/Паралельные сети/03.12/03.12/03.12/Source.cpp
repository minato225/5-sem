#include <iostream>
#include <vector>
#include <thread>
#include <functional>
#include <algorithm>

template<typename Iterator, typename T>
struct max_block
{
	void operator()(Iterator first, Iterator last, T& result)
	{
		result = *std::max_element(first, last);
	}
};

template<typename Iterator, typename T>
T parallel_max(Iterator first, Iterator last)
{
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
		threads[i] = std::thread(max_block<Iterator, T>(), block_start, block_end, std::ref(results[i]));
		block_start = block_end;
	}
	max_block<Iterator, T>() (block_start, last, results[num_threads - 1]);

	for (auto& entry : threads)
		entry.join();
	return std::max_element(results.begin(), results.end());
}

int main()
{
	std::vector<int> vec = { 1, 2, 3, 4, 5 };
	int sum = parallel_max(vec.begin(), vec.end());
	std::cout << sum << "\n";
}