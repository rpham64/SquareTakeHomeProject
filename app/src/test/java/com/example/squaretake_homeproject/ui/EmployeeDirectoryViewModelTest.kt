package com.example.squaretake_homeproject.ui

import android.util.MalformedJsonException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.squaretake_homeproject.data.EmployeeDirectoryRepository
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.model.EmployeeType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class EmployeeDirectoryViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: EmployeeDirectoryRepository
    private lateinit var ioDispatcher: CoroutineDispatcher

    private lateinit var viewModel: EmployeeDirectoryViewModel

    @Before
    fun setUp() {
        repository = mock()
        ioDispatcher = UnconfinedTestDispatcher()
        viewModel = EmployeeDirectoryViewModel(repository, ioDispatcher)
    }

    @Test
    fun fetchEmployees_success() {
        runTest {
            whenever(repository.getEmployeesList()).thenReturn(Result.success(testEmployeesList))

            viewModel.fetchEmployeesList()

            val result = viewModel.results.getOrAwaitValue()
            assert(result.isSuccess)

            val employeesList = result.getOrNull()!!
            assert(employeesList.size == 1)
            assert(employeesList == testEmployeesList)
        }
    }

    @Test
    fun fetchEmployees_empty() {
        runTest {
            whenever(repository.getEmployeesList()).thenReturn(Result.success(emptyList()))

            viewModel.fetchEmployeesList()

            val result = viewModel.results.getOrAwaitValue()
            assert(result.isSuccess)

            val employeesList = result.getOrNull()!!
            assert(employeesList.isEmpty())
        }
    }

    @Test
    fun fetchEmployees_malformedEmployeesList() {
        runTest {
            val malformedJsonException = MalformedJsonException("backend returned a malformed list")
            whenever(repository.getEmployeesList()).thenReturn(Result.failure(malformedJsonException))

            viewModel.fetchEmployeesList()

            val result = viewModel.results.getOrAwaitValue()
            assert(result.isFailure)
        }
    }

    @Test
    fun fetchEmployees_error() {
        runTest {
            val error = Throwable("error returned from backend")
            whenever(repository.getEmployeesList()).thenReturn(Result.failure(error))

            viewModel.fetchEmployeesList()

            val result = viewModel.results.getOrAwaitValue()
            assert(result.isFailure)
        }
    }

    /**
     * Taken from https://github.com/android/architecture-components-samples/blob/85587900b68a5d3a7edf95065fe2d8c768e66164/GithubBrowserSample/app/src/test-common/java/com/android/example/github/util/LiveDataTestUtil.kt#L32-L57
     */
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}

private val testEmployeesList: List<Employee> = listOf(
    Employee(
        uuid = UUID.randomUUID().toString(),
        name = "Rudolf",
        phoneNumber = "4081234567",
        email = "testemail@gmail.com",
        biography = "Hello my name is Rudolf",
        photoSmall = "small photo",
        photoLarge = "large photo",
        team = "team awesome",
        employeeType = EmployeeType.FULL_TIME
    )
)