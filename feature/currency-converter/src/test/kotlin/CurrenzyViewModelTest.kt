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
    lateinit var viewModel : CurrenzyConverterViewModel
    private lateinit var currenzyRepository: TestCurrenzyRepository

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    private var workSyncManager = TestWorkSyncManager()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        currenzyRepository = TestCurrenzyRepository()

        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = CurrenzyConverterViewModel(
            currenzyRepository = currenzyRepository,
            sharedPreferences = sharedPreferences,
            workSyncManager = workSyncManager)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() {
        assert(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `state is not loading when syncing is done` () = testScope.runTest {
        workSyncManager.emit(isSyncing = false)

        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        job.join()
        assert(!viewModel.uiState.value.isLoading)
    }

    @Test
    fun `state has changed after a successful emission from repository` () = testScope.runTest {
        workSyncManager.emit(isSyncing = true)

        currenzyRepository.sendExchangeRate(
            ExchangeRates(
                baseCurrency = "",
                rates = mapOf("USD" to 1.0, "PHP" to 56.0),
                lastUpdatedDate = "08-23-2024")
        )

        workSyncManager.emit(isSyncing = false)

        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        job.join()
        assert(!viewModel.uiState.value.isLoading)
        assert(viewModel.uiState.value.indicativeExchangeRate.isNotEmpty())
    }
}