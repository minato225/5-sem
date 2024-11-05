#include <execution>
#include <algorithm>
#include <iostream>
#include <vector>
#include <chrono>
#include <concepts>

enum class Policy { seq, par, par_unseq };

// ������� ������ ������ ���������� : ��������������� ��� �����������.
template<class F>
auto maybe_parallel(F f, const Policy p) {
	switch (p) {
		case Policy::seq: return f(std::execution::seq);
		case Policy::par: return f(std::execution::par);
	default: return f(std::execution::par_unseq);
	}
}

// ������� ������� std::generate() � ���������� Policy.
auto policy_generate(std::vector<int>& vec, Policy p) {
	return maybe_parallel(
		[&](auto& pol)
		{
			return std::generate(pol, vec.begin(), vec.end(),
				[x = 2]()mutable
			{
				return x = pow(cos(x) * sin(x), 2);
			});
		}, p);
}

// ������� ������������.
auto generate_test(const Policy policy, int n) {
	std::vector<int>v(n);

	auto start = std::chrono::high_resolution_clock::now();
	policy_generate(v, policy);
	auto stop = std::chrono::high_resolution_clock::now();

	return std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6;
}

int main() {
	setlocale(LC_ALL, "ru");

	for (auto n : { 10000, 1000000, 1000000000 }) {
		auto time = generate_test(Policy::seq, n);
		auto par_time = generate_test(Policy::par, n);
		std::cout
			<< "����������� " << n << ": \n"
			<< "�������� �����: " << time << std::endl
			<< "������������ �����: " << par_time << std::endl
			<< "������������ ����� ��� ������� �: "
			<< (time - par_time) / par_time
			<< "���.\n\n";
	}
}