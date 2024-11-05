#include <iostream>
#include <fstream>
#include <queue>
#include <tuple>
#include <condition_variable>
#include <thread>
#include <algorithm>
#include <chrono>

using namespace std;
using namespace chrono_literals;
ifstream in("Source.txt");
ofstream out("Result.txt");


queue<string> q;
mutex mut;
condition_variable cv;
bool finished{ false };

static void producer() {
	string line;
	while (in >> line) {
		{
			lock_guard<mutex> lk{ mut };
			q.push(line);
		}

		cv.notify_all();
	}

	{
		lock_guard<mutex> lk{ mut };
		finished = true;
	}
	cv.notify_all();
}

static void consumer() {
	while (!finished) {
		unique_lock<mutex> l{ mut };
		cv.wait(l, [] { return !q.empty() || finished; });

		string tmp = q.front();
		q.pop();
		l.unlock();

		reverse(tmp.begin(), tmp.end());
		out << tmp << "\n";
	}
}

void linnear() {
	string line;
	while (in >> line) {
		reverse(line.begin(), line.end());
		out << line << "\n";
	}
}

int main() {
	auto start = std::chrono::high_resolution_clock::now();
	thread t1{ producer };
	thread t2{ consumer };
	t1.join(); t2.join();
	auto stop = std::chrono::high_resolution_clock::now();
	cout << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6 << endl;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	start = std::chrono::high_resolution_clock::now();
	linnear();
	stop = std::chrono::high_resolution_clock::now();
	cout << std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1e6 << endl;
}