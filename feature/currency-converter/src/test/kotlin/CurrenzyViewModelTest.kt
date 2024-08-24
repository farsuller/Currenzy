import android.content.SharedPreferences
import com.currenzy.currenzyconverter.CurrenzyConverterViewModel
import com.currenzy.model.currency_converter.ExchangeRates
import com.currenzy.testing.repository.TestCurrenzyRepository
import com.currenzy.testing.worker.TestWorkSyncManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class CurrenzyViewModelTest {

    // ViewModel instance to be tested
    lateinit var viewModel: CurrenzyConverterViewModel

    // Repository for providing exchange rates in tests
    private lateinit var currenzyRepository: TestCurrenzyRepository

    // Mocked SharedPreferences
    @Mock
    lateinit var sharedPreferences: SharedPreferences

    // Test implementation of SyncManager
    private var workSyncManager = TestWorkSyncManager()

    // Dispatcher and Scope for managing coroutines in tests
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    // Set up test environment
    @Before
    fun setUp() {
        currenzyRepository = TestCurrenzyRepository()

        // Initialize mocks and setup test dispatcher
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        // Create instance of ViewModel with test dependencies
        viewModel = CurrenzyConverterViewModel(
            currenzyRepository = currenzyRepository,
            sharedPreferences = sharedPreferences,
            workSyncManager = workSyncManager
        )
    }

    // Clean up test environment
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Test to ensure that initial state is loading
    @Test
    fun `initial state is loading`() {
        assert(viewModel.uiState.value.isLoading)
    }

    // Test to ensure that state is not loading when syncing is done
    @Test
    fun `state is not loading when syncing is done`() = testScope.runTest {
        // Emit syncing false to simulate end of syncing process
        workSyncManager.emit(isSyncing = false)

        // Collect the state from the ViewModel
        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        // Wait for the job to complete
        job.join()

        // Assert that loading is false
        assert(!viewModel.uiState.value.isLoading)
    }

    // Test to ensure that the state changes after a successful emission from the repository
    @Test
    fun `state has changed after a successful emission from repository`() = testScope.runTest {
        // Simulate syncing state as true
        workSyncManager.emit(isSyncing = true)

        // Send exchange rate data to the repository
        currenzyRepository.sendExchangeRate(
            ExchangeRates(
                baseCurrency = "",
                rates = mapOf("USD" to 1.0, "PHP" to 56.0),
                lastUpdatedDate = "08-23-2024"
            )
        )

        // Simulate syncing state as false
        workSyncManager.emit(isSyncing = false)

        // Collect the state from the ViewModel
        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        // Wait for the job to complete
        job.join()

        // Assert that loading is false and indicative exchange rate data is not empty
        assert(!viewModel.uiState.value.isLoading)
        assert(viewModel.uiState.value.indicativeExchangeRate.isNotEmpty())
    }
}